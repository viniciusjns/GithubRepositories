package com.example.githubrepositories.dao

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.githubrepositories.model.Repo

@Dao
interface RepoDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(repos: List<Repo>)

    @Query("SELECT * FROM github_repository")
    fun getRepos(): PagingSource<Int, Repo>

    @Query("DELETE FROM github_repository")
    suspend fun deleteAll()

}