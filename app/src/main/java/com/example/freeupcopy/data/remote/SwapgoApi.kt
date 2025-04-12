package com.example.freeupcopy.data.remote

import com.example.freeupcopy.data.remote.dto.auth.AuthResponse
import com.example.freeupcopy.data.remote.dto.your_profile.AvatarsResponse
import com.example.freeupcopy.data.remote.dto.auth.ForgotPasswordRequest
import com.example.freeupcopy.data.remote.dto.auth.LoginRequest
import com.example.freeupcopy.data.remote.dto.auth.LoginResponse
import com.example.freeupcopy.data.remote.dto.auth.LoginStatusResponse
import com.example.freeupcopy.data.remote.dto.auth.OtpRequest
import com.example.freeupcopy.data.remote.dto.auth.OtpResendRequest
import com.example.freeupcopy.data.remote.dto.your_profile.ProfileResponse
import com.example.freeupcopy.data.remote.dto.auth.RefreshTokenRequest
import com.example.freeupcopy.data.remote.dto.auth.RefreshTokenResponse
import com.example.freeupcopy.data.remote.dto.auth.SignUpOtpVerifyResponse
import com.example.freeupcopy.data.remote.dto.auth.SignUpRequest
import com.example.freeupcopy.data.remote.dto.sell.AutocompleteResponse
import com.example.freeupcopy.data.remote.dto.sell.GstRequest
import com.example.freeupcopy.data.remote.dto.sell.GstResponse
import com.example.freeupcopy.data.remote.dto.sell.ProductCardsResponse
import com.example.freeupcopy.data.remote.dto.sell.ProductRequest
import com.example.freeupcopy.data.remote.dto.sell.ProductResponse
import com.example.freeupcopy.data.remote.dto.sell.UploadImagesResponse
import com.example.freeupcopy.data.remote.dto.sell.UploadVideoResponse
import com.example.freeupcopy.data.remote.dto.sell.address.AddAddressRequest
import com.example.freeupcopy.data.remote.dto.sell.address.AddressesResponse
import com.example.freeupcopy.data.remote.dto.sell.address.UserAddressResponse
import com.example.freeupcopy.data.remote.dto.your_profile.UpdateProfileResponse
import okhttp3.MultipartBody
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Multipart
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Part
import retrofit2.http.Path
import retrofit2.http.Query
import retrofit2.http.QueryMap

interface SwapgoApi {

    @POST("/api/auth/signup")
    suspend fun signUp(
        @Body signUpRequest: SignUpRequest
    ): AuthResponse

    @POST("/api/auth/signup/otp/verify")
    suspend fun verifyOtp(
        @Body otpRequest: OtpRequest
    ): SignUpOtpVerifyResponse

    @POST("/api/auth/resend-otp")
    suspend fun resendOtp(
        @Body otpResendRequest: OtpResendRequest
    ): AuthResponse

    @POST("/api/auth/signin")
    suspend fun login(
        @Body loginRequest: LoginRequest
    ): LoginResponse

//    @POST("signin/otp")
//    suspend fun loginOtp(
//        @Body otpRequest: OtpRequest
//    ): AuthResponse

//    @POST("signin/otp/verify")
//    suspend fun verifyLoginOtp(
//        @Body otpRequest: OtpRequest
//    ): AuthResponse

    @POST("/api/auth/forgot-password")
    suspend fun forgotPassword(
        @Body forgotPasswordRequest: ForgotPasswordRequest
    ): AuthResponse

    @POST("/api/auth/refresh")
    suspend fun refreshToken(
        @Body refreshTokenRequest: RefreshTokenRequest
    ): retrofit2.Response<RefreshTokenResponse>

    @GET("/api/auth/check-login-status")
    suspend fun checkLoginStatus(): LoginStatusResponse

    @GET("/api/userprofile/profile")
    suspend fun getProfile(): ProfileResponse

    @GET("/api/userProfile/avatars")
    suspend fun getAvatars(): AvatarsResponse

    @PUT("/api/userProfile/profile")
    suspend fun updateProfile(
        @Body updateProfileResponse: UpdateProfileResponse
    ): Unit

    @GET("/api/userProfile/profile/gst")
    suspend fun getGst(): GstResponse

    @PUT("/api/userProfile/profile/gst")
    suspend fun updateGst(
        @Body gstRequest: GstRequest
    ): GstResponse

    @POST("/api/useraddress/add-address")
    suspend fun addAddress(
        @Body addAddressRequest: AddAddressRequest
    ): UserAddressResponse

    @GET("/api/useraddress/user-address")
    suspend fun getAddresses(
        @Query("page") page: Int
    ): AddressesResponse

    @PUT("/api/useraddress/set-default-address/{addressId}")
    suspend fun setDefaultAddress(
        @Path("addressId") addressId: String
    ): Unit

    @DELETE("/api/useraddress/delete-address/{addressId}")
    suspend fun deleteAddress(
        @Path("addressId") addressId: String
    ): Unit

    @Multipart
    @POST("/api/productsell/upload-images")
    suspend fun uploadImages(
        // 'images' key matches the one expected by the server.
        @Part images: List<MultipartBody.Part>
    ): UploadImagesResponse

    @Multipart
    @POST("api/productsell/upload-video")
    suspend fun uploadVideo(
        // The key "video" must match the one expected by the server.
        @Part video: MultipartBody.Part
    ): UploadVideoResponse

    @POST("api/productsell/add-product")
    suspend fun addProduct(
        @Body product: ProductRequest
    ): ProductResponse

    @PUT("/api/productsell/edit-product/{id}") // Not implemented
    suspend fun editProduct(
        @Path("id") productId: String,
        @Body product: ProductRequest
    ): Unit

    @PATCH("/api/productsell/product/{id}/toggle-availability") // Not implemented
    suspend fun toggleProductAvailability(
        @Path("id") productId: String
    ): Unit

    @DELETE("/api/productsell/delete-product/{id}") // Not implemented
    suspend fun deleteProduct(
        @Path("id") productId: String
    ): Unit

    @GET("/api/productcard/products-card-fetch")
    suspend fun fetchProductCards(
        @Query("page") page: Int = 1,
        @Query("search") search: String? = null,
        @Query("sort") sort: String? = null,
        @Query("priceType") priceType: String? = null,
        @Query("minPrice") minPrice: Float? = null,
        @Query("maxPrice") maxPrice: Float? = null,
        @Query("minCashMix") minCashMix: Float? = null,
        @Query("maxCashMix") maxCashMix: Float? = null,
        @Query("minCoinMix") minCoinMix: Float? = null,
        @Query("maxCoinMix") maxCoinMix: Float? = null,
        @QueryMap filters: Map<String, String> = emptyMap()
    ): ProductCardsResponse

    @GET("/api/productcard/autocomplete")
    suspend fun getAutocompleteSuggestions(
        @Query("search") search: String
    ): AutocompleteResponse
}