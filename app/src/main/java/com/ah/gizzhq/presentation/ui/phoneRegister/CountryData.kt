package com.ah.gizzhq.presentation.ui.phoneRegister

import java.util.*

data class CountryData(
    private val cCodes: String,
    val countryPhoneCode: String = "+90",
    val cNames:String = "tr",
) {
    val countryCode = cCodes.lowercase(Locale.getDefault())
}
