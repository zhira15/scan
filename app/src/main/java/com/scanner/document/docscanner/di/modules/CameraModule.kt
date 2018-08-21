package com.scanner.document.docscanner.di.modules

import com.scanner.document.docscanner.di.scopes.CameraApplicationScope
import com.scanner.document.docscanner.ui.camera.CameraNavigator
import com.scanner.document.docscanner.util.CameraPreview
import dagger.Module
import dagger.Provides

/**
 * Created by AnthonyCAS on 8/21/18.
 */

@Module
class CameraModule {

    @Provides
    @CameraApplicationScope
    fun provideCameraPreview(): CameraNavigator.Preview = CameraPreview()
}