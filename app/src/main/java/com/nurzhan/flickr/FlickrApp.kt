package com.nurzhan.flickr

import android.content.Context
import androidx.multidex.MultiDex
import com.nurzhan.flickr.dagger.components.AppComponent
import com.nurzhan.flickr.dagger.components.DaggerAppComponent
import dagger.android.AndroidInjector
import dagger.android.support.DaggerApplication

class FlickrApp : DaggerApplication(){

    companion object{
        lateinit var appComponent:AppComponent
    }

    override fun applicationInjector(): AndroidInjector<out DaggerApplication> {
        appComponent = DaggerAppComponent.builder().application(this).build()
        return appComponent
    }

    override fun attachBaseContext(base: Context) {
        super.attachBaseContext(base)
        MultiDex.install(this)
    }

}