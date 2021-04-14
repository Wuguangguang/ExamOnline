package com.volcano.examonline.mvvm.study.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.volcano.examonline.databinding.AdapterFooterBinding
import com.volcano.examonline.databinding.AdapterQuestionListBinding
import com.volcano.examonline.mvvm.detail.view.DetailActivity
import com.volcano.examonline.mvvm.study.model.Question
import com.volcano.examonline.util.ConstantData

class QuestionListAdapter(val context : Context, private val questions : ArrayList<Question>)
    : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val FOOTER_ITEM = 0x1
    private val DATA_ITEM = 0x2


    class DataViewHolder(private val binding : AdapterQuestionListBinding) : RecyclerView.ViewHolder(binding.root){
        fun bind(question : Question) {
            binding.tvQuestionLevel.text = question.level
            binding.tvQuestionType.text = question.type
            binding.tvQuestionSource.text = question.source
            binding.tvQuestionTitle.text = question.description
        }
    }

    class FooterViewHolder(binding: AdapterFooterBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) : RecyclerView.ViewHolder {
        lateinit var holder : RecyclerView.ViewHolder
        when(viewType) {
            FOOTER_ITEM -> {
                val binding = AdapterFooterBinding.inflate(LayoutInflater.from(parent.context),parent,false)
                holder = FooterViewHolder(binding)
            }
            DATA_ITEM -> {
                val binding = AdapterQuestionListBinding.inflate(LayoutInflater.from(parent.context),parent,false)
                holder = DataViewHolder(binding)
                holder.itemView.setOnClickListener {
                    val question = questions[holder.adapterPosition]
                    val intent = Intent(this.context, DetailActivity::class.java)
                    intent.apply {
                        putExtra("detailType", ConstantData.QUESTION_DETAIL)
                        putExtra("question", question)
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