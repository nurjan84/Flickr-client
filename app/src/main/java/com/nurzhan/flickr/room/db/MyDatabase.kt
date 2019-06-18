package com.nurzhan.flickr.room.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.nurzhan.flickr.room.dao.PhotosDao
import com.nurzhan.flickr.room.dao.SearchSuggestionsDao
import com.nurzhan.flickr.room.entities.Photo
import com.nurzhan.flickr.room.entities.SearchSuggestions

@Database(entities = [(SearchSuggestions::class), (Photo::class)], version = 1, exportSchema = false)
abstract class MyDatabase: RoomDatabase(){
    abstract fun searchSuggestionsDao(): SearchSuggestionsDao
    abstract fun photosDao(): PhotosDao
}