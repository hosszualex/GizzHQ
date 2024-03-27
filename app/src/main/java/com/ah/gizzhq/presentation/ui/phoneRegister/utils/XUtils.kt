package com.ah.gizzhq.presentation.ui.phoneRegister.utils

import android.content.Context
import android.telephony.TelephonyManager
import androidx.compose.ui.text.intl.Locale
import com.google.i18n.phonenumbers.PhoneNumberUtil
import com.google.i18n.phonenumbers.Phonenumber

fun getDefaultLangCode(context: Context): String {
    val localeCode: TelephonyManager =
        context.getSystemService(Context.TELEPHONY_SERVICE) as TelephonyManager
    val countryCode = localeCode.networkCountryIso
    val defaultLocale = Locale.current.language
    return countryCode?.ifBlank { defaultLocale }?:defaultLocale
}
fun checkPhoneNumber(phone: String, fullPhoneNumber: String, countryCode: String): Boolean {
    val number: Phonenumber.PhoneNumber?
    if (phone.length > 6) {
        return try {
            number = PhoneNumberUtil.getInstance().parse(
                fullPhoneNumber,
                Phonenumber.PhoneNumber.CountryCodeSource.UNSPECIFIED.name
            )
            PhoneNumberUtil.getInstance().isValidNumberForRegion(number, countryCode.uppercase())
        } catch (ex: Exception) {
            false
        }
    }
    return false
}
