package com.ah.gizzhq.xmaterialccp.utils

import android.content.Context
import com.ah.gizzhq.xmaterialccp.data.CountryData
import com.ah.gizzhq.xmaterialccp.data.utils.getCountryName

fun List<CountryData>.searchCountry(key: String, context: Context): MutableList<CountryData> {
    val tempList = mutableListOf<CountryData>()
    this.forEach {
        if (context.resources.getString(getCountryName(it.countryCode)).lowercase().contains(key.lowercase())) {
            tempList.add(it)
        }
    }
    return tempList
}