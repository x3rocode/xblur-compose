package com.x3rocode.xblur

import android.graphics.RuntimeShader
import android.graphics.Shader
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.core.withInfiniteAnimationFrameMillis
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.draw.*
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.*
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.graphics.Color.Companion.Yellow
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.LifecycleCoroutineScope
import androidx.navigation.compose.rememberNavController
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.rememberLottieComposition
import com.x3rocode.xblur.ui.theme.XblurTheme
import com.x3rocode.xblurlib.BlurDialog

import com.x3rocode.xblurlib.xBlur
import org.intellij.lang.annotations.Language

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            XblurTheme {
                // A surface container using the 'background' color from the theme
                TestBlurBottomNav()
                val scaffoldState = rememberScaffoldState()
                Surface() {
//                    TestBlurDialog()


//                    BlurTopAppBar(
//                        blurRadius = 50,
//                        size = IntOffset(280,160),
//                        shape = RoundedCornerShape(30.dp),
//                        backgroundColor = Color.Blue,
//                        backgroundColorAlpha = 0.3f,
//                        isRealtime = true,
//                        dialogDimAmount = 0.3f,
//                    ) {
//                    }
                }


            }
        }
    }
}



