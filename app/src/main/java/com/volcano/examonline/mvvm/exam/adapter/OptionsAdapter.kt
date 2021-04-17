package com.volcano.examonline.mvvm.exam.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.volcano.examonline.R
import com.volcano.examonline.databinding.AdapterOptionBinding
import com.volcano.examonline.util.ConstantData

class OptionsAdapter(private val mContext: Context,
                     private val options: ArrayList<String>,
                     private val type: String,
                     private val mode: Int,
                     private val mySelect: ArrayList<Int>?,
                     private val correctSelect: ArrayList<Int>?)
    : RecyclerView.Adapter<OptionsAdapter.ViewHolder>() {

    private var onItemClickListener: OnItemClickListener? = null
    private var ivCurrentSelected: ImageView? = null
    private var tvCurrentSelected: TextView? = null
    private var currentSelectedPos: Int? = null
    private var myAnswer = arrayListOf<String>()
    private var isHasSelected: Boolean = false

    class ViewHolder(val binding: AdapterOptionBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(option: String, pos:Int) {
            binding.tvOption.text = option
            binding.ivOption.setImageResource(ConstantData.normalImages[pos])
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(AdapterOptionBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun getItemCount(): Int = options.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(options[position], position)
        if(mode == ConstantData.MODE_ANALYSIS) {
            if(correctSelect!!.contains(position)) {
                holder.binding.ivOption.setImageResource(ConstantData.correctImages[position])
                holder.binding.tvOption.setTextColor(mContext.resources.getColor(R.color.colorAccent))
            }else if(mySelect!!.contains(position)) {
                holder.binding.ivOption.setImageResource(ConstantData.incorrectImages[position])
                holder.binding.tvOption.setTextColor(mContext.resources.getColor(R.color.COLOR_RED))
            }
        }else {
            holder.itemView.setOnClickListener {
                // 单题详情、顺序练习    点击一次后就不可以再点击
                if(mode == ConstantData.MODE_IMMEDIATELY && (type == ConstantData.TYPE_SINGLE_SELECT || type == ConstantData.TYPE_JUDGE)) {
                    if(!isHasSelected) {
                        this.onItemClickListener!!.onClick(arrayListOf(ConstantData.answers[position]))
                    }
                }else {
                    // 1.单题详情、顺序练习 多选题  2.模拟考试
                    if(type == ConstantData.TYPE_SINGLE_SELECT || type == ConstantData.TYPE_JUDGE) {
                        if(ivCurrentSelected != null) {
                            ivCurrentSelected!!.setImageResource(ConstantData.normalImages[currentSelectedPos!!])
                            tvCurrentSelected!!.setTextColor(mContext.resources.getColor(R.color.black))
                            myAnswer.clear()  //清空答案
                        }
                        ivCurrentSelected = holder.itemView.findViewById(R.id.iv_option)
                        tvCurrentSelected = holder.itemView.findViewById(R.id.tv_option)
                        currentSelectedPos = position
                    }
                    //多选题取消选择
                    if(myAnswer.contains(ConstantData.answers[position]) && type == ConstantData.TYPE_MULTI_SELECT) {
                        holder.binding.ivOption.setImageResource(ConstantData.normalImages[position])
                        holder.binding.tvOption.setTextColor(mContext.resources.getColor(R.color.black))
                        myAnswer.remove(ConstantData.answers[position])
                    }else {
                        holder.binding.ivOption.setImageResource(ConstantData.correctImages[position])
                        holder.binding.tvOption.setTextColor(mContext.resources.getColor(R.color.colorAccent))
                        myAnswer.add(ConstantData.answers[position])
                    }
                    this.onItemClickListener!!.onClick(myAnswer)
                }
            }
        }
    }

    fun setOnClickListener(onItemClickListener: OnItemClickListener) {
        this.onItemClickListener = onItemClickListener
    }

    fun setSelected() {
        isHasSelected = true
    }
}