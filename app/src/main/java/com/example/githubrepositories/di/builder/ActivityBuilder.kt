package com.example.githubrepositories.di.builder

import com.example.githubrepositories.di.Activity
import com.example.githubrepositories.ui.MainActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ActivityBuilder {

    @Activity
    @ContributesAndroidInjector
    abstract fun bindMainActivity(): MainActivity
}