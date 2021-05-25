package com.volcano.examonline.mvvm.forum.view

import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.volcano.examonline.R
import com.volcano.examonline.base.BaseMvvmFragment
import com.volcano.examonline.databinding.FragmentForumDetailBinding
import com.volcano.examonline.mvvm.forum.adapter.ArticleListAdapter
import com.volcano.examonline.mvvm.forum.viewmodel.ForumDetailViewModel
import com.volcano.examonline.util.ConstantData

class ForumDetailFragment(private val mId : Int) : BaseMvvmFragment<FragmentForumDetailBinding, ForumDetailViewModel>(ConstantData.VIEWMODEL_EXCLUSIVE) {

    companion object {
        fun newInstance(id : Int) = ForumDetailFragment(id)
    }

    private val articleAdapter : ArticleListAdapter by lazy { ArticleListAdapter(activity!!,mViewModel.articles) }

    override fun initView() {
        contentView = mBinding.loading
        mBinding.projectDetailSwipeRefreshL.setColorSchemeResources(R.color.colorAccent)
        mBinding.projectDetailSwipeRefreshL.setOnRefreshListener {
            mBinding.projectDetailSwipeRefreshL.isRefreshing = true
            mViewModel.getArticles(mId)
        }
        mBinding.projectDetailRcv.apply {
            layoutManager = LinearLayoutManager(activity)
            adapter = articleAdapter
        }
    }

    override fun initData() {
        setDataStatus(mViewModel.articlePage, {
            mBinding.projectDetailSwipeRefreshL.isRefreshing = false
        }, {
            if(!it.isNullOrEmpty()) {
                mViewModel.articles.clear()
                mViewModel.articles.addAll(it)
                articleAdapter.notifyDataSetChanged()
                mBinding.projectDetailSwipeRefreshL.isRefreshing = false
            }
        })
        mViewModel.getArticles(mId)
    }

    override fun doRetry() {
        super.doRetry()
        contentView?.showLoading()
        mViewModel.getArticles(mId)
    }

    override fun onResume() {
        super.onResume()
        mViewModel.getArticles(mId)
    }

}