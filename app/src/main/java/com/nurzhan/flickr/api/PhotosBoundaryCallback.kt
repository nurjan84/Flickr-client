package com.nurzhan.flickr.api

import androidx.lifecycle.MutableLiveData
import androidx.paging.PagedList
import com.nurzhan.flickr.room.dao.PhotosDao
import com.nurzhan.flickr.room.dao.SearchSuggestionsDao
import com.nurzhan.flickr.room.entities.Photo
import com.nurzhan.flickr.room.entities.SearchSuggestions
import io.reactivex.Observable
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers


class PhotosBoundaryCallback (private val flickrRepository: FlickrRepository) : PagedList.BoundaryCallback<Photo>() {
    private var loading =  MutableLiveData<Boolean>()
    private lateinit var disposables: CompositeDisposable
    private var searchText:String = ""
    fun setSearchText(searchText:String){
        this.searchText = searchText
    }
    fun setCompositeDisposable(disposables: CompositeDisposable){
        this.disposables = disposables
    }
    fun setLoader(loader:  MutableLiveData<Boolean>){
        loading = loader
    }

    override fun onZeroItemsLoaded() {
        requestAndSavePhotos()
    }

    override fun onItemAtEndLoaded(itemAtEnd: Photo) {
        requestAndSavePhotos()
    }

    override fun onItemAtFrontLoaded(itemAtFront: Photo) {
        println("onItemAtFrontLoaded")
    }

    private fun requestAndSavePhotos(){
        disposables.add(
            flickrRepository.requestAndSavePhotos(searchText, loading)
                .subscribeOn(Schedulers.io())
                .subscribe(
                    { run {
                        loading.postValue(false)
                    }},
                    { e -> run{
                        println("requestAndSavePhotos error = $e")
                        loading.postValue(false)
                    }})
        )

    }

   /* private fun requestAndSavePhotos(){
        disposables.add(
            Observable.just(searchText)
                .filter { it.isNotEmpty() }
                .doOnNext { loading.postValue(true)}
                .flatMap { photosDao.getLastPhoto(it).toObservable() }
                .flatMap {
                    if(it.pages?:1 > it.page?:0){
                        api.getPhotos(FlickrApi.search_method, searchText, FlickrApi.extras_original, FlickrApi.format, FlickrApi.nojsoncallback, FlickrApi.api_key, (it.page?:0)+1)
                    }else{
                        Observable.empty()
                    }
                }
                .onErrorResumeNext(
                    api.getPhotos(FlickrApi.search_method, searchText, FlickrApi.extras_original, FlickrApi.format, FlickrApi.nojsoncallback, FlickrApi.api_key, 1)
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
                .subscribeOn(Schedulers.io())
                .subscribe(
                    { run {
                        loading.postValue(false)
                    }},
                    { e -> run{
                        println("requestAndSavePhotos error = $e")
                        loading.postValue(false)
                    }})

        )
    }*/
}