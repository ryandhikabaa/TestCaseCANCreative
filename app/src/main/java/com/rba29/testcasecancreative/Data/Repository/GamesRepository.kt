package com.rba29.testcasecancreative.Data.Repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.liveData
import com.rba29.testcasecancreative.BuildConfig
import com.rba29.testcasecancreative.Data.Api.ApiService
import com.rba29.testcasecancreative.Data.Db.FavoriteGames.FavoriteGames
import com.rba29.testcasecancreative.Data.Db.GamesDatabase
import com.rba29.testcasecancreative.Data.Paging.GamesRemoteMediator
import com.rba29.testcasecancreative.Data.Response.DetailGameResponse
import com.rba29.testcasecancreative.Data.Response.ListResultItem
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

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

    //    Detail Games
    private val _gamesDataDetail = MutableLiveData<DetailGameResponse>()
    val gamesDataDetail: LiveData<DetailGameResponse> = _gamesDataDetail

    private val _isStateDataDetail = MutableLiveData<Boolean>()
    val isStateDataDetail: LiveData<Boolean> = _isStateDataDetail

    fun getDetailGame(id: String) {
        _isLoading.value = true
        val client = apiService.getDetailStory(id, BuildConfig.TOKEN)
        client.enqueue(object : Callback<DetailGameResponse> {
            override fun onResponse(
                call: Call<DetailGameResponse>,
                response: Response<DetailGameResponse>
            ) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    _gamesDataDetail.value = response.body()
                    _isStateDataDetail.value = true
                    Log.e(TAG, response.message())
                } else {
                    _isStateDataDetail.value = false
                    _message.value = response.message()
                    Log.e(TAG, "onFailure: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<DetailGameResponse>, t: Throwable) {
                _isLoading.value = false
                _isStateDataDetail.value = false
                _message.value = t.message
                Log.e(TAG, "onFailure: ${t.message}")
            }
        })
    }

    //    CRD TO FAV
    private val executorService: ExecutorService = Executors.newSingleThreadExecutor()
    fun insert(favoriteGames: FavoriteGames) {
        executorService.execute { db.favoriteGamesDao().insert(favoriteGames) }
    }

    fun delete(favoriteGames: FavoriteGames) {
        executorService.execute { db.favoriteGamesDao().delete(favoriteGames) }
    }

    fun getFavoriteGamesById(id: String): LiveData<FavoriteGames> = db.favoriteGamesDao().getFavoriteGamesById(id)


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