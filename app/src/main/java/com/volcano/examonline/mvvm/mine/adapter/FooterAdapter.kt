package com.volcano.examonline.mvvm.mine.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.volcano.examonline.R
import com.volcano.examonline.databinding.AdapterMineFooterBinding
import com.volcano.examonline.mvvm.mine.model.FooterEntity

class FooterAdapter(val mContext: Context, val datas : ArrayList<FooterEntity>) : RecyclerView.Adapter<FooterAdapter.ViewHolder>() {

    class ViewHolder(val binding : AdapterMineFooterBinding) : RecyclerView.ViewHolder(binding.root){
        fun bind(data : FooterEntity) {
            binding.apply {
                mineFooterLeftImg.setImageResource(data.drawableId!!)
                mineFooterText.setText(data.text!!)
                mineFooterRightImg.setImageResource(R.drawable.shape_right_triangle)
            }
        }
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = AdapterMineFooterBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        val holder = ViewHolder(binding)
        holder.itemView.setOnClickListener {
            val position = holder.adapterPosition
            when(datas[position].text) {
            }
        }
        return holder
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(datas[position])
    }

    override fun getItemCount(): Int = datas.size
}