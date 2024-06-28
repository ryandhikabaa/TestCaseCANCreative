package com.rba29.testcasecancreative.Data.Db.FavoriteGames

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update

@Dao
interface FavoriteGamesDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(favoriteGames: FavoriteGames)
    @Delete
    fun delete(favoriteGames: FavoriteGames)
    @Query("SELECT * from favorite_games ORDER BY name ASC")
    fun getAllUsersFav(): LiveData<List<FavoriteGames>>

    @Query("SELECT * FROM favorite_games WHERE id = :id")
    fun getFavoriteGamesById(id: String): LiveData<FavoriteGames>

}