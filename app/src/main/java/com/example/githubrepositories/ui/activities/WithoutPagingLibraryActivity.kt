package com.example.githubrepositories.ui.activities

import androidx.core.view.isVisible
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.githubrepositories.R
import com.example.githubrepositories.databinding.ActivityWithoutPagingLibraryBinding
import com.example.githubrepositories.ui.adapter.RepoAdapter
import com.example.githubrepositories.ui.viewmodel.WithoutPagingLibraryViewModel
import kotlinx.android.synthetic.main.activity_without_paging_library.*


class WithoutPagingLibraryActivity : BaseActivity<ActivityWithoutPagingLibraryBinding, WithoutPagingLibraryViewModel>() {

    private val repoAdapter = RepoAdapter()

    override fun getViewModelClass(): Class<WithoutPagingLibraryViewModel>? =
        WithoutPagingLibraryViewModel::class.java

    override fun getLayout(): Int = R.layout.activity_without_paging_library

    override fun init() {
        setupToolbar()
        setupAdapter()
        observeLiveData()

        viewModel.searchRepos()
    }

    private fun observeLiveData() {
        viewModel.reposLiveData.observe(this, Observer {
            repoAdapter.submitList(it)
        })
        viewModel.isLoading.observe(this, Observer {
            actWithoutPaging_loading_pb.isVisible = it
        })
    }

    private fun setupToolbar() {
        supportActionBar?.title = "Github Repositories"
    }

    private fun setupAdapter() {
        val linearLayoutManager =
            LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)

        actWithoutPaging_Repos_rv.apply {
            layoutManager = linearLayoutManager
            setHasFixedSize(true)
            adapter = repoAdapter
        }

        actWithoutPaging_Repos_rv.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                viewModel.isLoading.value?.let {
                    if (!it) {
                        val totalItemCount = linearLayoutManager.itemCount
                        val lastVisibleItem = linearLayoutManager.findLastVisibleItemPosition()
                        if (totalItemCount <= lastVisibleItem + 5) {
                            // End has been reached
                            // Do something
                            viewModel.searchRepos()
                        }
                    }
                }
            }
        })
    }
}