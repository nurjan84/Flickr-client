package com.nurzhan.flickr.mvvm.models

import com.google.gson.annotations.SerializedName
import com.nurzhan.flickr.room.entities.Photo


data class PhotosModel(
    @SerializedName("photos") val photos: Photos,
    @SerializedName("stat") val stat: String // ok
)

data class Photos(
    @SerializedName("page") val page: Int, // 1
    @SerializedName("pages") val pages: Int, // 420
    @SerializedName("perpage") val perpage: Int, // 100
    @SerializedName("photo") val photo: List<Photo>,
    @SerializedName("total") val total: String // 41938
)