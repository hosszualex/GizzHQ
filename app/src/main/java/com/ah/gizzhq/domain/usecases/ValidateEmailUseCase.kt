package com.ah.gizzhq.domain.usecases

import androidx.core.util.PatternsCompat
import javax.inject.Inject

class ValidateEmailUseCase
    @Inject
    constructor() {
        operator fun invoke(value: String) = PatternsCompat.EMAIL_ADDRESS.matcher(value).matches()
    }
