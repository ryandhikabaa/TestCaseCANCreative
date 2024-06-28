package com.rba29.testcasecancreative.Data.Db

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.rba29.testcasecancreative.Data.Response.ListResultItem

@Dao
interface GamesDAO {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertGames(stories: List<ListResultItem>)

    @Query("SELECT * FROM results")
    fun getAllGames(): PagingSource<Int, ListResultItem>

    @Query("DELETE FROM results")
    suspend fun deleteAll()
}