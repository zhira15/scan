package com.scanner.document.docscanner.di.components

import com.scanner.document.docscanner.ScannerAplication
import com.scanner.document.docscanner.di.modules.AppModule
import com.scanner.document.docscanner.di.modules.BuildersModule
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
        fun application(application: ScannerAplication): Builder
        fun build(): AppComponent
    }

    fun inject(app: ScannerAplication)

}