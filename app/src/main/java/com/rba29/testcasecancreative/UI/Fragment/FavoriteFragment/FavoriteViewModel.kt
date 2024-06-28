package com.rba29.testcasecancreative.UI.Fragment.FavoriteFragment

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.rba29.testcasecancreative.Data.Db.FavoriteGames.FavoriteGames
import com.rba29.testcasecancreative.Data.Repository.GamesRepository
import com.rba29.testcasecancreative.Data.Response.ListResultItem

class FavoriteViewModel(private val repository: GamesRepository) : ViewModel() {

    fun getAllFavorite(): LiveData<List<FavoriteGames>> = repository.getAllFavorite()



}