package com.nurzhan.flickr.room.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "search_suggestions")
data class SearchSuggestions(
    @PrimaryKey
    @SerializedName("text") var text:String
)