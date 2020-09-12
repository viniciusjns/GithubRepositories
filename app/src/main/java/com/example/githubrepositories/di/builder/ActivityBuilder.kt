package com.example.githubrepositories.di.builder

import com.example.githubrepositories.di.Activity
import com.example.githubrepositories.ui.activities.MainActivity
import com.example.githubrepositories.ui.activities.WithPagingLibraryActivity
import com.example.githubrepositories.ui.activities.WithoutPagingLibraryActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ActivityBuilder {

    @Activity
    @ContributesAndroidInjector
    abstract fun bindMainActivity(): MainActivity

    @Activity
    @ContributesAndroidInjector
    abstract fun bindWithPagingLibraryActivity(): WithPagingLibraryActivity

    @Activity
    @ContributesAndroidInjector
    abstract fun bindWithoutPagingLibraryActivity(): WithoutPagingLibraryActivity
}