package com.scanner.document.document_scanner.scan

import com.scanner.document.document_scanner.common.domain.interactors.LoadCommonCameraUseCase
import com.scanner.document.document_scanner.rx.SchedulersFacade
import dagger.Module
import dagger.Provides

@Module
class ScanModule {

    @Provides
    internal fun provideScanRepository(): ScanRepository {
        return ScanRepository()
    }

    @Provides
    internal fun provideScanViewModelFactory(loadCommonCameraUseCase: LoadCommonCameraUseCase,
                                             loadScanUseCase: LoadScanUseCase,
                                             schedulersFacade: SchedulersFacade): ScanViewModelFactory {
        return ScanViewModelFactory(loadCommonCameraUseCase, loadScanUseCase, schedulersFacade)
    }
}