package com.example.githubrepositories.ui

import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.githubrepositories.R
import com.example.githubrepositories.databinding.ActivityMainBinding
import com.example.githubrepositories.model.Repo
import com.example.githubrepositories.model.Resource
import com.example.githubrepositories.ui.adapter.RepoAdapter
import com.example.githubrepositories.ui.viewmodel.MainViewModel
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : BaseActivity<ActivityMainBinding, MainViewModel>() {

    override fun getViewModelClass(): Class<MainViewModel>? = MainViewModel::class.java

    override fun getLayout(): Int = R.layout.activity_main

    override fun init() {
        viewModel.searchRepositories()

        viewModel.reposLiveData.observe(this, Observer {
            when(it.status) {
                Resource.Status.LOADING -> {

                }
                Resource.Status.SUCCESS -> {
                    it.data?.let {
                            data -> setupList(data)
                    } ?: run {

                    }
                }
                else -> {

                }
            }
        })
    }

    private fun setupList(repos: List<Repo>) {
        val adapter = RepoAdapter(repos)
        actMain_Repos_rv.adapter = adapter
        actMain_Repos_rv.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
    }
}