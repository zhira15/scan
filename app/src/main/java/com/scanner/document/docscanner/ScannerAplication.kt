package com.scanner.document.docscanner

import android.app.Application
import com.scanner.document.docscanner.di.components.AppComponent
import com.scanner.document.docscanner.di.components.DaggerAppComponent
import com.scanner.document.docscanner.di.modules.CameraModule
import com.scanner.document.docscanner.di.modules.ContextModule
import timber.log.Timber

/**
 * Created by AnthonyCAS on 8/17/18.
 */

class ScannerAplication : Application() {

    init {
        appInstance = this
    }

    companion object {
        private var appInstance: ScannerAplication? = null
        lateinit var appComponent: AppComponent
    }

    override fun onCreate() {
        super.onCreate()

        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }

        appComponent = DaggerAppComponent.builder()
                .contextModule(ContextModule(this))
                .cameraModule(CameraModule())
                .build()
    }

    /*override fun activityInjector(): AndroidInjector<Activity>? {
        return dispatchingAndroidInjector
    }*/
}