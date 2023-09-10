package com.example.camera.top

import androidx.compose.foundation.Image
import androidx.compose.material3.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.runtime.Composable
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

@Preview(showBackground =false)
@Composable
fun TopToolsPreview(){
    CameraTheme {
       TopTools()
        DiagonalLine()
    }
}

@Composable
fun TopTools(){
    Box(modifier=Modifier.fillMaxSize()){
            LazyRow(
                modifier= Modifier
                    .fillMaxWidth()
                    .align(alignment = Alignment.TopCenter
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
//                            cameraState=!cameraState
                        },
                        modifier=Modifier.size(50.dp)

                    ){
                        Image(painter= painterResource(id= R.drawable.flash0),
                            contentDescription = "flash",
                            modifier=Modifier
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
//                            cameraState=!cameraState
                        },
                        modifier=Modifier.size(50.dp)

                    ){
                        Image(painter= painterResource(id= R.drawable.flashlight0),
                            contentDescription = "flashlight",
                            modifier=Modifier
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
                        },
                        modifier=Modifier.size(50.dp)
                    ){
                        Image(painter= painterResource(id= R.drawable.video_recorder0),
                            contentDescription = "video_recorder",
                            modifier=Modifier
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
                        },
                        modifier=Modifier.size(50.dp)
                    ){
                        Image(painter= painterResource(id= R.drawable.grid1),
                            contentDescription = "GuideLine",
                            modifier=Modifier
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
                        },
                        modifier=Modifier.size(50.dp)
                    ){
                        Image(painter= painterResource(id= R.drawable.hdr0),
                            contentDescription = "Hdr",
                            modifier=Modifier
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
                        },
                        modifier=Modifier.size(50.dp)
                    ){
                        Image(painter= painterResource(id= R.drawable.tickclock0),
                            contentDescription = "TickClock",
                            modifier=Modifier
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
                        },
                        modifier=Modifier.size(50.dp)
                    ){
                        Image(painter= painterResource(id= R.drawable.beauty0),
                            contentDescription = "Beauty",
                            modifier=Modifier
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
                        },
                        modifier=Modifier.size(50.dp)
                    ){
                        Image(painter= painterResource(id= R.drawable.filter0),
                            contentDescription = "Filter",
                            modifier=Modifier
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
                        },
                        modifier=Modifier.size(50.dp)
                    ){
                        Image(painter= painterResource(id= R.drawable.ml0),
                            contentDescription = "MeachineLearning",
                            modifier=Modifier
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
                        },
                        modifier=Modifier.size(50.dp)
                    ){
                        Image(painter= painterResource(id= R.drawable.shake0),
                            contentDescription = "Shake",
                            modifier=Modifier
                                .size(50.dp)
                                .alpha(defalpha)
                        )
                    }
                    Spacer(modifier = Modifier.width(40.dp))
                }

            } // end of lazyRow
    }// end of Box
}



@Preview
@Composable
fun DrawColorRing(){
    Spacer(
        modifier = Modifier
            .fillMaxSize()
            .drawWithContent {
                // this = DrawScope
//                Canvas(modifier = Modifier.fillMaxSize()) {
                    val canvasQuadrantSize = size / 2F
                    drawRect(
                        color = Color.White,
                        size = canvasQuadrantSize
                    )
//                }
            }
    )
}


@Preview
@Composable
// 对角线参考线
fun DiagonalLine(){
    Box(modifier = Modifier.fillMaxSize()
        .drawWithContent{
            val canvasWidth = size.width
            val canvasHeight = size.height
            val defColor=Color.White
            val defWidth=6f
            val defalpha=0.2f
            drawLine(
                start = Offset(x = canvasWidth, y = 0f),
                end = Offset(x = 0f, y = canvasHeight),
                strokeWidth=defWidth,
                alpha=defalpha,
                color = defColor
            )
            drawLine(
                start = Offset(x = 0f, y = 0f),
                end = Offset(x = canvasWidth, y = canvasHeight),
                strokeWidth=defWidth,
                alpha=defalpha,
                color = defColor
            )
        }
    ) {}
    }


@Preview
@Composable
// 九宫格参考线
fun NineBoxLine(){
    Box(modifier = Modifier.fillMaxSize()
        .drawWithContent{
            val canvasWidth = size.width
            val canvasHeight = size.height
            val defColor=Color.White
            val defWidth=6f
            val defalpha=0.2f
            drawLine(
                start = Offset(x = canvasWidth/3, y = 0f),
                end = Offset(x = canvasWidth/3, y = canvasHeight),
                strokeWidth=defWidth,
                alpha=defalpha,
                color = defColor
            )
            drawLine(
                start = Offset(x = 2*canvasWidth/3, y = 0f),
                end = Offset(x = 2*canvasWidth/3, y = canvasHeight),
                strokeWidth=defWidth,
                alpha=defalpha,
                color = defColor
            )
            drawLine(
                start = Offset(x = 0f, y = canvasHeight/3),
                end = Offset(x = canvasWidth, y = canvasHeight/3),
                strokeWidth=defWidth,
                alpha=defalpha,
                color = defColor
            )
            drawLine(
                start = Offset(x = 0f, y = 2*canvasHeight/3),
                end = Offset(x = canvasWidth, y = 2*canvasHeight/3),
                strokeWidth=defWidth,
                alpha=defalpha,
                color = defColor
            )
        }
    ) {}
}


@Preview
@Composable
// 居中参考线
fun CornerLine(){
    Box(modifier=Modifier.fillMaxSize()
        .drawWithContent(){
            val canvasWidth = size.width
            val canvasHeight = size.height
            val defColor=Color.White
            val defWidth=6f
            val defalpha=0.2f
            drawLine(
                start = Offset(x = canvasWidth/3, y = canvasHeight/2),
                end = Offset(x = 2*canvasWidth/3, y = canvasHeight/2),
                strokeWidth=defWidth,
                alpha=defalpha,
                color = defColor
            )
            drawLine(
                start = Offset(x = canvasWidth/2, y = canvasHeight/3),
                end = Offset(x = canvasWidth/2, y = 2*canvasHeight/3),
                strokeWidth=defWidth,
                alpha=defalpha,
                color = defColor
            )


        }){

    }
}




