package com.volcano.examonline.mvvm.detail.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.AdapterView
import androidx.recyclerview.widget.RecyclerView
import com.volcano.examonline.R
import com.volcano.examonline.databinding.AdapterCommitResultBinding

class CommitResultAdapter(private val mContext: Context): RecyclerView.Adapter<CommitResultAdapter.ViewHolder>() {

    private var results: ArrayList<Int>? = null
    private var onItemClickListener: AdapterView.OnItemClickListener? = null

    fun setOnClickListener(onItemClickListener: AdapterView.OnItemClickListener) {
        this.onItemClickListener = onItemClickListener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = AdapterCommitResultBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int = if(results != null) results!!.size else 0

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(results!![position], position)
        holder.itemView.setOnClickListener {
            this.onItemClickListener!!.onItemClick(null, null, position, -1)
        }
    }

    fun setResults(it: ArrayList<Int>) {
        if(results == null ) {
            results = ArrayList()
        }
        results!!.addAll(it)
    }

    class ViewHolder(val binding: AdapterCommitResultBinding) : RecyclerView.ViewHolder(binding.root){
        fun bind(isCorrect: Int, position: Int) {
            binding.tvQuestion.apply {
                setBackgroundResource(if(isCorrect == 1) R.drawable.shape_green_circle else R.drawable.shape_red_circle)
                val pos = position+1
                text = "$pos"
            }
        }
    }
}