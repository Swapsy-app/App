package com.example.freeupcopy.domain.repository

import com.example.freeupcopy.common.Resource
import com.example.freeupcopy.data.remote.dto.product.AcceptedOfferResponse
import com.example.freeupcopy.data.remote.dto.product.AddCommentRequest
import com.example.freeupcopy.data.remote.dto.product.AddReplyRequest
import com.example.freeupcopy.data.remote.dto.product.BargainCountResponse
import com.example.freeupcopy.data.remote.dto.product.BargainDetails
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
import kotlinx.coroutines.flow.Flow

interface ProductRepository {

    fun makeOffer(productId: String, request: BargainOfferRequest): Flow<Resource<BargainResponse>>

    fun updateOffer(productId: String, request: BargainOfferRequest): Flow<Resource<BargainResponse>>

    fun deleteOffer(bargainId: String): Flow<Resource<Unit>>

    suspend fun getOffersForProduct(
        productId: String,
        status: String? = null,
        page: Int
    ): ProductBargainListResponse

    suspend fun getBuyerOffers(
        userId: String,
        status: String? = null,
        page: Int
    ): BuyerBargainsResponse

    suspend fun getSellerOffers(
        sellerId: String,
        status: String? = null,
        page: Int
    ): SellerBargainsResponse

    fun acceptOffer(bargainId: String): Flow<Resource<BargainResponse>>

    fun getAcceptedOfferForProduct(productId: String): Flow<Resource<AcceptedOfferResponse>>

    suspend fun getBargainDetails(bargainId: String): BargainDetails?

    fun getOfferCount(productId: String): Flow<Resource<BargainCountResponse>>

    // ————— Comment Endpoints —————

    suspend fun addComment(productId: String, request: AddCommentRequest): Flow<Resource<CommentResponse>>

    fun addReply(commentId: String, request: AddReplyRequest): Flow<Resource<ReplyResponse>>

    suspend fun getCommentsForProduct(productId: String, page: Int, limit: Int): CommentsResponse

    suspend fun getRepliesForComment(commentId: String, page: Int, limit: Int): RepliesResponse

    fun deleteComment(commentId: String): Flow<Resource<Unit>>

    fun deleteReply(replyId: String): Flow<Resource<Unit>>

    suspend fun searchUsers(query: String): List<UserSearchResult>

    suspend fun getCommentById(commentId: String): Flow<Resource<CommentResponse>>

    suspend fun getReplyById(replyId: String): Flow<Resource<ReplyResponse>>
}