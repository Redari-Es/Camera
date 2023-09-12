package com.example.camera.top

import androidx.compose.foundation.Image
import androidx.compose.material3.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.camera.R
import com.example.camera.ui.theme.CameraTheme

//var lineState by remember {mutableStateOf(false)}
@Preview(showBackground =false)
@Composable
fun TopToolsPreview(){
    CameraTheme {
        TopTools()
//        DiagonalLine()
    }
}

@Composable
fun TopTools(){
    // Guids
    var showLines by remember {mutableStateOf(R.drawable.grid0)}
    when(showLines){
        0->showLines=R.drawable.grid0
        R.drawable.grid0->Unit
        R.drawable.grid1->NineBoxLine()
        R.drawable.grid2->DiagonalLine()
        R.drawable.grid3->CornerLine()
    }
    // flash
    var showflash by remember {mutableStateOf(R.drawable.flash0)}
    when(showflash){
        0->showflash=R.drawable.flash0
        R.drawable.flash0->Unit
        R.drawable.flash1->Unit
    }
    // flashlight
    var showflashlight by remember {mutableStateOf(R.drawable.flashlight0)}
    when(showflashlight){
        0->showflashlight=R.drawable.flashlight0
        R.drawable.flash0->Unit
        R.drawable.flashlight1->Unit
        R.drawable.flashlight2->Unit
    }
    // video recorder
    var showvideo by remember {mutableStateOf(R.drawable.video_recorder0)}
    when(showvideo){
        0->showvideo=R.drawable.video_recorder0
        R.drawable.video_recorder0->Unit
        R.drawable.video_recorder1->Unit
    }
    // HDR
    var showhdr by remember {mutableStateOf(R.drawable.hdr0)}
    when(showhdr){
        0->showhdr=R.drawable.hdr0
        R.drawable.hdr0->Unit
        R.drawable.hdr1->Unit
    }
    // tickclock
    var showtickclock by remember {mutableStateOf(R.drawable.tickclock0)}
    when(showtickclock){
        0->showtickclock=R.drawable.tickclock0
        R.drawable.tickclock0->Unit
        R.drawable.tickclock1->Unit
    }
    // beauty
    var showbeauty by remember {mutableStateOf(R.drawable.beauty0)}
    when(showbeauty){
        0->showbeauty=R.drawable.beauty0
        R.drawable.beauty0->Unit
        R.drawable.beauty1->Unit
    }
    // filter
    var showfilter by remember {mutableStateOf(R.drawable.filter0)}
    when(showfilter){
        0->showfilter=R.drawable.filter0
        R.drawable.filter0->Unit
        R.drawable.filter1->Unit
    }
    // ml
    var showml by remember {mutableStateOf(R.drawable.ml0)}
    when(showml){
        0->showml=R.drawable.ml0
        R.drawable.ml0->Unit
        R.drawable.ml1->Unit
    }
    // shake
    var showshake by remember {mutableStateOf(R.drawable.shake0)}
    when(showshake){
        0->showshake=R.drawable.shake0
        R.drawable.shake0->Unit
        R.drawable.shake1->Unit
    }
    Box(modifier=Modifier.fillMaxSize()){
            LazyRow(
                modifier= Modifier
                    .fillMaxWidth()
                    .align(
                        alignment = Alignment.TopCenter
                    )
                    .padding(10.dp)
                ,
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ){
                val defalpha=0.8f
                val defwidth=1f
                item{
                    // 手电
                    IconButton(
                        onClick={
                            when(showflash){
                                R.drawable.flash0->showflash=R.drawable.flash1
                                R.drawable.flash1->showflash=0
                            }
                        },
                        modifier=Modifier.size(50.dp)

                    ){
                        Image(painter= painterResource(id= showflash),
                            contentDescription = "flash",
                            modifier= Modifier
                                .size(50.dp)
                                .alpha(defalpha)
                        )
                    }
                    Spacer(modifier = Modifier.width(40.dp))
                }
                // 闪光灯
                item{
                    IconButton(
                        onClick={
                                when(showflashlight){
                                    R.drawable.flashlight0->showflashlight=R.drawable.flashlight1
                                    R.drawable.flashlight1->showflashlight=R.drawable.flashlight2
                                    R.drawable.flashlight2->showflashlight=0
                                }
                        },
                        modifier=Modifier.size(50.dp)

                    ){
                        Image(painter= painterResource(id= showflashlight),
                            contentDescription = "flashlight",
                            modifier= Modifier
                                .size(50.dp)
                                .alpha(defalpha)
                        )
                    }
                    Spacer(modifier = Modifier.width(40.dp))
                }
                // 视频录制
                item{
                    IconButton(
                        onClick={
                                when(showvideo){
                                    R.drawable.video_recorder0->showvideo=R.drawable.video_recorder1
                                    R.drawable.video_recorder1->showvideo=0
                                }
                        },
                        modifier=Modifier.size(50.dp)
                    ){
                        Image(painter= painterResource(id= showvideo),
                            contentDescription = "video_recorder",
                            modifier= Modifier
                                .size(40.dp)
                                .alpha(defalpha)
                        )
                    }
                    Spacer(modifier = Modifier.width(40.dp))
                }
                // 参考线
                item{
                    IconButton(
                        onClick={
                            when(showLines){
                               R.drawable.grid0->showLines=R.drawable.grid1
                                R.drawable.grid1->showLines=R.drawable.grid2
                                R.drawable.grid2->showLines=R.drawable.grid3
                                R.drawable.grid3->showLines=0

                            }
                        },
                        modifier=Modifier.size(50.dp)
                    ){
                        Image(painter= painterResource(id= showLines),
                            contentDescription = "GuideLine",
                            modifier= Modifier
                                .size(40.dp)
                                .alpha(defalpha)
                        )
                    }
                    Spacer(modifier = Modifier.width(40.dp))
                }
                // Hdr
                item{
                    IconButton(
                        onClick={
                            when(showhdr){
                                R.drawable.hdr0->showhdr=R.drawable.hdr1
                                R.drawable.hdr1->showhdr=0
                            }
                        },
                        modifier=Modifier.size(50.dp)
                    ){
                        Image(painter= painterResource(id=showhdr),
                            contentDescription = "Hdr",
                            modifier= Modifier
                                .size(50.dp)
                                .alpha(defalpha)
                        )
                    }
                    Spacer(modifier = Modifier.width(40.dp))
                }
                // 延时
                item{
                    IconButton(
                        onClick={
                            when(showtickclock){
                                R.drawable.tickclock0->showtickclock=R.drawable.tickclock1
                                R.drawable.tickclock1->showtickclock=0
                            }
                        },
                        modifier=Modifier.size(50.dp)
                    ){
                        Image(painter= painterResource(id= showtickclock),
                            contentDescription = "TickClock",
                            modifier= Modifier
                                .size(50.dp)
                                .alpha(defalpha)
                        )
                    }
                    Spacer(modifier = Modifier.width(40.dp))
                }
                // 美颜
                item{
                    IconButton(
                        onClick={
                            when(showbeauty){
                                R.drawable.beauty0->showbeauty=R.drawable.beauty1
                                R.drawable.beauty1->showbeauty=0
                            }
                        },
                        modifier=Modifier.size(50.dp)
                    ){
                        Image(painter= painterResource(id= showbeauty),
                            contentDescription = "Beauty",
                            modifier= Modifier
                                .size(40.dp)
                                .alpha(defalpha)
                        )
                    }
                    Spacer(modifier = Modifier.width(40.dp))
                }
                // 滤镜
                item{
                    IconButton(
                        onClick={
                            when(showfilter){
                                R.drawable.filter0->showfilter=R.drawable.filter1
                                R.drawable.filter1->showfilter=0
                            }
                        },
                        modifier=Modifier.size(50.dp)
                    ){
                        Image(painter= painterResource(id= showfilter),
                            contentDescription = "Filter",
                            modifier= Modifier
                                .size(50.dp)
                                .alpha(defalpha)
                        )
                           }
                    Spacer(modifier = Modifier.width(40.dp))
                }
                // 机器算法集成
                item{
                    IconButton(
                        onClick={
                            when(showml){
                                R.drawable.ml0->showml=R.drawable.ml1
                                R.drawable.ml1->showml=0
                            }
                        },
                        modifier=Modifier.size(50.dp)
                    ){
                        Image(painter= painterResource(id= showml),
                            contentDescription = "MeachineLearning",
                            modifier= Modifier
                                .size(40.dp)
                                .alpha(defalpha)
                        )
                    }
                    Spacer(modifier = Modifier.width(40.dp))
                }
                // 防抖
                item{
                    IconButton(
                        onClick={
                            when(showshake){
                                R.drawable.shake0->showshake=R.drawable.shake1
                                R.drawable.shake1->showshake=0
                            }
                        },
                        modifier=Modifier.size(50.dp)
                    ){
                        Image(painter= painterResource(id= showshake),
                            contentDescription = "Shake",
                            modifier= Modifier
                                .size(50.dp)
                                .alpha(defalpha)
                        )
                    }
                    Spacer(modifier = Modifier.width(40.dp))
                }

            } // end of lazyRow
    }// end of Box
}
