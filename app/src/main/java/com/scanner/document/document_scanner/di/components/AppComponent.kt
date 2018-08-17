package com.scanner.document.document_scanner.di.components

import com.scanner.document.document_scanner.App
import com.scanner.document.document_scanner.di.modules.ActivityInjectorsModule
import com.scanner.document.document_scanner.di.modules.AppModule
import com.scanner.document.document_scanner.di.modules.FragmentsInjectorsModule
import dagger.BindsInstance
import dagger.Component
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton

@Singleton
@Component(modules = [
    AndroidSupportInjectionModule::class,
    ActivityInjectorsModule::class,
    FragmentsInjectorsModule::class,
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