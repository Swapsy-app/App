package com.example.freeupcopy.domain.use_case

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.PagingSource
import androidx.paging.PagingState
import androidx.paging.cachedIn
import com.example.freeupcopy.data.remote.dto.your_profile.FollowingItem
import com.example.freeupcopy.domain.repository.ProfileRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetFollowingUseCase @Inject constructor(
    private val profileRepository: ProfileRepository
) {
    private val scope = CoroutineScope(Dispatchers.Default)

    operator fun invoke(userId: String): Flow<PagingData<FollowingItem>> =
        Pager(
            config = PagingConfig(
                pageSize = 20,
                enablePlaceholders = false
            )
        ) {
            FollowingPagingSource(profileRepository, userId)
        }.flow.cachedIn(scope)
}

class FollowingPagingSource(
    private val repository: ProfileRepository,
    private val userId: String
) : PagingSource<Int, FollowingItem>() {

    override fun getRefreshKey(state: PagingState<Int, FollowingItem>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, FollowingItem> {
        val page = params.key ?: 1
        return try {
            val response = repository.getFollowing(userId = userId, page = page, limit = params.loadSize)
            val following = response.following

            LoadResult.Page(
                data = following,
                prevKey = if (page == 1) null else page.minus(1),
                nextKey = if (following.isEmpty()) null else page.plus(1),
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }
}
