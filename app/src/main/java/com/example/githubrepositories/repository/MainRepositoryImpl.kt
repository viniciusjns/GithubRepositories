package com.example.githubrepositories.repository

import com.example.githubrepositories.dao.GithubDatabase
import com.example.githubrepositories.model.Repo
import com.example.githubrepositories.model.RepoEntity
import com.example.githubrepositories.service.GithubService
import com.example.githubrepositories.utils.Constants.CURRENT_PAGE_PREF
import com.example.githubrepositories.utils.PreferencesHelper
import javax.inject.Inject

class MainRepositoryImpl @Inject constructor(
    private val service: GithubService,
    private val database: GithubDatabase,
    private val preferencesHelper: PreferencesHelper
) : MainRepository {

    override suspend fun searchRepositories(query: String,
                                            sort: String,
                                            page: Int): List<Repo> {
        val result = runCatching {
            service.searchRepositories(query, sort, page).items
        }.getOrNull()

        return if (result == null) {
            database.repoDao().getRepos().map {
                Repo(
                    id = it.id,
                    name = it.name,
                    fullName = it.fullName,
                    stars = it.stars,
                    forks = it.forks,
                    owner = it.owner
                )
            }
        } else {
            val datas: MutableList<Repo> = database.repoDao().getRepos().map {
                Repo(
                    id = it.id,
                    name = it.name,
                    fullName = it.fullName,
                    stars = it.stars,
                    forks = it.forks,
                    owner = it.owner
                )
            } as MutableList
            database.repoDao().insertAll(result.map {
                RepoEntity(
                    id = it.id,
                    name = it.name,
                    fullName = it.fullName,
                    stars = it.stars,
                    forks = it.forks,
                    owner = it.owner
                )
            })
            preferencesHelper.saveInteger(page.plus(1), CURRENT_PAGE_PREF)
            datas.addAll(result)
            datas
        }
    }

}