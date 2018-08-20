package com.scanner.document.docscanner.scan

import com.scanner.document.docscanner.common.domain.interactors.LoadDataUseCase
import javax.inject.Inject

import io.reactivex.Single

class LoadScanUseCase @Inject
constructor(private val scanRepository: ScanRepository): LoadDataUseCase {

    override fun execute(): Single<String> {
        return scanRepository.say
    }
}