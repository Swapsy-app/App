package com.example.freeupcopy.ui.navigation

import com.example.freeupcopy.data.pref.SwapGoPref
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AuthStateManager @Inject constructor(
    private val swapGoPref: SwapGoPref
) {
    private val _authState = MutableStateFlow(AuthState.AUTHENTICATED)
    val authState: StateFlow<AuthState> = _authState.asStateFlow()

    suspend fun setUnauthenticated() {
        swapGoPref.clearRefreshToken()
        swapGoPref.clearAccessToken()
        _authState.emit(AuthState.UNAUTHENTICATED)
    }

    suspend fun setAuthenticated() {
        _authState.emit(AuthState.AUTHENTICATED)
    }
}

enum class AuthState {
    AUTHENTICATED,
    UNAUTHENTICATED
}