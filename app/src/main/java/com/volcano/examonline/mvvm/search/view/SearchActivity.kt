package com.volcano.examonline.mvvm.search.view

import android.view.View
import androidx.core.widget.addTextChangedListener
import androidx.recyclerview.widget.LinearLayoutManager
import com.volcano.examonline.base.BaseMvvmActivity
import com.volcano.examonline.base.hideSoftInput
import com.volcano.examonline.databinding.ActivitySearchBinding
import com.volcano.examonline.mvvm.forum.adapter.ArticleListAdapter
import com.volcano.examonline.mvvm.question.adapter.QuestionAdapter
import com.volcano.examonline.mvvm.search.adapter.SearchAdapter
import com.volcano.examonline.mvvm.search.viewmodel.SearchViewModel
import com.volcano.examonline.mvvm.study.adapter.QuestionListAdapter
import com.volcano.examonline.widget.FlowLayoutManager
import com.volcano.examonline.widget.SpaceItemDecoration

class SearchActivity : BaseMvvmActivity<ActivitySearchBinding, SearchViewModel>() {

    private val articleAdapter : ArticleListAdapter by lazy{ ArticleListAdapter(this, mViewModel.articlesRes) }
    private val questionAdapter: QuestionListAdapter by lazy { QuestionListAdapter(this, mViewModel.questionsRes) }

    override fun initView() {
        mViewModel.type = intent.getStringExtra("type")!!
        contentView = mBinding.mslSearch
        mBinding.etSearch.apply {
            requestFocus()
            addTextChangedListener {
                if(it.isNullOrEmpty()) {
                    mBinding.rvSearchResult.visibility = View.GONE
                }
            }
        }
        mBinding.ivSearch.setOnClickListener {
            hideSoftInput(mBinding.root, this)
            mBinding.etSearch.clearFocus()
            refresh()
        }
        mBinding.ivBack.setOnClickListener{
            finish()
        }
        mBinding.rvSearchResult.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = if(mViewModel.type == "试题") questionAdapter else articleAdapter
            visibility = View.GONE
        }
    }

    override fun initData() {
        when(mViewModel.type) {
            "试题" -> {
                setDataStatus(mViewModel.liveQuestionResult, {

                }, {
                    if(!it.isNullOrEmpty()) {
                        mBinding.rvSearchResult.visibility = View.VISIBLE
                        mViewModel.questionsRes.clear()
                        mViewModel.questionsRes.addAll(it)
                        questionAdapter.notifyDataSetChanged()
                        contentView?.hideLoading()
                    }else {
                        contentView?.showEmpty()
                    }
                })
            }
            else -> {
                setDataStatus(mViewModel.liveArticleResult, {

                }, {
                    if(!it.isNullOrEmpty()) {
                        mBinding.rvSearchResult.visibility = View.VISIBLE
                        mViewModel.articlesRes.clear()
                        mViewModel.articlesRes.addAll(it)
                        articleAdapter.notifyDataSetChanged()
                        contentView?.hideLoading()
                    }else {
                        contentView?.showEmpty()
                    }
                })
            }
        }
    }

    override fun doRetry() {
        super.doRetry()
        refresh()
    }

    private fun refresh() {
        val content = mBinding.etSearch.text.toString()
        contentView?.showLoading()
        when(mViewModel.type) {
            "试题" -> mViewModel.searchQuestions(content)
            else -> mViewModel.searchArticles(content)
        }
    }
}