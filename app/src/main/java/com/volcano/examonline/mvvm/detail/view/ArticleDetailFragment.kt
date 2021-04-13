package com.volcano.examonline.mvvm.detail.view

import com.volcano.examonline.base.BaseMvvmFragment
import com.volcano.examonline.databinding.ArticleDetailFragmentBinding
import com.volcano.examonline.mvvm.detail.viewmodel.DetailViewModel
import com.volcano.examonline.mvvm.forum.model.Article

class ArticleDetailFragment(private val article: Article) : BaseMvvmFragment<ArticleDetailFragmentBinding, DetailViewModel>() {

    companion object {
        fun newInstance(article: Article) = ArticleDetailFragment(article)
    }

    override fun initView() {
        mBinding.tvArticleTitle.text = article.title
        mBinding.tvArticleAuthorName.text = article.username
        mBinding.tvArticleContent.text = article.description
    }

    override fun initData() {
        mViewModel.getComments(article.id!!)
    }

}