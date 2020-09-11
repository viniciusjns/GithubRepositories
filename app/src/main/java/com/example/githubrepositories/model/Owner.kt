package com.example.githubrepositories.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

data class Owner(
    @SerializedName("id") val ownerId: Int,
    val login: String,
    @SerializedName("avatar_url")
    val avatarUrl: String
)