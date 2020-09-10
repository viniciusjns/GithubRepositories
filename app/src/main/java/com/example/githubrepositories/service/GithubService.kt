package com.example.githubrepositories.service

import com.example.githubrepositories.model.Repo
import com.example.githubrepositories.model.ResponseWrap
import retrofit2.http.GET
import retrofit2.http.Query

interface GithubService {

    @GET("search/repositories")
    suspend fun searchRepositories(
        @Query("q") query: String,
        @Query("sort") sort: String,
        @Query("page") page: Int
    ): ResponseWrap<Repo>

}