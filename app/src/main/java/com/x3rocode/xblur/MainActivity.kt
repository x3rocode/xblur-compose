package com.x3rocode.xblur

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.rememberLottieComposition
import com.x3rocode.xblur.ui.theme.XblurTheme
import com.x3rocode.xblurlib.BlurDialog

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            XblurTheme {
                // A surface container using the 'background' color from the theme
                Surface() {
                    TestLottieBackground()
                    Content()
                }
            }
        }
    }
}

@Composable
fun Content(){
    var openDialog by remember { mutableStateOf(false)  }

    Box(modifier = Modifier
        .fillMaxSize()){
        Button(onClick = { openDialog = true },
            modifier = Modifier
                .align(Alignment.Center)) {
            Text(text = "Open")
        }
        if(openDialog){
            //here is blur dialog!!!!!!!!!!!!!!!!!
            BlurDialog(
                content = { DialogContent { openDialog = false } },
                blurRadius = 250,
                onDismiss = { openDialog = false },
                size = IntOffset(280,160),
                shape = RoundedCornerShape(30.dp),
                backgroundColor = Color.White,
                backgroundColorAlpha = 0.4f,
                dialogDimAmount = 0.3f,
            )
        }
    }
}


@Composable
fun TestLottieBackground(){
    val composition by rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.test_lottie_bg))
    LottieAnimation(
        composition,
        modifier = Modifier.rotate(90f).fillMaxSize().scale(2.1f),
        iterations = LottieConstants.IterateForever
    )
}

@Composable
fun DialogContent(closeDialog: () -> Unit){
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .padding(top = 15.dp)
    ) {
        Text(text = "Blur Dialog",fontSize = 20.sp, color = Color.Black,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.align(Alignment.CenterHorizontally)
                .padding(bottom = 10.dp))
        Text(text = "I think this is so cool!",fontSize = 16.sp, color = Color.Black,
            modifier = Modifier.align(Alignment.CenterHorizontally)
                .padding(bottom = 20.dp))
        Divider(thickness = 0.5.dp, color = Color.DarkGray, modifier = Modifier.alpha(0.3f))
        TextButton(onClick = closeDialog,
            modifier = Modifier.align(Alignment.CenterHorizontally).padding(top = 5.dp)){
            Text(text = "Close",fontSize = 20.sp, color = Color.Red,fontWeight = FontWeight.Bold,)
        }
    }

}
