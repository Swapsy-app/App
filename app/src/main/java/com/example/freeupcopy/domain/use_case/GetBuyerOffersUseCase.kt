package com.example.freeupcopy.domain.use_case

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.PagingSource
import androidx.paging.PagingState
import androidx.paging.cachedIn
import com.example.freeupcopy.data.remote.dto.product.BuyerBargain
import com.example.freeupcopy.domain.repository.ProductRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetBuyerOffersUseCase @Inject constructor(
    private val productRepository: ProductRepository
) {
    private val scope = CoroutineScope(Dispatchers.Default)

    operator fun invoke(
        userId: String,
        status: String? = null
    ): Flow<PagingData<BuyerBargain>> =
        Pager(
            config = PagingConfig(
                pageSize = 20,
                enablePlaceholders = false
            )
        ) {
            BuyerOffersPagingSource(productRepository, userId, status)
        }.flow.cachedIn(scope)
}

class BuyerOffersPagingSource(
    private val repository: ProductRepository,
    private val userId: String,
    private val status: String?
) : PagingSource<Int, BuyerBargain>() {

    override fun getRefreshKey(state: PagingState<Int, BuyerBargain>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, BuyerBargain> {
        val page = params.key ?: 1
        return try {
            val response = repository.getBuyerOffers(
                userId = userId,
                status = status,
                page = page
            )
            val bargains = response.bargains

            LoadResult.Page(
                data = bargains,
                prevKey = if (page == 1) null else page.minus(1),
                nextKey = if (bargains.isEmpty() || page >= response.totalPages) null else page.plus(1),
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }
}

