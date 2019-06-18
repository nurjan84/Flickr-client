package com.nurzhan.flickr.mvvm.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import io.reactivex.disposables.CompositeDisposable

open class BaseViewModel :ViewModel(){
    val disposables = CompositeDisposable()
    val error = MutableLiveData<Throwable>()
    val loading = MutableLiveData<Boolean>()


    override fun onCleared() {
        super.onCleared()
        disposables.clear()
    }

    fun error(): LiveData<Throwable> {
        return error
    }

    fun loading(): LiveData<Boolean> {
        return loading
    }


}