package com.scanner.document.docscanner.util

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Point
import android.net.Uri
import android.provider.MediaStore
import android.view.WindowManager
import java.io.ByteArrayOutputStream
import java.io.IOException
import java.util.*

class Utils( val context: Context) {

    private fun IsSupportedFile(filePath: String): Boolean {
        val ext = filePath.substring(filePath.lastIndexOf(".") + 1, filePath.length)
        return Constants.FILE_EXT.contains(ext.toLowerCase(Locale.getDefault()))
    }

    companion object {

        fun getUri(context: Context, bitmap: Bitmap): Uri {
            val bytes = ByteArrayOutputStream()
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, bytes)
            val path = MediaStore.Images.Media.insertImage(context.contentResolver, bitmap, "Title", null)
            return Uri.parse(path)
        }

        @Throws(IOException::class)
        fun getBitmap(context: Context, uri: Uri): Bitmap {
            return MediaStore.Images.Media.getBitmap(context.contentResolver, uri)
        }
    }
}