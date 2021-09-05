package com.kanaya.common.network

import com.google.gson.JsonElement
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class ResponseWrapper(response: String) {
    /*@SerializedName("status")
    @Expose*/
    var isSuccess: Boolean = false
    var reason: String = ""
    var message: String = ""
    var messageArb: String = ""


    @SerializedName("data")
    @Expose
    var data: JsonElement? = null
    /*@SerializedName("errorCode")
    @Expose*/
    var errorCode: Int = -1
    /*@SerializedName("token")
   @Expose*/
    var token: String = ""

    var throwable: Throwable? = null

    constructor(throwable: Throwable?) : this("") {
        this.throwable = throwable
    }

}