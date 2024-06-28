package com.rba29.testcasecancreative.Data.Db.FavoriteGames

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize

@Entity(tableName = "favorite_games")
data class FavoriteGames(
    @PrimaryKey
    @field:SerializedName("id")
    val id: String,

    @field:SerializedName("name")
    val name: String,

    @field:SerializedName("released")
    val released: String,

    @field:SerializedName("slug")
    val slug: String,

    @field:SerializedName("rating")
    val rating: String,

    @field:SerializedName("background_image")
    val background_image: String
) : Parcelable
