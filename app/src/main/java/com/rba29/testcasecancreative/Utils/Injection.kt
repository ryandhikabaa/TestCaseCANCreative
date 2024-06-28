package com.rba29.testcasecancreative.Utils

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import com.rba29.testcasecancreative.Data.Api.ApiConfig
import com.rba29.testcasecancreative.Data.Db.GamesDatabase
import com.rba29.testcasecancreative.Data.Repository.GamesRepository

object Injection {
    fun provideRepository(context: Context): GamesRepository {
        val apiService = ApiConfig.getApiService()
        val database = GamesDatabase.getDatabase(context)
        return GamesRepository.getInstance(apiService, database)
    }
}