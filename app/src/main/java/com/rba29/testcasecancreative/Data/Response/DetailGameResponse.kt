package com.rba29.testcasecancreative.Data.Response

import com.google.gson.annotations.SerializedName

data class DetailGameResponse(

	@field:SerializedName("id")
	val id: String,

	@field:SerializedName("slug")
	val slug: String,

	@field:SerializedName("name")
	val name: String,

	@field:SerializedName("released")
	val released: String,

	@field:SerializedName("background_image")
	val image: String,

	@field:SerializedName("rating")
	val rating: String,

	@field:SerializedName("description_raw")
	val description: String,

	@field:SerializedName("message")
	val message: String,

	@field:SerializedName("website")
	val website: String
)