package com.example.freeupcopy.data.repository

import android.util.Log
import com.example.freeupcopy.common.Resource
import com.example.freeupcopy.data.pref.SwapGoPref
import com.example.freeupcopy.data.remote.SwapgoApi
import com.example.freeupcopy.data.remote.dto.auth.AuthResponse
import com.example.freeupcopy.data.remote.dto.auth.ForgotPasswordRequest
import com.example.freeupcopy.data.remote.dto.auth.LoginRequest
import com.example.freeupcopy.data.remote.dto.auth.LoginResponse
import com.example.freeupcopy.data.remote.dto.auth.LoginStatusResponse
import com.example.freeupcopy.data.remote.dto.auth.OtpRequest
import com.example.freeupcopy.data.remote.dto.auth.OtpResendRequest
import com.example.freeupcopy.data.remote.dto.auth.SignUpOtpVerifyResponse
import com.example.freeupcopy.data.remote.dto.auth.SignUpRequest
import com.example.freeupcopy.domain.repository.AuthRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import org.json.JSONObject
import retrofit2.HttpException

class AuthRepositoryImpl(
    private val authApi: SwapgoApi,
//    private val api: SwapgoApi,
    private val pref: SwapGoPref
) : AuthRepository {
//    override suspend fun signUp(signUpRequest: SignUpRequest): Flow<Resource<AuthResponse>> = flow {
//        emit(Resource.Loading())
//        try {
//            val authResponse = api.signUp(signUpRequest)
//            emit(Resource.Success(authResponse))
//        } catch (e: Exception) {
//            emit(Resource.Error(e.localizedMessage ?: "An unexpected error occurred"))
//        }
//    }.flowOn(Dispatchers.IO)

//    override suspend fun signUp(signUpRequest: SignUpRequest): Flow<Resource<AuthResponse>> = flow {
//        emit(Resource.Loading())
//        try {
//            val response = api.signUp(signUpRequest)
//            emit(Resource.Success(response))
//        } catch (e: HttpException) {
//            val errorBody = e.response()?.errorBody()?.string()
//            val errorMessage = try {
//                JSONObject(errorBody ?: "").getString("message")
//            } catch (ex: Exception) {
//                e.message ?: "An error occurred during sign up"
//            }
//            emit(Resource.Error(errorMessage))
//        } catch (e: Exception) {
//            emit(Resource.Error(e.message ?: "An unexpected error occurred"))
//        }
//    }.flowOn(Dispatchers.IO)


//        override suspend fun signUp(signUpRequest: SignUpRequest): Flow<Resource<AuthResponse>> = flow {
//            emit(Resource.Loading())
//            try {
//                val response = api.signUp(signUpRequest)
//                emit(Resource.Success(response))
//            }
//            catch (e: HttpException) {
//                val errorBody = e.response()?.errorBody()?.string()
//                val errorStatus = try {
//                    JSONObject(errorBody ?: "").getString("status")
//                } catch (ex: Exception) {
//                    e.message ?: "An error occurred during sign up"
//                }
//                val errorMessage = try {
//                    JSONObject(errorBody ?: "").getString("message")
//                } catch (ex: Exception) {
//                    e.message ?: "An error occurred during sign up"
//                }
//                emit(Resource.Error(message = errorMessage, AuthResponse(message = errorMessage, status = errorStatus)))
//            }
//            catch (e: Exception) {
//                emit(Resource.Error(e.message ?: "An unexpected error occurred"))
//            }
//        }.flowOn(Dispatchers.IO)

    override suspend fun signUp(signUpRequest: SignUpRequest): Flow<Resource<AuthResponse>> = flow {
        emit(Resource.Loading())
        try {
            val response = authApi.signUp(signUpRequest)
            emit(Resource.Success(response))
        }
        catch (e: HttpException) {
            val errorBody = e.response()?.errorBody()?.string()
            val json = errorBody?.let { JSONObject(it) }

            val errorMessage = json?.getString("message") ?:
            e.message ?: "An error occurred during sign up"

            emit(Resource.Error(
                message = errorMessage
            ))
        }
        catch (e: Exception) {
            emit(Resource.Error(
                message = e.message ?: "An unexpected error occurred"
            ))
        }
    }.flowOn(Dispatchers.IO)

    override suspend fun verifyOtp(otpRequest: OtpRequest): Flow<Resource<SignUpOtpVerifyResponse>> = flow {
        emit(Resource.Loading())
        try {
            val response = authApi.verifyOtp(otpRequest)
            response.refreshToken.let { pref.saveRefreshToken(it) }
            response.accessToken.let { pref.saveAccessToken(it) }
            Log.e("AuthRepositoryImpl", "verifyOtp: $response")
            emit(Resource.Success(response))
        }
        catch (e: HttpException) {
            val errorBody = e.response()?.errorBody()?.string()
            val json = errorBody?.let { JSONObject(it) }

            val errorMessage = json?.getString("message") ?:
            e.message ?: "An error occurred during sign up"

            emit(Resource.Error(
                message = errorMessage
            ))
        }
        catch (e: Exception) {
            emit(Resource.Error(
                message = e.message ?: "An unexpected error occurred"
            ))
        }
    }.flowOn(Dispatchers.IO)

    override suspend fun resendOtp(otpResendRequest: OtpResendRequest): Flow<Resource<AuthResponse>> = flow {
        emit(Resource.Loading())
        try {
            val response = authApi.resendOtp(otpResendRequest)
            emit(Resource.Success(response))
        }
        catch (e: HttpException) {
            val errorBody = e.response()?.errorBody()?.string()
            val json = errorBody?.let { JSONObject(it) }

            val errorMessage = json?.getString("message") ?:
            e.message ?: "An error occurred during sign up"

            emit(Resource.Error(
                message = errorMessage
            ))
        }
        catch (e: Exception) {
            emit(Resource.Error(
                message = e.message ?: "An unexpected error occurred"
            ))
        }
    }.flowOn(Dispatchers.IO)

    override suspend fun login(loginRequest: LoginRequest): Flow<Resource<LoginResponse>> = flow {
        emit(Resource.Loading())
        try {
            val response = authApi.login(loginRequest)
            response.refreshToken.let { pref.saveRefreshToken(it) }
            response.accessToken.let { pref.saveAccessToken(it) }
//            response.token?.let { pref.saveUserToken(it) }
            emit(Resource.Success(response))
        }
        catch (e: HttpException) {
            val errorBody = e.response()?.errorBody()?.string()
            val json = errorBody?.let { JSONObject(it) }

            val errorMessage = json?.getString("message") ?:
            e.message ?: "An error occurred during sign up"

            emit(Resource.Error(
                message = errorMessage,
                data = LoginResponse(message = e.code().toString(), "", "")
            ))
        }
        catch (e: Exception) {
            emit(Resource.Error(
                message = e.message ?: "An unexpected error occurred"
            ))
        }
    }.flowOn(Dispatchers.IO)

    override suspend fun forgotPassword(forgotPasswordRequest: ForgotPasswordRequest): Flow<Resource<AuthResponse>> = flow {
        emit(Resource.Loading())
        try {
            val response = authApi.forgotPassword(forgotPasswordRequest)
            emit(Resource.Success(response))
        }
        catch (e: HttpException) {
            val errorBody = e.response()?.errorBody()?.string()
            val json = errorBody?.let { JSONObject(it) }

            val errorMessage = json?.getString("message") ?:
            e.message ?: "An error occurred during sign up"

            emit(Resource.Error(
                message = errorMessage
            ))
        }
        catch (e: Exception) {
            emit(Resource.Error(
                message = e.message ?: "An unexpected error occurred"
            ))
        }
    }.flowOn(Dispatchers.IO)

    override suspend fun checkLoginStatus(): Flow<Resource<LoginStatusResponse>> = flow {
        emit(Resource.Loading())
        try {
            val response = authApi.checkLoginStatus()
            emit(Resource.Success(response))
        }
        catch (e: HttpException) {
            val errorBody = e.response()?.errorBody()?.string()
            val json = errorBody?.let { JSONObject(it) }

            val errorMessage = json?.getString("message") ?:
            e.message ?: "An error occurred during sign up"

            emit(Resource.Error(
                message = errorMessage
            ))
        }
        catch (e: Exception) {
            emit(Resource.Error(
                message = e.message ?: "An unexpected error occurred"
            ))
        }
    }.flowOn(Dispatchers.IO)

    override suspend fun logout(): Flow<Resource<AuthResponse>> = flow {
        emit(Resource.Loading())
        try {
            val response = authApi.logout()
            // Clear all authentication-related data on successful logout
            pref.clearAccessToken()
            pref.clearRefreshToken()
            emit(Resource.Success(response))
        }
        catch (e: HttpException) {
            val errorBody = e.response()?.errorBody()?.string()
            val json = errorBody?.let { JSONObject(it) }

            val errorMessage = json?.getString("message") ?:
            e.message ?: "An error occurred during sign up"

            emit(Resource.Error(
                message = errorMessage
            ))
        }
        catch (e: Exception) {
            emit(Resource.Error(
                message = e.message ?: "An unexpected error occurred"
            ))
        }
    }.flowOn(Dispatchers.IO)

}
