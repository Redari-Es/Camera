package com.example.camera.bottom

import android.content.Context
import android.net.Uri
import android.os.Environment
import android.widget.Toast
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageCapture
import androidx.camera.core.ImageCaptureException
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.IconButton
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import coil.compose.rememberImagePainter
import com.example.camera.R
import com.example.camera.util.LogUtil
import java.io.File


// BottomRow1
@Composable
fun ImageUris(imageUri: Uri?){
    //显示拍照的图片
//            imageUri.value?.let { //导致排列错位，由于可为空
//            /*
//                imageUri.value.let {
    imageUri.let {
//                images.second.value.let {
        if (it==null){
            Image(
                painter = painterResource(id=R.drawable.album),
                contentDescription = "",
                modifier = Modifier
                    .size(60.dp),
            )
        }else{
            Surface(
                shape=CircleShape
            ){
                Image(
                    painter = rememberImagePainter(data = it),
                    contentDescription = "",
                    modifier = Modifier
                        .size(60.dp),
                    contentScale= ContentScale.Crop
                )
            }
        }
    }
}

// BottomRow2
@Composable
fun ImageCaptures(context:Context): Pair<ImageCapture, MutableState<Uri?>>{
//    fun ImageCaptures(context:Context):Result{
    //imageCapture是图像捕获用例，提交takePicture函数将图片拍摄到内存或保存到文件,并提供图像原数据
    var takeState by remember {mutableStateOf(false)}
    // imageCapture
    val imageCapture = remember {
        ImageCapture.Builder().build()
    }
    //在界面上显示图片,保存uri状态
    val imageGetUri = remember {
        mutableStateOf<Uri?>(null)
    }
    val fileUtils: com.example.camera.bottom.FileUtils by lazy { FileUtilsImpl() }
    // row1 //设置摄像头，默认使用背面的摄像头
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
                    imageGetUri.value = saveUri//设置图像显示路径
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
    return Pair(imageCapture, imageGetUri)
}// end

// BottomRow3
@Composable
fun CameraSelectors():CameraSelector{
    val cameraSelector1 = CameraSelector.DEFAULT_BACK_CAMERA
    val cameraSelector2 = CameraSelector.DEFAULT_FRONT_CAMERA
    var cameraSelectors by remember {mutableStateOf(cameraSelector1)}
    var cameraState by remember {mutableStateOf(false)}
    if (cameraState){
        cameraSelectors=cameraSelector2
    }else{
        cameraSelectors=cameraSelector1
    }
    IconButton(
        onClick={
            cameraState=!cameraState
        },
        modifier= Modifier.size(60.dp)
    ){
        Image(painter= painterResource(id= R.drawable.cameraselector),
            contentDescription = null,
            modifier= Modifier
                .size(60.dp)
        )
    }
return cameraSelectors
}


// File
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
