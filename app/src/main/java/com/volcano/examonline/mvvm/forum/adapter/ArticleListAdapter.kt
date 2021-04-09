package com.volcano.examonline.mvvm.forum.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.volcano.examonline.databinding.AdapterArticleFooterBinding
import com.volcano.examonline.databinding.AdapterArticleListBinding
import com.volcano.examonline.mvvm.forum.model.Article
import java.text.SimpleDateFormat

class ArticleListAdapter(val context : Context, private val articles : ArrayList<Article>)
    : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val FOOTER_ITEM = 0x1
    private val DATA_ITEM = 0x2


    class DataViewHolder(private val binding : AdapterArticleListBinding) : RecyclerView.ViewHolder(binding.root){
        fun bind(article : Article) {
            binding.tvArticleAuthor.text = article.username
//            binding.tvArticleDate.text = SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(article.createat)
            binding.tvArticleTitle.text = article.title
        }
    }

    class FooterViewHolder(binding: AdapterArticleFooterBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) : RecyclerView.ViewHolder {
        lateinit var holder : RecyclerView.ViewHolder
        when(viewType) {
            FOOTER_ITEM -> {
                val binding = AdapterArticleFooterBinding.inflate(LayoutInflater.from(parent.context),parent,false)
                holder = FooterViewHolder(binding)
            }
            DATA_ITEM -> {
                val binding = AdapterArticleListBinding.inflate(LayoutInflater.from(parent.context),parent,false)
                holder = DataViewHolder(binding)
                holder.itemView.setOnClickListener {
//                    val pos = holder.adapterPosition
//                    val intent = Intent(this.context, WebviewActivity::class.java).apply {
//                        putExtra("link",articles[pos].link)
//                        putExtra("title",articles[pos].title)
//                    }
//                    this.context.startActivity(intent)
                }
            }
        }
        return holder
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if(holder is DataViewHolder) {
            holder.bind(articles[position])
        }
    }

    override fun getItemCount(): Int = articles.size + 1

    override fun getItemViewType(position: Int): Int = when {
        position == itemCount - 1 -> {
            FOOTER_ITEM
        }
        else -> {
            DATA_ITEM
        }
    }
}