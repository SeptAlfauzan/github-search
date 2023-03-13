package com.example.githubprofile.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.githubprofile.databinding.ItemUserBinding
import com.example.githubprofile.response.UserResponse

class UserRecyclerViewAdapter(private var users: List<UserResponse>, val onClick: (String) -> Unit = {}): RecyclerView.Adapter<UserRecyclerViewAdapter.ViewHolder>() {
    class ViewHolder(val binding: ItemUserBinding): RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemUserBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int = users.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        holder.binding.profileUsername.text = users[position].login
        Glide.with(holder.itemView.context).load(users[position].avatarUrl).into(holder.binding.profileImage)

        holder.binding.profileImage.setOnClickListener{
            onClick(users[position].login!!)
        }

    }
//
//    fun updateData(newData: List<UserResponse>){
//        users = newData
//        notifyDataSetChanged()
//    }

    fun updateData(newData: List<UserResponse>){
        val callback = object: DiffUtil.Callback(){
            override fun getOldListSize(): Int = users.size

            override fun getNewListSize(): Int = newData.size

            override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
                return users[oldItemPosition] == newData[newItemPosition]
            }

            override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
                return users[oldItemPosition].name == newData[newItemPosition].name
            }

        }
        val result = DiffUtil.calculateDiff(callback)
        users = newData
        result.dispatchUpdatesTo(this)
    }


}