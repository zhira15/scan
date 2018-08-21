package com.scanner.document.docscanner.util

/**
 * Created by AnthonyCAS on 8/21/18.
 */
import android.media.Image
import timber.log.Timber

import java.io.File
import java.io.FileOutputStream
import java.io.IOException

/**
 * This class save the picture to the selected location
 * @image picture buffer
 * @file it contains the path lo save the picture
 */
internal class ImageSaver( private val image: Image, private val file: File) : Runnable {

    override fun run() {
        val t1 = System.currentTimeMillis()
        val buffer = image.planes[0].buffer
        val bytes = ByteArray(buffer.remaining())
        buffer.get(bytes)
        var output: FileOutputStream? = null
        try {
            output = FileOutputStream(file).apply {
                write(bytes)
            }
        } catch (e: IOException) {
            Timber.e("File write output error: $e")
        } finally {
            image.close()
            Timber.d("Time for imageSaver to save image: ${System.currentTimeMillis() - t1}ms")
            output?.let {
                try {
                    it.close()
                } catch (e: IOException) {
                    Timber.e("File closing exception: $e")
                }
            }
        }
    }
}