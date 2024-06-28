package com.rba29.testcasecancreative.UI.Fragment.HomeFragment

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.rba29.testcasecancreative.Data.Repository.GamesRepository
import com.rba29.testcasecancreative.Data.Response.ListResultItem

class HomeFragmentViewModel(private val repository: GamesRepository) : ViewModel() {

    val isLoading: LiveData<Boolean> = repository.isLoading
    val message: LiveData<String> = repository.message
    val listGames: LiveData<PagingData<ListResultItem>> = repository.getAllGames().cachedIn(viewModelScope)


}