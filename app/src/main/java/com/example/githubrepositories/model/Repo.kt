package com.example.githubrepositories.model

import com.google.gson.annotations.SerializedName

data class Repo(
    val id: Int,
    val name: String,
    @SerializedName("full_name") val fullName: String,
    @SerializedName("stargazers_count") val stars: Int,
    val forks: Int,
    val owner: Owner
)