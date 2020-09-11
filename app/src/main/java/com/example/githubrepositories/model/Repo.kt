package com.example.githubrepositories.model

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "github_repository")
data class Repo(
    @PrimaryKey(autoGenerate = true) val primaryKey: Int,
    val id: Int,
    val name: String,
    @SerializedName("full_name") val fullName: String,
    @SerializedName("stargazers_count") val stars: Int,
    val forks: Int,
    @Embedded val owner: Owner
)