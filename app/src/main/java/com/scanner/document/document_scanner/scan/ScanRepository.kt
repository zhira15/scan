package com.scanner.document.document_scanner.scan

import io.reactivex.Single

class ScanRepository {
    val say: Single<String>
        get() = Single.just("Hello from ScanRepository")
}