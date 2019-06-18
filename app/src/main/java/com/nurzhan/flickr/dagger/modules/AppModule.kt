package com.nurzhan.flickr.dagger.modules

import android.app.Application
import android.content.Context
import com.nurzhan.flickr.BuildConfig
import com.nurzhan.flickr.api.CacheProviders
import com.nurzhan.flickr.api.FlickrApi
import com.nurzhan.flickr.api.FlickrRepository
import com.nurzhan.flickr.api.PhotosBoundaryCallback
import com.nurzhan.flickr.dagger.scopes.AppScope
import com.nurzhan.flickr.glide.CustomGlideModule
import com.nurzhan.flickr.room.dao.PhotosDao
import com.nurzhan.flickr.room.dao.SearchSuggestionsDao
import com.nurzhan.flickr.room.db.MyDatabase
import com.nurzhan.flickr.utils.Utils
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

@Module
open class AppModule {

    @AppScope
    @Provides
    open fun provideContext(app: Application): Context {
        return app
    }

    @AppScope
    @Provides
    open fun retrofit(okHttpClient: OkHttpClient, provideRxAdapter: RxJava2CallAdapterFactory): Retrofit {
        return Retrofit.Builder()
            .addCallAdapterFactory(provideRxAdapter)
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(BuildConfig.BASE_URL)
            .client(okHttpClient)
            .build()
    }

    @AppScope
    @Provides
    open fun provideRxAdapter(): RxJava2CallAdapterFactory {
        return RxJava2CallAdapterFactory.create()
    }

    @AppScope
    @Provides
    open fun  getApiService(retrofit: Retrofit): FlickrApi {
        return retrofit.create(FlickrApi::class.java)
    }

    @AppScope
    @Provides
    open fun getPhotosBoundaryCallback(flickrRepository : FlickrRepository): PhotosBoundaryCallback {
        return PhotosBoundaryCallback(flickrRepository)
    }

    @AppScope
    @Provides
    open fun getFlickrRepo(api:FlickrApi, photosDao: PhotosDao, suggestionsDao: SearchSuggestionsDao, cacheProviders: CacheProviders):FlickrRepository{
        return FlickrRepository(api, photosDao, suggestionsDao, cacheProviders)
    }

}