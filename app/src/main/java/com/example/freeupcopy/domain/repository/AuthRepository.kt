package com.example.freeupcopy.domain.repository

import com.example.freeupcopy.common.Resource
import com.example.freeupcopy.data.remote.dto.auth.AuthResponse
import com.example.freeupcopy.data.remote.dto.auth.ForgotPasswordRequest
import com.example.freeupcopy.data.remote.dto.auth.LoginRequest
import com.example.freeupcopy.data.remote.dto.auth.LoginResponse
import com.example.freeupcopy.data.remote.dto.auth.LoginStatusResponse
import com.example.freeupcopy.data.remote.dto.auth.OtpRequest
import com.example.freeupcopy.data.remote.dto.auth.OtpResendRequest
import com.example.freeupcopy.data.remote.dto.auth.SignUpOtpVerifyResponse
import com.example.freeupcopy.data.remote.dto.auth.SignUpRequest
import kotlinx.coroutines.flow.Flow

interface AuthRepository {

    suspend fun signUp(signUpRequest: SignUpRequest): Flow<Resource<AuthResponse>>

    suspend fun verifyOtp(otpRequest: OtpRequest): Flow<Resource<SignUpOtpVerifyResponse>>

    suspend fun resendOtp(otpResendRequest: OtpResendRequest): Flow<Resource<AuthResponse>>

    suspend fun login(loginRequest: LoginRequest): Flow<Resource<LoginResponse>>

//    suspend fun loginOtp(otpRequest: OtpRequest): Flow<Resource<AuthResponse>>

//    suspend fun verifyLoginOtp(otpRequest: OtpRequest): Flow<Resource<AuthResponse>>

    suspend fun forgotPassword(forgotPasswordRequest: ForgotPasswordRequest): Flow<Resource<AuthResponse>>

    suspend fun checkLoginStatus(): Flow<Resource<LoginStatusResponse>>

    suspend fun logout(): Flow<Resource<AuthResponse>>
}