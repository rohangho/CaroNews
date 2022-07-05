package com.example.caronews.network

import com.example.caronews.model.NewsListResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.POST

interface NetworkCall {

    @GET("carousell_news.json")
    suspend fun getQuotes(): Response<List<NewsListResponse>>
}