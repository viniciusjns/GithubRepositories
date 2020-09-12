package com.example.githubrepositories.di.modules

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import com.example.githubrepositories.utils.PreferencesHelper
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
open class AppModule {

    @Provides
    @Singleton
    fun provideContext(app: Application): Context =
        app.applicationContext

    @Provides
    @Singleton
    fun providePreferencesHelper(): PreferencesHelper =
        PreferencesHelper()
}