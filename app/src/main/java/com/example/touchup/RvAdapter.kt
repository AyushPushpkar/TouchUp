package com.example.touchup

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.touchup.databinding.RvitemBinding

class RvAdapter (var context: Context, var userInfo: ArrayList<userInfoItem>) :
    RecyclerView.Adapter<RvAdapter.rvViewholder>() {

    inner class rvViewholder( var binding : RvitemBinding) : RecyclerView.ViewHolder(binding.root) {
        val userImage =binding.imageView2
        val userName = binding.textView3
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): rvViewholder {
        val binding = RvitemBinding.inflate(LayoutInflater.from(context),parent,false)
        return rvViewholder(binding)
    }

    override fun getItemCount(): Int {
       return userInfo.size
    }

    override fun onBindViewHolder(holder: rvViewholder, position: Int) {

        Glide.with(context).load(userInfo[position].avatar_url).into(holder.userImage)
        holder.userName.text = userInfo[position].login
    }

}