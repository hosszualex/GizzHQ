package com.ah.gizzhq.domain.usecases

import com.ah.gizzhq.data.repositories.data.DataRepository
import javax.inject.Inject

class GetPhoneNumberCountryCodesUseCase
    @Inject
    constructor(
        private val dataRepository: DataRepository,
    ) {
        suspend operator fun invoke() = dataRepository.getPhoneNumberCountryCode()
    }
