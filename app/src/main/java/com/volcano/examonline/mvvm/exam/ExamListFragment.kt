package com.volcano.examonline.mvvm.exam

import android.view.View
import androidx.lifecycle.observe
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.volcano.examonline.base.BaseMvvmFragment
import com.volcano.examonline.databinding.ExamListFragmentBinding
import com.volcano.examonline.mvvm.exam.viewmodel.ExamListViewModel
import com.volcano.examonline.mvvm.forum.adapter.ArticleListAdapter

class ExamListFragment(private val examId: Int) : BaseMvvmFragment<ExamListFragmentBinding, ExamListViewModel>() {

    companion object {
        fun newInstance(id: Int) = ExamListFragment(id)
    }

    private val examListAdapter : ArticleListAdapter by lazy { ArticleListAdapter(context!!, mViewModel.articles) }
    private val FIRST_PAGE = 1
    private var currentPage = FIRST_PAGE
    private var total = 0
    private var lastItemPosition = 0


    override fun initView() {
        mBinding.rcvExamList.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = examListAdapter
            addOnScrollListener(object : RecyclerView.OnScrollListener() {
                override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                    if (newState == RecyclerView.SCROLL_STATE_IDLE && lastItemPosition == examListAdapter.itemCount) {
                        mViewModel.getArticleList(++currentPage)
                    }
                }

                override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                    val layoutManager = recyclerView.layoutManager
                    if (layoutManager is LinearLayoutManager) {
                        val firstVisibleItem = layoutManager.findFirstVisibleItemPosition()
                        val last = layoutManager.findLastCompletelyVisibleItemPosition()
                        lastItemPosition = firstVisibleItem + (last - firstVisibleItem) + 1
                    }
                    if (dy < 0) {    // 上滑
                        mBinding.fabBackTop.visibility = View.VISIBLE
                    } else if (dy > 0) {
                        mBinding.fabBackTop.visibility = View.GONE
                    }
                }
            })
            mBinding.swipeRefreshL.setOnRefreshListener {
                mViewModel.getArticleList(0)
                mBinding.swipeRefreshL.isRefreshing = false
            }
            mBinding.fabBackTop.setOnClickListener {
                mBinding.rcvExamList.smoothScrollToPosition(0)
            }
        }
    }

    override fun initData() {
        mViewModel.articleList.observe(activity!!) {
            if(it != null && !it.datas.isNullOrEmpty()) {
                mViewModel.articles.addAll(it.datas!!)
                examListAdapter.notifyDataSetChanged()
            }
        }
        mViewModel.getArticleList(currentPage)
    }

}