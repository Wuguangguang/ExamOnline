package com.volcano.examonline.mvvm.study.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.volcano.examonline.databinding.AdapterRankingBinding
import com.volcano.examonline.mvvm.study.model.Ranking

class RankingAdapter(private val mContext: Context, private val rankings: ArrayList<Ranking>)
    : RecyclerView.Adapter<RankingAdapter.ViewHolder>() {

    class ViewHolder(private val binding: AdapterRankingBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(ranking: Ranking) {

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(AdapterRankingBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun getItemCount() = rankings.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(rankings[position])
        holder.itemView.setOnClickListener {
            //点击跳转到个人信息页面
        }
    }
}