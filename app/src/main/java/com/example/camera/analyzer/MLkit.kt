package com.example.camera.analyzer

import androidx.compose.runtime.Composable

import androidx.camera.mlkit.vision.MlKitAnalyzer
import androidx.camera.view.CameraController.COORDINATE_SYSTEM_VIEW_REFERENCED
import androidx.camera.view.LifecycleCameraController
import androidx.camera.view.PreviewView
import androidx.core.content.ContextCompat
import com.google.mlkit.vision.barcode.BarcodeScannerOptions
import com.google.mlkit.vision.barcode.BarcodeScanning
import com.google.mlkit.vision.barcode.common.Barcode

/*
@Composable
fun MLkit(){
var cameraController = LifecycleCameraController(baseContext)
val previewView: PreviewView = viewBinding.preview_view

val options = BarcodeScannerOptions.Builder()
    .setBarcodeFormats(Barcode.FORMAT_QR_CODE)
    .build()
barcodeScanner = BarcodeScanning.getClient(options)

cameraController.setImageAnalysisAnalyzer(
ContextCompat.getMainExecutor(this),
MlKitAnalyzer(
listOf(barcodeScanner),
COORDINATE_SYSTEM_VIEW_REFERENCED,
ContextCompat.getMainExecutor(this)
) { result: MlKitAnalyzer.Result? ->
    val barcodeResults = result?.getValue(barcodeScanner)
    if ((barcodeResults == null) ||
        (barcodeResults.size == 0) ||
        (barcodeResults.first() == null)
    ) {
        previewView.overlay.clear()
        previewView.setOnTouchListener { _, _ -> false } //no-op
        return@MlKitAnalyzer
    }

    val qrCodeViewModel = QrCodeViewModel(barcodeResults[0])
    val qrCodeDrawable = QrCodeDrawable(qrCodeViewModel)

    previewView.setOnTouchListener(qrCodeViewModel.qrCodeTouchCallback)
    previewView.overlay.clear()
    previewView.overlay.add(qrCodeDrawable)
}
)

cameraController.bindToLifecycle(this)
previewView.controller = cameraControlle
}

 */