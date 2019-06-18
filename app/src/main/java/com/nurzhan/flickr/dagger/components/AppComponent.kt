package com.nurzhan.flickr.dagger.components

import android.app.Application
import com.nurzhan.flickr.FlickrApp
import com.nurzhan.flickr.api.FlickrApi
import com.nurzhan.flickr.api.FlickrRepository
import com.nurzhan.flickr.dagger.modules.*
import com.nurzhan.flickr.dagger.scopes.AppScope
import com.nurzhan.flickr.glide.CustomGlideModule
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjector
import dagger.android.support.AndroidSupportInjectionModule
import okhttp3.OkHttpClient

@AppScope
@Component(modules = [
    AndroidSupportInjectionModule::class,
    ActivityBuildersModule::class,
    ViewModelFactoryModule::class,
    AppModule::class,
    NetworkModule::class,
    RoomModule::class])
interface AppComponent : AndroidInjector<FlickrApp> {
    @Component.Builder
    interface Builder {
        @BindsInstance
        fun application(application: Application): Builder
        fun build(): AppComponent
    }

    fun getFlickrRepository(): FlickrRepository
    fun getFlickrAPI(): FlickrApi
    fun injectGlideModule(module:CustomGlideModule)


}