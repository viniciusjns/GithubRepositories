package com.example.githubrepositories.dao

import androidx.paging.PagingSource
import com.example.githubrepositories.model.Repo

interface RepoDAO {

    fun getRepos(): PagingSource<Int, Repo>

}