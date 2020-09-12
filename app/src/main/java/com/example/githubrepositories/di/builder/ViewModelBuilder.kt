package com.example.githubrepositories.di.builder

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.githubrepositories.di.ViewModelKey
import com.example.githubrepositories.di.ViewModelProviderFactory
import com.example.githubrepositories.ui.viewmodel.WithPagingLibraryViewModel
import com.example.githubrepositories.ui.viewmodel.WithoutPagingLibraryViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class ViewModelBuilder {

    @Binds
    @IntoMap
    @ViewModelKey(WithPagingLibraryViewModel::class)
    abstract fun bindWithPagingLibraryViewModel(withPagingLibraryViewModel: WithPagingLibraryViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(WithoutPagingLibraryViewModel::class)
    abstract fun bindWithoutPagingLibraryViewModel(withoutPagingLibraryViewModel: WithoutPagingLibraryViewModel): ViewModel

    @Binds
    abstract fun bindViewModelFactory(factory: ViewModelProviderFactory): ViewModelProvider.Factory
}