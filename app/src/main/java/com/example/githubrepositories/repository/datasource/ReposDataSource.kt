package com.example.githubrepositories.repository.datasource

import androidx.paging.PageKeyedDataSource
import com.example.githubrepositories.model.Repo
import com.example.githubrepositories.service.GithubService
import kotlinx.coroutines.*
import javax.inject.Inject

class ReposDataSource(
    private val service: GithubService,
    private val query: String,
    private val sort: String
) : PageKeyedDataSource<Int, Repo>() {

    private val scope: CoroutineScope =
        CoroutineScope(Dispatchers.Main + SupervisorJob())

    override fun loadInitial(
        params: LoadInitialParams<Int>,
        callback: LoadInitialCallback<Int, Repo>
    ) {
        scope.launch {
            val searchRepositories = service.searchRepositories(
                query = query,
                sort = sort,
                page = 1
            ).items
            callback.onResult(searchRepositories, null, 2)
        }
    }

    override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<Int, Repo>) {
        scope.launch {
            val currentPage = params.key
            val searchRepositories = service.searchRepositories(
                query = query,
                sort = sort,
                page = currentPage
            ).items
            callback.onResult(searchRepositories, currentPage + 1)
        }
    }

    override fun loadBefore(params: LoadParams<Int>, callback: LoadCallback<Int, Repo>) { }

    override fun invalidate() {
        super.invalidate()
        scope.cancel()
    }
}