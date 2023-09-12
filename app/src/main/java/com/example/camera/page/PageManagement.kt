package com.example.camera.page

import androidx.compose.animation.Crossfade
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import com.example.camera.CameraXDemo
import com.example.camera.MyCameraX
import com.example.camera.top.TopTools

// PageStateManager
@Composable
fun PageStates(page:String):String{
    var currentPage by remember { mutableStateOf(page) }
    var nextPage by remember {mutableStateOf("")}
    Crossfade(targetState=currentPage){ screen->
        when(screen){
            "CameraXDemo"-> CameraXDemo()
            "LoadingPage"->nextPage=LoadingPage(true)
            "MyCameraX"-> MyCameraX()
            "TopTools"->TopTools()
        }
    }
    return nextPage
}