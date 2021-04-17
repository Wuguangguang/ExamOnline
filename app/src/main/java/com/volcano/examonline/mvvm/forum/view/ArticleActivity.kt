package com.volcano.examonline.mvvm.forum.view

import android.os.Bundle
import androidx.lifecycle.observe
import androidx.recyclerview.widget.LinearLayoutManager
import com.volcano.examonline.R
import com.volcano.examonline.base.BaseMvvmActivity
import com.volcano.examonline.databinding.ActivityArticleBinding
import com.volcano.examonline.mvvm.forum.adapter.CommentsAdapter
import com.volcano.examonline.mvvm.forum.viewmodel.ArticleViewModel
import com.volcano.examonline.mvvm.forum.model.Article

class ArticleActivity : BaseMvvmActivity<ActivityArticleBinding, ArticleViewModel>() {

    private var article: Article? = null
    private val commentsAdapter: CommentsAdapter by lazy{ CommentsAdapter(this, mViewModel.comments) }

    override fun onCreate(savedInstanceState: Bundle?) {
        article = intent.getSerializableExtra("article") as Article
        super.onCreate(savedInstanceState)
    }

    override fun initView() {
        initToolbar()
        mBinding.rvArticleCommentList.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = commentsAdapter
        }
        mBinding.tvArticleTitle.text = article!!.title
        mBinding.tvArticleAuthorName.text = article!!.username
        mBinding.tvArticleDate.text = article!!.createat
        mBinding.tvArticleContent.text = article!!.description
        mBinding.tvArticleCommentNum.text = "${article!!.commentnums ?: 0}"
        mBinding.tvCommentNums.text = "${article!!.commentnums ?: 0}"
        mBinding.tvZanNums.text = "${article!!.zannums ?: 0}"

    }

    private fun initToolbar() {
        mBinding.toolbarArticle.toolbarLeftImageBack.apply {
            setImageResource(R.drawable.ic_black_back)
            setOnClickListener { finish() }
        }
        mBinding.toolbarArticle.toolbarTitle.text = article!!.field
    }

    override fun initData() {
        mViewModel.liveComments.observe(this) {
            if(!it.isNullOrEmpty()) {
                mViewModel.comments.addAll(it)
                commentsAdapter.notifyDataSetChanged()
            }
        }
        mViewModel.getArticleComments(article!!.id!!)
    }
}