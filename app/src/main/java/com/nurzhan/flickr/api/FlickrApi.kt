package com.nurzhan.flickr.api

import com.nurzhan.flickr.BuildConfig
import com.nurzhan.flickr.mvvm.models.PhotosModel
import io.reactivex.Flowable
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Query

interface FlickrApi {
    companion object {
        const val search_method = "flickr.photos.search"
        const val extras_original = "url_o"
        const val nojsoncallback = "1"
        const val format = "json"
        const val api_key = BuildConfig.API_KEY
    }

    @GET("/services/rest/")
    fun getPhotos(
        @Query("method")method:String,
        @Query("text")text:String,
        @Query("extras")extras:String,
        @Query("format")format:String,
        @Query("nojsoncallback")noJsonCallback:String,
        @Query("api_key")apiKey:String,
        @Query("page")page:Int
    ): Observable<PhotosModel>
}