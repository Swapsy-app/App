package com.example.freeupcopy.utils

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.location.Geocoder
import android.location.LocationManager
import android.net.ConnectivityManager
import android.util.Log
import androidx.core.content.ContextCompat
import java.util.Locale

fun checkLocationPrerequisites(context: Context): Boolean {
    val locationManager = context.getSystemService(Context.LOCATION_SERVICE) as LocationManager
    val networkManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

    val isLocationEnabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) ||
            locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)

    val hasInternetConnection = networkManager.activeNetwork != null

    val hasLocationPermission = ContextCompat.checkSelfPermission(
        context,
        Manifest.permission.ACCESS_FINE_LOCATION
    ) == PackageManager.PERMISSION_GRANTED

    Log.d("LocationDebug", """
        Location enabled: $isLocationEnabled
        Internet connection: $hasInternetConnection
        Location permission: $hasLocationPermission
    """.trimIndent())

    return isLocationEnabled && hasInternetConnection && hasLocationPermission
}