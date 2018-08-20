package com.scanner.document.docscanner.common.domain.interactors

import io.reactivex.Single

interface LoadDataUseCase {
    fun execute(): Single<String>
}