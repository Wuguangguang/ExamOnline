package com.volcano.examonline.mvvm.homepage.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.volcano.examonline.WebviewActivity
import com.volcano.examonline.databinding.AdapterQuestionListBinding
import com.volcano.examonline.mvvm.homepage.model.Question

class QuestionListAdapter(val context : Context, private val articles : ArrayList<Question>)
    : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val FOOTER_ITEM = 0x1
    private val DATA_ITEM = 0x2


    class DataViewHolder(private val binding : AdapterQuestionListBinding) : RecyclerView.ViewHolder(binding.root){
        fun bind(question : Question) {
            binding.tvHomepageQuestionLevel.text = question.difficultyLevel
            binding.tvHomepageQuestionSource.text = question.source
            binding.tvHomepageQuestionTitle.text = question.questionTitle
            binding.tvHomepageQuestionType.text = question.questionType
        }
    }

    class FooterViewHolder(binding: AdapterQuestionListBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) : RecyclerView.ViewHolder {
        lateinit var holder : RecyclerView.ViewHolder
        when(viewType) {
            FOOTER_ITEM -> {
                val binding = AdapterQuestionListBinding.inflate(LayoutInflater.from(parent.context),parent,false)
                holder = FooterViewHolder(binding)
            }
            DATA_ITEM -> {
                val binding = AdapterQuestionListBinding.inflate(LayoutInflater.from(parent.context),parent,false)
                holder = DataViewHolder(binding)
                holder.itemView.setOnClickListener {
                    val pos = holder.adapterPosition
                    val intent = Intent(this.context, WebviewActivity::class.java).apply {
//                        putExtra("link",articles[pos].link)
                        putExtra("title",articles[pos].questionTitle)
                    }
                    this.context.startActivity(intent)
                }
            }
        }
        return holder
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if(holder is DataViewHolder) {
            holder.bind(articles[position])
        }
    }

    override fun getItemCount(): Int = articles.size + 1

    override fun getItemViewType(position: Int): Int = when {
        position == itemCount - 1 -> {
            FOOTER_ITEM
        }
        else -> {
            DATA_ITEM
        }
    }
}