package com.rba29.testcasecancreative.UI.Activity.DetailGamesActivity

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rba29.testcasecancreative.Data.Repository.GamesRepository
import com.rba29.testcasecancreative.Data.Response.DetailGameResponse
import kotlinx.coroutines.launch

class DetailGameViewModel(private val gamesRepository: GamesRepository) : ViewModel() {
    val gameDataDetail: LiveData<DetailGameResponse> = gamesRepository.gamesDataDetail
    val isStateDataDetail: LiveData<Boolean> = gamesRepository.isStateDataDetail

    val isLoading: LiveData<Boolean> = gamesRepository.isLoading
    val message: LiveData<String> = gamesRepository.message


    fun getDetailGame(id: String) {
        viewModelScope.launch {
            gamesRepository.getDetailGame(id)
        }
    }
}