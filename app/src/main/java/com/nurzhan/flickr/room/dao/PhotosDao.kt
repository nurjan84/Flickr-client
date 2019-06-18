package com.nurzhan.flickr.room.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.paging.DataSource
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import com.nurzhan.flickr.room.entities.Photo
import io.reactivex.Maybe
import io.reactivex.Single

@Dao
interface PhotosDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(o: Photo)

    @Query("SELECT * FROM photos WHERE searchText LIKE :str")
    fun getPhotos(str : String): DataSource.Factory<Int, Photo>

    @Query("SELECT * FROM photos WHERE searchText LIKE :str ORDER BY photoId DESC LIMIT 1")
    fun getLastPhoto(str : String): Single<Photo>
}