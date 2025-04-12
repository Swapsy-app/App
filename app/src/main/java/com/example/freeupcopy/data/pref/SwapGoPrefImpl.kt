package com.example.freeupcopy.data.pref

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class SwapGoPrefImpl(private val dataStore: DataStore<Preferences>) : SwapGoPref {
    override fun getDefaultAddress(): Flow<Int> = dataStore.data.map { preferences ->
        preferences[DEFAULT_ADDRESS_KEY] ?: 0
    }

    override suspend fun saveDefaultAddress(address: Int) {
        dataStore.edit {
            it[DEFAULT_ADDRESS_KEY] = address
        }
    }

    override fun getGSTIN(): Flow<String?> = dataStore.data.map { preferences ->
        preferences[GSTIN_KEY] ?: ""
    }

    override suspend fun saveGSTIN(gstin: String) {
        dataStore.edit {
            it[GSTIN_KEY] = gstin
        }
    }

    override suspend fun clearGSTIN() {
        dataStore.edit {
            it.remove(GSTIN_KEY)
        }
    }

    //    override fun getUserToken(): Flow<String?> = dataStore.data.map { preferences ->
//        preferences[USER_KEY] ?: ""
//    }
//
//    override suspend fun saveUserToken(user: String) {
//        dataStore.edit {
//            it[USER_KEY] = user
//        }
//    }
//
//    override suspend fun clearUserToken() {
//        dataStore.edit {
//            it.remove(USER_KEY)
//        }
//    }
    override fun getAccessToken(): Flow<String?> {
        return dataStore.data.map { preferences ->
            preferences[ACCESS_TOKEN_KEY]
        }
    }

    override suspend fun saveAccessToken(token: String) {
        dataStore.edit {
            it[ACCESS_TOKEN_KEY] = token
        }
    }

    override suspend fun clearAccessToken() {
        dataStore.edit {
            it.remove(ACCESS_TOKEN_KEY)
        }
    }

    override fun getRefreshToken(): Flow<String?> {
        return dataStore.data.map { preferences ->
            preferences[REFRESH_TOKEN_KEY]
        }
    }

    override suspend fun saveRefreshToken(token: String) {
        dataStore.edit {
            it[REFRESH_TOKEN_KEY] = token
        }
    }

    override suspend fun clearRefreshToken() {
        dataStore.edit {
            it.remove(REFRESH_TOKEN_KEY)
        }
    }
}