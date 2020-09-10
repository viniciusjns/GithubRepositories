package com.example.githubrepositories.di.app

import android.app.Application
import com.example.githubrepositories.GithubRepositoriesApplication
import com.example.githubrepositories.di.builder.ActivityBuilder
import com.example.githubrepositories.di.builder.ViewModelBuilder
import com.example.githubrepositories.di.modules.AppModule
import com.example.githubrepositories.di.modules.NetworkModule
import com.example.githubrepositories.di.modules.RepositoryModule
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjectionModule
import dagger.android.support.AndroidSupportInjectionModule
import org.jetbrains.annotations.NotNull
import javax.inject.Singleton


@Singleton
@Component(
    modules = [
        AndroidInjectionModule::class,
        AndroidSupportInjectionModule::class,
        AppModule::class,
        NetworkModule::class,
        RepositoryModule::class,
        ActivityBuilder::class,
        ViewModelBuilder::class
    ]
)
interface AppComponent {
    fun inject(@NotNull application: GithubRepositoriesApplication)

    @Component.Builder
    interface Builder {

        @BindsInstance
        fun application(application: Application): Builder

        fun build(): AppComponent
    }
}