package com.example.githubrepositories.repository

import androidx.lifecycle.LiveData
import androidx.paging.PagedList
import com.example.githubrepositories.model.Repo

interface MainRepository {

    suspend fun searchRepositories(
        query: String,
        sort: String,
        page: Int = 1
    ): List<Repo>

    fun searchPagedRepositories(query: String,
                                sort: String
    ): LiveData<PagedList<Repo>>

}