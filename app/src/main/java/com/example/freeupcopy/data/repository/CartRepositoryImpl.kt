package com.example.freeupcopy.data.repository

import com.example.freeupcopy.common.Resource
import com.example.freeupcopy.data.remote.SwapgoApi
import com.example.freeupcopy.data.remote.dto.cart.AddToCartRequest
import com.example.freeupcopy.data.remote.dto.cart.CartActionResponse
import com.example.freeupcopy.data.remote.dto.cart.CartResponse
import com.example.freeupcopy.data.remote.dto.cart.CartSummaryResponse
import com.example.freeupcopy.data.remote.dto.cart.UpdateSellerCartRequest
import com.example.freeupcopy.domain.repository.CartRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import org.json.JSONObject
import retrofit2.HttpException

class CartRepositoryImpl(
    private val api: SwapgoApi
): CartRepository {
    override suspend fun addToCart(productId: String, selectedPriceMode: String): Flow<Resource<CartActionResponse>> = flow {
        emit(Resource.Loading())
        try {
            val response = api.addToCart(AddToCartRequest(productId, selectedPriceMode))
            emit(Resource.Success(response))
        } catch (e: HttpException) {
            val errorBody = e.response()?.errorBody()?.string()
            val json = errorBody?.let { JSONObject(it) }
            val errorMessage =
                json?.getString("message") ?: e.message ?: "An error occurred during fetching shapes"
            emit(Resource.Error(message = errorMessage))
        } catch (e: Exception) {
            emit(Resource.Error(message = e.message ?: "An unexpected error occurred"))
        }
    }.flowOn(Dispatchers.IO)

    override suspend fun getCart(): Flow<Resource<CartResponse>> = flow {
        emit(Resource.Loading())
        try {
            val response = api.getCart()
            emit(Resource.Success(response))
        } catch (e: HttpException) {
            val errorBody = e.response()?.errorBody()?.string()
            val json = errorBody?.let { JSONObject(it) }
            val errorMessage =
                json?.getString("message") ?: e.message ?: "An error occurred during fetching shapes"
            emit(Resource.Error(message = errorMessage))
        } catch (e: Exception) {
            emit(Resource.Error(message = e.message ?: "An unexpected error occurred"))
        }
    }.flowOn(Dispatchers.IO)

    override suspend fun getCartSummary(): Flow<Resource<CartSummaryResponse>> = flow {
        emit(Resource.Loading())
        try {
            val response = api.getCartSummary()
            emit(Resource.Success(response))
        } catch (e: HttpException) {
            val errorBody = e.response()?.errorBody()?.string()
            val json = errorBody?.let { JSONObject(it) }
            val errorMessage =
                json?.getString("message") ?: e.message ?: "An error occurred during fetching shapes"
            emit(Resource.Error(message = errorMessage))
        } catch (e: Exception) {
            emit(Resource.Error(message = e.message ?: "An unexpected error occurred"))
        }
    }.flowOn(Dispatchers.IO)

    override suspend fun removeFromCart(
        sellerId: String?,
        productId: String?
    ): Flow<Resource<CartActionResponse>> = flow {
        emit(Resource.Loading())
        try {
            val response = api.removeFromCart(sellerId, productId)
            emit(Resource.Success(response))
        } catch (e: HttpException) {
            val errorBody = e.response()?.errorBody()?.string()
            val json = errorBody?.let { JSONObject(it) }
            val errorMessage =
                json?.getString("message") ?: e.message ?: "An error occurred during fetching shapes"
            emit(Resource.Error(message = errorMessage))
        } catch (e: Exception) {
            emit(Resource.Error(message = e.message ?: "An unexpected error occurred"))
        }
    }.flowOn(Dispatchers.IO)

    override suspend fun updateProductPaymentMode(
        sellerId: String,
        productId: String,
        selectedPriceMode: String
    ): Flow<Resource<CartActionResponse>> = flow {
        emit(Resource.Loading())
        try {
            val response = api.updateProductPaymentMode(
                UpdateSellerCartRequest(
                    sellerId = sellerId,
                    productId = productId,
                    selectedPriceMode = selectedPriceMode
                )
            )
            emit(Resource.Success(response))
        } catch (e: HttpException) {
            val errorBody = e.response()?.errorBody()?.string()
            val json = errorBody?.let { JSONObject(it) }
            val errorMessage =
                json?.getString("message") ?: e.message ?: "An error occurred during fetching shapes"
            emit(Resource.Error(message = errorMessage))
        } catch (e: Exception) {
            emit(Resource.Error(message = e.message ?: "An unexpected error occurred"))
        }
    }.flowOn(Dispatchers.IO)
}