package com.example.githubrepositories.utils

import com.example.githubrepositories.model.Repo
import com.example.githubrepositories.model.RepoEntity

class RepoMapper {

    fun transformRepoToEntity(repos: List<Repo>): List<RepoEntity> =
        repos.map {
            RepoEntity(
                id = it.id,
                name = it.name,
                fullName = it.fullName,
                stars = it.stars,
                forks = it.forks,
                owner = it.owner
            )
        }

    fun transformRepoEntitytoRepo(reposEntities: List<RepoEntity>): List<Repo> =
        reposEntities.map {
            Repo(
                id = it.id,
                name = it.name,
                fullName = it.fullName,
                stars = it.stars,
                forks = it.forks,
                owner = it.owner
            )
        }

}