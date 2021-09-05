package com.kanaya.core.model.dto

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import java.io.Serializable

class TweetModel : Serializable {
    @SerializedName("id_str")
    @Expose
    var idStr: String? = null
    @SerializedName("id")
    @Expose
    var id: Any? = null
    @SerializedName("user")
    @Expose
    var user: User? = null
    @SerializedName("text")
    @Expose
    var text: String? = null
    @SerializedName("source")
    @Expose
    var source: String? = null

    @SerializedName("created_at")
    @Expose
    var createdAt: String? = null
    @SerializedName("favorite_count")
    @Expose
    var favouritesCount: Int? = null
    @SerializedName("lang")
    @Expose
    var language: String? = null

    var lat: Double? = null
    var long: Double? = null



}