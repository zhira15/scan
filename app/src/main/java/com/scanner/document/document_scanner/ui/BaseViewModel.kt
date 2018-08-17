package com.scanner.document.document_scanner.ui

import android.app.Application
import android.arch.lifecycle.ViewModel
import io.reactivex.disposables.CompositeDisposable

abstract class BaseViewModel() : ViewModel() {
    val compositeDisposable = CompositeDisposable()
}