package com.example.camera.top

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview


object defDraw {
//     const val  Color =Color.White
     const val Width = 6f
     const val Alpha = 0.4f
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
            drawLine(
                start = Offset(x = canvasWidth, y = 0f),
                end = Offset(x = 0f, y = canvasHeight),
                strokeWidth=defDraw.Width,
                alpha=defDraw.Alpha,
                color = defColor
            )
            drawLine(
                start = Offset(x = 0f, y = 0f),
                end = Offset(x = canvasWidth, y = canvasHeight),
                strokeWidth=defDraw.Width,
                alpha=defDraw.Alpha,
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
            val defColor= Color.White
            drawLine(
                start = Offset(x = canvasWidth/3, y = 0f),
                end = Offset(x = canvasWidth/3, y = canvasHeight),
                strokeWidth=defDraw.Width,
                alpha=defDraw.Alpha,
                color = defColor
            )
            drawLine(
                start = Offset(x = 2*canvasWidth/3, y = 0f),
                end = Offset(x = 2*canvasWidth/3, y = canvasHeight),
                strokeWidth=defDraw.Width,
                alpha=defDraw.Alpha,
                color = defColor
            )
            drawLine(
                start = Offset(x = 0f, y = canvasHeight/3),
                end = Offset(x = canvasWidth, y = canvasHeight/3),
                strokeWidth=defDraw.Width,
                alpha=defDraw.Alpha,
                color = defColor
            )
            drawLine(
                start = Offset(x = 0f, y = 2*canvasHeight/3),
                end = Offset(x = canvasWidth, y = 2*canvasHeight/3),
                strokeWidth=defDraw.Width,
                alpha=defDraw.Alpha,
                color = defColor
            )
        }
    ) {}
}

@Preview
@Composable
// 居中参考线
fun CornerLine(){
    Box(modifier= Modifier.fillMaxSize()
        .drawWithContent(){
            val canvasWidth = size.width
            val canvasHeight = size.height
            val defColor= Color.White
            drawLine(
                start = Offset(x = canvasWidth/3, y = canvasHeight/2),
                end = Offset(x = 2*canvasWidth/3, y = canvasHeight/2),
                strokeWidth=defDraw.Width,
                alpha=defDraw.Alpha,
                color = defColor
            )
            drawLine(
                start = Offset(x = canvasWidth/2, y = canvasHeight/3),
                end = Offset(x = canvasWidth/2, y = 2*canvasHeight/3),
                strokeWidth=defDraw.Width,
                alpha=defDraw.Alpha,
                color = defColor
            )
        }){
    }
}
