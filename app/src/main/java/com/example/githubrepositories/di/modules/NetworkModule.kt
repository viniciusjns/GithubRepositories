package com.example.githubrepositories.di.modules

import com.example.githubrepositories.service.APIClient
import com.example.githubrepositories.service.APIClientImpl
import com.example.githubrepositories.service.GithubService
import com.example.githubrepositories.utils.Constants
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
open class NetworkModule {

    @Provides
    @Singleton
    fun provideApiClient(): APIClient = APIClientImpl()

    @Singleton
    @Provides
    fun provideGithubService(retrofit: Retrofit): GithubService =
        retrofit.create(GithubService::class.java)

    @Provides
    @Singleton
    fun provideRetrofit(apiClient: APIClient): Retrofit =
        apiClient.configure(Constants.BASE_URL)

}