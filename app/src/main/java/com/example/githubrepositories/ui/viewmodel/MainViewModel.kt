package com.example.githubrepositories.ui.viewmodel

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.githubrepositories.model.Repo
import com.example.githubrepositories.repository.MainRepository
import com.example.githubrepositories.repository.datasource.RepoPagingSource
import com.example.githubrepositories.service.GithubService
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class MainViewModel @Inject constructor(
    val service: GithubService
) : BaseViewModel() {

    fun getRepos(): Flow<PagingData<Repo>> = Pager(
        config = PagingConfig(30)
    ) {
        RepoPagingSource(service, "language:kotlin", "stars")
    }.flow.cachedIn(viewModelScope)

}