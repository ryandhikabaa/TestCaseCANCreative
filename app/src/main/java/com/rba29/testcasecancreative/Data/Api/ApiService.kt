package com.rba29.testcasecancreative.Data.Api

import com.rba29.testcasecancreative.Data.Response.AllGamesResponse
import com.rba29.testcasecancreative.Data.Response.DetailGameResponse
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.http.*

interface ApiService {

    @GET("games")
    suspend fun getAllGames(
        @Query("page") page: Int,
        @Query("page_size") size: Int,
        @Query("key") token: String,
    ) : AllGamesResponse

    @GET("games/{id}")
    fun getDetailStory(
        @Path("id") id: String,
        @Query("key") token: String
    ): Call<DetailGameResponse>

    @GET("games")
    fun searchGame(
        @Query("search") search: String,
        @Query("key") token: String
    ): Call<AllGamesResponse>

}