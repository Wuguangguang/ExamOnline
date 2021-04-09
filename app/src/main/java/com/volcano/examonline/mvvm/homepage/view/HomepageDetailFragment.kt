package com.volcano.examonline.mvvm.homepage.view

import androidx.lifecycle.observe
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.volcano.examonline.R
import com.volcano.examonline.base.BaseMvvmFragment
import com.volcano.examonline.databinding.HomepageDetailFragmentBinding
import com.volcano.examonline.mvvm.homepage.adapter.QuestionListAdapter
import com.volcano.examonline.mvvm.homepage.viewmodel.HomepageDetailViewModel

class HomepageDetailFragment(private val subjectId : Int) : BaseMvvmFragment<HomepageDetailFragmentBinding, HomepageDetailViewModel>() {

    companion object {
        fun newInstance(id : Int) = HomepageDetailFragment(id)
    }

    private val questionAdapter by lazy { QuestionListAdapter(activity!! , mViewModel.questions) }
    private var currentPage = 0
    private var lastItemPosition = 0

    override fun initView() {
        mBinding.swipeRefreshL.apply{
            setColorSchemeResources(R.color.colorAccent)
            setOnRefreshListener {
                mViewModel.getWxArticles(0, subjectId)
            }
        }
        mBinding.rcvQuestionDetail.apply {
            layoutManager = LinearLayoutManager(activity!!)
            adapter = questionAdapter
            addOnScrollListener(object : RecyclerView.OnScrollListener(){
                override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                    if(newState == RecyclerView.SCROLL_STATE_IDLE && lastItemPosition == questionAdapter.itemCount) {
                        mViewModel.getWxArticles(++currentPage,subjectId)
                    }
                }
                override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                    val layoutManager = recyclerView.layoutManager
                    if(layoutManager is LinearLayoutManager) {
                        val firstVisibleItem = layoutManager.findFirstVisibleItemPosition()
                        val last = layoutManager.findLastCompletelyVisibleItemPosition()
                        lastItemPosition = firstVisibleItem + (last - firstVisibleItem) + 1
                    }
                }
            })
        }
    }

    override fun initData() {
        mViewModel.questionPage.observe(this){
            if(it != null && !it.datas.isNullOrEmpty()){
                mViewModel.questions.addAll(it.datas!!)
                questionAdapter.notifyDataSetChanged()
                mBinding.swipeRefreshL.isRefreshing = false
            }
        }
        mViewModel.getWxArticles(currentPage,subjectId)
    }
}
