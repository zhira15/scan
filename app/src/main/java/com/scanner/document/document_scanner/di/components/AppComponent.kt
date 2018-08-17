package com.scanner.document.document_scanner.di.components

import com.scanner.document.document_scanner.App
import com.scanner.document.document_scanner.di.modules.AppModule
import com.scanner.document.document_scanner.di.modules.BuildersModule
import dagger.BindsInstance
import dagger.Component
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton

@Singleton
@Component(modules = [
    AndroidSupportInjectionModule::class,
    BuildersModule::class,
    AppModule::class])
interface AppComponent {

    @Component.Builder
    interface Builder {
        @BindsInstance
        fun application(application: App): Builder
        fun build(): AppComponent
    }

    fun inject(app: App)

}