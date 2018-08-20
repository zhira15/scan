package com.scanner.document.docscanner.scan

import com.scanner.document.docscanner.common.domain.interactors.LoadCommonCameraUseCase
import com.scanner.document.docscanner.rx.SchedulersFacade
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