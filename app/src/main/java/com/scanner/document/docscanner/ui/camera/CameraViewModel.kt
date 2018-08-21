package com.scanner.document.docscanner.ui.camera

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import android.arch.lifecycle.MutableLiveData
import android.databinding.ObservableBoolean
import com.scanner.document.docscanner.util.SingleLiveEvent
import io.reactivex.disposables.CompositeDisposable
import timber.log.Timber

/**
 * Created by AnthonyCAS on 8/20/18.
 */

class CameraViewModel(context: Application): AndroidViewModel(context) {

    //region variables
    internal val actionTakePicture = SingleLiveEvent<Void>()
    internal val isFlashActive = SingleLiveEvent<Boolean>()
    internal val actionExtraOptions = SingleLiveEvent<Boolean>()

    val isSettingActive = ObservableBoolean(false)
    private val disposables = CompositeDisposable()
    //endregion

    //region Lifecycle
    init {
    }

    override fun onCleared() {
        super.onCleared()
    }
    //endregion

    //region Bind
    private fun bind() {

    }
    //endregion

    //region Button Actions
    fun attempTakePicture() {
        actionTakePicture.call()
    }

    fun atttempUseSettings() {
        val option = isSettingActive.get().not()
        isSettingActive.set(option)
        actionExtraOptions.value = option
    }

    fun turnOnFlash() {
        isFlashActive.value = true
    }

    fun turnOffFlash() {
        isFlashActive.value = false
    }
    //endregion
}