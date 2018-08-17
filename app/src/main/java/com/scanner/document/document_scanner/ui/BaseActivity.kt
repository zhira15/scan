package com.scanner.document.document_scanner.ui

import dagger.android.support.DaggerAppCompatActivity
import android.os.Bundle
import android.support.annotation.LayoutRes

abstract class BaseActivity : DaggerAppCompatActivity() {

    @LayoutRes
    abstract fun layoutRes(): Int

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(layoutRes())

    }

}