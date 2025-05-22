package com.example.freeupcopy.domain.use_case

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.PagingSource
import androidx.paging.PagingState
import androidx.paging.cachedIn
import com.example.freeupcopy.data.remote.dto.sell.WishlistedProduct
import com.example.freeupcopy.domain.repository.SellRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetWishlistUseCase @Inject constructor(
    private val repository: SellRepository
) {
    private val scope = CoroutineScope(Dispatchers.Default)

    operator fun invoke(queryParams: WishlistQueryParameters): Flow<PagingData<WishlistedProduct>> =
        Pager(
            config = PagingConfig(
                pageSize = 15,
                enablePlaceholders = false
            )
        ) {
            WishlistPagingSource(repository, queryParams)
        }.flow.cachedIn(scope)
}

class WishlistPagingSource(
    private val repository: SellRepository,
    private val queryParams: WishlistQueryParameters
) : PagingSource<Int, WishlistedProduct>() {

    override fun getRefreshKey(state: PagingState<Int, WishlistedProduct>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, WishlistedProduct> {
        val page = params.key ?: 1
        return try {
            val response = repository.fetchWishlist(
                page = page,
                sort = queryParams.sort,
                status = queryParams.status,
                condition = queryParams.condition,
                primaryCategory = queryParams.primaryCategory,
                secondaryCategory = queryParams.secondaryCategory,
                tertiaryCategory = queryParams.tertiaryCategory,
                priceType = queryParams.priceType,
                minPrice = queryParams.minPrice,
                maxPrice = queryParams.maxPrice
            )

            val wishlistItems = response.products // Assuming the response structure is similar to product cards

            LoadResult.Page(
                data = wishlistItems,
                prevKey = if (page == 1) null else page.minus(1),
                nextKey = if (wishlistItems.isEmpty()) null else page.plus(1)
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }
}

data class WishlistQueryParameters(
    val sort: String? = null,
    val status: String? = null,
    val condition: String? = null,
    val primaryCategory: String? = null,
    val secondaryCategory: String? = null,
    val tertiaryCategory: String? = null,
    val priceType: String? = null,
    val minPrice: Float? = null,
    val maxPrice: Float? = null
)
