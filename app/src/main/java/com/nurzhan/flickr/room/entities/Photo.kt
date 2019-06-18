package com.nurzhan.flickr.room.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import java.io.Serializable

@Entity(tableName = "photos")
data class Photo(
    @SerializedName("id") val id: Long, // 45810346995
    @SerializedName("searchText") var searchText: String?,
    @SerializedName("page") var page: Int?,
    @SerializedName("pages") var pages: Int?,
    @SerializedName("farm") val farm: Int?, // 8
    @SerializedName("height_o") val heightO: String?, // 3456
    @SerializedName("isfamily") val isfamily: Int?, // 0
    @SerializedName("isfriend") val isfriend: Int?, // 0
    @SerializedName("ispublic") val ispublic: Int?, // 1
    @SerializedName("owner") val owner: String?, // 92225347@N07
    @SerializedName("secret") val secret: String?, // c4488d25a7
    @SerializedName("server") val server: String?, // 7904
    @SerializedName("title") val title: String?, // IMG_20190106_121835
    @SerializedName("url_o") val urlO: String?, // https://farm8.staticflickr.com/7904/45810346995_60a4273413_o.jpg
    @SerializedName("width_o") val widthO: String? // 4608
):Serializable{
    @PrimaryKey(autoGenerate = true)
    @SerializedName("photoId") var photoId: Long? = null // 45810346995
}