package com.example.camera.page

import android.net.Uri
import android.provider.CalendarContract
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.IconButton
import androidx.compose.material3.ListItemDefaults.contentColor
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.contentColorFor
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.*
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.rememberImagePainter
import com.example.camera.R
var init = 0
@Preview
@Composable
fun Album(imageUri: Uri?,click:Boolean){
    var back by remember {mutableStateOf(true)}
    if(init==0){
       back=false
        init=1
    }
    if (back){
   Box(modifier= Modifier
       .fillMaxSize()
       // 设置背景为黑底
       .background(Color(0x00, 0x00, 0x00)),
   ) {
       Surface(
           shape = RectangleShape,
//           shape = RoundedCornerShape(30.dp),
//           tonalElevation = 20.dp,
           modifier = Modifier
               .fillMaxWidth()
               .height(100.dp),
           color = Color(0x65, 0x43, 0xa0),
       ) {
           Row(
               modifier = Modifier
                   .fillMaxWidth()
                   .align(alignment = Alignment.TopStart)
                   .padding(20.dp),
               verticalAlignment = Alignment.CenterVertically,
               horizontalArrangement = Arrangement.SpaceBetween
           ) {
               // 1 back
               IconButton(onClick = {
                                    back=!back
                                    },
               modifier=Modifier.size(50.dp),
//                   .align(Alignment.TopStart),
               ){
                   Image(painter= painterResource(id=R.drawable.back),
                       contentDescription = null,
                       modifier=Modifier
                           .size(60.dp)
//                           .align(Alignment.TopStart),
                   )
               }
               // 2 back
               IconButton(onClick = {
               },
                   modifier=Modifier.size(50.dp),
               ){
                   Image(painter= painterResource(id=R.drawable.info),
                       contentDescription = null,
                       modifier=Modifier
                           .size(60.dp)
                   )
               }
               // 3 back
               IconButton(onClick = {
               },
                   modifier=Modifier.size(50.dp),
               ){
                   Image(painter= painterResource(id=R.drawable.album),
                       contentDescription = null,
                       modifier=Modifier
                           .size(60.dp)
                   )
               }

           }
       }
       imageUri.let{
           if (it==null){
               Image(
//                   painter = rememberImagePainter(data = it),
           painter = painterResource(id = R.drawable.album),
                   contentDescription = "",
                   modifier = Modifier
                       .fillMaxSize()
                       .align(alignment = Alignment.Center)
                       .padding(10.dp)
               )
           }else{
               Image(
                   painter = rememberImagePainter(data = it),
//           painter = painterResource(id = R.drawable.album),
                   contentDescription = "",
                   modifier = Modifier
                       .fillMaxSize()
                       .align(alignment = Alignment.Center)
                       .padding(10.dp)
               )
           }

       }
       Surface(
           shape = RectangleShape,
//           shape = RoundedCornerShape(30.dp),
//           tonalElevation = 20.dp,
           modifier = Modifier
               .fillMaxWidth()
               .height(140.dp)
               .align(alignment = Alignment.BottomCenter),
//               .padding(10.dp),
           color = Color(0x65, 0x43, 0xa0),
       ) {
           LazyRow(modifier= Modifier
               .fillMaxWidth()
               .height(30.dp)
               .padding(20.dp)
               ,
               verticalAlignment = Alignment.Top,
               horizontalArrangement = Arrangement.Center
               ){
               for(i in 1..10){
                   item{
                       Text("$i",color=Color.White)
                       Spacer(modifier = Modifier.width(20.dp))                   }
               }
           }
       Row(
               modifier = Modifier
                   .fillMaxWidth()
                   .padding(20.dp)
                   .align(
                       alignment = Alignment.BottomStart
                   ),
               verticalAlignment = Alignment.Bottom,
               horizontalArrangement = Arrangement.SpaceAround
           ) {
               // 1
               IconButton(onClick={},modifier=Modifier
//                   .align(Alignment.BottomStart),
               ) {
                   Image(
                       painter = painterResource(id = R.drawable.share),
                       contentDescription = "share",
                       modifier = Modifier
                           .size(40.dp)
                   )
               }
               // 2
               IconButton(onClick={},modifier=Modifier
//                   .align(Alignment.BottomStart),
               ) {
                   Image(
                       painter = painterResource(id = R.drawable.love0),
                       contentDescription = "love",
                       modifier = Modifier
                           .size(40.dp)
                   )
               }
               // 3
               IconButton(onClick={},modifier=Modifier
//                   .align(Alignment.BottomStart),
               ) {
                   Image(
                       painter = painterResource(id = R.drawable.edit),
                       contentDescription = "edit",
                       modifier = Modifier
                           .size(40.dp)
                   )
               }
                   // 4
                   IconButton(onClick={},modifier=Modifier
//                   .align(Alignment.BottomStart),
                   ) {
                       Image(
                           painter = painterResource(id = R.drawable.delete),
                           contentDescription = "delete",
                           modifier = Modifier
                               .size(40.dp)
                       )
                   }
                       //5
                       IconButton(onClick={},modifier=Modifier
//                   .align(Alignment.BottomStart),
                       ) {
                           Image(
                               painter = painterResource(id = R.drawable.more),
                               contentDescription = "more",
                               modifier = Modifier
                                   .size(40.dp)
                           )
                       }
               }
           }
       }
   }
}


