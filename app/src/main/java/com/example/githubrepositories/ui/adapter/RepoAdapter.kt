package com.example.githubrepositories.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView
import com.example.githubrepositories.R
import com.example.githubrepositories.databinding.ItemRepoBinding
import com.example.githubrepositories.model.Repo

class RepoAdapter(
    private val repoList: List<Repo>?
) : RecyclerView.Adapter<RepoAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
        ViewHolder(
            DataBindingUtil.inflate<ViewDataBinding>(
                LayoutInflater.from(parent.context),
                R.layout.item_repo,
                parent,
                false
            ) as ItemRepoBinding
        )


    override fun getItemCount(): Int =
        repoList?.size ?: 0

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val repo = repoList?.get(position)
        holder.binding.repo = repo
    }

    class ViewHolder(val binding: ItemRepoBinding) : RecyclerView.ViewHolder(binding.root)
}