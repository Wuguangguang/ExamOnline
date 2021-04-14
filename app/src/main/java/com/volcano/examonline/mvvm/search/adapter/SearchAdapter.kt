package com.volcano.examonline.mvvm.search.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.volcano.examonline.databinding.AdapterSearchHotkeyItemBinding
//import com.volcano.examonline.mvvm.exam.model.Hotkey

class SearchAdapter(private val hotkeys : List<Any>, private val onItemClickListener: OnItemClickListener) : RecyclerView.Adapter<SearchAdapter.ViewHolder>() {

    class ViewHolder(val binding: AdapterSearchHotkeyItemBinding) : RecyclerView.ViewHolder(binding.root){
//        fun bind(hotkey : Hotkey) {
//            binding.tvSearchHotkeyItem.text = hotkey.name
//        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val holder =
            ViewHolder(
                AdapterSearchHotkeyItemBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
            )
        holder.itemView.setOnClickListener {
            onItemClickListener.onClick(holder.adapterPosition)
        }
        return holder
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
//        holder.bind(hotkeys[position])
    }

    override fun getItemCount(): Int = if(hotkeys.size > 9) 9 else hotkeys.size

    interface OnItemClickListener{
        fun onClick(position: Int)
    }

}