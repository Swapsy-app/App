package com.example.freeupcopy.data.repository

import com.example.freeupcopy.common.Resource
import com.example.freeupcopy.data.remote.SwapgoApi
import com.example.freeupcopy.data.remote.dto.your_profile.AvatarsResponse
import com.example.freeupcopy.data.remote.dto.your_profile.ProfileResponse
import com.example.freeupcopy.data.remote.dto.your_profile.UpdateProfileResponse
import com.example.freeupcopy.domain.repository.SellerProfileRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import org.json.JSONObject
import retrofit2.HttpException

class SellerProfileRepositoryImpl(
    private val api: SwapgoApi
) : SellerProfileRepository {
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
}