package com.example.githubrepositories.ui.viewmodel

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

    private var currentPage: Int = 1
    val isLoading = MutableLiveData<Boolean>(false)
    val reposLiveData = MutableLiveData<List<Repo>>(mutableListOf())

    fun searchRepos() {
        isLoading.value = true
        getPage()?.let { currentPage = it }
        viewModelScope.launch {
            val result = mainRepository.searchRepositories(
                "language:kotlin",
                "stars",
                currentPage
            )

            if (reposLiveData.value != result)
                reposLiveData.value = result

            isLoading.value = false
        }
    }

    private fun getPage(): Int? {
        val page = preferencesHelper.restoreInteger(CURRENT_PAGE_PREF)
        return if (page == -1) 1 else page
    }

}