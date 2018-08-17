package com.scanner.document.document_scanner.ui

import android.os.Bundle
import android.support.annotation.LayoutRes
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import dagger.android.support.DaggerFragment

abstract class BaseFragment : DaggerFragment() {

    @LayoutRes
    abstract fun layoutRes(): Int

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(layoutRes(), container, false)
    }
}