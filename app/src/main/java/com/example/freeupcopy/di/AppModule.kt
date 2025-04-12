package com.example.freeupcopy.di

import android.app.Application
import android.content.Context
import android.util.Log
import androidx.datastore.core.DataStore
import androidx.datastore.core.handlers.ReplaceFileCorruptionHandler
import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.preferencesDataStoreFile
import androidx.room.Room
import com.example.freeupcopy.common.Constants.BASE_URL
import com.example.freeupcopy.data.local.AddressDao
import com.example.freeupcopy.data.local.RecentSearchesDao
import com.example.freeupcopy.data.pref.SwapGoPref
import com.example.freeupcopy.data.pref.SwapGoPrefImpl
import com.example.freeupcopy.data.remote.SwapgoApi
import com.example.freeupcopy.data.remote.dto.auth.RefreshTokenRequest
import com.example.freeupcopy.data.remote.dto.auth.RefreshTokenResponse
import com.example.freeupcopy.data.repository.AuthRepositoryImpl
import com.example.freeupcopy.data.repository.LocationRepositoryImpl
import com.example.freeupcopy.data.repository.SellRepositoryImpl
import com.example.freeupcopy.data.repository.SellerProfileRepositoryImpl
import com.example.freeupcopy.db.SwapsyDatabase
import com.example.freeupcopy.domain.repository.AuthRepository
import com.example.freeupcopy.domain.repository.LocationRepository
import com.example.freeupcopy.domain.repository.SellRepository
import com.example.freeupcopy.domain.repository.SellerProfileRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import okhttp3.Authenticator
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import okhttp3.Route
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Inject
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun provideAuthInterceptor(swapGoPref: SwapGoPref): AuthInterceptor =
        AuthInterceptor(swapGoPref)

    @Singleton
    @Provides
    fun provideAuthAuthenticator(swapGoPref: SwapGoPref): AuthAuthenticator =
        AuthAuthenticator(swapGoPref)

    @Provides
    @Singleton
    fun provideOkHttpClient(
        authInterceptor: AuthInterceptor,
        authAuthenticator: AuthAuthenticator
    ): OkHttpClient {
        val loggingInterceptor = HttpLoggingInterceptor()
        loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY

        return OkHttpClient.Builder()
            .addInterceptor(authInterceptor)
            .addInterceptor(loggingInterceptor)
            .authenticator(authAuthenticator)
            .build()
    }

    @Provides
    @Singleton
    fun provideSwapgoApi(okHttpClient: OkHttpClient): SwapgoApi {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(SwapgoApi::class.java)
    }

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
    fun providesSellerProfileRepository(api: SwapgoApi): SellerProfileRepository {
        return SellerProfileRepositoryImpl(api)
    }

    @Provides
    @Singleton
    fun providesSellRepository(api: SwapgoApi): SellRepository {
        return SellRepositoryImpl(api)
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
    fun provideRecentSearchesDao(db: SwapsyDatabase): RecentSearchesDao = db.recentSearchesDao

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

class AuthInterceptor @Inject constructor(
    private val swapGoPref: SwapGoPref,
): Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val token = runBlocking {
            swapGoPref.getAccessToken().first()
        }
        val request = chain.request().newBuilder()
//        Log.e("ForgotViewModel", token.toString())
        request.addHeader("Authorization", "Bearer $token")
        return chain.proceed(request.build())
    }
}

class AuthAuthenticator @Inject constructor(
    private val swapGoPref: SwapGoPref,
): Authenticator {

    override fun authenticate(route: Route?, response: Response): Request? {

        Log.e("ForgotViewModel", "AuthAuthenticator")

        val token = runBlocking {
            swapGoPref.getRefreshToken().first()
        }
        return runBlocking {
            val newToken = getNewToken(token)

            if (!newToken.isSuccessful || newToken.body() == null) { //Couldn't refresh the token, so restart the login process
                swapGoPref.clearRefreshToken()
                swapGoPref.clearAccessToken()
            }

            newToken.body()?.let {
                swapGoPref.saveAccessToken(it.accessToken)
                response.request.newBuilder()
                    .header("Authorization", "Bearer ${it.accessToken}")
                    .build()
            }
        }
    }

    private suspend fun getNewToken(refreshToken: String?): retrofit2.Response<RefreshTokenResponse> {
        val loggingInterceptor = HttpLoggingInterceptor()
        loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
        val okHttpClient = OkHttpClient.Builder().addInterceptor(loggingInterceptor).build()

        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)
            .build()
        val service = retrofit.create(SwapgoApi::class.java)
        return service.refreshToken(RefreshTokenRequest(refreshToken ?: ""))
    }
}