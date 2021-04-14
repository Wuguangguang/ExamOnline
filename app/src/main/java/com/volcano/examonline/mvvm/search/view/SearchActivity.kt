package com.volcano.examonline.mvvm.search.view

import android.view.View
import androidx.core.widget.addTextChangedListener
import androidx.recyclerview.widget.LinearLayoutManager
import com.volcano.examonline.base.BaseMvvmActivity
import com.volcano.examonline.base.hideSoftInput
import com.volcano.examonline.databinding.ActivitySearchBinding
import com.volcano.examonline.mvvm.forum.adapter.ArticleListAdapter
import com.volcano.examonline.mvvm.search.adapter.SearchAdapter
import com.volcano.examonline.mvvm.search.viewmodel.SearchViewModel
import com.volcano.examonline.widget.FlowLayoutManager
import com.volcano.examonline.widget.SpaceItemDecoration

class SearchActivity : BaseMvvmActivity<ActivitySearchBinding, SearchViewModel>() {

//    private val searchAdapter : SearchAdapter by lazy { SearchAdapter(mViewModel.hotkeys, onItemClickListener) }
    private val articleAdapter : ArticleListAdapter by lazy{ ArticleListAdapter(this, mViewModel.results) }
    private val onItemClickListener = object : SearchAdapter.OnItemClickListener {
        override fun onClick(position: Int) {
//            val name = mViewModel.hotkeys[position].name
//            mBinding.searchEditText.text = name
//            mBinding.searchEditText.setSelection(name!!.length)
        }
    }

    override fun initView() {
        mBinding.etSearch.apply {
            requestFocus()
            addTextChangedListener {
                if(it.isNullOrEmpty()) {
                    mBinding.rvHotkey.visibility = View.VISIBLE
                    mBinding.rvSearchResult.visibility = View.GONE
                    mViewModel.getSearchWords()
                }
            }
        }
        mBinding.ivSearch.setOnClickListener {
            hideSoftInput(mBinding.root, this)
            mBinding.etSearch.clearFocus()
            mViewModel.getSearchResult(mBinding.etSearch.text.toString())
        }
        mBinding.ivBack.setOnClickListener{
            finish()
        }
        mBinding.rvHotkey.apply {
            addItemDecoration(
                SpaceItemDecoration(8)
            )
            layoutManager = FlowLayoutManager()
//            adapter = searchAdapter
        }
        mBinding.rvSearchResult.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = articleAdapter
            visibility = View.GONE
        }
    }

    override fun initData() {
//        mViewModel.hotkeyFlag.observe(this){
//            if(!it.isNullOrEmpty()) {
//                mViewModel.hotkeys.addAll(it)
//                searchAdapter.notifyDataSetChanged()
//            }
//        }
//        mViewModel.searchKey.observe(this) {
//            if(it != null) {
//                mBinding.hotkeyRcv.visibility = View.GONE
//                mBinding.searchRcv.visibility = View.VISIBLE
//                mViewModel.results.add(it)
//                articleAdapter.notifyDataSetChanged()
//            }
//        }
        mViewModel.getSearchWords()
    }


}