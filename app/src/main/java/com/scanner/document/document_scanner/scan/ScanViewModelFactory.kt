package com.scanner.document.document_scanner.scan

import android.arch.lifecycle.ViewModel
import com.scanner.document.document_scanner.rx.SchedulersFacade
import android.arch.lifecycle.ViewModelProvider
import com.scanner.document.document_scanner.common.domain.interactors.LoadCommonCameraUseCase


class ScanViewModelFactory(val loadCommonCameraUseCase: LoadCommonCameraUseCase,
                           val loadScanUseCase: LoadScanUseCase,
                           val schedulersFacade: SchedulersFacade): ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ScanViewModel::class.java!!)) {
            return ScanViewModel(loadCommonCameraUseCase, loadScanUseCase, schedulersFacade) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}