package com.rba29.testcasecancreative.ViewModelFactory

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.rba29.testcasecancreative.Data.Repository.GamesRepository
import com.rba29.testcasecancreative.UI.Activity.DetailGamesActivity.DetailGameViewModel
import com.rba29.testcasecancreative.UI.Activity.SearchActivity.SearchActivityViewModel
import com.rba29.testcasecancreative.UI.Fragment.FavoriteFragment.FavoriteViewModel
import com.rba29.testcasecancreative.UI.Fragment.HomeFragment.HomeFragmentViewModel
import com.rba29.testcasecancreative.Utils.Injection

class ViewModelFactory(private val repository: GamesRepository) :
    ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(HomeFragmentViewModel::class.java) -> {
                HomeFragmentViewModel(repository) as T
            }

            modelClass.isAssignableFrom(DetailGameViewModel::class.java) -> {
                DetailGameViewModel(repository) as T
            }

            modelClass.isAssignableFrom(FavoriteViewModel::class.java) -> {
                FavoriteViewModel(repository) as T
            }

            modelClass.isAssignableFrom(SearchActivityViewModel::class.java) -> {
                SearchActivityViewModel(repository) as T
            }
            else -> throw IllegalArgumentException("Unknown ViewModel class: " + modelClass.name)
        }
    }

    companion object {
        @Volatile
        private var instance: ViewModelFactory? = null
        fun getInstance(context: Context): ViewModelFactory =
            instance ?: synchronized(this) {
                instance ?: ViewModelFactory(Injection.provideRepository(context))
            }.also { instance = it }
    }
}