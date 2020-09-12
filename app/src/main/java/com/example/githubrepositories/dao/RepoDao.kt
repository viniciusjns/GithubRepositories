package com.example.githubrepositories.dao

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.githubrepositories.model.RepoEntity

@Dao
interface RepoDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(repos: List<RepoEntity>)

    @Query("SELECT * FROM github_repository")
    fun getPagingRepos(): PagingSource<Int, RepoEntity>

    @Query("SELECT * FROM github_repository")
    suspend fun getRepos(): List<RepoEntity>

    @Query("DELETE FROM github_repository")
    suspend fun deleteAll()

    @Query("SELECT * FROM github_repository WHERE primaryKey = :id")
    suspend fun getRepoByPrimaryKey(id: Int): RepoEntity

}