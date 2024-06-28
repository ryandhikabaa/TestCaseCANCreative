package com.rba29.testcasecancreative.Data.Repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.liveData
import com.rba29.testcasecancreative.Data.Api.ApiService
import com.rba29.testcasecancreative.Data.Db.GamesDatabase
import com.rba29.testcasecancreative.Data.Paging.GamesRemoteMediator
import com.rba29.testcasecancreative.Data.Response.ListResultItem

class GamesRepository(
    private val apiService: ApiService,
    private val db: GamesDatabase
) {

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _message = MutableLiveData<String>()
    val message: LiveData<String> = _message

    private val _listAllGames = MutableLiveData<List<ListResultItem>>()
    val listAllGames: LiveData<List<ListResultItem>> = _listAllGames

    fun getAllGames(): LiveData<PagingData<ListResultItem>> {
        @OptIn(ExperimentalPagingApi::class)
        return Pager(
            config = PagingConfig(
                pageSize = 5
            ),
            remoteMediator = GamesRemoteMediator(db, apiService),
            pagingSourceFactory = {
                db.gamesDao().getAllGames()
            }

        ).liveData

    }

    companion object {
        private const val TAG = "GamesRepository"

        @Volatile
        private var instance: GamesRepository? = null
        fun getInstance(
            apiService: ApiService,
            db: GamesDatabase
        ): GamesRepository =
            instance ?: synchronized(this) {
                instance ?: GamesRepository(apiService, db)
            }.also { instance = it }
    }
}