package com.example.camera.page

import androidx.compose.animation.Animatable
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideInVertically
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.sp
import com.example.camera.util.LogUtil

//@Preview(showBackground=true)
@Composable
fun LoadingPage(getState:Boolean) :String{
    var state by remember { mutableStateOf(getState) }
    LogUtil.d("Loading", "state")
    var ok by remember { mutableStateOf(false) }
    val color01 = Color(0x3c, 0x91, 0xfa)
    val color02 = Color(0xcf, 0x2f, 0xfa)
    val color1 = remember { Animatable(Color(0x00, 0x00, 0x00, 0)) }
    val color2 = remember { Animatable(Color(0x00, 0x00, 0x00, 0)) }
    LogUtil.d("color", "init color")
    var change by remember {mutableStateOf(false)}
    change=state
    if(change){
       LaunchedEffect(state) {
           color1.animateTo(color01)
           color2.animateTo(color02)
           LogUtil.d("blender1", "state")
           change=false
           state = false
       }
        if(!state){
            LaunchedEffect(ok) {
                color1.animateTo(color02)
                color2.animateTo(color01)
                LogUtil.d("blender2", "ok")
                ok=false
            }
        }
        change=false
    }

    AnimatedVisibility(
        visible = state,
        enter = fadeIn(
            animationSpec = tween(
                durationMillis = 3000,
                delayMillis = 200,
                easing = LinearOutSlowInEasing
            ),
        ).apply {
           LogUtil.d("blender","Enter")
            ok=true
            change=ok
            LogUtil.d("Status","Next is Exit")
        },
        exit = fadeOut(
            animationSpec = tween(
                durationMillis = 3000,
                delayMillis = 200,
                easing = LinearOutSlowInEasing
            ),
        ).apply{
            ok=false
            LogUtil.d("blender","Exit")
            LogUtil.d("Status","Next is None")
        }
    ) {
        Box(modifier = Modifier.fillMaxSize()) {
//
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.Center),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Text("怪物", color = color1.value, fontSize = 80.sp, fontWeight = FontWeight.Bold)
                Text(
                    "卡美啦",
                    color = color2.value,
                    fontSize = 100.sp,
                    fontWeight = FontWeight.Bold
                )
            }

        }
    }
    return "MyCameraX"
    }



