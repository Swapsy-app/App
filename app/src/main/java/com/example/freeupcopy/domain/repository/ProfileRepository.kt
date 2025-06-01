package com.example.freeupcopy.domain.repository

import com.example.freeupcopy.common.Resource
import com.example.freeupcopy.data.remote.dto.sell.UserBasicInfoResponse
import com.example.freeupcopy.data.remote.dto.your_profile.AvatarsResponse
import com.example.freeupcopy.data.remote.dto.your_profile.FollowersResponse
import com.example.freeupcopy.data.remote.dto.your_profile.FollowingResponse
import com.example.freeupcopy.data.remote.dto.your_profile.ProfileResponse
import com.example.freeupcopy.data.remote.dto.your_profile.UpdateProfileResponse
import com.example.freeupcopy.data.remote.dto.your_profile.UserProductsResponse
import kotlinx.coroutines.flow.Flow

interface ProfileRepository {
    suspend fun getUserBasicInfo(): Flow<Resource<UserBasicInfoResponse>>

    suspend fun getProfile(): Flow<Resource<ProfileResponse>>

    suspend fun getProfileById(userId: String): Flow<Resource<ProfileResponse>>

    suspend fun getAvatars(): Flow<Resource<AvatarsResponse>>

    suspend fun updateProfile(updateProfileResponse: UpdateProfileResponse): Flow<Resource<Unit>>

    fun followUser(userId: String): Flow<Resource<Unit>>

    fun unfollowUser(userId: String): Flow<Resource<Unit>>

    suspend fun getFollowers(
        userId: String,
        page: Int = 1,
        limit: Int = 20
    ): FollowersResponse

    suspend fun getFollowing(
        userId: String,
        page: Int = 1,
        limit: Int = 20
    ): FollowingResponse

    suspend fun getUserProducts(
        userId: String,
        page: Int = 1,
        status: String? = null,
    ): UserProductsResponse
}