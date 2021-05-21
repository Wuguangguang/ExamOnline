package com.volcano.examonline.mvvm.forum.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.volcano.examonline.databinding.AdapterCommentsBinding
import com.volcano.examonline.databinding.AdapterFooterBinding
import com.volcano.examonline.databinding.AdapterQuestionListBinding
import com.volcano.examonline.mvvm.question.view.QuestionActivity
import com.volcano.examonline.mvvm.study.adapter.QuestionListAdapter
import com.volcano.examonline.mvvm.study.model.Comment
import com.volcano.examonline.util.ConstantData

class CommentsAdapter(private val mContext: Context, private val comments: ArrayList<Comment>) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val FOOTER_ITEM = 0x1
    private val DATA_ITEM = 0x2

    class DataViewHolder(private val binding: AdapterCommentsBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(comment: Comment) {
            binding.tvCommenterAuthor.text = comment.userName
            binding.tvCommentDesc.text = comment.description
            binding.tvCommenterDate.text = ConstantData.str2Timestamp(comment.createat!!)
        }

    }

    class FooterViewHolder(binding: AdapterFooterBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        lateinit var holder : RecyclerView.ViewHolder
        when(viewType) {
            FOOTER_ITEM -> {
                val binding = AdapterFooterBinding.inflate(LayoutInflater.from(parent.context),parent,false)
                holder = FooterViewHolder(binding)
            }
            DATA_ITEM -> {
                val binding = AdapterCommentsBinding.inflate(LayoutInflater.from(parent.context),parent,false)
                holder = DataViewHolder(binding)
            }
        }
        return holder
    }

    override fun getItemCount() = comments.size + 1

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if(holder is DataViewHolder) {
            holder.bind(comments[position])
        }
    }

    override fun getItemViewType(position: Int): Int = when {
        position == itemCount - 1 -> {
            FOOTER_ITEM
        }
        else -> {
            DATA_ITEM
        }
    }
}