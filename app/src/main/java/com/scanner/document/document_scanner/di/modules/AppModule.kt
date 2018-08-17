package com.scanner.document.document_scanner.di.modules

import android.app.Application
import android.content.Context
import com.scanner.document.document_scanner.App
import com.scanner.document.document_scanner.common.CommonService;
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class AppModule {

    @Provides
    @Singleton
    fun provideApplication(app: App): Application = app

    @Provides
    @Singleton
    fun provideApplicationContext(app: App): Context = app.applicationContext

    @Singleton
    @Provides
    fun provideCommonHelloService(): CommonService {
        return CommonService()
    }
}