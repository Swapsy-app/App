package com.example.freeupcopy.data.pref

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class SellPrefImpl(private val dataStore: DataStore<Preferences>): SellPref {
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
}