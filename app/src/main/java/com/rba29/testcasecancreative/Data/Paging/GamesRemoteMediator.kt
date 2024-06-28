package com.rba29.testcasecancreative.Data.Paging

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import kotlinx.coroutines.flow.first
import androidx.room.withTransaction
import com.rba29.testcasecancreative.BuildConfig
import com.rba29.testcasecancreative.Data.Api.ApiService
import com.rba29.testcasecancreative.Data.Db.GamesDatabase
import com.rba29.testcasecancreative.Data.Response.ListResultItem
import com.rba29.testcasecancreative.Data.Db.RemoteKeys

@OptIn(ExperimentalPagingApi::class)
class GamesRemoteMediator(
    private val database: GamesDatabase,
    private val apiService: ApiService,
) : RemoteMediator<Int, ListResultItem>(){

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, ListResultItem>
    ): MediatorResult {

        val page = when (loadType) {
            LoadType.REFRESH -> {
                val remoteKeys = getRemoteKeyClosestToCurrentPosition(state)
                remoteKeys?.nextKey?.minus(1) ?: INITIAL_PAGE_INDEX
            }
            LoadType.PREPEND -> {
                val remoteKeys = getRemoteKeyForFirstItem(state)
                val prevKeys = remoteKeys?.prevKey
                    ?: return MediatorResult.Success(endOfPaginationReached = remoteKeys != null)
                prevKeys
            }
            LoadType.APPEND -> {
                val remoteKeys = getRemoteKeyForLastItem(state)
                val nextKey = remoteKeys?.nextKey
                    ?: return MediatorResult.Success(endOfPaginationReached = remoteKeys != null)
                nextKey
            }
        }

        try {
            val responseData = apiService.getAllGames(page, state.config.pageSize, BuildConfig.TOKEN)

            val endOfPaginationReached = responseData.listResult.isEmpty()

            database.withTransaction {
                if (loadType == LoadType.REFRESH) {
                    database.remoteKeysDao().deleteRemoteKeys()
                    database.gamesDao().deleteAll()
                }
                val prevKey = if (page == 1) null else page - 1
                val nextKey = if (endOfPaginationReached) null else page + 1
                val keys = responseData.listResult.map {
                    RemoteKeys(id = it.id, prevKey = prevKey, nextKey = nextKey)
                }
                database.remoteKeysDao().insertAll(keys)
                database.gamesDao().insertGames(responseData.listResult)
            }

            return MediatorResult.Success(endOfPaginationReached = endOfPaginationReached)

        } catch (expected: Exception) {
            return MediatorResult.Error(expected)
        }
    }

    private suspend fun getRemoteKeyForLastItem(state: PagingState<Int, ListResultItem>): RemoteKeys? {
        return state.pages.lastOrNull { it.data.isNotEmpty() }?.data?.lastOrNull()?.let { data ->
            database.remoteKeysDao().getRemoteKeysId(data.id)
        }
    }

    private suspend fun getRemoteKeyForFirstItem(state: PagingState<Int, ListResultItem>): RemoteKeys? {
        return state.pages.firstOrNull { it.data.isNotEmpty() }?.data?.firstOrNull()?.let { data->
            database.remoteKeysDao().getRemoteKeysId(data.id)
        }
    }

    private suspend fun getRemoteKeyClosestToCurrentPosition(state: PagingState<Int, ListResultItem>): RemoteKeys? {
        return state.anchorPosition?.let { position ->
            state.closestItemToPosition(position)?.id?.let { id ->
                database.remoteKeysDao().getRemoteKeysId(id)
            }
        }
    }

    override suspend fun initialize(): InitializeAction {
        return InitializeAction.LAUNCH_INITIAL_REFRESH
    }

    private companion object {
        const val INITIAL_PAGE_INDEX = 1
    }

}