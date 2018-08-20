package com.scanner.document.docscanner.ui.home

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import com.scanner.document.docscanner.util.SingleLiveEvent
import io.reactivex.disposables.CompositeDisposable

/**
 * Created by AnthonyCAS on 8/20/18.
 */

class HomeViewModel(context: Application): AndroidViewModel(context) {

    private val disposables = CompositeDisposable()

    internal val openCamera = SingleLiveEvent<Void>()

    //region Lifecycle
    init {

    }

    override fun onCleared() {
        super.onCleared()
    }
    //endregion

    private fun bind () {

    }

    fun attemptScanDoc() {
        openCamera.call()
    }

}