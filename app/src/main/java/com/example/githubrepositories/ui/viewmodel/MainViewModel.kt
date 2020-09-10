package com.example.githubrepositories.ui.viewmodel

import androidx.lifecycle.MutableLiveData
import com.example.githubrepositories.model.Repo
import com.example.githubrepositories.model.Resource
import com.example.githubrepositories.repository.MainRepository
import kotlinx.coroutines.launch
import javax.inject.Inject

class MainViewModel @Inject constructor(
    private val mainRepository: MainRepository
) : BaseViewModel() {

    val reposLiveData = MutableLiveData<Resource<List<Repo>>>()

    fun searchRepositories() {
        viewModelScope.launch {
            reposLiveData.value = Resource.loading()
            runCatching {
                val repos = mainRepository.searchRepositories(
                    "language:kotlin",
                    "stars"
                )
                reposLiveData.value = Resource.success(repos)
            }.onFailure {
                reposLiveData.value = Resource.error(it.message)
            }
        }
    }

}