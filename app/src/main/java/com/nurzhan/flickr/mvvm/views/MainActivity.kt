package com.nurzhan.flickr.mvvm.views

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.github.piasy.biv.BigImageViewer
import com.github.piasy.biv.loader.glide.GlideImageLoader
import com.nurzhan.flickr.R
import dagger.android.support.DaggerAppCompatActivity
import okhttp3.OkHttpClient
import javax.inject.Inject

class MainActivity : DaggerAppCompatActivity() {

    @Inject lateinit var client: OkHttpClient

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        BigImageViewer.initialize(GlideImageLoader.with(this, client))

    }
}
