package com.scanner.document.docscanner.util

import android.os.Environment
import java.util.*

object Constants {
    val PICKFILE_REQUEST_CODE = 1
    val START_CAMERA_REQUEST_CODE = 2
    val OPEN_INTENT_PREFERENCE = "selectContent"
    val IMAGE_BASE_PATH_EXTRA = "ImageBasePath"
    val OPEN_CAMERA = 4
    val SCANNED_RESULT = "scannedResult"
    val IMAGE_PATH = Environment.getExternalStorageDirectory().path + "/scanSample"
    val SELECTED_BITMAP = "selectedBitmap"
    val FILE_EXT = Arrays.asList("jpg", "jpeg", "png")
}