package com.scanner.document.docscanner.di.modules

import android.content.Context
import com.scanner.document.docscanner.di.qualifiers.ApplicationContextQualifiers
import com.scanner.document.docscanner.di.scopes.CameraApplicationScope
import dagger.Module
import dagger.Provides

/**
 * Created by AnthonyCAS on 8/21/18.
 */

@Module
class ContextModule(var context: Context) {

    @Provides
    @CameraApplicationScope
    @ApplicationContextQualifiers
    fun provideContext(): Context = context
}