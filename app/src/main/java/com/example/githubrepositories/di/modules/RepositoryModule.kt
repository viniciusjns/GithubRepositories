package com.example.githubrepositories.di.modules

import android.content.Context
import com.example.githubrepositories.dao.GithubDatabase
import com.example.githubrepositories.dao.RepoDao
import com.example.githubrepositories.repository.MainRepository
import com.example.githubrepositories.repository.MainRepositoryImpl
import dagger.Module
import dagger.Provides

@Module
open class RepositoryModule {

    @Provides
    fun provideMainRepository(mainRepository: MainRepositoryImpl):
            MainRepository = mainRepository

    @Provides
    fun provideRepoDao(context: Context): RepoDao =
        GithubDatabase.getDatabase(context).repoDao()
}