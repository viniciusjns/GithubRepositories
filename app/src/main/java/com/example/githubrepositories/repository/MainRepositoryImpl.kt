package com.example.githubrepositories.repository

import com.example.githubrepositories.dao.RepoDao
import com.example.githubrepositories.model.Repo
import com.example.githubrepositories.service.GithubService
import com.example.githubrepositories.utils.Constants.CURRENT_PAGE_PREF
import com.example.githubrepositories.utils.PreferencesHelper
import com.example.githubrepositories.utils.RepoMapper
import javax.inject.Inject

class MainRepositoryImpl @Inject constructor(
    private val service: GithubService,
    private val repoDao: RepoDao,
    private val preferencesHelper: PreferencesHelper,
    private val mapper: RepoMapper
) : MainRepository {

    override suspend fun searchRepositories(query: String,
                                            sort: String,
                                            page: Int): List<Repo> {
        val result = runCatching {
            service.searchRepositories(query, sort, page).items
        }.getOrNull()

        return if (result == null) {
            mapper.transformRepoEntitytoRepo(repoDao.getRepos())
        } else {
            val datas: MutableList<Repo> =
                mapper.transformRepoEntitytoRepo(repoDao.getRepos()) as MutableList
            repoDao.insertAll(mapper.transformRepoToEntity(result))
            preferencesHelper.saveInteger(page.plus(1), CURRENT_PAGE_PREF)
            datas.addAll(result)
            datas
        }
    }

}