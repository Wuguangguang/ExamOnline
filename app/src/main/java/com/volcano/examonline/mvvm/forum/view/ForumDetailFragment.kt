package com.volcano.examonline.mvvm.forum.view

import androidx.lifecycle.observe
import androidx.recyclerview.widget.LinearLayoutManager
import com.volcano.examonline.base.BaseMvvmFragment
import com.volcano.examonline.databinding.FragmentForumDetailBinding
import com.volcano.examonline.mvvm.forum.adapter.ArticleListAdapter
import com.volcano.examonline.mvvm.forum.viewmodel.ForumDetailViewModel

class ForumDetailFragment(private val mId : Int) : BaseMvvmFragment<FragmentForumDetailBinding, ForumDetailViewModel>() {

    companion object {
        fun newInstance(id : Int) = ForumDetailFragment(id)
    }

    private val articleAdapter : ArticleListAdapter by lazy { ArticleListAdapter(activity!!,mViewModel.articles) }

    override fun initView() {
        mBinding.projectDetailSwipeRefreshL.setOnRefreshListener {
//            mViewModel.getArticles()
        }
        mBinding.projectDetailRcv.apply {
            layoutManager = LinearLayoutManager(activity)
            adapter = articleAdapter
        }
    }

    override fun initData() {
        mViewModel.articlePage.observe(this) {
            if(!it.isNullOrEmpty()) {
                mViewModel.articles.addAll(it)
                articleAdapter.notifyDataSetChanged()
                mBinding.projectDetailSwipeRefreshL.isRefreshing = false
            }
        }
        when(mId) {
            1 -> {
                //推荐
                mViewModel.getArticles()
            }
            2 -> {
                //热榜
                mViewModel.getHotArticles()
            }
        }
    }
}