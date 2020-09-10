package com.example.githubrepositories.repository

import androidx.lifecycle.LiveData
import androidx.paging.DataSource
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.example.githubrepositories.model.Repo
import com.example.githubrepositories.repository.datasource.ReposDataSource
import com.example.githubrepositories.service.GithubService
import javax.inject.Inject

class MainRepositoryImpl @Inject constructor(
    private val service: GithubService
) : MainRepository {

    override suspend fun searchRepositories(query: String,
                                            sort: String,
                                            page: Int): List<Repo> =
        service.searchRepositories(query, sort, page).items

    override fun searchPagedRepositories(query: String,
                                        sort: String): LiveData<PagedList<Repo>> {
        val config = PagedList.Config.Builder()
            .setPageSize(30)
            .setEnablePlaceholders(false)
            .build()
        return initializedPagedListBuilder(config, query, sort).build()
    }

    private fun initializedPagedListBuilder(config: PagedList.Config, query: String, sort: String):
            LivePagedListBuilder<Int, Repo> {

        val dataSourceFactory = object : DataSource.Factory<Int, Repo>() {
            override fun create(): DataSource<Int, Repo> {
                return ReposDataSource(service, query, sort)
            }
        }
        return LivePagedListBuilder<Int, Repo>(dataSourceFactory, config)
    }


}