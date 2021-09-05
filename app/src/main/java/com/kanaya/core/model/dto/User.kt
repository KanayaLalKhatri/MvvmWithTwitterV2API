package com.kanaya.core.model.dto

import com.google.gson.annotations.Expose

import com.google.gson.annotations.SerializedName
import java.io.Serializable


class User :Serializable{
    @SerializedName("id")
    @Expose
    var id: Any? = null
    @SerializedName("id_str")
    @Expose
    var idStr: String? = null
    @SerializedName("name")
    @Expose
    var name: String? = null
    @SerializedName("screen_name")
    @Expose
    var screenName: String? = null

    @SerializedName("location")
    @Expose
    var location: String? = null

    @SerializedName("description")
    @Expose
    var description: String? = null

    @SerializedName("url")
    @Expose
    var url: String? = null
    @SerializedName("followers_count")
    @Expose
    var followersCount: Int? = null

    @SerializedName("friends_count")
    @Expose
    var friendsCount: Int? = null

    @SerializedName("listed_count")
    @Expose
    var listedCount: Int? = null

    @SerializedName("created_at")
    @Expose
    var createdAt: String? = null

    @SerializedName("favourites_count")
    @Expose
    var favouritesCount: Int? = null

    @SerializedName("statuses_count")
    @Expose
    var statusesCount: Int? = null




    @SerializedName("profile_image_url")
    @Expose
    var profileImageUrl: String? = null


}