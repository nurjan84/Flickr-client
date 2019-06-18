package com.nurzhan.flickr.dagger.modules

import com.nurzhan.flickr.dagger.scopes.ActivityScope
import com.nurzhan.flickr.mvvm.views.MainActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector


@Module
abstract class ActivityBuildersModule {
    @ActivityScope
    @ContributesAndroidInjector(modules = [FragmentsBuildersModule::class, ViewModelsModule::class])
    abstract fun contributeMainActivity(): MainActivity
}