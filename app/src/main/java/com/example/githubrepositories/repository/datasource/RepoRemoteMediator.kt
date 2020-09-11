package com.example.githubrepositories.repository.datasource

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import com.example.githubrepositories.model.Repo
import com.example.githubrepositories.service.GithubService
import java.io.IOException

@ExperimentalPagingApi
class RepoRemoteMediator(
    private val service: GithubService
) : RemoteMediator<Int, Repo>() {

    override suspend fun load(loadType: LoadType, state: PagingState<Int, Repo>): MediatorResult {
        val page = 1
        return try {
            val response = service.searchRepositories("", "", page)
            val repos = response.items
//            repoDataBase.reposDao().insertAll(repos)

            val endOfPaginationReached = repos.isEmpty()
            MediatorResult.Success(
                endOfPaginationReached = endOfPaginationReached
            )
        } catch (ex: IOException) {
            MediatorResult.Error(ex)
        }
    }
}