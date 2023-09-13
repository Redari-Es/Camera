package com.example.camera.top

import android.content.ContentValues
import androidx.camera.core.CameraSelector
import androidx.camera.core.DisplayOrientedMeteringPointFactory
import androidx.camera.core.FocusMeteringAction
import androidx.camera.core.ImageAnalysis
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.camera.view.PreviewView
import androidx.compose.foundation.Image
import androidx.compose.material3.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import com.example.camera.Mode
import com.example.camera.R
import com.example.camera.ToolsMode
import com.example.camera.analyzer.LuminosityAnalyzer
import com.example.camera.bottom.CameraSelectors
import com.example.camera.cameraSelects
import com.example.camera.modeStatus
import com.example.camera.modes
import com.example.camera.ui.theme.CameraTheme
import com.example.camera.util.LogUtil
import kotlinx.coroutines.processNextEventInCurrentThread
import java.util.concurrent.TimeUnit

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
    val lifecycleObserver = LocalLifecycleOwner.current//创建生命周期所有者
    val context = LocalContext.current
    val cameraProviderFuture = remember {
        ProcessCameraProvider.getInstance(context)
    }
    val cameraProvider: ProcessCameraProvider = cameraProviderFuture.get()
    val previewView = remember {
        PreviewView(context).apply {
            id = R.id.preview_view//指定xml中创建的id
        }
    }
//    var Selectors = CameraSelectors()
//    var cameraGetSelectors by remember {mutableStateOf(cameraSelects)}
    cameraProviderFuture.addListener(
        {
            //添加监听器
            LogUtil.d("flashmode", "Flash Testing ")
            val cameraProvider:ProcessCameraProvider = cameraProviderFuture.get() //创建cameraProvider
            //构建相机预览对象
            val preview = androidx.camera.core.Preview.Builder()
                .build()
                .also {
                    it.setSurfaceProvider(previewView.getSurfaceProvider())
                }
            try {
                //绑定生命周期
                val camera= cameraProvider.bindToLifecycle(
                    lifecycleObserver,
//                    cameraGetSelectors,
                    cameraSelects,
                    preview,
                )
                val cameraControl=camera.cameraControl
                val cameraInfo=camera.cameraInfo
                // flash
//                when(modeStatus){
//                    0-> cameraControl.enableTorch(true)
//                    1-> cameraControl.enableTorch(false)
//                }
//                        cameraControl.enableTorch(true)
//                         */
            } catch (e: Exception) {
                LogUtil.d(ContentValues.TAG,"BindTOLifecycle() Error")
                LogUtil.e(ContentValues.TAG, e.toString())
            }
            cameraProvider.unbind(preview)
            LogUtil.d("flashmode", "Flash Ending ")
        },
        ContextCompat.getMainExecutor(context)//添加到主执行器中
    )
    var flashmode by remember {mutableStateOf(false)}
    var hdrmode by remember {mutableStateOf(false)}
    when(flashmode){
        false->modeStatus=0
        true->modeStatus=1
    }
//    LogUtil.d("flashmode","-------------$modeStatus-------------")
    LogUtil.d("flashmode","-------------START-------------")
//    val modes by remember {
//        mutableStateOf(ToolsMode(mutableStateOf(false),mutableStateOf(false)))
////        mutableStateOf(mode)
//    }
//    var modes=ToolsMode
//    var modes=Mode()
    var modes = ToolsMode(flashmode,hdrmode)
//    val modes by remember{mutableStateOf(mode)}
//    var modes=mode
//    var mode =modes
    LogUtil.d("flashmode","Now is $modes")
//    LogUtil.d("flashmode","$mode")
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
//        R.drawable.flash0-> modes.flash.value=false
        R.drawable.flash0-> flashmode=false
        R.drawable.flash1->flashmode=true
    }
    LogUtil.d("flash", "click set flash $modes")
//    LogUtil.d("flash", "click set flash $mode")
    LogUtil.d("flashmode","-------------END-------------")
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
    // scale
    var showScale by remember {mutableStateOf(R.drawable.scale0)}
    when(showScale){
        0->showScale=R.drawable.scale0
        R.drawable.scale0->Unit
        R.drawable.scale1->Unit
        R.drawable.scale2->Unit
        R.drawable.scale3->Unit
    }
    // HDR
    var showHdr by remember {mutableStateOf(R.drawable.hdr0)}
    when(showHdr){
        0->showHdr=R.drawable.hdr0
//        R.drawable.hdr0-> modes.hdr.value=false
//        R.drawable.hdr1->modes.hdr.value=true
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
        R.drawable.ml1->Unit//MLkit()
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

                item{
                    IconButton(
                        onClick={
                            when(showScale){
                                R.drawable.scale0->showScale=R.drawable.scale1
                                R.drawable.scale1->showScale=R.drawable.scale2
                                R.drawable.scale2->showScale=R.drawable.scale3
                                R.drawable.scale3->showScale=0

                            }
                        },
                        modifier=Modifier.size(50.dp)
                    ){
                        Image(painter= painterResource(id= showScale),
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
                            when(showHdr){
                                R.drawable.hdr0->showHdr=R.drawable.hdr1
                                R.drawable.hdr1->showHdr=0
                            }
                        },
                        modifier=Modifier.size(50.dp)
                    ){
                        Image(painter= painterResource(id=showHdr),
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
