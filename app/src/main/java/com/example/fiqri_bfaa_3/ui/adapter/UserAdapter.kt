package com.example.fiqri_bfaa_3.ui.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.fiqri_bfaa_3.databinding.ItemUserBinding
import com.example.fiqri_bfaa_3.data.model.User

class UserAdapter(private var ListUser: List<User>) :
    RecyclerView.Adapter<UserAdapter.ListViewHolder>() {
    private lateinit var onItemClickCallback: OnItemClickCallback

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        val binding = ItemUserBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ListViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        val user = ListUser[position]
        val photo = user.avatarUrl
        val username = user.login

        holder.binding.tvUsername.text = username

        Glide.with(holder.itemView.context)
            .load(photo)
            .circleCrop()
            .into(holder.binding.ivUser)



        holder.itemView.setOnClickListener { onItemClickCallback.onItemClicked(ListUser[holder.adapterPosition]) }
    }

    override fun getItemCount(): Int = ListUser.size

    @SuppressLint("NotifyDataSetChanged")
    fun setList(it: ArrayList<User>) {
        ListUser = it
        notifyDataSetChanged()


    }

    class ListViewHolder(var binding: ItemUserBinding) : RecyclerView.ViewHolder(binding.root)

    interface OnItemClickCallback {
        fun onItemClicked(data: User)
    }


}