package com.scanner.document.document_scanner.common.viewmodel

import android.support.annotation.NonNull
import android.support.annotation.Nullable

/**
 * Response holder provided to the UI
 */
class Response private constructor(
        val status: Status,
        @param:Nullable @field:Nullable val data: String?,
        @param:Nullable @field:Nullable val error: Throwable?) {

    companion object {

        fun loading(): Response {
            return Response(Status.LOADING, null, null)
        }

        fun success(@NonNull data: String): Response {
            return Response(Status.SUCCESS, data, null)
        }

        fun error(@NonNull error: Throwable): Response {
            return Response(Status.ERROR, null, error)
        }
    }
}