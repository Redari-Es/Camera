package com.example.camera

import android.Manifest
import android.content.ContentValues.TAG
import android.content.Context
import android.net.Uri
import android.os.Environment
import android.util.Log
import android.widget.Toast
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageCapture
import androidx.camera.core.ImageCaptureException
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.camera.view.PreviewView
import androidx.compose.foundation.Image
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
import coil.compose.rememberImagePainter
import com.example.camera.R
import com.google.accompanist.permissions.*
import java.io.File

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.camera.core.Camera
import androidx.camera.core.CameraInfo
import androidx.camera.core.CameraState
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.content.ContentProviderCompat.requireContext
import com.example.camera.ui.theme.CameraTheme


/*
Author: Shon(Redari-Es)
Email: shon@redaries.xyz
Github: https://github.com/Redari-Es/camera
* */
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CameraTheme {
                //实机
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
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
                            MyCameraX()
                            // 若成功获取权限运行相机
                        }
                        is PermissionStatus.Denied -> {
                            LogUtil.i("permissions", "CAMERA Denied")
                            NoPermissionView(permissionsState)//用户不同意权限
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

@Composable
private fun MyCameraX() {
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
    LogUtil.d("MyCameraX", "previewView")
    //imageCapture是图像捕获用例，提交takePicture函数将图片拍摄到内存或保存到文件,并提供图像原数据
    // take
    var takeState by remember {mutableStateOf(false)}

    // imageCapture
    val imageCapture = remember {
        ImageCapture.Builder().build()
    }
    // videoCapture

    //在界面上显示图片,保存uri状态
    val imageUri = remember {
        mutableStateOf<Uri?>(null)
    }
    LogUtil.d("MyCameraX", "show image")
    val fileUtils: FileUtils by lazy { FileUtilsImpl() }
    LogUtil.d("MyCameraX", "show app")
    //  ImageAnalysis

    //设置摄像头，默认使用背面的摄像头
    val cameraSelector1 = CameraSelector.DEFAULT_BACK_CAMERA
    val cameraSelector2 = CameraSelector.DEFAULT_FRONT_CAMERA
    var cameraSelectors by remember {mutableStateOf(cameraSelector1)}
    var cameraState by remember {mutableStateOf(false)}
    if (cameraState){
        cameraSelectors=cameraSelector2
    }else{
        cameraSelectors=cameraSelector1
    }

    // Show Camera UI
    Box(modifier = Modifier.fillMaxSize()) {
        // 相机视图
        AndroidView(
            factory = { previewView },//添加相机预览视图
            modifier = Modifier.fillMaxSize()
            
        ) {
            LogUtil.d("MyCameraX", "start Listener ")
            cameraProviderFuture.addListener(
                {
                    // cameraselect
                    //添加监听器
                    LogUtil.d("MyCameraX", "add Listener ")
                    val cameraProvider:ProcessCameraProvider = cameraProviderFuture.get() //创建cameraProvider
                    //构建相机预览对象
                    val preview = androidx.camera.core.Preview.Builder()
                        .build()
                        .also {
                        //it.setSurfaceProvider(previewView.surfaceProvider)
                        it.setSurfaceProvider(previewView.getSurfaceProvider())
                    }
                    LogUtil.d("MyCameraX", "finish add Listener ")
                    LogUtil.d("MyCameraX", "set cameraSelector")
                    try {
                        LogUtil.d("LifeCycle", "start lifeCycle")
                        //从应用程序的生命周期中，解除所有应用的摄像机绑定，这将关闭所有当前打开的摄像机，并运行在启动时添加摄像机用例
                        cameraProvider.unbindAll()
                        LogUtil.d("LifeCycle", "unbindAll()")

                        //绑定生命周期
                        cameraProvider.bindToLifecycle(
                            lifecycleObserver,
                            cameraSelectors,
                            preview,
                            imageCapture
                        )
                        LogUtil.d("LifeCycle", "bindToLifecycle()")
                    } catch (e: Exception) {
                        LogUtil.d("LifeCycle", "erro")
                        LogUtil.e("Camera", e.toString())
                    }
                },
                ContextCompat.getMainExecutor(context)//添加到主执行器中
            )
        }// end of AndroidView
        //将图像存储到文件中
        LogUtil.d("show", "other")
        // 左侧图片栏
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp)
                .align(Alignment.BottomCenter),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            // row1
            //显示拍照的图片
//            imageUri.value?.let { //导致排列错位，由于可为空
                imageUri.value.let {
                    if (it==null){
                        Image(
                            painter = painterResource(id=R.drawable.album),
                            contentDescription = "",
                            modifier = Modifier
                                .size(50.dp),
                        )
                    }else{
                Image(
                    painter = rememberImagePainter(data = it),
                    contentDescription = "",
                    modifier = Modifier
                        .size(50.dp),
                )
                    }
            }
            Spacer(modifier = Modifier.width(50.dp))
//            row2
            IconButton(onClick = {
                fileUtils.createDirectoryIfNotExist(context)
                val file = fileUtils.createFile(context)
                //用于存储新捕获图像的选项outPutOptions
                val outputOption = ImageCapture.OutputFileOptions.Builder(file).build()
                // 调用拍照
                imageCapture.takePicture(outputOption,
                    ContextCompat.getMainExecutor(context),//调用线程执行器
                    object : ImageCapture.OnImageSavedCallback { //为新捕获的图像调用回调
                        override fun onImageSaved(outputFileResults: ImageCapture.OutputFileResults) {
                            val saveUri = Uri.fromFile(file)//保存的图像地址
                            Toast.makeText(context, saveUri.path, Toast.LENGTH_SHORT).show()
                            imageUri.value = saveUri//设置图像显示路径
                        }
                        override fun onError(exception: ImageCaptureException) {
                            LogUtil.e("Camera", "$exception")
                        }
                    }
                )
            },
                modifier=Modifier.size(100.dp)
            )
            {
                Image(painter= painterResource(id=R.drawable.takephoto),
                    contentDescription = null,
//                    tint= Color.White,
                    modifier=Modifier
                        .size(120.dp)
                )
//                Text(text = "拍照")
            }
            Spacer(modifier = Modifier.width(50.dp))
            IconButton(
                onClick={
                    if (!cameraState){
                        cameraState=true
                    }else{
                        cameraState=false
                    }
                },
                modifier=Modifier.size(50.dp)

            ){
                Image(painter=painterResource(id=R.drawable.cameraselector),
                    contentDescription = null,
//                    tint=Color.White,
                    modifier=Modifier
                        .size(50.dp)
                )
            }
            // end of row
        }

    }
}


interface FileUtils {
    //创建文件目录
    fun createDirectoryIfNotExist(context: Context)

    //创建文件
    fun createFile(context: Context): File
}

class FileUtilsImpl : FileUtils {
    companion object {
        private const val IMAGE_PREFIX = "Image_"
        private const val JPG_SUFFIX = ".jpg"
        private const val FOLDER_NAME = "Photo"
    }

    override fun createDirectoryIfNotExist(context: Context) {
        val folder = File(
            "${context.getExternalFilesDir(Environment.DIRECTORY_PICTURES)?.absoluteFile}"
                    + File.separator
                    + FOLDER_NAME
        )
        if (!folder.exists()) {
            folder.mkdir()
        }
    }

    override fun createFile(context: Context) = File(
        "${context.getExternalFilesDir(Environment.DIRECTORY_PICTURES)?.absoluteFile}"
                + File.separator + FOLDER_NAME + File.separator + IMAGE_PREFIX + System.currentTimeMillis() + JPG_SUFFIX
    )

}

// startCamera()
/*
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


