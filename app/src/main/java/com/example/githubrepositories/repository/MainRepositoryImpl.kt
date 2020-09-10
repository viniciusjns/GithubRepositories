package com.example.githubrepositories.repository

import com.example.githubrepositories.model.Repo
import com.example.githubrepositories.service.GithubService
import javax.inject.Inject

class MainRepositoryImpl @Inject constructor(
    private val service: GithubService
) : MainRepository {

    override suspend fun searchRepositories(query: String,
                                            sort: String,
                                            page: Int): List<Repo> =
        service.searchRepositories(query, sort, page).items


}