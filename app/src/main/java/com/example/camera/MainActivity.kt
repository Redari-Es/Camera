package com.example.camera

import android.Manifest
import android.annotation.SuppressLint
import android.content.ContentValues.TAG
import android.net.Uri
import androidx.camera.core.CameraSelector
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.camera.view.PreviewView
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.content.ContextCompat
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import com.google.accompanist.permissions.*

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.camera.core.DisplayOrientedMeteringPointFactory
import androidx.camera.core.FocusMeteringAction
import androidx.camera.core.FocusMeteringAction.FLAG_AF
import androidx.camera.core.FocusMeteringAction.FLAG_AWB
import androidx.camera.core.ImageAnalysis
import androidx.camera.core.MeteringPoint
import androidx.camera.core.MeteringPointFactory
import androidx.camera.core.Preview
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.Crossfade
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import com.example.camera.analyzer.LuminosityAnalyzer
import com.example.camera.bottom.CameraSelectors
import com.example.camera.bottom.ImageCaptures
import com.example.camera.bottom.ImageUris
import com.example.camera.page.Album
import com.example.camera.page.LoadingPage
import com.example.camera.page.PageStates
import com.example.camera.ui.theme.CameraTheme
import com.example.camera.util.LogUtil
import com.example.camera.util.NoPermissionView
import java.lang.reflect.Array.get
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors
import java.util.concurrent.TimeUnit

/** Helper type alias used for analysis use case callbacks */
//typealias LumaListener = (luma: Double) -> Unit


/*
Copyright (Shon)
Author: Shon(Redari-Es)
Email: shon@redaries.xyz
ProjectName: The Monster Camera - 怪物卡美啦
Github: https://github.com/Redari-Es/camera

* */
class MainActivity : ComponentActivity() {
    /** Blocking camera operations are performed using this executor */
    private lateinit var cameraExecutor: ExecutorService
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CameraTheme {
                //实机
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) // Initialize our background executor
                {
//                    PageStates("cameraXDemo")
                    // 运行
                    CameraXDemo()

                }
            }
        }
    }
}



/*
启用权限：Permission
* */
@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun CameraXDemo() {
    //申请权限
    val permissionsState = rememberMultiplePermissionsState(permissions = listOf(Manifest.permission.CAMERA))
    // 绑定生命周期
    val lifecycleOwner = LocalLifecycleOwner.current
    LogUtil.d(TAG, "var life")
    DisposableEffect(key1 = lifecycleOwner, effect = {
        val observer = LifecycleEventObserver { _, event ->
            if (event == Lifecycle.Event.ON_START) {
                permissionsState.launchMultiplePermissionRequest()
            }
        }
        LogUtil.d(TAG, "life2")
        lifecycleOwner.lifecycle.addObserver(observer)

        onDispose { lifecycleOwner.lifecycle.removeObserver(observer) }
    })
    // 权限获取UI
    Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
        permissionsState.permissions.forEach { permissionState ->
            LogUtil.d(TAG, "for permission")
            when (permissionState.permission) {
                Manifest.permission.CAMERA -> {
                    LogUtil.d(TAG, "permission status")
                    when (permissionState.status) {
                        is PermissionStatus.Granted -> {
                            LogUtil.i("permissions", "CAMERA Granted")
                            //用户同意权限
                            // 加载页
//                             PageStates("LoadingPage")
//                            PageStates(nextpage)

                            PageStates("MyCameraX")
//                            MyCameraX()
                            // 若成功获取权限运行相机
                        }
                        is PermissionStatus.Denied -> {
                            LogUtil.i("permissions", "CAMERA Denied")
                            NoPermissionView(permissionsState)//用户不同意权限
                            // Shut down our background executor
                        }
                    }
                }
            }
        }
    }
}


/*
@Preview
@Composable
fun MyCameraXPreview(){
    CameraTheme(){
        MyCameraX()
    }
}
 */

//public data class ToolsMode(var flash: MutableState<Boolean>, var hdr: MutableState<Boolean>)
public data class ToolsMode(var flash: Boolean, var hdr: Boolean)
//public data class ToolsMode(var flash: Boolean, var hdr:Boolean)
public  lateinit var modes:ToolsMode
@Composable
fun Mode(): ToolsMode {
    val _flash=remember{mutableStateOf(false)}
    val _hdr=remember{mutableStateOf(false)}
    val mode = remember { mutableStateOf(ToolsMode(false, false)) }
    mode.value.flash=_flash.value
    mode.value.hdr=_hdr.value
    return mode.value
}

var cameraSelects=CameraSelector.DEFAULT_BACK_CAMERA
var modeStatus= 0
@SuppressLint("UnrememberedMutableState")
@Composable
fun MyCameraX() {
//   val flashmode=ToolsMode(false,false)
//    var flashmode by remember {mutableStateOf(ToolsMode(true,false))}
//    var flashmode = remember {mutableStateOf(false,false)}
//   var modes=Mode()
//    LogUtil.d("flashmod","Mode() return value$modes")
//    val flashmode = Mode()


    LogUtil.d("MyCameraX", "START")

    val lifecycleObserver = LocalLifecycleOwner.current//创建生命周期所有者
    val context = LocalContext.current
    LogUtil.d("MyCameraX", "build lifecycle")
    //
    val cameraProviderFuture = remember {
        ProcessCameraProvider.getInstance(context)
    }
    LogUtil.d("MyCameraX", "ProviederFuture")
    //创建相机预览的视图
    // previewView
    val previewView = remember {
        PreviewView(context).apply {
            id = R.id.preview_view//指定xml中创建的id
        }
    }
    // 3A

//    previewView.setOnTouchListener(view,motionEvent) -> {
//       val meteringPoint = previewView.meteringPointFactory.createPoint(motionEvent.x,motionEventr.y)
//    }
//    val meteringPointFactory=DisplayOrientedMeteringPointFactory(
//       surfaceView.display,
//        camera.cameraInfo,
//        surfaceView.width
//                surfaceView.height
//    )
//    val meteringPointFactory = SurfaceOrientedMeteringPointFactory(imageWidth,imageHeight,imageAnalysis)

    //imageCapture是图像捕获用例，提交takePicture函数将图片拍摄到内存或保存到文件,并提供图像原数据
    // imageCapture
    val images = ImageCaptures(context)
    var imageGetCapture by remember {mutableStateOf(images.first)}
    var imageUri by remember {
        mutableStateOf<Uri?>(images.second.value)
    }
//    val fileUtils: FileUtils by lazy { FileUtilsImpl() }
//    LogUtil.d("MyCameraX", "show app")
    //  ImageAnalysis
    var cameraExecutor = Executors.newSingleThreadExecutor()
//    var cameraSelectors by remember {mutableStateOf(CameraSelector.DEFAULT_BACK_CAMERA)}
    var cameraGetSelectors by remember {mutableStateOf(CameraSelector.DEFAULT_BACK_CAMERA)}
    var androidview by remember{mutableStateOf(true)}

    AnimatedVisibility(
        visible=androidview,
        enter=fadeIn(
            animationSpec = tween(
                durationMillis = 1200,
                delayMillis = 50,
                easing = LinearOutSlowInEasing
            )
        ),
        exit=fadeOut(
            animationSpec = tween(
                durationMillis = 1200,
                delayMillis = 50,
                easing = LinearOutSlowInEasing
            )
        ),
    ){
    // Show Camera UI
    Box(modifier = Modifier.fillMaxSize()) {
        LogUtil.d(TAG,"Start The Monster Camera UI")
        AndroidView(
            factory = { previewView },//添加相机预览视图
            modifier = Modifier.fillMaxSize()

        ) {
            LogUtil.d(TAG,"Start of AndroidView")
            LogUtil.d("MyCameraX", "start Listener ")
            /** Blocking camera operations are performed using this executor */
            cameraProviderFuture.addListener(
                {

                    //添加监听器
                    LogUtil.d("MyCameraX", "add Listener ")
                    var cameraProvider:ProcessCameraProvider = cameraProviderFuture.get() //创建cameraProvider
                    //构建相机预览对象
                    var preview = Preview.Builder()
                        .build()
                        .also {
                        //it.setSurfaceProvider(previewView.surfaceProvider)
                        it.setSurfaceProvider(previewView.getSurfaceProvider())
                    }
                    // imageAnalyzer
                    var imageAnalyzer = ImageAnalysis.Builder()
                        .build()
                        .also{
                            it.setAnalyzer(cameraExecutor, LuminosityAnalyzer{ luma:Double ->
//                                LogUtil.d("ImageAnalyzer","Average luminosity:$luma")
                            })
                        }
                    // 3A

                    LogUtil.d("MyCameraX", "finish add Listener ")
                    try {
                        LogUtil.d("LifeCycle", "start lifeCycle")
                        //从应用程序的生命周期中，解除所有应用的摄像机绑定，这将关闭所有当前打开的摄像机，并运行在启动时添加摄像机用例
                        cameraProvider.unbindAll()
                        LogUtil.d("LifeCycle", "unbindAll()")
                        //绑定生命周期
                        var camera= cameraProvider.bindToLifecycle(
                            lifecycleObserver,
//                            cameraGetSelectors,
                           cameraSelects,
                            preview,
                            imageGetCapture,
                            imageAnalyzer
                        )
                        val cameraControl=camera.cameraControl
                        val cameraInfo=camera.cameraInfo

                        // flash
                        LogUtil.d("flashmode","-----------change start--------")
                        when(modeStatus){
                            1-> cameraControl.enableTorch(true)
                            0-> cameraControl.enableTorch(false)
                        }
                        LogUtil.d("flashmode","change flash is $modeStatus")
                        LogUtil.d("flashmode","-----------change end--------")
//                        cameraControl.enableTorch(true)
//                        cameraControl.enableTorch(flashmode.flash.value)
                        // foucus
//                        previewView.setOnTouchListener((view,motionEvent)->{
//                            val meteringPoint=previewView.meteringPointFactory.create(motionEvent.x,motionEvent.y)
//                            )
//                        }
//                        /* start
                        val meteringPointFactory = DisplayOrientedMeteringPointFactory(
                            previewView.display,
                            camera.cameraInfo,
                            0.2f,0.2f
                        )
//                        val meteringPointFactory = SurfaceOrientedMeteringPointFactory(
//                            imageWidth,imageHeight,imageAnalysis
//                        )
//                        previewView.setOnTouchListener(R.id.preview_view,motionEvent)
                        val meteringPoint1= meteringPointFactory.createPoint(0.1f,0.1f,0.4f)
                        val meteringPoint2= meteringPointFactory.createPoint(0.3f,0.3f,0.4f)
                        val action = FocusMeteringAction.Builder(meteringPoint1) // default AF/AE/AWB
                            .addPoint(meteringPoint2,FLAG_AF or FLAG_AWB)
                            .setAutoCancelDuration(3, TimeUnit.SECONDS)
                            .build()
                        val result= cameraControl.startFocusAndMetering(action)
                        result.addListener({
//                            result.get()
                                   LogUtil.d("flashmod","focus is ok")
                        },ContextCompat.getMainExecutor(context))

//                            focus end
//                         */
                    } catch (e: Exception) {
                        LogUtil.d(TAG,"BindTOLifecycle() Error")
                        LogUtil.e(TAG, e.toString())
                    }
                },
                ContextCompat.getMainExecutor(context)//添加到主执行器中
            )
        }
        LogUtil.d(TAG,"End of AndroidView")
        LogUtil.d(TAG,"Loading TopTools")
//        TopTools()
        PageStates("TopTools")
        LogUtil.d(TAG,"Show TopTools")
        LogUtil.d(TAG,"Loading BottomTools")
        Box(
            modifier = Modifier
                .fillMaxWidth()
                // 设置其他页全屏
//                .padding(20.dp)
                .align(Alignment.BottomCenter),
//            verticalAlignment = Alignment.CenterVertically,
//            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            LogUtil.d(TAG,"Row2 - Loading ChangeCameraSelectors")
            cameraGetSelectors = CameraSelectors()
             cameraSelects = cameraGetSelectors
            Spacer(modifier = Modifier.width(50.dp))
            LogUtil.d(TAG,"Row2 - Loading ImageCaputre")
             val images= ImageCaptures(context)
            imageGetCapture=images.first
            imageUri=images.second.value
            Spacer(modifier = Modifier.width(50.dp))
            LogUtil.d(TAG,"Row0 - Loading the imageUri")
           var showAlbum= ImageUris(imageUri)
//            Album(imageUri,showAlbum)
            when(showAlbum){
                true->Album(imageUri,showAlbum)
                false->Album(imageUri,showAlbum)
            }

        } // end of row
    }
}
}
/*
 startCamera() Offical Sample
private fun startCamera(){
    val cameraProviderFuture=ProcessCameraProvider.getInstance(this)
    cameraProviderFuture.addListener(Runnable{
        // Used to bind the lifecycle of cameras to the lifecycle owner
        val cameraProvider: ProcessCameraProvider=cameraProviderFutrue.get()
        // Preview

        val preview = Preview.Builder()
            .build()
            .also{
                it.setSurfaceProvider(previewView.createSurfaceProvider())
            }
        // Select back camera as a default camera
        val cameraSelector=CameraSelector.DEFAULT_BACK_CAMERA
        try{
            // Unbind use cases before rebinding
             cameraProvider.unbindAll()
            // Bind use cases to cameraProvider
            cameraProvider.bindToLifecycle(
                this,cameraSelector,preview
            )
        }catch(exc:Exception){
           Log.e(TAG,"Use case binding failed",exc)
        }
    },
        ContextCompat.getMainExecutor(this))
}
 */

object def {
    private const val TAG = "Monster of Camera"
    private const val FILENAME = "yyyy-MM-dd-HH-mm-ss-SSS"
    private const val PHOTO_TYPE = "image/jpeg"
    private const val RATIO_4_3_VALUE = 4.0 / 3.0
    private const val RATIO_16_9_VALUE = 16.0 / 9.0
}

