package com.example.freeupcopy.data.repository

import com.example.freeupcopy.common.Resource
import com.example.freeupcopy.data.remote.SwapgoApi
import com.example.freeupcopy.data.remote.dto.sell.UserBasicInfoResponse
import com.example.freeupcopy.data.remote.dto.your_profile.AvatarsResponse
import com.example.freeupcopy.data.remote.dto.your_profile.FollowersResponse
import com.example.freeupcopy.data.remote.dto.your_profile.FollowingResponse
import com.example.freeupcopy.data.remote.dto.your_profile.ProfileResponse
import com.example.freeupcopy.data.remote.dto.your_profile.UpdateProfileResponse
import com.example.freeupcopy.data.remote.dto.your_profile.UserProductsResponse
import com.example.freeupcopy.domain.repository.ProfileRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import org.json.JSONObject
import retrofit2.HttpException

class ProfileRepositoryImpl(
    private val api: SwapgoApi
) : ProfileRepository {

    override suspend fun getUserBasicInfo(): Flow<Resource<UserBasicInfoResponse>> = flow {
        emit(Resource.Loading())
        try {
            val response = api.getUserBasicInfo()
            emit(Resource.Success(response))
        } catch (e: HttpException) {
            val errorBody = e.response()?.errorBody()?.string()
            val json = errorBody?.let { JSONObject(it) }

            val errorMessage =
                json?.getString("message") ?: e.message ?: "An error occurred during sign up"

            emit(
                Resource.Error(
                    message = errorMessage
                )
            )
        } catch (e: Exception) {
            emit(
                Resource.Error(
                    message = e.message ?: "An unexpected error occurred"
                )
            )
        }
    }.flowOn(Dispatchers.IO)

    override suspend fun getProfile(): Flow<Resource<ProfileResponse>> = flow {
        emit(Resource.Loading())
        try {
            val response = api.getProfile()
            emit(Resource.Success(response))
        } catch (e: HttpException) {
            val errorBody = e.response()?.errorBody()?.string()
            val json = errorBody?.let { JSONObject(it) }

            val errorMessage =
                json?.getString("message") ?: e.message ?: "An error occurred during sign up"

            emit(
                Resource.Error(
                    message = errorMessage
                )
            )
        } catch (e: Exception) {
            emit(
                Resource.Error(
                    message = e.message ?: "An unexpected error occurred"
                )
            )
        }
    }.flowOn(Dispatchers.IO)

    override suspend fun getProfileById(userId: String): Flow<Resource<ProfileResponse>> = flow {
        emit(Resource.Loading())
        try {
            val response = api.getProfileById(userId = userId)
            emit(Resource.Success(response))
        } catch (e: HttpException) {
            val errorBody = e.response()?.errorBody()?.string()
            val json = errorBody?.let { JSONObject(it) }

            val errorMessage =
                json?.getString("message") ?: e.message ?: "An error occurred during sign up"

            emit(
                Resource.Error(
                    message = errorMessage
                )
            )
        } catch (e: Exception) {
            emit(
                Resource.Error(
                    message = e.message ?: "An unexpected error occurred"
                )
            )
        }
    }.flowOn(Dispatchers.IO)

    override suspend fun getAvatars(): Flow<Resource<AvatarsResponse>> = flow {
        emit(Resource.Loading())
        try {
            val response = api.getAvatars()
            emit(Resource.Success(response))
        } catch (e: HttpException) {
            val errorBody = e.response()?.errorBody()?.string()
            val json = errorBody?.let { JSONObject(it) }

            val errorMessage =
                json?.getString("message") ?: e.message ?: "An error occurred during sign up"

            emit(
                Resource.Error(
                    message = errorMessage
                )
            )
        } catch (e: Exception) {
            emit(
                Resource.Error(
                    message = e.message ?: "An unexpected error occurred"
                )
            )
        }
    }.flowOn(Dispatchers.IO)

    override suspend fun updateProfile(updateProfileResponse: UpdateProfileResponse): Flow<Resource<Unit>> = flow {
        emit(Resource.Loading())
        try {
            val response = api.updateProfile(updateProfileResponse = updateProfileResponse)
            emit(Resource.Success(response))
        } catch (e: HttpException) {
            val errorBody = e.response()?.errorBody()?.string()
            val json = errorBody?.let { JSONObject(it) }

            val errorMessage =
                json?.getString("message") ?: e.message ?: "An error occurred during sign up"

            emit(
                Resource.Error(
                    message = errorMessage
                )
            )
        } catch (e: Exception) {
            emit(
                Resource.Error(
                    message = e.message ?: "An unexpected error occurred"
                )
            )
        }
    }.flowOn(Dispatchers.IO)

    override fun followUser(userId: String): Flow<Resource<Unit>> = flow {
        emit(Resource.Loading())
        try {
            val response = api.followUser(userId = userId)
            emit(Resource.Success(response))
        } catch (e: HttpException) {
            val errorBody = e.response()?.errorBody()?.string()
            val json = errorBody?.let { JSONObject(it) }

            val errorMessage =
                json?.getString("message") ?: e.message ?: "An error occurred during sign up"

            emit(Resource.Error(message = errorMessage))
        } catch (e: Exception) {
            emit(Resource.Error(message = e.message ?: "An unexpected error occurred"))
        }
    }.flowOn(Dispatchers.IO)

    override fun unfollowUser(userId: String): Flow<Resource<Unit>> = flow {
        emit(Resource.Loading())
        try {
            val response = api.unfollowUser(userId = userId)
            emit(Resource.Success(response))
        } catch (e: HttpException) {
            val errorBody = e.response()?.errorBody()?.string()
            val json = errorBody?.let { JSONObject(it) }

            val errorMessage =
                json?.getString("message") ?: e.message ?: "An error occurred during sign up"

            emit(Resource.Error(message = errorMessage))
        } catch (e: Exception) {
            emit(Resource.Error(message = e.message ?: "An unexpected error occurred"))
        }
    }.flowOn(Dispatchers.IO)

    override suspend fun getFollowers(userId: String, page: Int, limit: Int): FollowersResponse {
        return api.getFollowers(userId = userId, page = page, limit = limit)
    }

    override suspend fun getFollowing(userId: String, page: Int, limit: Int): FollowingResponse {
        return api.getFollowing(userId = userId, page = page, limit = limit)
    }

    override suspend fun getUserProducts(
        userId: String,
        page: Int,
        status: String?
    ): UserProductsResponse {
        return api.getUserProducts(
            userId = userId,
            page = page,
            status = status
        )
    }
}