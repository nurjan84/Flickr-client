package com.nurzhan.flickr.glide

import android.content.Context
import com.bumptech.glide.Glide
import com.bumptech.glide.Registry
import com.bumptech.glide.annotation.GlideModule
import com.bumptech.glide.integration.okhttp3.OkHttpUrlLoader
import com.bumptech.glide.load.model.GlideUrl
import com.bumptech.glide.module.AppGlideModule
import com.nurzhan.flickr.FlickrApp
import okhttp3.OkHttpClient
import java.io.InputStream
import javax.inject.Inject

@GlideModule
class CustomGlideModule : AppGlideModule() {
    init {
        FlickrApp.appComponent.injectGlideModule(this)
    }

    @Inject lateinit var client: OkHttpClient

    override fun registerComponents(context: Context, glide: Glide, registry: Registry) {
        registry.replace(GlideUrl::class.java, InputStream::class.java, OkHttpUrlLoader.Factory(client))
    }
}