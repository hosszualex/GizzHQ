package com.ah.gizzhq.domain.usecases

import javax.inject.Inject

class ValidatePasswordUseCase @Inject constructor() {

    // Regex pattern which makes sure a word contains a small character, large character and a number
    private val passwordPattern = Regex("^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=\\S+$).{8,}$")
    operator fun invoke(value: String) = passwordPattern.matches(value) && value.isNotEmpty() && value.length > 8
}