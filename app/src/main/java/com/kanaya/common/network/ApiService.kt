package com.kanaya.common.network

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query


interface ApiService {

   // @GET("tweets.json?")
    @GET("search/recent")
    suspend fun getTweets(@Query("query") query: String): Response<ResponseWrapper>
}