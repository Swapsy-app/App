package com.example.freeupcopy.domain.use_case

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.PagingSource
import androidx.paging.PagingState
import androidx.paging.cachedIn
import com.example.freeupcopy.data.remote.dto.your_profile.UserProductCard
import com.example.freeupcopy.domain.repository.ProfileRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetUserProductsUseCase @Inject constructor(
    private val productRepository: ProfileRepository
) {
    private val scope = CoroutineScope(Dispatchers.Default)

    operator fun invoke(
        userId: String,
        status: String? = null
    ): Flow<PagingData<UserProductCard>> =
        Pager(
            config = PagingConfig(
                pageSize = 15, // Matching your backend pageSize
                enablePlaceholders = false
            )
        ) {
            UserProductsPagingSource(productRepository, userId, status)
        }.flow.cachedIn(scope)
}

class UserProductsPagingSource(
    private val repository: ProfileRepository,
    private val userId: String,
    private val status: String?
) : PagingSource<Int, UserProductCard>() {

    override fun getRefreshKey(state: PagingState<Int, UserProductCard>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, UserProductCard> {
        val page = params.key ?: 1
        return try {
            val response = repository.getUserProducts(
                userId = userId,
                page = page,
                status = status
            )
            val products = response.products

            LoadResult.Page(
                data = products,
                prevKey = if (page == 1) null else page.minus(1),
                nextKey = if (products.isEmpty() || page >= response.totalPages) null else page.plus(1),
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }
}
