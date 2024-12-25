package com.example.freeupcopy.di

import android.app.Application
import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.core.handlers.ReplaceFileCorruptionHandler
import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.preferencesDataStoreFile
import androidx.room.Room
import com.example.freeupcopy.data.local.AddressDao
import com.example.freeupcopy.data.pref.SellPref
import com.example.freeupcopy.data.pref.SellPrefImpl
import com.example.freeupcopy.data.repository.LocationRepositoryImpl
import com.example.freeupcopy.db.SwapsyDatabase
import com.example.freeupcopy.domain.repository.LocationRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

//    @Provides
//    @Singleton
//    fun providesChatRepository(api: TodoApi): TodoRepository {
//        return TodoRepositoryImpl(todoApi = api)
//    }
    @Provides
    @Singleton
    fun providesLocationRepository(addressDao: AddressDao, sellPref: SellPref): LocationRepository {
        return LocationRepositoryImpl(addressDao = addressDao, sellPref = sellPref)
    }

    @Provides
    @Singleton
    fun provideSwapsyDatabase(app: Application): SwapsyDatabase {
        return Room.databaseBuilder(
            app,
            SwapsyDatabase::class.java,
            "swapsy.db"
        ).build()
    }

    @Provides
    @Singleton
    fun provideAddressDao(db: SwapsyDatabase) = db.addressDao

    @Provides
    @Singleton
    fun providesDataStore(@ApplicationContext context: Context): DataStore<Preferences> {
        return PreferenceDataStoreFactory.create(
            corruptionHandler = ReplaceFileCorruptionHandler(
                produceNewData = { emptyPreferences() }
            ),
            produceFile = {
                context.preferencesDataStoreFile("sell_data")
            }
        )
    }
    @Provides
    fun providesSellPref(dataStore: DataStore<Preferences>): SellPref = SellPrefImpl(dataStore)
}