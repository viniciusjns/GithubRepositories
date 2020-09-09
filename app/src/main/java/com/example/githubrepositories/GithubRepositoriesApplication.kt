package com.example.githubrepositories

import android.app.Activity
import android.app.Application
import androidx.multidex.MultiDexApplication
import com.example.githubrepositories.di.app.AppComponent
import com.example.githubrepositories.di.app.DaggerAppComponent
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasActivityInjector
import javax.inject.Inject

open class GithubRepositoriesApplication : MultiDexApplication(), HasActivityInjector {

    @Inject
    lateinit var dispatchingActivityInjector: DispatchingAndroidInjector<Activity>

    lateinit var appComponent: AppComponent

    override fun activityInjector(): AndroidInjector<Activity> = dispatchingActivityInjector

    companion object {
        var mInstance: GithubRepositoriesApplication? = null
    }

    override fun onCreate() {
        super.onCreate()
        mInstance = this
        initInjector()
    }

    private fun initInjector() {
        appComponent = getDaggerAppComponent()
        appComponent.inject(this)
    }

    protected open fun getDaggerAppComponent(): AppComponent {
        return DaggerAppComponent.builder()
            .application(this)
            .build()
    }
}