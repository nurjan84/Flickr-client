package com.nurzhan.flickr.mvvm.views

import com.nurzhan.flickr.mvvm.viewmodels.ViewModelProviderFactory
import dagger.android.support.DaggerFragment
import javax.inject.Inject

open class BaseFragment : DaggerFragment(){
    @Inject lateinit var providerFactory: ViewModelProviderFactory
}