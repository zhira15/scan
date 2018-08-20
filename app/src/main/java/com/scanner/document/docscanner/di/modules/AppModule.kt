package com.scanner.document.docscanner.di.modules

import android.app.Application
import android.content.Context
import com.scanner.document.docscanner.ScannerAplication
import com.scanner.document.docscanner.common.domain.model.CommonCameragRepository
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class AppModule {

    @Provides
    @Singleton
    fun provideApplication(app: ScannerAplication): Application = app

    @Provides
    @Singleton
    fun provideApplicationContext(app: ScannerAplication): Context = app.applicationContext

    @Singleton
    @Provides
    fun provideCommonHelloService(): CommonCameragRepository {
        return CommonCameragRepository()
    }
}