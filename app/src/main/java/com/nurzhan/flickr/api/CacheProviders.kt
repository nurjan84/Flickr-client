package com.nurzhan.flickr.api

import com.nurzhan.flickr.mvvm.models.PhotosModel
import io.reactivex.Flowable
import io.reactivex.Observable
import io.rx_cache2.DynamicKey
import io.rx_cache2.LifeCache
import java.util.concurrent.TimeUnit

interface CacheProviders {

    @LifeCache(duration = 1, timeUnit = TimeUnit.HOURS)
    fun getPhotos(getPhotos: Observable<PhotosModel>, key: DynamicKey): Observable<PhotosModel>


}