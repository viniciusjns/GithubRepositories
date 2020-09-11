package com.example.githubrepositories.di.modules

import android.content.Context
import androidx.room.RoomDatabase
import com.example.githubrepositories.dao.GithubDatabase
import com.example.githubrepositories.dao.RepoDao
import com.example.githubrepositories.repository.MainRepository
import com.example.githubrepositories.repository.MainRepositoryImpl
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
open class RepositoryModule {

    @Provides
    fun provideMainRepository(mainRepository: MainRepositoryImpl):
            MainRepository = mainRepository

    @Singleton
    @Provides
    fun provideRepoDao(context: Context): RepoDao =
        GithubDatabase.getDatabase(context).repoDao()

    @Singleton
    @Provides
    fun provideDatabase(context: Context): GithubDatabase =
        GithubDatabase.getDatabase(context)

}