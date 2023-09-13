package com.example.camera.top

import android.content.ContentValues
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageAnalysis
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.camera.view.PreviewView
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.core.content.ContextCompat
import com.example.camera.R
import com.example.camera.analyzer.LuminosityAnalyzer
import com.example.camera.util.LogUtil
/*
@Composable
fun CameraControl(){
//    val camera =processCameraProvider.bindToLifecycle(CameraXDemo.lifecycleOwner,cameraSelector,preview)
//    val cameraControl1=camera.cameraControl
//    val cameraInfo=camera.cameraInfo
    val lifecycleObserver = LocalLifecycleOwner.current//创建生命周期所有者
    val context = LocalContext.current
    LogUtil.d("MyCameraX", "build lifecycle")
    //
    val cameraProviderFuture = remember {
        ProcessCameraProvider.getInstance(context)
    }
    // previewView
    val previewView = remember {
        PreviewView(context).apply {
            id = R.id.preview_view//指定xml中创建的id
        }
    }
    var cameraGetSelectors by remember { mutableStateOf(CameraSelector.DEFAULT_BACK_CAMERA) }
    cameraProviderFuture.addListener(
        {
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
//            try {
                //绑定生命周期
              val camera=  cameraProvider.bindToLifecycle(
                    lifecycleObserver,
                    cameraGetSelectors,
                    preview,
//                    imageGetCapture,
                )
            val cameraControl=camera.cameraControl
            val cameraInfo=camera.cameraInfo
            cameraControl.enableTorch(true)
//                LogUtil.d(ContentValues.TAG, "bindToLifecycle()")
//            } catch (e: Exception) {
//                LogUtil.d(ContentValues.TAG,"BindTOLifecycle() Error")
//                LogUtil.e(ContentValues.TAG, e.toString())
//            }
        },
        ContextCompat.getMainExecutor(context)//添加到主执行器中
    )
}


 */
