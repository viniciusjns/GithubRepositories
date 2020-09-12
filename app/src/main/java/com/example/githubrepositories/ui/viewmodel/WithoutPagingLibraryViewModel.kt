package com.example.githubrepositories.ui.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.example.githubrepositories.model.Repo
import com.example.githubrepositories.repository.MainRepository
import com.example.githubrepositories.utils.Constants.CURRENT_PAGE_PREF
import com.example.githubrepositories.utils.PreferencesHelper
import kotlinx.coroutines.launch
import javax.inject.Inject

class WithoutPagingLibraryViewModel @Inject constructor(
    private val mainRepository: MainRepository,
    private val preferencesHelper: PreferencesHelper
) : BaseViewModel() {

    var currentPage: Int = 1
    val isLoading = MutableLiveData<Boolean>(false)
    val reposLiveData = MutableLiveData<List<Repo>>(mutableListOf())
    private val tempList = mutableListOf<Repo>()

    fun searchRepos() {
        isLoading.value = true
        getPage()?.let {
            currentPage = it
            viewModelScope.launch {
                val result = mainRepository.searchRepositories(
                    "language:kotlin",
                    "sort",
                    currentPage
                )

                if (tempList.isEmpty()) {
                    reposLiveData.value = result
                } else {
                    if (tempList != result) {
                        reposLiveData.value = result
                    }
                }

                isLoading.value = false
            }
        }
    }

    private fun getPage(): Int? {
        val page = preferencesHelper.restoreInteger(CURRENT_PAGE_PREF)
        return if (page == -1) 1 else page
    }

}