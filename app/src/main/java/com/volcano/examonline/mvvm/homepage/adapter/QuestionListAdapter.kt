package com.volcano.examonline.mvvm.homepage.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.volcano.examonline.WebviewActivity
import com.volcano.examonline.databinding.AdapterArticleFooterBinding
import com.volcano.examonline.databinding.AdapterQuestionListBinding
import com.volcano.examonline.mvvm.exam.model.Question
import com.volcano.examonline.mvvm.homepage.view.QuestionActivity

class QuestionListAdapter(val context : Context, private val questions : ArrayList<Question>)
    : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val FOOTER_ITEM = 0x1
    private val DATA_ITEM = 0x2


    class DataViewHolder(private val binding : AdapterQuestionListBinding) : RecyclerView.ViewHolder(binding.root){
        fun bind(question : Question) {
            binding.tvHomepageQuestionLevel.text = question.level
            binding.tvHomepageQuestionSource.text = question.source
            binding.tvHomepageQuestionTitle.text = question.desc
            binding.tvHomepageQuestionType.text = question.type
        }
    }

    class FooterViewHolder(binding: AdapterArticleFooterBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) : RecyclerView.ViewHolder {
        lateinit var holder : RecyclerView.ViewHolder
        when(viewType) {
            FOOTER_ITEM -> {
                val binding = AdapterArticleFooterBinding.inflate(LayoutInflater.from(parent.context),parent,false)
                holder = FooterViewHolder(binding)
            }
            DATA_ITEM -> {
                val binding = AdapterQuestionListBinding.inflate(LayoutInflater.from(parent.context),parent,false)
                holder = DataViewHolder(binding)
                holder.itemView.setOnClickListener {
                    val pos = holder.adapterPosition
                    val intent = Intent(this.context, QuestionActivity::class.java).apply {
                        putExtra("questionId", questions[pos].id)
                    }
                    this.context.startActivity(intent)
                }
            }
        }
        return holder
    }


    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if(holder is DataViewHolder) {
            holder.bind(questions[position])
        }
    }

    override fun getItemCount(): Int = questions.size + 1

    override fun getItemViewType(position: Int): Int = when {
        position == itemCount - 1 -> {
            FOOTER_ITEM
        }
        else -> {
            DATA_ITEM
        }
    }
}