package com.example.githubrepositories.service

import retrofit2.Retrofit

interface APIClient {
    fun configure(baseUrl: String): Retrofit
}