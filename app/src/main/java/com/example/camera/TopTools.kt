package com.example.camera

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.camera.ui.theme.CameraTheme

@Preview(showBackground =true)
@Composable
fun TopToolsPreview(){
    CameraTheme {
       TopTest()
    }
}

@Composable
fun TopTest(){
    Box(modifier=Modifier.fillMaxSize()){
            LazyRow(
                modifier= Modifier
                    .fillMaxWidth()
                    .align(alignment = Alignment.TopCenter),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceEvenly
            ){
                item{
                    Text(text = "hello")
                }
                item{
                    Text(text = "hello")
                }
                item{
                    Text(text = "hello")
                }
                item{
                    Text(text = "hello")
                }
                item{
                    Text(text = "hello")
                }

            }

            Row(modifier=Modifier
                .fillMaxWidth()
                .align(alignment = Alignment.BottomCenter),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween

            ){
                Text("test1")
                Text("test1")
                Text("test1")

            }

    }
}


