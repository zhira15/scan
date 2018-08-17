package com.scanner.document.document_scanner.common.domain.interactors

import io.reactivex.Single

interface LoadDataUseCase {
    fun execute(): Single<String>
}