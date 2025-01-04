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
import com.example.freeupcopy.common.Constants.BASE_URL
import com.example.freeupcopy.data.local.AddressDao
import com.example.freeupcopy.data.pref.SwapGoPref
import com.example.freeupcopy.data.pref.SwapGoPrefImpl
import com.example.freeupcopy.data.remote.SwapgoApi
import com.example.freeupcopy.data.repository.AuthRepositoryImpl
import com.example.freeupcopy.data.repository.LocationRepositoryImpl
import com.example.freeupcopy.db.SwapsyDatabase
import com.example.freeupcopy.domain.repository.AuthRepository
import com.example.freeupcopy.domain.repository.LocationRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideOkHttpClient(): OkHttpClient {
        val loggingInterceptor = HttpLoggingInterceptor()
        loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY

        return OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .build()
    }

    @Provides
    @Singleton
    fun provideTodoApi(okHttpClient: OkHttpClient): SwapgoApi {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(SwapgoApi::class.java)
    }

//    @Provides
//    @Singleton
//    fun providesChatRepository(api: TodoApi): TodoRepository {
//        return TodoRepositoryImpl(todoApi = api)
//    }

    @Provides
    @Singleton
    fun provideAuthRepository(api: SwapgoApi, swapGoPref: SwapGoPref): AuthRepository {
        return AuthRepositoryImpl(api, swapGoPref)
    }

    @Provides
    @Singleton
    fun providesLocationRepository(addressDao: AddressDao, swapGoPref: SwapGoPref): LocationRepository {
        return LocationRepositoryImpl(addressDao = addressDao, swapGoPref = swapGoPref)
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
    fun providesSellPref(dataStore: DataStore<Preferences>): SwapGoPref = SwapGoPrefImpl(dataStore)
}