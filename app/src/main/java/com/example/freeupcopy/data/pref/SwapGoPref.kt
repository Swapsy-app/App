package com.example.freeupcopy.data.pref

import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import kotlinx.coroutines.flow.Flow

val DEFAULT_ADDRESS_KEY = intPreferencesKey("sell_default_address")
val GSTIN_KEY = stringPreferencesKey("sell_gstin")

val ACCESS_TOKEN_KEY = stringPreferencesKey("token_key")
val REFRESH_TOKEN_KEY = stringPreferencesKey("refresh_token_key")

interface SwapGoPref {
    fun getDefaultAddress(): Flow<Int?>
    suspend fun saveDefaultAddress(address: Int)
    //suspend fun clearDefaultAddress()

    fun getGSTIN(): Flow<String?>
    suspend fun saveGSTIN(gstin: String)
    suspend fun clearGSTIN()

//    fun getUserToken(): Flow<String?>
//    suspend fun saveUserToken(user: String)
//    suspend fun clearUserToken()

    fun getAccessToken(): Flow<String?>
    suspend fun saveAccessToken(token: String)
    suspend fun clearAccessToken()

    fun getRefreshToken(): Flow<String?>
    suspend fun saveRefreshToken(token: String)
    suspend fun clearRefreshToken()
}