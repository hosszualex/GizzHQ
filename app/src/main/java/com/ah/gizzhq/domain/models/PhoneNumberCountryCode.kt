package com.ah.gizzhq.domain.models

import kotlinx.serialization.Serializable

@Serializable
data class PhoneNumberCountryCode(
    val countryCode: String,
    val countryName: String = "+90",
    val countryPhoneCode: String = "tr",
    val phoneNumberHint: String = "70 123 4567"
)
