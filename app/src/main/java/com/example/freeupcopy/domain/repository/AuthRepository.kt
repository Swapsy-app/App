package com.example.freeupcopy.domain.repository

import com.example.freeupcopy.common.Resource
import com.example.freeupcopy.data.remote.dto.AuthResponse
import com.example.freeupcopy.data.remote.dto.ForgotPasswordRequest
import com.example.freeupcopy.data.remote.dto.LoginRequest
import com.example.freeupcopy.data.remote.dto.LoginResponse
import com.example.freeupcopy.data.remote.dto.OtpRequest
import com.example.freeupcopy.data.remote.dto.OtpResendRequest
import com.example.freeupcopy.data.remote.dto.SignUpRequest
import kotlinx.coroutines.flow.Flow

interface AuthRepository {

    suspend fun signUp(signUpRequest: SignUpRequest): Flow<Resource<AuthResponse>>

    suspend fun verifyOtp(otpRequest: OtpRequest): Flow<Resource<AuthResponse>>

    suspend fun resendOtp(otpResendRequest: OtpResendRequest): Flow<Resource<AuthResponse>>

    suspend fun login(loginRequest: LoginRequest): Flow<Resource<LoginResponse>>

//    suspend fun loginOtp(otpRequest: OtpRequest): Flow<Resource<AuthResponse>>

//    suspend fun verifyLoginOtp(otpRequest: OtpRequest): Flow<Resource<AuthResponse>>

    suspend fun forgotPassword(forgotPasswordRequest: ForgotPasswordRequest): Flow<Resource<AuthResponse>>
}