package com.volcano.examonline.mvvm.forum.view

import androidx.lifecycle.observe
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.volcano.examonline.base.BaseMvvmFragment
import com.volcano.examonline.databinding.ForumDetailFragmentBinding
import com.volcano.examonline.mvvm.forum.adapter.ArticleListAdapter
import com.volcano.examonline.mvvm.forum.viewmodel.ForumDetailViewModel

class ForumDetailFragment(val mId : Int) : BaseMvvmFragment<ForumDetailFragmentBinding, ForumDetailViewModel>() {

    companion object {
        fun newInstance(id : Int) = ForumDetailFragment(id)
    }

    private val articleAdapter : ArticleListAdapter by lazy { ArticleListAdapter(activity!!,mViewModel.articles) }

    override fun initView() {
        mBinding.projectDetailSwipeRefreshL.setOnRefreshListener {
            mViewModel.getArticles()
        }
        mBinding.projectDetailRcv.apply {
            layoutManager = LinearLayoutManager(activity)
            addItemDecoration(DividerItemDecoration(activity, DividerItemDecoration.VERTICAL))
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
            }
        }
    }
}