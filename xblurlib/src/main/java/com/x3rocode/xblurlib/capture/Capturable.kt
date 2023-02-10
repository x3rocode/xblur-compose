package com.x3rocode.xblurlib.capture

import android.app.Activity
import android.content.Context
import android.content.ContextWrapper
import android.graphics.Bitmap
import android.graphics.Rect
import android.os.Build
import android.os.Environment
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.PixelCopy
import android.view.View
import android.view.Window
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asAndroidBitmap
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.findViewTreeCompositionContext
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.view.allViews
import androidx.core.view.doOnAttach
import androidx.core.view.doOnLayout
import androidx.core.view.drawToBitmap
import kotlinx.coroutines.flow.*
import java.io.File
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

@Composable
fun Capturable(
    controller: CaptureController,
    modifier: Modifier = Modifier,
    onCaptured: (ImageBitmap?, Throwable?) -> Unit,
    content: @Composable () -> Unit
) {
    val context = LocalContext.current
    AndroidView(
        factory = { ComposeView(it).applyCapturability(controller, onCaptured, content, context) },
        modifier = modifier
    )

}

private inline fun ComposeView.applyCapturability(
    controller: CaptureController,
    noinline onCaptured: (ImageBitmap?, Throwable?) -> Unit,
    crossinline content: @Composable () -> Unit,
    context: Context
) = apply {
    setContent {
        content()
        LaunchedEffect(controller, onCaptured) {

            controller.captureRequests
                .map { config -> drawToBitmapPostLaidOut(context, config) }
                .onEach { bitmap -> onCaptured(bitmap.asImageBitmap(), null) }
                .catch { error -> onCaptured(null, error) }
                .launchIn(this)
        }
    }
}

suspend fun View.drawToBitmapPostLaidOut(context: Context, config: Bitmap.Config): Bitmap {

    return suspendCoroutine { continuation ->

        doOnLayout { view ->


            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                val window = context.findActivity().window

                drawBitmapWithPixelCopy(
                    view = view,
                    window = window,
                    config = config,
                    onDrawn = { bitmap -> continuation.resume(bitmap) },
                    onError = { error -> continuation.resumeWithException(error) }
                )
            }
        }
    }
}



@RequiresApi(Build.VERSION_CODES.O)
fun drawBitmapWithPixelCopy(
    view: View,
    window: Window,
    config: Bitmap.Config,
    onDrawn: (Bitmap) -> Unit,
    onError: (Throwable) -> Unit
) {
    val width = view.width
    val height = view.height

    val bitmap = Bitmap.createBitmap(width, height, config)

    val (x, y) = IntArray(2).apply { view.getLocationInWindow(this) }
    val rect = Rect(x, y, x + width, y + height)
    PixelCopy.request(
        window,
        rect,
        bitmap,
        { copyResult ->
            if (copyResult == PixelCopy.SUCCESS) {
                onDrawn(bitmap)

            } else {
                onError(RuntimeException("Failed to draw bitmap"))
            }
        },
        Handler(Looper.getMainLooper())
    )
}


internal fun Context.findActivity(): Activity {
    var context = this
    while (context is ContextWrapper) {
        if (context is Activity) return context
        context = context.baseContext
    }
    throw IllegalStateException("Unable to retrieve Activity from the current context")
}


fun File.writeBitmap(bitmap: Bitmap, format: Bitmap.CompressFormat, quality: Int) {
    outputStream().use { out ->
        bitmap.compress(format, quality, out)
        out.flush()
    }
}