package com.nurzhan.flickr.dagger.modules

import com.nurzhan.flickr.mvvm.views.MainFragment
import com.nurzhan.flickr.mvvm.views.PhotoViewerFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector


@Module
abstract class FragmentsBuildersModule {
    @ContributesAndroidInjector
    abstract fun contributeMainFragment(): MainFragment

    @ContributesAndroidInjector
    abstract fun contributePhotoViewerFragment(): PhotoViewerFragment
}