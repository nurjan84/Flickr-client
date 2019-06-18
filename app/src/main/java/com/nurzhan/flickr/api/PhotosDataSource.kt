package com.nurzhan.flickr.api

import androidx.paging.PageKeyedDataSource
import com.nurzhan.flickr.room.entities.Photo

class PhotosDataSource : PageKeyedDataSource<Int, Photo>(){
    override fun loadInitial(params: LoadInitialParams<Int>, callback: LoadInitialCallback<Int, Photo>) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<Int, Photo>) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun loadBefore(params: LoadParams<Int>, callback: LoadCallback<Int, Photo>) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

}