package com.example.freeupcopy.domain.use_case

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.PagingSource
import androidx.paging.PagingState
import androidx.paging.cachedIn
import com.example.freeupcopy.common.Resource
import com.example.freeupcopy.data.remote.dto.sell.address.Address
import com.example.freeupcopy.domain.repository.SellRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import javax.inject.Inject

class GetAddressesUseCase @Inject constructor(
    private val sellRepository: SellRepository
) {
    private val scope = CoroutineScope(Dispatchers.Default)

    operator fun invoke(): Flow<PagingData<Address>> =
        Pager(
            config = PagingConfig(
                pageSize = 10,
                enablePlaceholders = false
            )
        ) {
            AddressPagingSource(sellRepository)
        }.flow.cachedIn(scope)
}

class AddressPagingSource(
    private val repository: SellRepository
) : PagingSource<Int, Address>() {

//    override fun getRefreshKey(state: PagingState<Int, Address>): Int =
//        ((state.anchorPosition ?: 0) - state.config.initialLoadSize / 2)
//            .coerceAtLeast(0)

//    override fun getRefreshKey(state: PagingState<Int, Address>): Int? {
//        return null
//    }

    override fun getRefreshKey(state: PagingState<Int, Address>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Address> {
        val page = params.key ?: 1
        return try {
            val response = repository.getAddresses(page=page)
            val responseAddress = response.addresses

            LoadResult.Page(
                data = responseAddress,
                prevKey = if (page == 1) null else page.minus(1),
                nextKey = if (responseAddress.isEmpty()) null else page.plus(1),
            )


        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }
}
