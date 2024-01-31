package com.ah.gizzhq.domain.usecases

import java.util.regex.Pattern
import javax.inject.Inject

class ValidatePasswordUseCase @Inject constructor() {

    // Regex pattern which makes sure a word contains a small character, large character and a number
    private val passwordPattern = Pattern.compile("^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=\\S+$).{8,}$")
    operator fun invoke(value: String) = passwordPattern.matcher(value).matches() && value.isNotEmpty() && value.length > 8
}