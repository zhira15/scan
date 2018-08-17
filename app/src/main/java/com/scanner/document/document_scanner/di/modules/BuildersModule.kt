package com.scanner.document.document_scanner.di.modules

import com.scanner.document.document_scanner.scan.ScanActivity
import com.scanner.document.document_scanner.scan.ScanModule
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