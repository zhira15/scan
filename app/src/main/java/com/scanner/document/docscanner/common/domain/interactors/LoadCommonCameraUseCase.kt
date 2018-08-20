package com.scanner.document.docscanner.common.domain.interactors

import com.scanner.document.docscanner.common.domain.model.CommonCameragRepository
import javax.inject.Inject
import io.reactivex.Single

class LoadCommonCameraUseCase @Inject
constructor(private val commonCameragRepository: CommonCameragRepository) : LoadDataUseCase {

    override fun execute(): Single<String> {
        return commonCameragRepository.say
    }
}