package com.nurzhan.flickr.mvvm.viewmodels

import android.os.Parcelable
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.nurzhan.flickr.api.PhotosBoundaryCallback
import com.nurzhan.flickr.room.dao.PhotosDao
import com.nurzhan.flickr.room.dao.SearchSuggestionsDao
import com.nurzhan.flickr.room.entities.Photo
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class MainFragmentViewModel @Inject constructor(private val photosDao: PhotosDao, private val suggestionsDao: SearchSuggestionsDao, private val photosBoundaryCallback: PhotosBoundaryCallback) : BaseViewModel(){

    private lateinit var searchResult : LiveData<PagedList<Photo>>
    private val suggestions = MutableLiveData<Array<String>>()
    var searchText:String = ""
    var suggestionSelected = false
    private var listState = MutableLiveData<Parcelable?>()

    private val config : PagedList.Config by lazy {
        PagedList.Config.Builder()
            .setPageSize(50)
            .setPrefetchDistance(10)
            .setEnablePlaceholders(true)
            .build()
    }


    fun setSearchText(str:CharSequence){
        if(!suggestionSelected){
            searchText = str.toString()
            loadSuggestions(str.toString())
        }else{
            suggestionSelected = false
        }
    }

    fun searchPhotos(){
        photosBoundaryCallback.let {
            it.setCompositeDisposable(disposables)
            it.setSearchText(searchText)
            it.setLoader(loading)
        }
        searchResult = LivePagedListBuilder(photosDao.getPhotos(searchText), config)
            .setBoundaryCallback(photosBoundaryCallback)
            .build()
    }

    fun searchResults() = searchResult


    private fun loadSuggestions(str:String){
        disposables.add(
            suggestionsDao.getAllLike("$str%")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                    { result -> run {
                        println("loadSuggestions = $result")
                        if(searchText.isNotEmpty()){
                            setSuggestions(result.toTypedArray())
                        }else{
                            setSuggestions(arrayOf())
                        }
                    }},
                    { error -> run{
                        suggestions.postValue(arrayOf())
                    }}
                )
        )
    }

    fun searchSuggestions() = suggestions
    fun setSuggestions(suggestion:Array<String>?){
        suggestions.postValue(suggestion)
    }

    fun listState() = listState
    fun setListState(parcelable: Parcelable?){
        listState.postValue(parcelable)
    }
}