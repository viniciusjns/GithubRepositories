package com.example.githubrepositories.ui.viewmodel

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.githubrepositories.dao.GithubDatabase
import com.example.githubrepositories.dao.RepoDao
import com.example.githubrepositories.model.Repo
import com.example.githubrepositories.repository.MainRepository
import com.example.githubrepositories.repository.datasource.RepoPagingSource
import com.example.githubrepositories.repository.datasource.RepoRemoteMediator
import com.example.githubrepositories.service.GithubService
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class MainViewModel @Inject constructor(
    private val service: GithubService,
    private val database: GithubDatabase
) : BaseViewModel() {

    private var currentQueryValue: String = ""
    private var currentSearchResult: Flow<PagingData<Repo>>? = null

    fun getRepos(query: String): Flow<PagingData<Repo>> {
//        RepoPagingSource(service, "language:kotlin", "stars")
        val lastResult = currentSearchResult
        if (query == currentQueryValue && lastResult != null)
            return lastResult
        currentQueryValue = query

        val newResult = Pager(
            config = PagingConfig(30),
            remoteMediator = RepoRemoteMediator(query, database, service)
        ) {
            database.repoDao().getRepos()
        }.flow.cachedIn(viewModelScope)

        currentSearchResult = newResult
        return newResult
    }

}