package com.nurzhan.flickr.room.dao

import androidx.room.*
import com.nurzhan.flickr.room.entities.SearchSuggestions
import io.reactivex.Single

@Dao
interface SearchSuggestionsDao{
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(o: SearchSuggestions)

    @Query("SELECT * FROM search_suggestions")
    fun getAll(): Single<List<SearchSuggestions>>

    @Query("SELECT text FROM search_suggestions WHERE text LIKE :str")
    fun getAllLike(str:String): Single<List<String>>

    @Update
    fun update(o: SearchSuggestions)

    @Query("DELETE FROM search_suggestions")
    fun deleteAll()

}