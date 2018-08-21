package com.scanner.document.docscanner.util

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Environment
import com.scanner.document.docscanner.data.model.DocItem
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
    companion object {

        object Constants {
            val DOCS_PATH = "/RavnDocumentScanner/images/"
            val DIR_PATH = android.os.Environment.getExternalStorageDirectory().toString()
        }

        fun walk(): ArrayList<DocItem> {

            val reader = File(Constants.DIR_PATH, Constants.DOCS_PATH)
            var docList: ArrayList<DocItem> = ArrayList<DocItem>()

            val dateFormat = SimpleDateFormat("dd/MM/yy hh:mm a")

            val fileList = reader.listFiles() ?: return docList
            for (file in fileList) {
                if (!file.isDirectory) {
                    var bitmap: Bitmap? = null
                    val file = File(Constants.DIR_PATH, Constants.DOCS_PATH + file.name)
                    val formattedDate = dateFormat.format(file.lastModified())
                    try {
                        bitmap = BitmapFactory.decodeStream(FileInputStream(file))

                    } catch (e: FileNotFoundException) {
                        e.printStackTrace()
                    }
                    docList.add(DocItem(file.name, formattedDate, bitmap))
                }
            }
            return docList
        }
    }

}