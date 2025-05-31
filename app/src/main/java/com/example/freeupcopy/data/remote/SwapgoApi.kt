package com.example.freeupcopy.data.remote

import com.example.freeupcopy.data.remote.dto.OnlyMessageResponse
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
import com.example.freeupcopy.data.remote.dto.product.AcceptedOfferResponse
import com.example.freeupcopy.data.remote.dto.product.AddCommentRequest
import com.example.freeupcopy.data.remote.dto.product.AddReplyRequest
import com.example.freeupcopy.data.remote.dto.product.BargainCountResponse
import com.example.freeupcopy.data.remote.dto.product.BargainDetailsResponse
import com.example.freeupcopy.data.remote.dto.product.BargainOfferRequest
import com.example.freeupcopy.data.remote.dto.product.BargainResponse
import com.example.freeupcopy.data.remote.dto.product.BuyerBargainsResponse
import com.example.freeupcopy.data.remote.dto.product.CommentResponse
import com.example.freeupcopy.data.remote.dto.product.CommentsResponse
import com.example.freeupcopy.data.remote.dto.product.ProductBargainListResponse
import com.example.freeupcopy.data.remote.dto.product.RepliesResponse
import com.example.freeupcopy.data.remote.dto.product.ReplyResponse
import com.example.freeupcopy.data.remote.dto.product.SellerBargainsResponse
import com.example.freeupcopy.data.remote.dto.product.UserSearchResult
import com.example.freeupcopy.data.remote.dto.sell.AutocompleteResponse
import com.example.freeupcopy.data.remote.dto.sell.UserBasicInfoResponse
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
import com.example.freeupcopy.data.remote.dto.your_profile.FollowersResponse
import com.example.freeupcopy.data.remote.dto.your_profile.FollowingResponse
import com.example.freeupcopy.data.remote.dto.your_profile.UpdateProfileResponse
import okhttp3.MultipartBody
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Header
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

    @POST("/api/auth/logout")
    suspend fun logout(): AuthResponse

    // basic-info
    @GET("/api/userProfile/basic-info")
    suspend fun getUserBasicInfo(
    ): UserBasicInfoResponse

    @GET("/api/userprofile/profile")
    suspend fun getProfile(): ProfileResponse

    @GET("/api/userprofile/profile/{userId}")
    suspend fun getProfileById(
        @Path("userId") userId: String
    ): ProfileResponse

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
        @Query("limit") limit: Int = 15,
        @Query("search") search: String? = null,
        @Query("userId") userId: String? = null,
        @Query("sort") sort: String? = null,
        @Query("priceType") priceType: String? = null,
        @Query("minPriceCash") minPriceCash: Float? = null,
        @Query("maxPriceCash") maxPriceCash: Float? = null,
        @Query("minPriceCoin") minPriceCoin: Float? = null,
        @Query("maxPriceCoin") maxPriceCoin: Float? = null,
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

    @GET("/api/productpage/product-details/{productId}")
    suspend fun getProductDetails(
        @Path("productId") productId: String,
        @Header("Authorization") authHeader: String? = null
    ): ProductDetailsResponse

    // 1. Add to wishlist
    @POST("/api/wishlist/add-wishlist")
    suspend fun addToWishlist(
        @Body request: WishlistRequest
    ): Unit

    // 2. Remove from wishlist
    @POST("/api/wishlist/remove-wishlist")
    suspend fun removeFromWishlist(
        @Body request: WishlistRequest
    ): Unit

    // 3. Fetch wishlisted products
    @GET("/api/wishlist/fetch-wishlist")
    suspend fun fetchWishlist(
        @Query("page") page: Int = 1,
        @Query("sort") sort: String? = null,
        @Query("status") status: String? = null,
        @Query("condition") condition: String? = null,
        @Query("primaryCategory") primaryCategory: String? = null,
        @Query("secondaryCategory") secondaryCategory: String? = null,
        @Query("tertiaryCategory") tertiaryCategory: String? = null,
        @Query("priceType") priceType: String? = null,
        @Query("minPrice") minPrice: Float? = null,
        @Query("maxPrice") maxPrice: Float? = null
    ): FetchWishlistResponse

    // 4. Get wishlist count for a product
    @GET("/api/wishlist/wishlist-count/{productId}")
    suspend fun getWishlistCount(
        @Path("productId") productId: String
    ): WishlistCountResponse


    // create bargain offer
    @POST("/api/bargain/bargain-offer/{productId}")
    suspend fun makeBargainOffer(
        @Path("productId") productId: String,
        @Body bargainRequest: BargainOfferRequest
    ): BargainResponse

    // offer bargain update
    @PUT("/api/bargain/bargain-update/{productId}")
    suspend fun updateBargainOffer(
        @Path("productId") productId: String,
        @Body bargainRequest: BargainOfferRequest
    ): BargainResponse

    // delete bargain offer
    @DELETE("/api/bargain/bargain-delete/{bargainId}")
    suspend fun deleteBargain(
        @Path("bargainId") bargainId: String
    ): Unit

    //Get Bargains for a Product
    @GET("/api/bargain/seller-bargain-fetch/product/{productId}")
    suspend fun getBargainsForProduct(
        @Path("productId") productId: String,
        @Query("status") status: String? = null,
        @Query("page") page: Int = 1
    ): ProductBargainListResponse

    //Get Bargains Made by Buyer
    @GET("/api/bargain/buyer-bargain-fetch/user/{userId}")
    suspend fun getBuyerBargains(
        @Path("userId") userId: String,
        @Query("status") status: String? = null,
        @Query("page") page: Int = 1
    ): BuyerBargainsResponse

    //Get Bargains Received by Seller
    @GET("/api/bargain/seller-bargains/{sellerId}")
    suspend fun getSellerBargains(
        @Path("sellerId") sellerId: String,
        @Query("status") status: String? = null,
        @Query("page") page: Int = 1
    ): SellerBargainsResponse

    //Accept a Bargain
    @PATCH("/api/bargain/accept-bargain/{bargainId}")
    suspend fun acceptBargain(
        @Path("bargainId") bargainId: String
    ): BargainResponse

    @GET("/api/bargain/products/{productId}/accepted-offer")
    suspend fun getAcceptedOfferForProduct(
        @Path("productId") productId: String
    ): AcceptedOfferResponse

    //Get Bargain Count for Product
    @GET("/api/bargain/bargain/count/{productId}")
    suspend fun getBargainCount(
        @Path("productId") productId: String
    ): BargainCountResponse

    @GET("/api/bargain/bargain-details/{bargainId}")
    suspend fun getBargainDetails(
        @Path("bargainId") bargainId: String
    ): BargainDetailsResponse

    /** 1️⃣ Add a comment **/
    @POST("/api/comments/{productId}")
    suspend fun addComment(
        @Path("productId") productId: String,
        @Body request: AddCommentRequest
    ): CommentResponse

    /** 2️⃣ Add a reply **/
    @POST("/api/comments/{commentId}/reply")
    suspend fun addReply(
        @Path("commentId") commentId: String,
        @Body request: AddReplyRequest
    ): ReplyResponse

    /** 3️⃣ Get all comments for a product **/
    @GET("/api/comments/{productId}")
    suspend fun getCommentsForProduct(
        @Path("productId") productId: String,
        @Query("page") page: Int = 1,
        @Query("limit") limit: Int = 10
    ): CommentsResponse

    /** 4️⃣ Get replies for a comment **/
    @GET("/api/comments/{commentId}/replies")
    suspend fun getRepliesForComment(
        @Path("commentId") commentId: String,
        @Query("page") page: Int = 1,
        @Query("limit") limit: Int = 10
    ): RepliesResponse

    /** 5️⃣ Delete a comment **/
    @DELETE("/api/comments/{commentId}")
    suspend fun deleteComment(
        @Path("commentId") commentId: String
    ): Unit

    /** 6️⃣ Delete a reply **/
    @DELETE("/api/comments/replies/{replyId}")
    suspend fun deleteReply(
        @Path("replyId") replyId: String
    ): Unit

    /** 7️⃣ Search users **/
    @GET("/api/comments/users/search")
    suspend fun searchUsers(
        @Query("query") query: String
    ): List<UserSearchResult>

    /** 8 Get a comment by ID **/
    @GET("/api/comments/comment/{commentId}")
    suspend fun getCommentById(
        @Path("commentId") commentId: String
    ): CommentResponse

    /** Get a reply by ID **/
    @GET("/api/comments/reply/{replyId}")
    suspend fun getReplyById(
        @Path("replyId") replyId: String
    ): ReplyResponse

    // Follow/Unfollow endpoints
    @POST("/api/community/follow/{id}")
    suspend fun followUser(
        @Path("id") userId: String
    ): Unit

    @POST("/api/community/unfollow/{id}")
    suspend fun unfollowUser(
        @Path("id") userId: String
    ): Unit

    @GET("/api/community/followers/{id}")
    suspend fun getFollowers(
        @Path("id") userId: String,
        @Query("page") page: Int = 1,
        @Query("limit") limit: Int = 20
    ): FollowersResponse

    @GET("/api/community/following/{id}")
    suspend fun getFollowing(
        @Path("id") userId: String,
        @Query("page") page: Int = 1,
        @Query("limit") limit: Int = 20
    ): FollowingResponse

}