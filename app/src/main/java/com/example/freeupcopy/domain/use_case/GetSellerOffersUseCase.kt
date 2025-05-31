package com.example.freeupcopy.domain.use_case

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.PagingSource
import androidx.paging.PagingState
import androidx.paging.cachedIn
import com.example.freeupcopy.data.remote.dto.product.SellerBargain
import com.example.freeupcopy.domain.repository.ProductRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetSellerOffersUseCase @Inject constructor(
    private val productRepository: ProductRepository
) {
    private val scope = CoroutineScope(Dispatchers.Default)

    operator fun invoke(
        sellerId: String,
        status: String? = null
    ): Flow<PagingData<SellerBargain>> =
        Pager(
            config = PagingConfig(
                pageSize = 20,
                enablePlaceholders = false
            )
        ) {
            SellerOffersPagingSource(productRepository, sellerId, status)
        }.flow.cachedIn(scope)
}

class SellerOffersPagingSource(
    private val repository: ProductRepository,
    private val sellerId: String,
    private val status: String?
) : PagingSource<Int, SellerBargain>() {

    override fun getRefreshKey(state: PagingState<Int, SellerBargain>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, SellerBargain> {
        val page = params.key ?: 1
        return try {
            val response = repository.getSellerOffers(
                sellerId = sellerId,
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
