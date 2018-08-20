package com.scanner.document.docscanner.data.model

import android.graphics.Bitmap

/**
 * Created by AnthonyCAS on 8/20/18.
 */
data class DocItem(val name: String, val timestamp: String, val bitmap: Bitmap) {
    companion object {
        fun getInstance(doc: DocItem) : DocItem {
            return DocItem(doc.name, doc.timestamp, doc.bitmap)
        }

        fun getInstanceList(dockItems: List<DocItem>) : List<DocItem> {
            return dockItems.mapNotNull {
                getInstance(it)
            }
        }
    }
}