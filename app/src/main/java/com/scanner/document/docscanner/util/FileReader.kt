package com.scanner.document.docscanner.util

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Environment
import android.util.Log
import com.scanner.document.docscanner.activities.DocItem
import timber.log.Timber
import java.io.File
import java.io.FileInputStream
import java.io.FileNotFoundException
import java.text.SimpleDateFormat
import java.util.*

/**
 * Created by AnthonyCAS on 8/20/18.
 */

class FileReader {
    fun walk(root: File): ArrayList<DocItem> {
        var docList: ArrayList<DocItem> = ArrayList<DocItem>()
        val df = SimpleDateFormat("dd/MM/yy")
        val formattedDate = df.format(Calendar.getInstance().time)
        Timber.e("Current time => $formattedDate")

        val fileList = root.listFiles() ?: return docList
        for (file in fileList) {
            if (file.isDirectory && file.name != "thumbnails") {
                var b: Bitmap? = null
                val file = File(Environment.getExternalStorageDirectory(), "/DocumentScanner/thumbnails/" + file.name + ".jpg")
                try {
                    b = BitmapFactory.decodeStream(FileInputStream(file))
                } catch (e: FileNotFoundException) {
                    e.printStackTrace()
                }
                docList.add(DocItem(file.name, formattedDate, b))
            }
        }
        return docList
    }
}