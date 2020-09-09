package com.example.githubrepositories.ui.viewmodel

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.SupervisorJob

open class BaseViewModel : ViewModel(), CoroutineScope {

    override val coroutineContext = Main

    private val viewModelJob = SupervisorJob()

    protected val viewModelScope = CoroutineScope(coroutineContext + viewModelJob)

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }

}