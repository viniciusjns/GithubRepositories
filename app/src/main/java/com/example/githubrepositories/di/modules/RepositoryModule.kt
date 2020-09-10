package com.example.githubrepositories.di.modules

import com.example.githubrepositories.repository.MainRepository
import com.example.githubrepositories.repository.MainRepositoryImpl
import dagger.Module
import dagger.Provides

@Module
open class RepositoryModule {

    @Provides
    fun provideMainRepository(mainRepository: MainRepositoryImpl):
            MainRepository = mainRepository
}