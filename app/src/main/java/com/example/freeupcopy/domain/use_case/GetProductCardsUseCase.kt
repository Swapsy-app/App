package com.example.freeupcopy.domain.use_case

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.PagingSource
import androidx.paging.PagingState
import androidx.paging.cachedIn
import com.example.freeupcopy.data.remote.dto.sell.ProductCard
import com.example.freeupcopy.domain.repository.SellRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject


class GetProductCardsUseCase @Inject constructor(
    private val repository: SellRepository
) {
    private val scope = CoroutineScope(Dispatchers.Default)

    operator fun invoke(queryParams: ProductCardsQueryParameters): Flow<PagingData<ProductCard>> =
        Pager(
            config = PagingConfig(
                pageSize = 15,
                enablePlaceholders = false
            )
        ) {
            ProductCardsPagingSource(repository, queryParams)
        }.flow.cachedIn(scope)
}


class ProductCardsPagingSource(
    private val repository: SellRepository,
    private val queryParams: ProductCardsQueryParameters
) : PagingSource<Int, ProductCard>() {

    override fun getRefreshKey(state: PagingState<Int, ProductCard>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, ProductCard> {
        val page = params.key ?: 1
        return try {

            val response = repository.fetchProductCards(
                page = page,
                search = queryParams.search,
                sort = queryParams.sort,
                priceType = queryParams.priceType,
                minPrice = queryParams.minPrice,
                maxPrice = queryParams.maxPrice,
                minCashMix = queryParams.minCashMix,
                maxCashMix = queryParams.maxCashMix,
                minCoinMix = queryParams.minCoinMix,
                maxCoinMix = queryParams.maxCoinMix,
                filters = queryParams.filters
            )
            val responseProducts = response.products

            LoadResult.Page(
                data = responseProducts,
                prevKey = if (page == 1) null else page.minus(1),
                nextKey = if (responseProducts.isEmpty()) null else page.plus(1)
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }
}

data class ProductCardsQueryParameters(
    val search: String? = null,
    val sort: String? = null,
    val priceType: String? = null,
    val minPrice: Float? = null,
    val maxPrice: Float? = null,
    val minCashMix: Float? = null,
    val maxCashMix: Float? = null,
    val minCoinMix: Float? = null,
    val maxCoinMix: Float? = null,
    val filters: Map<String, String> = emptyMap()
)
