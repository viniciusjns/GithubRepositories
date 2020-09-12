package com.example.githubrepositories.repository.datasource

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.example.githubrepositories.dao.GithubDatabase
import com.example.githubrepositories.dao.RepoDao
import com.example.githubrepositories.model.Repo
import com.example.githubrepositories.model.RepoEntity
import com.example.githubrepositories.service.GithubService
import retrofit2.HttpException
import java.io.IOException

@OptIn(ExperimentalPagingApi::class)
class RepoRemoteMediator(
    private val query: String,
    private val database: GithubDatabase,
    private val service: GithubService
) : RemoteMediator<Int, RepoEntity>() {

    private val repoDao = database.repoDao()

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, RepoEntity>
    ): MediatorResult {
        return try {
            val pageSize = state.config.pageSize
            // The network load method takes an optional after=<user.id>
            // parameter. For every page after the first, pass the last user
            // ID to let it continue from where it left off. For REFRESH,
            // pass null to load the first page.
            val page = when (loadType) {
                LoadType.REFRESH -> {
                    val repo = getRepoClosestToCurrentPosition(state)
                    if (repo != null) repo.primaryKey / pageSize else 1
                }
                // In this example, you never need to prepend, since REFRESH
                // will always load the first page in the list. Immediately
                // return, reporting end of pagination.
                LoadType.PREPEND ->
                    return MediatorResult.Success(endOfPaginationReached = true)
                LoadType.APPEND -> {
                    val lastItem = state.lastItemOrNull()
                        ?: return MediatorResult.Success(
                            endOfPaginationReached = true
                        )

                    // You must explicitly check if the last item is null when
                    // appending, since passing null to networkService is only
                    // valid for initial load. If lastItem is null it means no
                    // items were loaded after the initial REFRESH and there are
                    // no more items to load.

                    lastItem.primaryKey / pageSize
                }
            }

            // Suspending network load via Retrofit. This doesn't need to be
            // wrapped in a withContext(Dispatcher.IO) { ... } block since
            // Retrofit's Coroutine CallAdapter dispatches on a worker
            // thread.
            val response = service.searchRepositories(
                query = query,
                sort = "stars",
                page = page
            )

            database.withTransaction {
                if (loadType == LoadType.REFRESH) {
                    repoDao.deleteAll()
                }

                // Insert new users into database, which invalidates the
                // current PagingData, allowing Paging to present the updates
                // in the DB.
                repoDao.insertAll(response.items.map {
                    RepoEntity(
                        id = it.id,
                        name = it.name,
                        fullName = it.fullName,
                        stars = it.stars,
                        forks = it.forks,
                        owner = it.owner
                    )
                })
            }

            val endOfPaginationReached = response.items.isEmpty()
            MediatorResult.Success(
                endOfPaginationReached = endOfPaginationReached
            )
        } catch (e: IOException) {
            MediatorResult.Error(e)
        } catch (e: HttpException) {
            MediatorResult.Error(e)
        }
    }

    private suspend fun getRepoClosestToCurrentPosition(
        state: PagingState<Int, RepoEntity>
    ): RepoEntity? {
        return state.anchorPosition?.let { position ->
            state.closestItemToPosition(position)?.primaryKey?.let { repoId ->
                repoDao.getRepoByPrimaryKey(repoId)
            }
        }
    }
}