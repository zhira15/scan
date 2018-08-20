package com.scanner.document.docscanner.common.domain.model

import io.reactivex.Single

class CommonCameragRepository {
    val say: Single<String>
        get() = Single.just("Hello from CommonCameraRepository")
}