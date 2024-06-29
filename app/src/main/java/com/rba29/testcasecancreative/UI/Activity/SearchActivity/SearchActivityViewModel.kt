package com.rba29.testcasecancreative.UI.Activity.SearchActivity

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.rba29.testcasecancreative.BuildConfig
import com.rba29.testcasecancreative.Data.Api.ApiConfig
import com.rba29.testcasecancreative.Data.Repository.GamesRepository
import com.rba29.testcasecancreative.Data.Response.AllGamesResponse
import com.rba29.testcasecancreative.Data.Response.ListResultItem
import com.rba29.testcasecancreative.Utils.Event
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SearchActivityViewModel(private val repository: GamesRepository) : ViewModel() {

    private val _listItem = MutableLiveData<List<ListResultItem>>()
    val listItem: LiveData<List<ListResultItem>> = _listItem

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _snackbarText = MutableLiveData<Event<String>>()
    val snackbarText: LiveData<Event<String>> = _snackbarText

    fun searchGame(search: String) {
        _isLoading.value = true
        val client = ApiConfig.getApiService().searchGame(search, BuildConfig.TOKEN)
        client.enqueue(object : Callback<AllGamesResponse> {
            override fun onResponse(
                call: Call<AllGamesResponse>,
                response: Response<AllGamesResponse>
            ) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    val responseBody = response.body()
                    if (responseBody != null) {
                        _listItem.value = responseBody?.listResult
                    }
                } else {
                    Log.e(TAG, "onFailure respon: ${response}")
                    _snackbarText.value = Event("Opps!, Respon Server Gagal, silahkan ulangi beberapa saat kembali ${response}")
                }
            }
            override fun onFailure(call: Call<AllGamesResponse>, t: Throwable) {
                _isLoading.value = false
                Log.e(TAG, "onFailure error: ${t.message}")
                _snackbarText.value = Event("Opps! Mohon maaf terjadi kesalahan sistem, silahkan ulangi beberapa saat kembali ")
            }
        })
    }

    companion object{
        private const val TAG = "SearchActivityViewModel"
    }

}