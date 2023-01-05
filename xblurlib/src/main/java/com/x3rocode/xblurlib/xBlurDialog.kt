package com.x3rocode.xblurlib


import android.annotation.SuppressLint
import android.app.Dialog
import android.content.Context
import android.graphics.*
import android.graphics.Paint
import android.graphics.RenderEffect
import android.graphics.Shader
import android.os.Build
import android.util.Log
import android.view.Gravity
import android.view.ViewGroup
import android.view.WindowManager
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.*
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.*
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.compose.ui.window.DialogWindowProvider
import androidx.compose.ui.window.SecureFlagPolicy
import com.x3rocode.xblurlib.capture.Capturable
import com.x3rocode.xblurlib.capture.rememberCaptureController

import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import java.io.File

@SuppressLint("FlowOperatorInvokedInComposition")
@OptIn(ExperimentalComposeUiApi::class)
@RequiresApi(Build.VERSION_CODES.TIRAMISU)
@Composable
fun BlurDialog(
    modifier: Modifier = Modifier,
    onDismiss: () -> Unit,
    size: IntOffset = IntOffset(300,100),
    blurRadius: Int = 40,
    shape: Shape = RoundedCornerShape(38.dp),
    backgroundColor: Color = Color.White,
    backgroundColorAlpha: Float = 0.4f,
    dialogDimAmount: Float? = null,
    dialogBehindBlurRadius: Int = 0,
    content: @Composable () -> Unit,
){
    val captureController = rememberCaptureController()

    var bitmap by remember { mutableStateOf(ImageBitmap(size.x, size.y)) }
    var dialogPos by remember { mutableStateOf(Offset.Zero) }
    val shader = RuntimeShader(GET_BEHIND_CANVAS)
    val colorShader = RuntimeShader(SET_COLOR_FILTER)
    val paint = Paint().apply {
        this.shader = shader
    }

    LaunchedEffect(Unit) {
        do{
            captureController.capture()
            delay(30)
        }while (true)
    }

    Box(modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center){
        Capturable(
            controller = captureController,
            onCaptured = { b, t ->
                b?.let {
                    if(it.asAndroidBitmap().sameAs(bitmap?.asAndroidBitmap()).not()){
                        bitmap= it

                    }
                }
            }
        ) {
            Canvas(modifier = modifier
                .size(width = size.x.dp, height = size.y.dp)
                .clip(shape)
                .graphicsLayer {
                    shader.setColorUniform("returnColor", Color.Transparent.toArgb())

                    renderEffect = RenderEffect
                        .createRuntimeShaderEffect(shader, "background")
                        .asComposeRenderEffect()
                }
                .drawWithCache {
                    onDrawWithContent { drawContent() }
                }
                .clipToBounds()
                , onDraw = {
                    drawIntoCanvas {
                        it.nativeCanvas.drawPaint(paint)
                    }
                }
            )
        }
    }
    Dialog(
        onDismissRequest = onDismiss,
        properties = DialogProperties(
            decorFitsSystemWindows = false,
            dismissOnBackPress = true,
            dismissOnClickOutside = true,
            usePlatformDefaultWidth = false,
            securePolicy = SecureFlagPolicy.SecureOff)) {

        val dialogWindowProvider = LocalView.current.parent as DialogWindowProvider
        val wdw = dialogWindowProvider.window

        wdw.setGravity(Gravity.CENTER)
        if(dialogDimAmount == null){
            wdw.clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND)
        }else{
            wdw.setDimAmount(dialogDimAmount)
        }
        wdw.setFlags(WindowManager.LayoutParams.FLAG_BLUR_BEHIND, WindowManager.LayoutParams.FLAG_BLUR_BEHIND)
        wdw.attributes.blurBehindRadius = dialogBehindBlurRadius

        Box( modifier = Modifier
            .clip(shape)
            .size(width = size.x.dp, height = size.y.dp)){
            Image(bitmap = bitmap,
                contentDescription = null,
                contentScale = ContentScale.Crop,
                filterQuality = FilterQuality.Low,
                modifier = Modifier
                    .graphicsLayer {
                        colorShader.setColorUniform("filterColor", backgroundColor.toArgb())
                        colorShader.setFloatUniform("alpha", backgroundColorAlpha)
                        var colorEffct = RenderEffect
                            .createRuntimeShaderEffect(colorShader, "background")
                        var blurEffct = RenderEffect
                            .createBlurEffect(
                                blurRadius.toFloat(),
                                blurRadius.toFloat(),
                                Shader.TileMode.REPEAT
                            )
                        var chainEffct = RenderEffect.createChainEffect(blurEffct, colorEffct)

                        renderEffect = chainEffct.asComposeRenderEffect()
                    }
                    .clipToBounds(),
            )
            content()
        }
    }
}