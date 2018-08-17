package com.scanner.document.document_scanner.scan

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import com.scanner.document.document_scanner.common.domain.interactors.LoadCommonCameraUseCase
import com.scanner.document.document_scanner.common.domain.interactors.LoadDataUseCase
import com.scanner.document.document_scanner.common.viewmodel.Response
import com.scanner.document.document_scanner.rx.SchedulersFacade
import io.reactivex.disposables.CompositeDisposable


class ScanViewModel(private val loadCommonCameraUseCase: LoadCommonCameraUseCase,
                             private val loadScanUseCase: LoadScanUseCase,
                             private val schedulersFacade: SchedulersFacade) : ViewModel() {

    val disposables = CompositeDisposable()

    val response = MutableLiveData<Response>()

    override fun onCleared() {
        disposables.clear()
    }

    fun loadCommonCamera() {
        loadData(loadCommonCameraUseCase)
    }

    fun loadScanData() {
        loadData(loadScanUseCase)
    }

    fun response(): MutableLiveData<Response> {
        return response
    }

    private fun loadData(loadDataUseCase: LoadDataUseCase) {
        disposables.add(loadDataUseCase.execute()
                .subscribeOn(schedulersFacade.io())
                .observeOn(schedulersFacade.ui())
                .doOnSubscribe { response.setValue(Response.loading()) }
                .subscribe(
                        { data -> response.setValue(Response.success(data)) },
                        { throwable -> response.setValue(Response.error(throwable)) }
                )
        )
    }
}