package com.example.freeupcopy.data.remote

import com.example.freeupcopy.data.remote.dto.AuthResponse
import com.example.freeupcopy.data.remote.dto.ForgotPasswordRequest
import com.example.freeupcopy.data.remote.dto.LoginRequest
import com.example.freeupcopy.data.remote.dto.LoginResponse
import com.example.freeupcopy.data.remote.dto.LoginStatusResponse
import com.example.freeupcopy.data.remote.dto.OtpRequest
import com.example.freeupcopy.data.remote.dto.OtpResendRequest
import com.example.freeupcopy.data.remote.dto.RefreshTokenRequest
import com.example.freeupcopy.data.remote.dto.RefreshTokenResponse
import com.example.freeupcopy.data.remote.dto.SignUpOtpVerifyResponse
import com.example.freeupcopy.data.remote.dto.SignUpRequest
import okhttp3.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface SwapgoApi {

    @POST("signup")
    suspend fun signUp(
        @Body signUpRequest: SignUpRequest
    ): AuthResponse

    @POST("signup/otp/verify")
    suspend fun verifyOtp(
        @Body otpRequest: OtpRequest
    ): SignUpOtpVerifyResponse

    @POST("resend-otp")
    suspend fun resendOtp(
        @Body otpResendRequest: OtpResendRequest
    ): AuthResponse

    @POST("signin")
    suspend fun login(
        @Body loginRequest: LoginRequest
    ): LoginResponse

//    @POST("signin/otp")
//    suspend fun loginOtp(
//        @Body otpRequest: OtpRequest
//    ): AuthResponse

//    @POST("signin/otp/verify")
//    suspend fun verifyLoginOtp(
//        @Body otpRequest: OtpRequest
//    ): AuthResponse

    @POST("forgot-password")
    suspend fun forgotPassword(
        @Body forgotPasswordRequest: ForgotPasswordRequest
    ): AuthResponse

    @POST("refresh")
    suspend fun refreshToken(
        @Body refreshTokenRequest: RefreshTokenRequest
    ): retrofit2.Response<RefreshTokenResponse>

    @GET("check-login-status")
    suspend fun checkLoginStatus(): LoginStatusResponse
}