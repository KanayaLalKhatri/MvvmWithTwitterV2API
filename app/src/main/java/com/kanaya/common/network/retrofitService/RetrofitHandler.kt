package com.kanaya.common.network.retrofitService

import android.content.Context
import android.util.Log
import com.google.gson.Gson
import com.kanaya.common.base.activity.BaseActivity
import com.kanaya.common.base.application.BaseApplication
import com.kanaya.common.network.ResponseWrapper
import com.kanaya.common.network.callbacks.APICallBacks
import com.kanaya.common.utils.AppUtils
import com.kanaya.mvvmexample.R
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.net.HttpURLConnection.*

open class RetrofitHandler<T> : Callback<T> {

    var context: Context? = null
    var apiCallback: APICallBacks<T>? = null

    constructor(context: Context, apiCallback: APICallBacks<T>?, showLoaderOrNot: Boolean = true,appUtils: AppUtils) {
        this.context = context
        this.apiCallback = apiCallback
        if (appUtils.isNetworkAvailable(context)) {
            if (showLoaderOrNot) {
                (context as BaseActivity).showLoadingDialog()
            } else {
            }

        } else {
            appUtils.networkAlert(
                context,
                context.getString(R.string.network_not_found),
                context.getString(R.string.check_device_internet)
            )

            // return
        }

    }

    override fun onResponse(call: Call<T>, response: Response<T>) {
        try {
            when (response.code()) {
                HTTP_OK -> {
                    val eCode = (response.body() as ResponseWrapper).errorCode
                    val wrapper = (response.body() as ResponseWrapper)
                    Log.d("ERROR_CODE -> ", "$eCode")
                    Log.d("response.raw()", "${response.raw()}")
                    Log.d("response.raw()", ".body() :  ${Gson().toJson(response.raw().body)}")
                    val message = wrapper!!.message
                    when (eCode) {

                        0 -> {
                            this.apiCallback?.onSuccess(call, response)
                        }
                        10 -> {

                        }
                        401 -> {


                        }
                        1013 -> {

                        }
                        else -> {

                            this.apiCallback?.onSuccess(call, response)
                        }
                    }
                }
                HTTP_BAD_METHOD -> {
                    this.apiCallback?.onError("HTTP_BAD_METHOD : ${response.message()}")
                }
                HTTP_BAD_REQUEST -> {
                    this.apiCallback?.onError("HTTP_BAD_REQUEST : ${response.message()}")
                }
                HTTP_BAD_GATEWAY -> {
                    this.apiCallback?.onError("HTTP_BAD_GATEWAY : ${response.message()}")
                }
                HTTP_NOT_FOUND -> {
                    this.apiCallback?.onError("HTTP_NOT_FOUND : ${response.message()}")
                }
                HTTP_INTERNAL_ERROR -> {
                    this.apiCallback?.onError("HTTP_INTERNAL_ERROR \n ${response.message()}")
                }
                else -> {
                    this.apiCallback?.onError("UNKNOWN_ERROR_OCCURRED\n ${response.message()}")
                }
            }


        } catch (e: Exception) {
            e.printStackTrace()
            this.apiCallback?.onError("UNKNOWN_ERROR_OCCURRED")
        }
    }

    override fun onFailure(call: Call<T>, t: Throwable) {
        this.apiCallback?.onFailure(call, t)
    }


}