package com.example.githubrepositories.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.githubrepositories.R
import com.example.githubrepositories.databinding.ItemRepoBinding
import com.example.githubrepositories.model.Repo

class RepoPagingAdapter : PagingDataAdapter<Repo, RepoPagingAdapter.ViewHolder>(DiffUtilCallBack()) {

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
        val repo = getItem(position)
        repo?.let {
            holder.binding.repo = it
        }
    }

    class ViewHolder(val binding: ItemRepoBinding) : RecyclerView.ViewHolder(binding.root)
}

class DiffUtilCallBack : DiffUtil.ItemCallback<Repo>() {
    override fun areItemsTheSame(oldItem: Repo, newItem: Repo): Boolean =
        oldItem.id == newItem.id

    override fun areContentsTheSame(oldItem: Repo, newItem: Repo): Boolean =
        oldItem.name == newItem.name &&
        oldItem.fullName == newItem.fullName &&
        oldItem.stars == newItem.stars &&
        oldItem.forks == newItem.forks


}