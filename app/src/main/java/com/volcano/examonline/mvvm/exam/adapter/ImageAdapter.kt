package com.volcano.examonline.mvvm.exam.adapter

import android.content.Context
import android.content.Intent
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.volcano.examonline.WebviewActivity
import com.volcano.examonline.mvvm.exam.model.Banner
import com.youth.banner.adapter.BannerAdapter

class ImageAdapter(val mContext : Context, val banners : ArrayList<Banner>)
    : BannerAdapter<Banner, ImageAdapter.ViewHolder?>(banners){

    class ViewHolder(view : ImageView) : RecyclerView.ViewHolder(view) {
        val imageView : ImageView = view
    }

    override fun onCreateHolder(parent: ViewGroup?, viewType: Int): ViewHolder? {
        val imageView = ImageView(parent!!.context)
        imageView.layoutParams = ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT,
        )
        imageView.scaleType = ImageView.ScaleType.CENTER_CROP
        val holder = ViewHolder(imageView)
        holder.itemView.setOnClickListener {
            val pos = holder.adapterPosition
            val intent = Intent(this.mContext, WebviewActivity::class.java).apply {
                putExtra("link",banners[pos].url)
                putExtra("title",banners[pos].title)
            }
            this.mContext.startActivity(intent)
        }
        return holder
    }

    override fun onBindView(holder: ViewHolder?, data: Banner?, position: Int, size: Int) {
        Glide.with(mContext).load(data?.imagePath).into(holder!!.imageView)
    }

}