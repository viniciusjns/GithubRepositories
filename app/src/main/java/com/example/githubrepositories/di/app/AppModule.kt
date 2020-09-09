package com.example.githubrepositories.di.app

import android.app.Application
import android.content.Context
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
open class AppModule {
    @Provides
    @Singleton
    fun provideContext(app: Application): Context =
        app.applicationContext
}