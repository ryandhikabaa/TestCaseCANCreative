package com.rba29.testcasecancreative.Data.Db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.rba29.testcasecancreative.Data.Response.ListResultItem

@Database(
    entities = [ListResultItem::class, RemoteKeys::class],
    version = 2,
    exportSchema = false
)
abstract class GamesDatabase : RoomDatabase() {

    companion object {
        @Volatile
        private var INSTANCE: GamesDatabase? = null

        @JvmStatic
        fun getDatabase(context: Context): GamesDatabase {
            return INSTANCE ?: synchronized(this) {
                INSTANCE ?: Room.databaseBuilder(
                    context.applicationContext,
                    GamesDatabase::class.java, "games_database"
                )
                    .fallbackToDestructiveMigration()
                    .build()
                    .also { INSTANCE = it }
            }
        }
    }

    abstract fun gamesDao(): GamesDAO
    abstract fun remoteKeysDao(): RemoteKeysDao

}