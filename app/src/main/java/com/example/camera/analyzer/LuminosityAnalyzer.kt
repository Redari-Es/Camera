package com.example.camera.analyzer

import androidx.camera.core.ImageAnalysis
import androidx.camera.core.ImageProxy
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import java.nio.ByteBuffer
import java.time.format.TextStyle

/** Helper type alias used for analysis use case callbacks */
typealias LumaListener = (luma: Double) -> Unit


class LuminosityAnalyzer(private val listener: LumaListener): ImageAnalysis.Analyzer {
    fun ByteBuffer.toByteArray(): ByteArray {
        rewind() //Rewind the buffer to zero
        val data = ByteArray(remaining())
        get(data) //Copy the buffer into a byte array
        return data  // Return the byte array
    }

    override fun analyze(image: ImageProxy) {
        val buffer = image.planes[0].buffer
        val data = buffer.toByteArray()
        val pixels = data.map { it.toInt() and 0xFF }
        val luma = pixels.average()
        listener(luma)
        image.close()
    }
}

    /* 官方sample
    /**
         * Our custom image analysis class.
         *
         * <p>All we need to do is override the function `analyze` with our desired operations. Here,
         * we compute the average luminosity of the image by looking at the Y plane of the YUV frame.
         */
        private class LuminosityAnalyzer(listener: LumaListener? = null) : ImageAnalysis.Analyzer {
            private val frameRateWindow = 8
            private val frameTimestamps = ArrayDeque<Long>(5)
            private val listeners = ArrayList<LumaListener>().apply { listener?.let { add(it) } }
            private var lastAnalyzedTimestamp = 0L
            var framesPerSecond: Double = -1.0
                private set

            /**
             * Helper extension function used to extract a byte array from an image plane buffer
             */
            private fun ByteBuffer.toByteArray(): ByteArray {
                rewind()    // Rewind the buffer to zero
                val data = ByteArray(remaining())
                get(data)   // Copy the buffer into a byte array
                return data // Return the byte array
            }

            /**
             * Analyzes an image to produce a result.
             *
             * <p>The caller is responsible for ensuring this analysis method can be executed quickly
             * enough to prevent stalls in the image acquisition pipeline. Otherwise, newly available
             * images will not be acquired and analyzed.
             *
             * <p>The image passed to this method becomes invalid after this method returns. The caller
             * should not store external references to this image, as these references will become
             * invalid.
             *
             * @param image image being analyzed VERY IMPORTANT: Analyzer method implementation must
             * call image.close() on received images when finished using them. Otherwise, new images
             * may not be received or the camera may stall, depending on back pressure setting.
             *
             */
            override fun analyze(image: ImageProxy) {
                // If there are no listeners attached, we don't need to perform analysis
                if (listeners.isEmpty()) {
                    image.close()
                    return
                }

                // Keep track of frames analyzed
                val currentTime = System.currentTimeMillis()
                frameTimestamps.push(currentTime)

                // Compute the FPS using a moving average
                while (frameTimestamps.size >= frameRateWindow) frameTimestamps.removeLast()
                val timestampFirst = frameTimestamps.peekFirst() ?: currentTime
                val timestampLast = frameTimestamps.peekLast() ?: currentTime
                framesPerSecond = 1.0 / ((timestampFirst - timestampLast) /
                        frameTimestamps.size.coerceAtLeast(1).toDouble()) * 1000.0

                // Analysis could take an arbitrarily long amount of time
                // Since we are running in a different thread, it won't stall other use cases

                lastAnalyzedTimestamp = frameTimestamps.first

                // Since format in ImageAnalysis is YUV, image.planes[0] contains the luminance plane
                val buffer = image.planes[0].buffer

                // Extract image data from callback object
                val data = buffer.toByteArray()

                // Convert the data into an array of pixel values ranging 0-255
                val pixels = data.map { it.toInt() and 0xFF }

                // Compute average luminance for the image
                val luma = pixels.average()

                // Call all listeners with new value
                listeners.forEach { it(luma) }

                image.close()
            }
            }
     */
@Preview(showBackground=true)
    @Composable
    fun Luma(luma: MutableState<Double>){
        Box(modifier=Modifier.fillMaxSize()){
            Surface(
                shape= RoundedCornerShape(20.dp),
                modifier = Modifier.width(140.dp)
                    .align(alignment = Alignment.CenterEnd)
                    .height(80.dp),
                color = Color(0x00, 0x00, 0x00,0x20),
                ){

                    Text(
                        "Luminosity: $luma.value",
                        fontSize = 17.sp,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(10.dp)
                            .align(Alignment.Center),
                        color=Color.White,
                    )



                }
            }



        }


