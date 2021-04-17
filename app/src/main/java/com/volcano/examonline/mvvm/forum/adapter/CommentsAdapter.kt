package com.volcano.examonline.mvvm.forum.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.volcano.examonline.databinding.AdapterCommentsBinding
import com.volcano.examonline.mvvm.study.model.Comment

class CommentsAdapter(private val mContext: Context, private val comments: ArrayList<Comment>) : RecyclerView.Adapter<CommentsAdapter.ViewHolder>() {

    class ViewHolder(private val binding: AdapterCommentsBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(comment: Comment) {
            binding.tvCommenterAuthor.text = comment.userName
            binding.tvCommentDesc.text = comment.description
            binding.tvCommenterDate.text = comment.createat
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = AdapterCommentsBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount() = comments.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(comments[position])
    }
}