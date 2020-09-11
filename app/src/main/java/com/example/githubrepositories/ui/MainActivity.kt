package com.example.githubrepositories.ui

import androidx.core.view.isVisible
import androidx.lifecycle.lifecycleScope
import androidx.paging.LoadState
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.githubrepositories.R
import com.example.githubrepositories.databinding.ActivityMainBinding
import com.example.githubrepositories.ui.adapter.RepoAdapter
import com.example.githubrepositories.ui.viewmodel.MainViewModel
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class MainActivity : BaseActivity<ActivityMainBinding, MainViewModel>() {

    private val repoAdapter = RepoAdapter()

    override fun getViewModelClass(): Class<MainViewModel>? = MainViewModel::class.java

    override fun getLayout(): Int = R.layout.activity_main

    override fun init() {
        setupAdapter()

        lifecycleScope.launch {
            repoAdapter.loadStateFlow.collectLatest {
                loading.isVisible = it.refresh is LoadState.Loading
            }
        }

        lifecycleScope.launch {
            viewModel.getRepos().collectLatest {
                repoAdapter.submitData(it)
            }
        }

    }

    private fun setupAdapter() {
        actMain_Repos_rv.adapter = repoAdapter
        actMain_Repos_rv.layoutManager = LinearLayoutManager(this,
            LinearLayoutManager.VERTICAL, false)
    }
}