package com.kanaya.core.repository

import com.kanaya.common.base.repo.BaseRepository
import com.kanaya.common.network.ApiService
import com.kanaya.common.network.ResponseWrapper
import com.kanaya.core.model.response.MyTaskApiResponse

class TaskRepository(private val apiService: ApiService) : BaseRepository() {


    suspend fun getTweets(query:String): ResponseWrapper? {
        var response: ResponseWrapper? = null
        val result = apiService.getTweets(query)
        if (result.isSuccessful) {
            response = result.body()!!
            //result.also { response?.data = it.body()!! }
        }
        return response
    }

}