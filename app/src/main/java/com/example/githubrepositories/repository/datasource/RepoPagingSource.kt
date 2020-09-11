package com.example.githubrepositories.repository.datasource

import androidx.paging.PagingSource
import com.example.githubrepositories.model.Repo
import com.example.githubrepositories.service.GithubService
import retrofit2.HttpException
import java.io.IOException

class RepoPagingSource(
    private val service: GithubService,
    private val query: String,
    private val sort: String
) : PagingSource<Int, Repo>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Repo> {
        return try {
            val page = params.key ?: STARTING_PAGE_INDEX
            val result = service.searchRepositories(query, sort, page).items
            LoadResult.Page(
                data = result,
                prevKey = null,
                nextKey = page + 1
            )
        } catch (e: IOException) {
            LoadResult.Error(e)
        } catch (e: HttpException) {
            LoadResult.Error(e)
        }
    }

    companion object {
        private const val STARTING_PAGE_INDEX = 1
    }
}