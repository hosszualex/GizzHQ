package com.ah.gizzhq.domain.utils

import android.content.Context
import android.graphics.Bitmap
import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Build
import android.provider.MediaStore
import android.util.Log
import com.ah.gizzhq.presentation.ui.phoneRegister.CountryData
import com.ah.gizzhq.presentation.ui.phoneRegister.utils.getCountryName

fun defaultLog(message: String) {
    Log.d("=======>", message)
}

fun Uri.toBitmap(context: Context): Bitmap? {
    val bitmap: Bitmap? =
        if (Build.VERSION.SDK_INT < 28) {
            MediaStore.Images
                .Media.getBitmap(context.contentResolver, this)
        } else {
            val source =
                ImageDecoder
                    .createSource(context.contentResolver, this)
            ImageDecoder.decodeBitmap(source)
        }

    return bitmap
}

fun List<CountryData>.searchCountry(key: String, context: Context): MutableList<CountryData> {
    val tempList = mutableListOf<CountryData>()
    this.forEach {
        if (context.resources.getString(getCountryName(it.countryCode)).lowercase().contains(key.lowercase())) {
            tempList.add(it)
        }
    }
    return tempList
}