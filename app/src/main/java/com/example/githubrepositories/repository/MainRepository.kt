package com.example.githubrepositories.repository

import com.example.githubrepositories.model.Repo
import com.example.githubrepositories.model.Resource

interface MainRepository {

    suspend fun searchRepositories(
        query: String,
        sort: String,
        page: Int = 1
    ): List<Repo>

}