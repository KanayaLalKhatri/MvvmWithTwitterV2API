package com.kanaya.core.model.response

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import com.kanaya.core.model.dto.TweetModel


class MyTaskApiResponse {
    @Expose
    @SerializedName("data")
    var data: ArrayList<TweetModel> = ArrayList()
}