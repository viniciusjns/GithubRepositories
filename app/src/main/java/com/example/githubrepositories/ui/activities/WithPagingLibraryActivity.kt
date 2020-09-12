package com.example.githubrepositories.ui.activities

import android.widget.Toast
import androidx.core.view.isVisible
import androidx.lifecycle.lifecycleScope
import androidx.paging.LoadState
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.githubrepositories.R
import com.example.githubrepositories.databinding.ActivityWithPagingLibraryBinding
import com.example.githubrepositories.model.Repo
import com.example.githubrepositories.ui.adapter.RepoPagingAdapter
import com.example.githubrepositories.ui.viewmodel.WithPagingLibraryViewModel
import kotlinx.android.synthetic.main.activity_with_paging_library.*
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class WithPagingLibraryActivity : BaseActivity<ActivityWithPagingLibraryBinding, WithPagingLibraryViewModel>() {

    private val repoAdapter = RepoPagingAdapter()

    override fun getViewModelClass(): Class<WithPagingLibraryViewModel>? = WithPagingLibraryViewModel::class.java

    override fun getLayout(): Int = R.layout.activity_with_paging_library

    override fun init() {
        setupAdapter()
        search()
    }

    private fun search() {
        lifecycleScope.launch {
            viewModel.getRepos("language:kotlin").collectLatest {
                repoAdapter.submitData(it.map {
                    Repo(
                        id = it.id,
                        name = it.name,
                        fullName = it.fullName,
                        stars = it.stars,
                        forks = it.forks,
                        owner = it.owner
                    )
                })
            }
        }
    }

    private fun setupAdapter() {
        actWithPaging_Repos_rv.apply {
            layoutManager = LinearLayoutManager(this@WithPagingLibraryActivity)
            setHasFixedSize(true)
            adapter = repoAdapter
        }

        lifecycleScope.launch {
            repoAdapter.loadStateFlow.collectLatest { loadState ->
                // Only show the list if refresh succeeds.
                actWithPaging_Repos_rv.isVisible = loadState.refresh is LoadState.NotLoading
                // Show loading spinner during initial load or refresh.
                loading.isVisible = loadState.refresh is LoadState.Loading
                // Show the retry state if initial load or refresh fails.
//                retry_button.isVisible = loadState.refresh is LoadState.Error

                // Toast on any error, regardless of whether it came from RemoteMediator or PagingSource
                val error = when {
                    loadState.prepend is LoadState.Error -> loadState.prepend as LoadState.Error
                    loadState.append is LoadState.Error -> loadState.append as LoadState.Error
                    loadState.refresh is LoadState.Error -> loadState.refresh as LoadState.Error
                    else -> null
                }
                error?.let {
                    Toast.makeText(this@WithPagingLibraryActivity,
                        "Um erro inesperado aconteceu. Verifique sua conexão e tente novamente.", Toast.LENGTH_LONG).show()
                    lifecycleScope.cancel(null)
                }
            }
        }
    }

}