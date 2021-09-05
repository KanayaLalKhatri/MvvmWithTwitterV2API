package com.kanaya.common.network.callbacks


import retrofit2.Call
import retrofit2.Response

interface APICallBacks<T> {
    fun onSuccess(call: Call<T>, response: Response<T>)
    fun onFailure(call: Call<T>, t: Throwable)
    fun onError(string: String)
}