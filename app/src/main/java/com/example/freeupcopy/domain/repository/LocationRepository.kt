package com.example.freeupcopy.domain.repository

import android.content.Context
import com.example.freeupcopy.common.Resource
import com.example.freeupcopy.data.local.Address
import com.example.freeupcopy.domain.model.AddressData
import com.example.freeupcopy.domain.model.StateAndCity
import com.google.android.gms.location.FusedLocationProviderClient
import kotlinx.coroutines.flow.Flow

interface LocationRepository {

    fun getAddresses(): Flow<Resource<List<Address>>>

    fun getAddressById(id: Int): Flow<Resource<Address?>>

    fun insertAddress(address: Address): Flow<Resource<Unit>>

    fun setDefaultAddress(addressId: Int): Flow<Resource<Unit>>

    fun deleteAddress(address: Address): Flow<Resource<Unit>>

    fun getDefaultAddressId(): Flow<Int?>

    suspend fun fetchLocationAndAddress(context: Context, fusedLocationClient: FusedLocationProviderClient): Flow<Resource<AddressData>>

    suspend fun fetchStateAndCityFromPincode(context: Context, pincode: String): Flow<Resource<StateAndCity>>
}