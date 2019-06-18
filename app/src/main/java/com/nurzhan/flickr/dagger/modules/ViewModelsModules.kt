package com.nurzhan.flickr.dagger.modules

import androidx.lifecycle.ViewModel
import com.nurzhan.flickr.dagger.scopes.ViewModelKey
import com.nurzhan.flickr.mvvm.viewmodels.MainFragmentViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap


@Module
abstract class ViewModelsModule {
    @Binds
    @IntoMap
    @ViewModelKey(MainFragmentViewModel::class)
    abstract fun bindMainViewModel(viewModel: MainFragmentViewModel): ViewModel
}