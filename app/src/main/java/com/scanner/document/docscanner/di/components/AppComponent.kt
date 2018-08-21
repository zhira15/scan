package com.scanner.document.docscanner.di.components

import com.scanner.document.docscanner.di.modules.CameraModule
import com.scanner.document.docscanner.di.modules.ContextModule
import com.scanner.document.docscanner.di.scopes.CameraApplicationScope
import com.scanner.document.docscanner.ui.camera.CameraNavigator
import dagger.BindsInstance
import dagger.Component

@CameraApplicationScope
@Component(modules = [CameraModule::class, ContextModule::class])

interface AppComponent {

    fun inject(cameraActivity: CameraNavigator.View)
    fun getCameraPreview(): CameraNavigator.Preview

}