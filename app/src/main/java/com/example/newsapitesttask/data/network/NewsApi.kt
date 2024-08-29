package com.example.newsapitesttask.data.network

import com.example.newsapitesttask.data.network.response.NewsResponse
import retrofit2.http.GET
import retrofit2.http.Query

internal interface NewsApi {

    @GET("v2/everything")
    suspend fun getEverything(
        @Query("q") query: String = "android",
    ): Result<NewsResponse>
}
