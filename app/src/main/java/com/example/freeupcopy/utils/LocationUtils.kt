package com.example.freeupcopy.utils

import android.content.Context
import android.location.Geocoder
import java.util.Locale

private fun fetchStateAndCityFromPincode(
    context: Context,
    pincode: String,
    onFetched: (String?, String?) -> Unit
) {
    val geocoder = Geocoder(context, Locale.getDefault())
    try {
        val addresses = geocoder.getFromLocationName(pincode, 1)
        if (!addresses.isNullOrEmpty()) {
            val state = addresses[0].adminArea
            val city = addresses[0].locality ?: addresses[0].subAdminArea
            onFetched(state, city)
        } else {
            onFetched(null, null)
        }
    } catch (e: Exception) {
        onFetched(null, null)
    }
}