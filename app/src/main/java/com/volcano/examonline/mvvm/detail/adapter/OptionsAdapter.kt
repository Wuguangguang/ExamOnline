package com.volcano.examonline.mvvm.detail.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.volcano.examonline.databinding.AdapterSelectOptionBinding
import com.volcano.examonline.mvvm.detail.view.DetailActivity
import com.volcano.examonline.util.ConstantData

class OptionsAdapter(
    val context: Context,
    private val options: ArrayList<String>,
    private val type: String
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

        companion object{
            private val SINGLE_SELECTED_ITEM = 0x1
            private val MULTI_SELECTED_ITEM = 0x2
            private val JUDGE_ITEM = 0x3
            private val FILL_ITEM = 0x4
            private val SUBJECTIVE_ITEM = 0x5
            val optionSign = listOf<String>(
                "A","B","C","D","E",
            )
        }


    class SelectViewHolder(private val binding: AdapterSelectOptionBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(option: String, position: Int) {
            binding.tvOptionSign.text = optionSign[position]
            binding.tvOptionDesc.text = option
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        var holder: RecyclerView.ViewHolder
//        when(viewType) {
//            SINGLE_SELECTED_ITEM, MULTI_SELECTED_ITEM -> {
//                val binding = AdapterSelectOptionBinding.inflate(LayoutInflater.from(parent.context), parent, false)
//                holder = SelectViewHolder(binding)
//            }
//            JUDGE_ITEM -> {
//
//            }
//            FILL_ITEM -> {
//
//            }
//            SUBJECTIVE_ITEM -> {
//
//            }
//        }
        val binding = AdapterSelectOptionBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        holder = SelectViewHolder(binding)
        return holder
    }


    override fun getItemCount(): Int = options.size - 1

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when(holder) {
            is SelectViewHolder -> holder.bind(options[position], position)
        }
    }

    override fun getItemViewType(position: Int): Int = when(type) {
        "单选题" -> SINGLE_SELECTED_ITEM
        "多选题" -> MULTI_SELECTED_ITEM
        "判断题" -> JUDGE_ITEM
        "填空题" -> FILL_ITEM
        else -> SUBJECTIVE_ITEM
    }
}