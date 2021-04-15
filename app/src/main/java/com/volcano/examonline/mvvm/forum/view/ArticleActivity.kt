package com.volcano.examonline.mvvm.forum.view

import com.volcano.examonline.R
import com.volcano.examonline.base.BaseMvvmActivity
import com.volcano.examonline.databinding.ActivityArticleBinding
import com.volcano.examonline.mvvm.forum.viewmodel.ArticleViewModel
import com.volcano.examonline.mvvm.forum.model.Article

class ArticleActivity : BaseMvvmActivity<ActivityArticleBinding, ArticleViewModel>() {

    private var article: Article? = null

    override fun initView() {
        initToolbar()
        article = intent.getSerializableExtra("article") as Article
        mBinding.tvArticleTitle.text = article!!.title
        mBinding.tvArticleAuthorName.text = article!!.username
        mBinding.tvArticleContent.text = article!!.description

    }

    private fun initToolbar() {
        mBinding.toolbarArticle.toolbarLeftImageBack.apply {
            setImageResource(R.drawable.ic_black_back)
            setOnClickListener { finish() }
        }
        mBinding.toolbarArticle.toolbarTitle.text = "帖子详情"
    }
}