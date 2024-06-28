package com.rba29.testcasecancreative.Data.Response

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

data class AllGamesResponse(

	@field:SerializedName("results")
	val listResult: List<ListResultItem>
)

@Parcelize

@Entity(tableName = "results")

data class ListResultItem(
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
): Parcelable
