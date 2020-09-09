package com.example.githubrepositories.ui

import com.example.githubrepositories.R
import com.example.githubrepositories.databinding.ActivityMainBinding
import com.example.githubrepositories.ui.viewmodel.MainViewModel
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : BaseActivity<ActivityMainBinding, MainViewModel>() {

    override fun getViewModelClass(): Class<MainViewModel>? = MainViewModel::class.java

    override fun getLayout(): Int = R.layout.activity_main

    override fun init() {
        tvMain.text = viewModel.teste()
    }



}