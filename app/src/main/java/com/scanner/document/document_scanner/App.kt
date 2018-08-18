package com.scanner.document.document_scanner

import android.app.Activity
import android.app.Application
import com.scanner.document.document_scanner.di.components.AppComponent
//import com.scanner.document.document_scanner.di.components.DaggerAppComponent
import com.scanner.document.document_scanner.di.modules.AppModule

import javax.inject.Inject

import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasActivityInjector
import timber.log.Timber

class App : Application(), HasActivityInjector {
    @Inject
    lateinit var dispatchingAndroidInjector: DispatchingAndroidInjector<Activity>

    override fun onCreate() {
        super.onCreate()

        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }

        /*DaggerAppComponent
                .builder()
                .application(this)
                .build()
                .inject(this)*/
    }

    override fun activityInjector(): AndroidInjector<Activity>? {
        return dispatchingAndroidInjector
    }
}