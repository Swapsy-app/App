package com.example.freeupcopy.data.pref

import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import com.example.freeupcopy.data.remote.dto.product.User
import kotlinx.coroutines.flow.Flow

val DEFAULT_ADDRESS_KEY = intPreferencesKey("sell_default_address")
val GSTIN_KEY = stringPreferencesKey("sell_gstin")

val ACCESS_TOKEN_KEY = stringPreferencesKey("token_key")
val REFRESH_TOKEN_KEY = stringPreferencesKey("refresh_token_key")

// Add these keys
val USER_ID_KEY = stringPreferencesKey("user_id")
val USER_NAME_KEY = stringPreferencesKey("user_name")
val USER_AVATAR_KEY = stringPreferencesKey("user_avatar")

interface SwapGoPref {
    fun getDefaultAddress(): Flow<Int?>
    suspend fun saveDefaultAddress(address: Int)

    fun getGSTIN(): Flow<String?>
    suspend fun saveGSTIN(gstin: String)
    suspend fun clearGSTIN()

    fun getAccessToken(): Flow<String?>
    suspend fun saveAccessToken(token: String)
    suspend fun clearAccessToken()

    fun getRefreshToken(): Flow<String?>
    suspend fun saveRefreshToken(token: String)
    suspend fun clearRefreshToken()

    // Add these methods for user data
    fun getUser(): Flow<User?>
    suspend fun saveUser(user: User)
    suspend fun clearUser()
}