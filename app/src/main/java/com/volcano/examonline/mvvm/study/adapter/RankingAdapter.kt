package com.volcano.examonline.mvvm.study.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.volcano.examonline.databinding.AdapterRankingBinding
import com.volcano.examonline.mvvm.mine.view.MyInfoActivity
import com.volcano.examonline.mvvm.study.model.Ranking

class RankingAdapter(private val mContext: Context, private val rankings: ArrayList<Ranking>)
    : RecyclerView.Adapter<RankingAdapter.ViewHolder>() {

    class ViewHolder(private val mContext: Context ,private val binding: AdapterRankingBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(ranking: Ranking, position: Int) {
            binding.tvRankingNum.text = "${position + 4}"
            binding.tvRankingUser.text = ranking.username
            binding.tvRankingAccu.text = "刷题${ranking.accumulate}道"
            if(!ranking.avatar.isNullOrEmpty()) {
                Glide.with(mContext).load(ranking.avatar).into(binding.ivRankingUser)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(mContext,AdapterRankingBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun getItemCount() = rankings.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(rankings[position], position)
    }
}