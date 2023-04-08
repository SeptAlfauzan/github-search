package com.example.githubprofile.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.githubprofile.databinding.ItemUserBinding
import com.example.githubprofile.model.User

class UserRecyclerViewAdapter(private var users: List<User>, private val onClick: (String) -> Unit = {}): RecyclerView.Adapter<UserRecyclerViewAdapter.ViewHolder>() {
    class ViewHolder(val binding: ItemUserBinding, val onClick: (String) -> Unit): RecyclerView.ViewHolder(binding.root) {
        fun bind(user: User) {
            with(binding){
                profileUsername.text = user.login
                Glide.with(itemView.context).load(user.avatarUrl).into(binding.profileImage)
                binding.root.setOnClickListener{
                    onClick(user.login!!)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemUserBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding, onClick)
    }

    override fun getItemCount(): Int = users.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(users[position])
    }
    fun updateData(newData: List<User>){
        val callback = object: DiffUtil.Callback(){
            override fun getOldListSize(): Int = users.size

            override fun getNewListSize(): Int = newData.size

            override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
                return users[oldItemPosition].id == newData[newItemPosition].id
            }

            override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
                return users[oldItemPosition] == newData[newItemPosition]
            }

        }
        val result = DiffUtil.calculateDiff(callback)
        users = newData
        result.dispatchUpdatesTo(this)
    }

}