package com.example.freeupcopy.data.repository

import android.annotation.SuppressLint
import android.content.Context
import android.location.Geocoder
import android.os.Build
import com.example.freeupcopy.common.Resource
import com.example.freeupcopy.data.local.Address
import com.example.freeupcopy.data.local.AddressDao
import com.example.freeupcopy.data.pref.SwapGoPref
import com.example.freeupcopy.domain.model.AddressData
import com.example.freeupcopy.domain.model.StateAndCity
import com.example.freeupcopy.domain.repository.LocationRepository
import com.example.freeupcopy.utils.checkLocationPrerequisites
import com.google.android.gms.location.FusedLocationProviderClient
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.channelFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlinx.coroutines.tasks.await
import java.util.Locale
import kotlin.coroutines.resume

class LocationRepositoryImpl(
    private val addressDao: AddressDao,
    private val swapGoPref: SwapGoPref
): LocationRepository {

    override fun getAddresses(): Flow<Resource<List<Address>>> = channelFlow {
        send(Resource.Loading())
        try {
            combine(
                addressDao.getAddresses(),
                swapGoPref.getDefaultAddress()
            ) { addresses, default ->
                addresses.sortedByDescending { it.id == default }
            }.collect { sortedAddresses ->
                if (sortedAddresses.isNotEmpty()) {
                    val currentDefault = swapGoPref.getDefaultAddress().first()
                    if (currentDefault == null || currentDefault == 0) {
                        val firstAddress = sortedAddresses.first()
                        swapGoPref.saveDefaultAddress(firstAddress.id)
                    }
                }
                send(Resource.Success(sortedAddresses))
            }
        } catch (e: Exception) {
            send(Resource.Error("Failed to load addresses: ${e.message}"))
        }
    }.flowOn(Dispatchers.IO)


    override fun getAddressById(id: Int): Flow<Resource<Address?>> = channelFlow {
        send(Resource.Loading())
        try {
            val address = addressDao.getAddressById(id)
            send(Resource.Success(address))
        } catch (e: Exception) {
            send(Resource.Error("Failed to load address: ${e.message}"))
        }
    }


    override fun insertAddress(address: Address): Flow<Resource<Unit>> = channelFlow {
        send(Resource.Loading())
        try {
            addressDao.insertAddress(address)
            send(Resource.Success(Unit))
        } catch (e: Exception) {
            send(Resource.Error("Failed to save address: ${e.message}"))
        }
    }.flowOn(Dispatchers.IO)


    override fun deleteAddress(address: Address): Flow<Resource<Unit>> = channelFlow {
        send(Resource.Loading())
        try {
            addressDao.deleteAddress(address = address)

            val remainingAddresses = addressDao.getAddresses().first()
            if (remainingAddresses.isNotEmpty()) {
                if (swapGoPref.getDefaultAddress().first() == address.id) {
                    val lastAddress = remainingAddresses.last()
                    swapGoPref.saveDefaultAddress(lastAddress.id)
                }
            } else {
                swapGoPref.saveDefaultAddress(0)
            }
            send(Resource.Success(Unit))
        } catch (e: Exception) {
            send(Resource.Error("Failed to delete address: ${e.message}"))
        }
    }.flowOn(Dispatchers.IO)


    override fun getDefaultAddressId(): Flow<Int?> =
        swapGoPref.getDefaultAddress().flowOn(Dispatchers.IO)


    override fun setDefaultAddress(addressId: Int): Flow<Resource<Unit>> = channelFlow {
        send(Resource.Loading())
        try {
            swapGoPref.saveDefaultAddress(address = addressId)
            send(Resource.Success(Unit))
        } catch (e: Exception) {
            send(Resource.Error("Failed to set default address: ${e.message}"))
        }
    }.flowOn(Dispatchers.IO)


    @SuppressLint("MissingPermission")
    override suspend fun fetchLocationAndAddress(
        context: Context,
        fusedLocationClient: FusedLocationProviderClient
    ): Flow<Resource<AddressData>> = flow {
        emit(Resource.Loading())
        if (!checkLocationPrerequisites(context)) {
            emit(Resource.Error("Location or internet is turned off"))
            return@flow
        }
        try {
            val location = fusedLocationClient.lastLocation.await()
                ?: throw Exception("Unable to fetch location")

            val addresses = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                suspendCancellableCoroutine { continuation ->
                    val geocoder = Geocoder(context, Locale.getDefault())
                    geocoder.getFromLocation(location.latitude, location.longitude, 1) { addresses ->
                        continuation.resume(addresses)
                    }
                }
            } else {
                @Suppress("DEPRECATION")
                Geocoder(context, Locale.getDefault())
                    .getFromLocation(location.latitude, location.longitude, 1)
                    ?: emptyList()
            }

            if (addresses.isEmpty()) {
                emit(Resource.Error("Unable to fetch address details"))
                return@flow
            }

            val address = addresses[0]
            emit(
                Resource.Success(
                    AddressData(
                        roadName = address.thoroughfare ?: "",
                        pincode = address.postalCode ?: "",
                        state = address.adminArea ?: "",
                        city = address.locality ?: address.subAdminArea ?: "",
                        landmark = address.featureName ?: ""
                    )
                )
            )
        } catch (e: Exception) {
            emit(Resource.Error(e.message ?: "An unknown error occurred"))
        }
    }.flowOn(Dispatchers.IO)


    override suspend fun fetchStateAndCityFromPincode(
        context: Context,
        pincode: String
    ): Flow<Resource<StateAndCity>> = flow {
        emit(Resource.Loading())

        try {
            val geocoder = Geocoder(context, Locale("en", "IN"))
            val searchQuery = "$pincode, India"

            val stateAndCity = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                suspendCancellableCoroutine { continuation ->
                    geocoder.getFromLocationName(searchQuery, 1) { addresses ->
                        if (addresses.isNotEmpty() && addresses[0].countryCode == "IN") {
                            val address = addresses[0]
                            continuation.resume(
                                StateAndCity(
                                    state = address.adminArea,
                                    city = address.locality ?: address.subAdminArea
                                )
                            )
                        } else {
                            continuation.resume(null)
                        }
                    }
                }
            } else {
                @Suppress("DEPRECATION")
                val addresses = geocoder.getFromLocationName(searchQuery, 1)
                if (!addresses.isNullOrEmpty() && addresses[0].countryCode == "IN") {
                    val address = addresses[0]
                    StateAndCity(
                        state = address.adminArea,
                        city = address.locality ?: address.subAdminArea
                    )
                } else null
            }

            if (stateAndCity?.state != null && stateAndCity.city != null) {
                emit(Resource.Success(stateAndCity))
            } else {
                emit(Resource.Error("Unable to fetch state and city for the provided pincode"))
            }

        } catch (e: Exception) {
            emit(Resource.Error(e.message ?: "An unknown error occurred"))
        }
    }.flowOn(Dispatchers.IO)
}