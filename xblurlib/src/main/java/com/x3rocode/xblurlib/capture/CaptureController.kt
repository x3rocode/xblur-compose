package com.x3rocode.xblurlib.capture

import android.graphics.Bitmap
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import kotlinx.coroutines.flow.*



class CaptureController internal constructor() {

    private val _captureRequests = MutableSharedFlow<Bitmap.Config>(replay = 1)
    internal val captureRequests = _captureRequests.asSharedFlow()
    fun capture() {
        _captureRequests.tryEmit(Bitmap.Config.ARGB_8888)
    }
}

@Composable
fun rememberCaptureController(): CaptureController {
    return remember { CaptureController() }
}


