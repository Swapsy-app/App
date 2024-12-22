package com.example.freeupcopy.data.pref

import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import com.example.freeupcopy.data.local.Address
import kotlinx.coroutines.flow.Flow

val DEFAULT_ADDRESS_KEY = intPreferencesKey("sell_default_address")
val GSTIN_KEY = stringPreferencesKey("sell_gstin")

interface SellPref {
    fun getDefaultAddress(): Flow<Int?>
    suspend fun saveDefaultAddress(address: Int)
    //suspend fun clearDefaultAddress()

    fun getGSTIN(): Flow<String?>
    suspend fun saveGSTIN(gstin: String)
    suspend fun clearGSTIN()
}