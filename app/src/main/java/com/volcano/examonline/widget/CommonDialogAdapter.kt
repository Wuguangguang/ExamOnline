package com.volcano.examonline.widget

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.volcano.examonline.databinding.AdapterCommonDialogListBinding
class CommonDialogAdapter(val mContext: Context, val datas: ArrayList<String>) : RecyclerView.Adapter<CommonDialogAdapter.ViewHolder>() {

    private var onItemClickListener: CommonDialogOnItemClickListener? = null

    class ViewHolder(val binding: AdapterCommonDialogListBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(s: String) {
            binding.tvCommonDialogListItem.text = s
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = AdapterCommonDialogListBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return ViewHolder(binding)
    }

    override fun getItemCount() = datas.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(datas[position])
        holder.itemView.setOnClickListener {
            this.onItemClickListener!!.onCLick(datas[position])
        }
    }

    fun setOnClickListener(onItemClickListener: CommonDialogOnItemClickListener) {
        this.onItemClickListener = onItemClickListener
    }
}