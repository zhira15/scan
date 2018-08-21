package com.scanner.document.docscanner.ui.camera

/**
 * Created by AnthonyCAS on 8/21/18.
 */

import android.hardware.camera2.CameraManager
import android.os.Handler
import android.view.TextureView

interface CameraNavigator {
    interface View {
        fun getGalleryFolder(): String
        fun openPreviewActivity(imagePath: String)
        fun getScreenRotation(): Int
        fun setTextureViewAspectRatio(width: Int, height: Int)
        var backgroundHandler: Handler?
    }

    interface Preview {
        fun attachView(view: CameraNavigator.View)
        fun detachView()
        fun launchCameraPreview(systemCameraManager: CameraManager, textureView: TextureView)
        fun captureImage()
        var cameraIsReady: Boolean
    }
}