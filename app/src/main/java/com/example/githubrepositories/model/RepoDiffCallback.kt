package com.example.githubrepositories.model

import androidx.recyclerview.widget.DiffUtil

class RepoDiffCallback(
    private val oldRepos: List<Repo>,
    private val newRepos: List<Repo>
) : DiffUtil.Callback() {

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean =
        oldRepos[oldItemPosition].id == newRepos[newItemPosition].id

    override fun getOldListSize(): Int =
        oldRepos.size

    override fun getNewListSize(): Int =
        newRepos.size

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val oldRepo = oldRepos[oldItemPosition]
        val newRepo = newRepos[newItemPosition]

        return oldRepo.name == newRepo.name &&
                oldRepo.fullName == newRepo.fullName &&
                oldRepo.stars == newRepo.stars &&
                oldRepo.forks == newRepo.forks
    }

    override fun getChangePayload(oldItemPosition: Int, newItemPosition: Int): Any? {
        return super.getChangePayload(oldItemPosition, newItemPosition)
    }
}