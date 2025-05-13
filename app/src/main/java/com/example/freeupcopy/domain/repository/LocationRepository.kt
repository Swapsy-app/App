package com.example.freeupcopy.domain.repository

import android.content.Context
import com.example.freeupcopy.common.Resource
import com.example.freeupcopy.data.local.Address
import com.example.freeupcopy.domain.model.AddressData
import com.example.freeupcopy.domain.model.StateAndCity
import com.google.android.gms.location.FusedLocationProviderClient
import kotlinx.coroutines.flow.Flow

interface LocationRepository {
    suspend fun fetchLocationAndAddress(context: Context, fusedLocationClient: FusedLocationProviderClient): Flow<Resource<AddressData>>

    suspend fun fetchStateAndCityFromPincode(context: Context, pincode: String): Flow<Resource<StateAndCity>>
}