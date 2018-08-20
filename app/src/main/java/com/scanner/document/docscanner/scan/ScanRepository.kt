package com.scanner.document.docscanner.scan

import io.reactivex.Single

class ScanRepository {
    val say: Single<String>
        get() = Single.just("Hello from ScanRepository")
}