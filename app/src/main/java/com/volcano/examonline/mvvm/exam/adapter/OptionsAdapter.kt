package com.volcano.examonline.mvvm.exam.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.volcano.examonline.R
import com.volcano.examonline.databinding.AdapterOptionBinding

class OptionsAdapter(private val mContext: Context, private val options: ArrayList<String>, private val type: String)
    : RecyclerView.Adapter<OptionsAdapter.ViewHolder>() {

    private var onItemClickListener: OnItemClickListener? = null
    private var ivCurrentSelected: ImageView? = null
    private var tvCurrentSelected: TextView? = null
    private var currentSelectedPos: Int? = null
    private var myAnswer = arrayListOf<String>()

    companion object {
        val images = arrayListOf(
            R.drawable.ic_option_a_normal,
            R.drawable.ic_option_a_selected,
            R.drawable.ic_option_b_normal,
            R.drawable.ic_option_b_selected,
            R.drawable.ic_option_c_normal,
            R.drawable.ic_option_c_selected,
            R.drawable.ic_option_d_normal,
            R.drawable.ic_option_d_selected,
            R.drawable.ic_option_e_normal,
            R.drawable.ic_option_e_selected
        )
        val answers = arrayListOf(
            "A","B","C","D","E"
        )
    }

    class ViewHolder(val binding: AdapterOptionBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(option: String, pos:Int) {
            binding.tvOption.text = option
            binding.ivOption.setImageResource(images[2*pos])
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = AdapterOptionBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(
            binding
        )
    }

    override fun getItemCount(): Int = options.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(options[position], position)
        holder.itemView.setOnClickListener {
            //控制单选
            if(type == "单选题" || type == "判断题") {
                if(ivCurrentSelected != null) {
                    ivCurrentSelected!!.setImageResource(images[currentSelectedPos!! * 2])
                    tvCurrentSelected!!.setTextColor(mContext.resources.getColor(R.color.black))
                    myAnswer.clear()  //清空答案
                }
                ivCurrentSelected = holder.itemView.findViewById(R.id.iv_option)
                tvCurrentSelected = holder.itemView.findViewById(R.id.tv_option)
                currentSelectedPos = position
            }
            //多选题取消选择
            if(myAnswer.contains(answers[position]) && type == "多选题") {
                holder.binding.ivOption.setImageResource(images[position * 2])
                holder.binding.tvOption.setTextColor(mContext.resources.getColor(R.color.black))
                myAnswer.remove(answers[position])
            }else {
                holder.binding.ivOption.setImageResource(images[position * 2 + 1])
                holder.binding.tvOption.setTextColor(mContext.resources.getColor(R.color.colorAccent))
                myAnswer.add(answers[position])
            }
            this.onItemClickListener!!.onClick(myAnswer)
        }
    }

    fun setOnClickListener(onItemClickListener: OnItemClickListener) {
        this.onItemClickListener = onItemClickListener
    }
}