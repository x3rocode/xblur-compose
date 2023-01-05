package com.x3rocode.xblurlib

import android.content.Context
import android.graphics.Bitmap
import android.graphics.PixelFormat
import android.view.Gravity
import android.view.View
import android.view.WindowManager
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.material.Checkbox
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.platform.AbstractComposeView
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewTreeLifecycleOwner
import androidx.lifecycle.ViewTreeViewModelStoreOwner
import androidx.savedstate.findViewTreeSavedStateRegistryOwner
import androidx.savedstate.setViewTreeSavedStateRegistryOwner
import java.util.*

class BlurComposeView(
    composeView: View
) :  AbstractComposeView(composeView.context){
    private val windowManager = context.getSystemService(Context.WINDOW_SERVICE) as WindowManager
    private var params: WindowManager.LayoutParams =
        WindowManager.LayoutParams().apply {
            gravity = Gravity.FILL
            type = WindowManager.LayoutParams.TYPE_APPLICATION_PANEL
            token = composeView.applicationWindowToken

            format = PixelFormat.TRANSLUCENT
            flags = flags or WindowManager.LayoutParams.FLAG_LAYOUT_ATTACHED_IN_DECOR

            width = WindowManager.LayoutParams.WRAP_CONTENT
            height = WindowManager.LayoutParams.WRAP_CONTENT
        }

    private var viewShowing = false

    init {
        ViewTreeLifecycleOwner.set(this, ViewTreeLifecycleOwner.get(composeView))
        ViewTreeViewModelStoreOwner.set(this, ViewTreeViewModelStoreOwner.get(composeView))
        setViewTreeSavedStateRegistryOwner(composeView.findViewTreeSavedStateRegistryOwner())

    }

    private var content: @Composable () -> Unit by mutableStateOf({})
    override var shouldCreateCompositionOnAttachedToWindow: Boolean = false
        private set

    @Composable
    override fun Content() {
        content()
    }

    fun setCustomContent(parent: CompositionContext? = null, content: @Composable () -> Unit) {
        parent?.let {
            setParentCompositionContext(it)
        }
        this.content = content
        shouldCreateCompositionOnAttachedToWindow = true
    }

    fun show() {
        if (viewShowing) dismiss()
        windowManager.addView(this, params)
        params.flags = params.flags or WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE
        windowManager.updateViewLayout(this, params)
        viewShowing = true
    }

    fun dismiss() {
        if (!viewShowing) return
        disposeComposition()
        windowManager.removeViewImmediate(this)
        viewShowing = false
    }

    fun dispose() {
        dismiss()
        setViewTreeSavedStateRegistryOwner(null)
        ViewTreeLifecycleOwner.set(this, null)
        ViewTreeViewModelStoreOwner.set(this, null)
    }
}


@Composable
fun MyComposableFun(textValue: String, content: @Composable (()->Unit) -> Unit) {
    val view = LocalView.current
    val id = rememberSaveable { UUID.randomUUID() }
    var checkedState by rememberSaveable { mutableStateOf(false) }
    var myText by rememberSaveable (textValue){ mutableStateOf(textValue) }

    val parentComposition = rememberCompositionContext()
    val myComposeView = remember {
        BlurComposeView(view).apply {
            setCustomContent(parentComposition) {
                content(::dismiss)
            }
        }
    }

    Row (verticalAlignment = Alignment.CenterVertically) {
        Checkbox(
            checked = checkedState,
            onCheckedChange = {
                myText = if (it) "Internal Change" else "Internal Change Again"
                checkedState = it
            }
        )
        Text("Title: $myText")
    }

    DisposableEffect(key1 = myComposeView) {
        myComposeView.show()
        onDispose {
            myComposeView.dispose()
        }
    }
}
@Composable
fun BlurBox(content: @Composable () -> Unit, blurBitmap: Bitmap, backgroundColor: Color, buttonClicked: (() -> Unit)? = null) {
    Box(){
        Image(bitmap = blurBitmap.asImageBitmap(), contentDescription = null,
            modifier = Modifier
            .blur(60.dp)
            , colorFilter = ColorFilter.tint(backgroundColor, BlendMode.Overlay))
        content()
    }


}