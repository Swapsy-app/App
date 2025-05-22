package com.example.freeupcopy.data.pref

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import com.example.freeupcopy.data.remote.dto.product.User
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
            // Also clear user data when refresh token is cleared
            it.remove(USER_ID_KEY)
            it.remove(USER_NAME_KEY)
            it.remove(USER_AVATAR_KEY)
        }
    }

    override fun getUser(): Flow<User?> = dataStore.data.map { preferences ->
        val id = preferences[USER_ID_KEY]
        val username = preferences[USER_NAME_KEY]
        val avatar = preferences[USER_AVATAR_KEY]

        if (id != null && username != null) {
            User(_id = id, username = username, avatar = avatar)
        } else {
            null
        }
    }

    override suspend fun saveUser(user: User) {
        dataStore.edit { preferences ->
            preferences[USER_ID_KEY] = user._id
            preferences[USER_NAME_KEY] = user.username
            preferences[USER_AVATAR_KEY] = user.avatar ?: ""
        }
    }

    override suspend fun clearUser() {
        dataStore.edit { preferences ->
            preferences.remove(USER_ID_KEY)
            preferences.remove(USER_NAME_KEY)
            preferences.remove(USER_AVATAR_KEY)
        }
    }
}