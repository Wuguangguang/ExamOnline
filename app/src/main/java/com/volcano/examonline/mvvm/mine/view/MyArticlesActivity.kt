package com.volcano.examonline.mvvm.mine.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.volcano.examonline.R
import com.volcano.examonline.base.BaseMvvmActivity
import com.volcano.examonline.databinding.ActivityMyArticlesBinding
import com.volcano.examonline.mvvm.forum.adapter.ArticleListAdapter
import com.volcano.examonline.mvvm.mine.viewmodel.MyArticlesViewModel

class MyArticlesActivity : BaseMvvmActivity<ActivityMyArticlesBinding, MyArticlesViewModel>() {

    private val articlesAdapter by lazy { ArticleListAdapter(this, mViewModel.articles) }

    override fun initView() {
        initToolbar()
        contentView = mBinding.mslMyArticles
        mBinding.rvMyArticles.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = articlesAdapter
        }
    }

    override fun initData() {
        setDataStatus(mViewModel.liveArticle) {
            if(!it.isNullOrEmpty()) {
                mViewModel.articles.clear()
                mViewModel.articles.addAll(it)
                articlesAdapter.notifyDataSetChanged()
            }
        }
        refresh()
    }

    private fun initToolbar() {
        mBinding.toolbarMyArticle.apply {
            toolbarTitle.text = "发布历史"
            toolbarLeftImageBack.setImageResource(R.drawable.ic_black_back)
            toolbarLeftImageBack.setOnClickListener { finish() }
        }
    }

    private fun refresh() {
        contentView?.showLoading()
        mViewModel.getMyArticles()
    }

    override fun doRetry() {
        super.doRetry()
        refresh()
    }
}