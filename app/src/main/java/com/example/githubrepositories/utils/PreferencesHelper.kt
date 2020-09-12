package com.example.githubrepositories.utils

import android.content.Context
import android.content.SharedPreferences
import com.example.githubrepositories.GithubRepositoriesApplication

class PreferencesHelper {

    private val TAG = PreferencesHelper::class.simpleName

    private fun getSharedPreferences(): SharedPreferences? =
        GithubRepositoriesApplication.mInstance?.getSharedPreferences(TAG, Context.MODE_PRIVATE)

    fun saveInteger(value: Int, key: String) {
        val sharedPref = getSharedPreferences()
        sharedPref?.edit()?.putInt(key, value)?.apply()
    }

    fun restoreInteger(key: String): Int? =
        getSharedPreferences()?.getInt(key, -1)
}