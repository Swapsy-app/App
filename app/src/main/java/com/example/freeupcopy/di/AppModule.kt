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
import com.example.freeupcopy.data.local.RecentSearchesDao
import com.example.freeupcopy.data.pref.SwapGoPref
import com.example.freeupcopy.data.pref.SwapGoPrefImpl
import com.example.freeupcopy.data.remote.SwapgoApi
import com.example.freeupcopy.data.remote.dto.auth.RefreshTokenRequest
import com.example.freeupcopy.data.remote.dto.auth.RefreshTokenResponse
import com.example.freeupcopy.data.repository.AuthRepositoryImpl
import com.example.freeupcopy.data.repository.CartRepositoryImpl
import com.example.freeupcopy.data.repository.LocationRepositoryImpl
import com.example.freeupcopy.data.repository.ProductRepositoryImpl
import com.example.freeupcopy.data.repository.SellRepositoryImpl
import com.example.freeupcopy.data.repository.ProfileRepositoryImpl
import com.example.freeupcopy.db.SwapsyDatabase
import com.example.freeupcopy.domain.repository.AuthRepository
import com.example.freeupcopy.domain.repository.CartRepository
import com.example.freeupcopy.domain.repository.LocationRepository
import com.example.freeupcopy.domain.repository.ProductRepository
import com.example.freeupcopy.domain.repository.SellRepository
import com.example.freeupcopy.domain.repository.ProfileRepository
import com.example.freeupcopy.ui.navigation.AuthStateManager
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
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

    @Provides
    @Singleton
    fun provideAuthStateManager(swapGoPref: SwapGoPref): AuthStateManager {
        return AuthStateManager(swapGoPref)
    }

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
    fun providesLocationRepository(): LocationRepository {
        return LocationRepositoryImpl()
    }

    @Provides
    @Singleton
    fun providesSellerProfileRepository(api: SwapgoApi): ProfileRepository {
        return ProfileRepositoryImpl(api)
    }

    @Provides
    @Singleton
    fun providesSellRepository(api: SwapgoApi): SellRepository {
        return SellRepositoryImpl(api)
    }

    @Provides
    @Singleton
    fun provideProductRepository(api: SwapgoApi): ProductRepository {
        return ProductRepositoryImpl(api)
    }

    @Provides
    @Singleton
    fun provideCartRepository(api: SwapgoApi): CartRepository {
        return CartRepositoryImpl(api)
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
    fun provideRecentlyViewedDao(db: SwapsyDatabase) = db.recentlyViewedDao

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

    // Create a new class in your di package
    @Singleton
    class WishlistStateManager @Inject constructor() {
        private val _wishlistUpdates = MutableSharedFlow<Pair<String, Boolean>>(extraBufferCapacity = 1)
        val wishlistUpdates = _wishlistUpdates.asSharedFlow()

        suspend fun notifyWishlistChanged(productId: String, isWishlisted: Boolean) {
            _wishlistUpdates.emit(Pair(productId, isWishlisted))
        }
    }

    @Provides
    @Singleton
    fun provideWishlistStateManager(): WishlistStateManager {
        return WishlistStateManager()
    }
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
    private val authStateManager: AuthStateManager
): Authenticator {

    override fun authenticate(route: Route?, response: Response): Request? {
        Log.e("AuthAuthenticator", "Token refresh attempt")

        val token = runBlocking {
            swapGoPref.getRefreshToken().first()
        }

        // If no refresh token exists, signal unauthenticated state
        if (token.isNullOrEmpty()) {
            runBlocking {
                authStateManager.setUnauthenticated()
            }
            return null
        }

        return runBlocking {
            val newToken = getNewToken(token)

            if (!newToken.isSuccessful || newToken.body() == null) {
                // Token refresh failed - trigger logout flow
                authStateManager.setUnauthenticated()
                return@runBlocking null
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