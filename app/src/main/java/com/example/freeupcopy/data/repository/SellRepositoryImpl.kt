package com.example.freeupcopy.data.repository

import com.example.freeupcopy.common.Resource
import com.example.freeupcopy.data.remote.SwapgoApi
import com.example.freeupcopy.data.remote.dto.sell.FetchWishlistResponse
import com.example.freeupcopy.data.remote.dto.sell.GstRequest
import com.example.freeupcopy.data.remote.dto.sell.GstResponse
import com.example.freeupcopy.data.remote.dto.sell.ProductCardsResponse
import com.example.freeupcopy.data.remote.dto.sell.ProductDetailsResponse
import com.example.freeupcopy.data.remote.dto.sell.ProductRequest
import com.example.freeupcopy.data.remote.dto.sell.ProductResponse
import com.example.freeupcopy.data.remote.dto.sell.UploadImagesResponse
import com.example.freeupcopy.data.remote.dto.sell.UploadVideoResponse
import com.example.freeupcopy.data.remote.dto.sell.WishlistCountResponse
import com.example.freeupcopy.data.remote.dto.sell.WishlistRequest
import com.example.freeupcopy.data.remote.dto.sell.address.AddAddressRequest
import com.example.freeupcopy.data.remote.dto.sell.address.AddressesResponse
import com.example.freeupcopy.data.remote.dto.sell.address.UserAddressResponse
import com.example.freeupcopy.domain.repository.SellRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import okhttp3.MultipartBody
import org.json.JSONObject
import retrofit2.HttpException
class SellRepositoryImpl(
    private val api: SwapgoApi
): SellRepository {
    override suspend fun getGst(): Flow<Resource<GstResponse>> = flow {
        emit(Resource.Loading())
        try {
            val response = api.getGst()
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

    override suspend fun updateGst(gstRequest: GstRequest): Flow<Resource<GstResponse>> = flow {
        emit(Resource.Loading())
        try {
            val response = api.updateGst(gstRequest)
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

    override suspend fun addAddress(addAddressRequest: AddAddressRequest): Flow<Resource<UserAddressResponse>> = flow {
        emit(Resource.Loading())
        try {
            val response = api.addAddress(addAddressRequest)
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

    override suspend fun getAddresses(page: Int): AddressesResponse {
        return api.getAddresses(page)
    }

    override suspend fun setDefaultAddress(addressId: String): Flow<Resource<Unit>> = flow {
        emit(Resource.Loading())
        try {
            val response = api.setDefaultAddress(addressId)
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

    override suspend fun deleteAddress(addressId: String): Flow<Resource<Unit>> = flow {
        emit(Resource.Loading())
        try {
            val response = api.deleteAddress(addressId)
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

    // Implementation for uploading images
    override suspend fun uploadImages(images: List<MultipartBody.Part>): Flow<Resource<UploadImagesResponse>> = flow {
        emit(Resource.Loading())
        try {
            val response = api.uploadImages(images)
            emit(Resource.Success(response))
        } catch (e: HttpException) {
            val errorBody = e.response()?.errorBody()?.string()
            val json = errorBody?.let { JSONObject(it) }
            val errorMessage =
                json?.getString("message") ?: e.message ?: "An error occurred during image upload"
            emit(Resource.Error(message = errorMessage))
        } catch (e: Exception) {
            emit(Resource.Error(message = e.message ?: "An unexpected error occurred"))
        }
    }.flowOn(Dispatchers.IO)

    override suspend fun uploadVideo(video: MultipartBody.Part): Flow<Resource<UploadVideoResponse>> = flow {
        emit(Resource.Loading())
        try {
            val response = api.uploadVideo(video)
            emit(Resource.Success(response))
        } catch (e: HttpException) {
            val errorBody = e.response()?.errorBody()?.string()
            val json = errorBody?.let { JSONObject(it) }
            val errorMessage = json?.getString("message") ?: e.message ?: "An error occurred during video upload"
            emit(Resource.Error(message = errorMessage))
        } catch (e: Exception) {
            emit(Resource.Error(message = e.message ?: "An unexpected error occurred"))
        }
    }.flowOn(Dispatchers.IO)

    override suspend fun addProduct(product: ProductRequest): Flow<Resource<ProductResponse>> = flow {
        emit(Resource.Loading())
        try {
            val response = api.addProduct(product)
            emit(Resource.Success(response))
        } catch (e: HttpException) {
            val errorBody = e.response()?.errorBody()?.string()
            val json = errorBody?.let { JSONObject(it) }
            val errorMessage =
                json?.getString("message") ?: e.message ?: "An error occurred during product addition"
            emit(Resource.Error(message = errorMessage))
        } catch (e: Exception) {
            emit(Resource.Error(message = e.message ?: "An unexpected error occurred"))
        }
    }.flowOn(Dispatchers.IO)

    override suspend fun editProduct(
        productId: String,
        product: ProductRequest
    ): Flow<Resource<Unit>> = flow {
        emit(Resource.Loading())
        try {
            val response = api.editProduct(productId, product)
            emit(Resource.Success(response))
        } catch (e: HttpException) {
            val errorBody = e.response()?.errorBody()?.string()
            val json = errorBody?.let { JSONObject(it) }
            val errorMessage =
                json?.getString("message") ?: e.message ?: "An error occurred during product editing"
            emit(Resource.Error(message = errorMessage))
        } catch (e: Exception) {
            emit(Resource.Error(message = e.message ?: "An unexpected error occurred"))
        }
    }.flowOn(Dispatchers.IO)

    override suspend fun toggleProductAvailability(productId: String): Flow<Resource<Unit>> = flow {
        emit(Resource.Loading())
        try {
            val response = api.toggleProductAvailability(productId)
            emit(Resource.Success(response))
        } catch (e: HttpException) {
            val errorBody = e.response()?.errorBody()?.string()
            val json = errorBody?.let { JSONObject(it) }
            val errorMessage =
                json?.getString("message") ?: e.message ?: "An error occurred during product availability toggle"
            emit(Resource.Error(message = errorMessage))
        } catch (e: Exception) {
            emit(Resource.Error(message = e.message ?: "An unexpected error occurred"))
        }
    }.flowOn(Dispatchers.IO)

    override suspend fun deleteProduct(productId: String): Flow<Resource<Unit>> = flow {
        emit(Resource.Loading())
        try {
            val response = api.deleteProduct(productId)
            emit(Resource.Success(response))
        } catch (e: HttpException) {
            val errorBody = e.response()?.errorBody()?.string()
            val json = errorBody?.let { JSONObject(it) }
            val errorMessage =
                json?.getString("message") ?: e.message ?: "An error occurred during product deletion"
            emit(Resource.Error(message = errorMessage))
        } catch (e: Exception) {
            emit(Resource.Error(message = e.message ?: "An unexpected error occurred"))
        }
    }.flowOn(Dispatchers.IO)

    override suspend fun fetchProductCards(
        page: Int,
        limit: Int,
        search: String?,
        sort: String?,
        priceType: String?,
        minPriceCash: Float?,
        maxPriceCash: Float?,
        minPriceCoin: Float?,
        maxPriceCoin: Float?,
        minCashMix: Float?,
        maxCashMix: Float?,
        minCoinMix: Float?,
        maxCoinMix: Float?,
        filters: Map<String, String>
    ): ProductCardsResponse {
        return api.fetchProductCards(
            page = page,
            limit = limit,
            search = search,
            sort = sort,
            priceType = priceType,
            minPriceCash = minPriceCash,
            maxPriceCash = maxPriceCash,
            minPriceCoin = minPriceCoin,
            maxPriceCoin = maxPriceCoin,
            minCashMix = minCashMix,
            maxCashMix = maxCashMix,
            minCoinMix = minCoinMix,
            maxCoinMix = maxCoinMix,
            filters = filters
        )
    }

    override suspend fun getAutoComplete(search: String): Flow<Resource<List<String>>> = flow {
        emit(Resource.Loading())
        try {
            val response = api.getAutocompleteSuggestions(search)
            emit(Resource.Success(response.suggestions))
        } catch (e: HttpException) {
            val errorBody = e.response()?.errorBody()?.string()
            val json = errorBody?.let { JSONObject(it) }
            val errorMessage =
                json?.getString("message") ?: e.message ?: "An error occurred during auto-complete"
            emit(Resource.Error(message = errorMessage))
        } catch (e: Exception) {
            emit(Resource.Error(message = e.message ?: "An unexpected error occurred"))
        }
    }.flowOn(Dispatchers.IO)

    override suspend fun getProductDetails(productId: String): Flow<Resource<ProductDetailsResponse>> = flow {
        emit(Resource.Loading())
        try {
            val response = api.getProductDetails(productId)
            emit(Resource.Success(response))
        } catch (e: HttpException) {
            val errorBody = e.response()?.errorBody()?.string()
            val json = errorBody?.let { JSONObject(it) }
            val errorMessage =
                json?.getString("message") ?: e.message ?: "An error occurred during product details fetch"
            emit(Resource.Error(message = errorMessage))
        } catch (e: Exception) {
            emit(Resource.Error(message = e.message ?: "An unexpected error occurred"))
        }
    }.flowOn(Dispatchers.IO)

    override suspend fun addToWishlist(productId: String): Flow<Resource<Unit>>  = flow {
        emit(Resource.Loading())
        try {
            val response = api.addToWishlist(WishlistRequest(productId))
            emit(Resource.Success(response))
        } catch (e: HttpException) {
            val errorBody = e.response()?.errorBody()?.string()
            val json = errorBody?.let { JSONObject(it) }
            val errorMessage =
                json?.getString("message") ?: e.message ?: "An error occurred during adding to wishlist"
            emit(Resource.Error(message = errorMessage))
        } catch (e: Exception) {
            emit(Resource.Error(message = e.message ?: "An unexpected error occurred"))
        }
    }.flowOn(Dispatchers.IO)

    override suspend fun removeFromWishlist(productId: String): Flow<Resource<Unit>> = flow {
        emit(Resource.Loading())
        try {
            val response = api.removeFromWishlist(WishlistRequest(productId))
            emit(Resource.Success(response))
        } catch (e: HttpException) {
            val errorBody = e.response()?.errorBody()?.string()
            val json = errorBody?.let { JSONObject(it) }
            val errorMessage =
                json?.getString("message") ?: e.message ?: "An error occurred during removing from wishlist"
            emit(Resource.Error(message = errorMessage))
        } catch (e: Exception) {
            emit(Resource.Error(message = e.message ?: "An unexpected error occurred"))
        }
    }.flowOn(Dispatchers.IO)

    override suspend fun fetchWishlist(
        page: Int,
        sort: String?,
        status: String?,
        condition: String?,
        primaryCategory: String?,
        secondaryCategory: String?,
        tertiaryCategory: String?,
        priceType: String?,
        minPrice: Float?,
        maxPrice: Float?
    ): Flow<Resource<FetchWishlistResponse>> = flow {
        emit(Resource.Loading())
        try {
            val res = api.fetchWishlist(
                page = page,
                sort = sort,
                status = status,
                condition = condition,
                primaryCategory = primaryCategory,
                secondaryCategory = secondaryCategory,
                tertiaryCategory = tertiaryCategory,
                priceType = priceType,
                minPrice = minPrice,
                maxPrice = maxPrice
            )
            emit(Resource.Success(res))
        } catch(e: HttpException) {
            val errorBody = e.response()?.errorBody()?.string()
            val json = errorBody?.let { JSONObject(it) }
            val errorMessage = json?.getString("message") ?: e.message ?: "An error occurred fetching wishlist"
            emit(Resource.Error(message = errorMessage))
        } catch(e: Exception) {
            emit(Resource.Error(message = e.message?: "An unexpected error occurred"))
        }
    }.flowOn(Dispatchers.IO)

    override suspend fun getWishlistCount(productId: String): Flow<Resource<WishlistCountResponse>> = flow {
        emit(Resource.Loading())
        try {
            val res = api.getWishlistCount(productId)
            emit(Resource.Success(res))
        } catch(e: HttpException) {
            val errorBody = e.response()?.errorBody()?.string()
            val json = errorBody?.let { JSONObject(it) }
            val errorMessage = json?.getString("message") ?: e.message ?: "An error occurred fetching wishlist count"
            emit(Resource.Error(message = errorMessage))
        } catch(e: Exception) {
            emit(Resource.Error(message = e.message?: "An unexpected error occurred"))
        }
    }.flowOn(Dispatchers.IO)
}

