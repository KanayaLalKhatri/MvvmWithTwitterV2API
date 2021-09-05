package com.kanaya.common.network.retrofitService

import com.google.gson.GsonBuilder
import com.kanaya.common.constant.AppConstants
import com.kanaya.common.network.ApiService
import com.kanaya.mvvmexample.BuildConfig
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class RetrofitService() {
    private lateinit var retrofit: Retrofit

    fun getInstance(): ApiService {
        val retrofit = getRetrofitInstance()
        return retrofit.create(ApiService::class.java)
    }

    private fun getRetrofitInstance(): Retrofit {
        val interceptor = HttpLoggingInterceptor()
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY)

        val clientBuilder = OkHttpClient.Builder()
            .connectTimeout(30, TimeUnit.SECONDS)
            .writeTimeout(90, TimeUnit.SECONDS)
            .readTimeout(90, TimeUnit.SECONDS)
            .addInterceptor(interceptor)
        clientBuilder.addInterceptor((Interceptor {
            it.proceed(
                it.request().newBuilder()
                    .header(
                        "Authorization",
                        if (BuildConfig.TWITTER_BARRIER_TOKEN == null)
                            ""
                        else
                            "Bearer " + BuildConfig.TWITTER_BARRIER_TOKEN
                    )
                    .build()
            )
        }))

        val client = clientBuilder.build()
        val builder = Retrofit.Builder()
        builder.baseUrl(BuildConfig.BASE_URL)
        builder.addConverterFactory(
            GsonConverterFactory.create(
                GsonBuilder().excludeFieldsWithoutExposeAnnotation().create()
            )
        )


        builder.client(client)
        retrofit = builder.build()
        return retrofit
    }

}