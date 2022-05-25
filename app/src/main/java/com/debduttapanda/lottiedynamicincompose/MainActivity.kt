package com.debduttapanda.lottiedynamicincompose

import android.graphics.PointF
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import com.airbnb.lottie.Lottie
import com.airbnb.lottie.LottieProperty
import com.airbnb.lottie.compose.*
import com.debduttapanda.lottiedynamicincompose.ui.theme.LottieDynamicInComposeTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            LottieDynamicInComposeTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    val point = remember { PointF() }
                    Column(
                        modifier = Modifier.padding(24.dp)
                    ){
                        var color by remember { mutableStateOf(Color(0xff63D4D7)) }
                        var offsetValue by remember { mutableStateOf(0f) }
                        var blurValue by remember { mutableStateOf(0f) }
                        val blurRadius = with(LocalDensity.current) { (blurValue*50).dp.toPx() }
                        val dynamicProperties = rememberLottieDynamicProperties(
                            rememberLottieDynamicProperty(
                                property = LottieProperty.STROKE_COLOR,
                                value = color.toArgb(),
                                keyPath = arrayOf(
                                    "LEG Back",
                                    "Group 1",
                                    "Stroke 1",
                                )
                            ),
                            rememberLottieDynamicProperty(
                                property = LottieProperty.BLUR_RADIUS,
                                value = blurRadius,
                                keyPath = arrayOf(
                                    "LEG Back",
                                    "Group 1",
                                    "Stroke 1",
                                )
                            ),
                            rememberLottieDynamicProperty(
                                property = LottieProperty.TRANSFORM_POSITION,
                                "HEAD Outlines",
                                "Group 1",
                            ){frameInfo ->
                                point.set(frameInfo.startValue.x+offsetValue*500f,frameInfo.startValue.y)
                                point
                            }
                        )
                        val composition by rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.walking))
                        val progress by animateLottieCompositionAsState(composition)
                        LottieAnimation(
                            composition,
                            iterations = Int.MAX_VALUE,
                            dynamicProperties = dynamicProperties,
                            modifier = Modifier
                                .fillMaxSize()
                                .weight(1f)
                        )
                        Slider(value = blurValue, onValueChange = {
                            blurValue = it
                        })
                        Slider(value = offsetValue, onValueChange = {
                            offsetValue = it
                        })
                        Row(){
                            Button(onClick = { color = Color.Red},colors = ButtonDefaults.buttonColors(backgroundColor = Color.Red),modifier = Modifier
                                .fillMaxWidth()
                                .weight(1f)) {}
                            Button(onClick = { color = Color.Blue},colors = ButtonDefaults.buttonColors(backgroundColor = Color.Blue),modifier = Modifier
                                .fillMaxWidth()
                                .weight(1f)) {}
                            Button(onClick = { color = Color.Green},colors = ButtonDefaults.buttonColors(backgroundColor = Color.Green),modifier = Modifier
                                .fillMaxWidth()
                                .weight(1f)) {}
                        }
                    }
                }
            }
        }
    }

}



