package com.nurzhan.flickr.api

import androidx.lifecycle.MutableLiveData
import com.nurzhan.flickr.mvvm.models.PhotosModel
import com.nurzhan.flickr.room.dao.PhotosDao
import com.nurzhan.flickr.room.dao.SearchSuggestionsDao
import com.nurzhan.flickr.room.entities.SearchSuggestions
import io.reactivex.Observable
import io.reactivex.Single
import io.rx_cache2.DynamicKey

class FlickrRepository(private val api: FlickrApi, private val photosDao: PhotosDao, private val suggestionsDao: SearchSuggestionsDao, private val cache: CacheProviders ){

    fun loadPhotos(text:String, page:Int): Observable<PhotosModel> {
        return cache.getPhotos(api.getPhotos(FlickrApi.search_method, text, FlickrApi.extras_original,FlickrApi.format,FlickrApi.nojsoncallback,FlickrApi.api_key, page ), DynamicKey("$text-$page"))
    }

    fun requestAndSavePhotos(searchText:String, loading : MutableLiveData<Boolean>):Observable<PhotosModel>{
        return Observable.just(searchText)
            .filter { it.isNotEmpty() }
            .doOnNext { loading.postValue(true)}
            .flatMap { photosDao.getLastPhoto(it).toObservable() }
            .flatMap {
                if(it.pages?:1 > it.page?:0){
                    loadPhotos(searchText, (it.page?:0)+1)
                }else{
                    Observable.empty()
                }
            }
            .onErrorResumeNext(
                loadPhotos(searchText, 1)
            )
            .doOnNext {response ->
                suggestionsDao.insert(SearchSuggestions(searchText))
                val page = response.photos.page
                val pages = response.photos.pages
                response.photos.photo.forEach {
                    it.searchText = searchText
                    it.page = page
                    it.pages = pages
                    photosDao.insert(it)
                }
            }
    }

    fun loadSuggestions(str:String): Single<List<String>> {
        return suggestionsDao.getAllLike("$str%")
    }
}