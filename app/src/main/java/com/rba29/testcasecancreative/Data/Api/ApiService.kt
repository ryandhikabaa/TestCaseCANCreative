package com.rba29.testcasecancreative.Data.Api

import com.rba29.testcasecancreative.Data.Response.AllGamesResponse
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

}