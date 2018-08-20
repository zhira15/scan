package com.scanner.document.docscanner.di.modules

import com.scanner.document.docscanner.scan.ScanActivity
import com.scanner.document.docscanner.scan.ScanModule
import dagger.Module
import dagger.android.ContributesAndroidInjector

/**
 * It binds all sub-components within the app.
 */
@Module
abstract class BuildersModule {

    @ContributesAndroidInjector(modules = arrayOf(ScanModule::class))
    internal abstract fun bindScanActivity(): ScanActivity
}