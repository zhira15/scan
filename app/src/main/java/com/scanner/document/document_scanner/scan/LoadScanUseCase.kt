package com.scanner.document.document_scanner.scan

import com.scanner.document.document_scanner.common.domain.interactors.LoadDataUseCase
import javax.inject.Inject

import io.reactivex.Single

class LoadScanUseCase @Inject
constructor(private val scanRepository: ScanRepository): LoadDataUseCase {

    override fun execute(): Single<String> {
        return scanRepository.say
    }
}