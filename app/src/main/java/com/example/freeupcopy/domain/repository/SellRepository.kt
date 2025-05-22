package com.example.freeupcopy.domain.repository

import com.example.freeupcopy.common.Resource
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
import com.example.freeupcopy.data.remote.dto.sell.address.AddAddressRequest
import com.example.freeupcopy.data.remote.dto.sell.address.AddressesResponse
import com.example.freeupcopy.data.remote.dto.sell.address.UserAddressResponse
import kotlinx.coroutines.flow.Flow
import okhttp3.MultipartBody

interface SellRepository {

    suspend fun getGst(): Flow<Resource<GstResponse>>

    suspend fun updateGst(gstRequest: GstRequest): Flow<Resource<GstResponse>>

    suspend fun addAddress(addAddressRequest: AddAddressRequest): Flow<Resource<UserAddressResponse>>

    suspend fun getAddresses(page: Int): AddressesResponse

    suspend fun setDefaultAddress(addressId: String): Flow<Resource<Unit>>

    suspend fun deleteAddress(addressId: String): Flow<Resource<Unit>>

    // New function for uploading images.
    suspend fun uploadImages(images: List<MultipartBody.Part>): Flow<Resource<UploadImagesResponse>>

    // New function for uploading a video.
    suspend fun uploadVideo(video: MultipartBody.Part): Flow<Resource<UploadVideoResponse>>

    // New function for adding a product.
    suspend fun addProduct(product: ProductRequest): Flow<Resource<ProductResponse>>

    suspend fun editProduct(
        productId: String,
        product: ProductRequest
    ): Flow<Resource<Unit>>

    suspend fun toggleProductAvailability(productId: String): Flow<Resource<Unit>>

    suspend fun deleteProduct(productId: String): Flow<Resource<Unit>>

    suspend fun fetchProductCards(
        page: Int,
        limit: Int,
        userId: String? = null,
        search: String? = null,
        sort: String? = null,
        priceType: String? = null,
        minPriceCash: Float? = null,
        maxPriceCash: Float? = null,
        minPriceCoin: Float? = null,
        maxPriceCoin: Float? = null,
        minCashMix: Float? = null,
        maxCashMix: Float? = null,
        minCoinMix: Float? = null,
        maxCoinMix: Float? = null,
        filters: Map<String, String> = emptyMap()
    ): ProductCardsResponse

    suspend fun getAutoComplete(search: String): Flow<Resource<List<String>>>

    suspend fun getProductDetails(productId: String): Flow<Resource<ProductDetailsResponse>>

    // 1️⃣ Add a product to the wishlist
    suspend fun addToWishlist(productId: String): Flow<Resource<Unit>>

    // 2️⃣ Remove a product from the wishlist
    suspend fun removeFromWishlist(productId: String): Flow<Resource<Unit>>

    // 3️⃣ Fetch wishlisted products (with pagination and optional filtering/sorting)
    suspend fun fetchWishlist(
        page: Int,
        sort: String? = null,
        status: String? = null,
        condition: String? = null,
        primaryCategory: String? = null,
        secondaryCategory: String? = null,
        tertiaryCategory: String? = null,
        priceType: String? = null,
        minPrice: Float? = null,
        maxPrice: Float? = null
    ): FetchWishlistResponse

    // 4️⃣ Get the wishlist count for a specific product
    suspend fun getWishlistCount(productId: String): Flow<Resource<WishlistCountResponse>>
}
