package com.example.freeupcopy.data.repository

import com.example.freeupcopy.common.Resource
import com.example.freeupcopy.data.remote.SwapgoApi
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
import com.example.freeupcopy.domain.repository.ProductRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import org.json.JSONObject
import retrofit2.HttpException

class ProductRepositoryImpl(
    private val api: SwapgoApi
): ProductRepository {
    override fun makeOffer(
        productId: String,
        request: BargainOfferRequest
    ): Flow<Resource<BargainResponse>> = flow {
        emit(Resource.Loading())
        try {
            val response = api.makeBargainOffer(
                productId = productId,
                bargainRequest = request
            )
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

    override fun updateOffer(
        productId: String,
        request: BargainOfferRequest
    ): Flow<Resource<BargainResponse>> = flow {
        emit(Resource.Loading())
        try {
            val response = api.updateBargainOffer(
                productId = productId,
                bargainRequest = request
            )
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

    override fun deleteOffer(bargainId: String): Flow<Resource<Unit>> = flow {
        emit(Resource.Loading())
        try {
            val response = api.deleteBargain(bargainId)
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

    override suspend fun getOffersForProduct(
        productId: String,
        status: String?,
        page: Int
    ): ProductBargainListResponse {
        return api.getBargainsForProduct(
            productId = productId,
            status = status,
            page = page
        )
    }

    override suspend fun getBuyerOffers(userId: String, status: String?, page: Int): BuyerBargainsResponse {
        return api.getBuyerBargains(
            userId = userId,
            status = status,
            page = page
        )
    }

    override suspend fun getSellerOffers(
        sellerId: String,
        status: String?,
        page: Int
    ): SellerBargainsResponse {
        return api.getSellerBargains(
            sellerId = sellerId,
            status = status,
            page = page
        )
    }

    override fun acceptOffer(bargainId: String): Flow<Resource<BargainResponse>> = flow<Resource<BargainResponse>> {
        emit(Resource.Loading())
        try {
            val response = api.acceptBargain(bargainId)
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

    override fun getAcceptedOfferForProduct(productId: String): Flow<Resource<AcceptedOfferResponse>> = flow<Resource<AcceptedOfferResponse>> {
        emit(Resource.Loading())
        try {
            val response = api.getAcceptedOfferForProduct(productId)
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

    override suspend fun getBargainDetails(bargainId: String): BargainDetails? {
        return try {
            val response = api.getBargainDetails(bargainId)
            response.bargain
        } catch (e: Exception) {
            null
        }
    }

    override fun getOfferCount(productId: String): Flow<Resource<BargainCountResponse>> = flow<Resource<BargainCountResponse>> {
        emit(Resource.Loading())
        try {
            val response = api.getBargainCount(productId)
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


    // Repository function
    override suspend fun addComment(productId: String, request: AddCommentRequest): Flow<Resource<CommentResponse>> = flow {
        emit(Resource.Loading())
        try {
            val response = api.addComment(
                productId = productId,
                request = request
            )
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

    override fun addReply(
        commentId: String,
        request: AddReplyRequest
    ): Flow<Resource<ReplyResponse>> = flow {
        emit(Resource.Loading())
        try {
            val response = api.addReply(
                commentId = commentId,
                request = request
            )
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

    override suspend fun getCommentsForProduct(
        productId: String,
        page: Int,
        limit: Int
    ): CommentsResponse {
        return api.getCommentsForProduct(
            productId = productId,
            page = page,
            limit = limit
        )
    }

    override suspend fun getRepliesForComment(
        commentId: String,
        page: Int,
        limit: Int
    ): RepliesResponse {
        return api.getRepliesForComment(
            commentId = commentId,
            page = page,
            limit = limit
        )
    }

    override fun deleteComment(commentId: String): Flow<Resource<Unit>> = flow {
        emit(Resource.Loading())
        try {
            val response = api.deleteComment(commentId)
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

    override fun deleteReply(replyId: String): Flow<Resource<Unit>> = flow {
        emit(Resource.Loading())
        try {
            val response = api.deleteReply(replyId)
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

    override suspend fun searchUsers(query: String): List<UserSearchResult> {
        return api.searchUsers(query)
    }

    override suspend fun getCommentById(commentId: String): Flow<Resource<CommentResponse>> = flow {
        emit(Resource.Loading())
        try {
            val response = api.getCommentById(commentId)
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

    override suspend fun getReplyById(replyId: String): Flow<Resource<ReplyResponse>> = flow {
        emit(Resource.Loading())
        try {
            val response = api.getReplyById(replyId)
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
}