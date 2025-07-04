package com.example.mytripactions.api

import com.example.mytripactions.UIState
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Headers

interface NewsApi {

    companion object {
        const val BASE_URL = "https://newsapi.org/v2/"
        const val API_KEY = "c755977cef4a42038404d3521819a3d5"
    }

    @Headers("X-Api-Key: $API_KEY")
    @GET("top-headlines?country=us&pageSize=100")
    suspend fun getNews(): Response<NewsResponse>
}