package com.example.mvvm.adapter


import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.load
import coil.size.Scale
import coil.size.ViewSizeResolver
import com.example.mvvm.model.GithubUser
import com.example.mvvm.databinding.ItemUserBinding

class UserAdapter(
    private val itemClick: (GithubUser) -> Unit
) : ListAdapter<GithubUser, UserViewHolder>(DIFF_UTIL) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return UserViewHolder(
            binding = ItemUserBinding.inflate(layoutInflater, parent, false),
            onUserClicked = itemClick
        )
    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    companion object {
        private val DIFF_UTIL = object : DiffUtil.ItemCallback<GithubUser>() {
            override fun areItemsTheSame(
                oldItem: GithubUser,
                newItem: GithubUser
            ): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(
                oldItem: GithubUser,
                newItem: GithubUser
            ): Boolean {
                return oldItem.login == newItem.login && oldItem.avatarUrl == newItem.avatarUrl
            }
        }
    }
}

class UserViewHolder(
    private val binding: ItemUserBinding,
    private val onUserClicked: (GithubUser) -> Unit
) : RecyclerView.ViewHolder(binding.root) {
    fun bind(githubUser: GithubUser) {
        with(binding) {
            binding.count.text = githubUser.login
            image.load(githubUser.avatarUrl) {
                scale(Scale.FIT)
                size(ViewSizeResolver(root))
            }
            binding.root.setOnClickListener {
                onUserClicked(githubUser)
            }
        }
    }
}