package com.volcano.examonline.mvvm.forum

import androidx.lifecycle.observe
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

    private val FIRST_PAGE = 1
    private var currentPage = FIRST_PAGE
    private var total = 0
    private var lastItemPosition = 0
    private val articleAdapter : ArticleListAdapter by lazy { ArticleListAdapter(activity!!,mViewModel.articles) }

    override fun initView() {
        mBinding.projectDetailSwipeRefreshL.setOnRefreshListener {
            mViewModel.getProjectArticles(FIRST_PAGE, mId)
        }
        mBinding.projectDetailRcv.apply {
            layoutManager = LinearLayoutManager(activity)
            adapter = articleAdapter
            addOnScrollListener(object : RecyclerView.OnScrollListener() {
                override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                    if(newState == RecyclerView.SCROLL_STATE_IDLE && lastItemPosition == articleAdapter.itemCount){
                        if(mViewModel.articles.size < total) {
                            mViewModel.getProjectArticles(++currentPage, mId)
                        }
                    }
                }
                override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                    val layoutManager = recyclerView.layoutManager
                    if(layoutManager is LinearLayoutManager) {
                        val last = layoutManager.findLastCompletelyVisibleItemPosition()
                        lastItemPosition = last + 1
                    }
                }
            })
        }
    }

    override fun initData() {
        mViewModel.articlePage.observe(this) {
            if(it != null && !it.datas.isNullOrEmpty()) {
                total = it.total!!
                mViewModel.articles.addAll(it.datas!!)
                articleAdapter.notifyDataSetChanged()
                mBinding.projectDetailSwipeRefreshL.isRefreshing = false
            }
        }
        mViewModel.getProjectArticles(currentPage,mId)
    }
}