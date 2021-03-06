package com.example.githubrepositories.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.githubrepositories.R
import com.example.githubrepositories.databinding.ItemRepoBinding
import com.example.githubrepositories.model.Repo
import com.example.githubrepositories.model.RepoDiffCallback

class RepoAdapter : RecyclerView.Adapter<RepoAdapter.ViewHolder>() {

    private var repos = mutableListOf<Repo>()

    override fun getItemCount(): Int = repos.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
        ViewHolder(
            DataBindingUtil.inflate<ViewDataBinding>(
                LayoutInflater.from(parent.context),
                R.layout.item_repo,
                parent,
                false
            ) as ItemRepoBinding
        )

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val repo = repos[position]
        holder.binding.repo = repo
    }

    fun submitList(list: List<Repo>) {
        val diffCallback = RepoDiffCallback(repos, list)
        val diffResult = DiffUtil.calculateDiff(diffCallback)

        repos.clear()
        repos.addAll(list)
        diffResult.dispatchUpdatesTo(this)
    }

    class ViewHolder(val binding: ItemRepoBinding) : RecyclerView.ViewHolder(binding.root)


}