package com.example.freeupcopy.domain.repository

import com.example.freeupcopy.common.Resource
import com.example.freeupcopy.data.remote.dto.your_profile.AvatarsResponse
import com.example.freeupcopy.data.remote.dto.your_profile.ProfileResponse
import com.example.freeupcopy.data.remote.dto.your_profile.UpdateProfileResponse
import kotlinx.coroutines.flow.Flow

interface SellerProfileRepository {
    suspend fun getProfile(): Flow<Resource<ProfileResponse>>

    suspend fun getAvatars(): Flow<Resource<AvatarsResponse>>

    suspend fun updateProfile(updateProfileResponse: UpdateProfileResponse): Flow<Resource<Unit>>
}