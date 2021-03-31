package com.volcano.examonline.mvvm.exam

import android.content.Intent
import androidx.core.content.ContextCompat
import androidx.lifecycle.observe
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.volcano.examonline.R
import com.volcano.examonline.base.BaseMvvmFragment
import com.volcano.examonline.databinding.ExamFragmentBinding
import com.volcano.examonline.mvvm.exam.adapter.ExamTabAdapter
import com.volcano.examonline.mvvm.exam.adapter.ImageAdapter
import com.volcano.examonline.mvvm.exam.viewmodel.ExamViewModel
import com.volcano.examonline.mvvm.search.SearchActivity
import com.youth.banner.indicator.RectangleIndicator

class ExamFragment : BaseMvvmFragment<ExamFragmentBinding, ExamViewModel>() {

    companion object {
        @JvmStatic
        fun newInstance() = ExamFragment()
    }

    private val bannerAdapter : ImageAdapter by lazy { ImageAdapter(context!!, mViewModel.banners) }
    private var tabLayoutMediator: TabLayoutMediator? = null
    private val examTabAdapter: ExamTabAdapter by lazy { ExamTabAdapter(this, mViewModel.tabs) }

    override fun initView() {
        initToolbar()
        mBinding.tabExam.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener{
            override fun onTabReselected(tab: TabLayout.Tab?) {
                mBinding.viewpagerExam.currentItem = tab!!.position
            }
            override fun onTabUnselected(tab: TabLayout.Tab?) {}
            override fun onTabSelected(tab: TabLayout.Tab?) {}
        })
        mBinding.viewpagerExam.adapter = examTabAdapter
        mBinding.bannerExam.apply {
            adapter = bannerAdapter
            indicator = RectangleIndicator(context)
            start()
        }

    }

    private fun tabBind(pager : ViewPager2) {
        tabLayoutMediator?.detach()
        tabLayoutMediator = TabLayoutMediator(mBinding.tabExam,pager) { tab, position ->
            tab.text = mViewModel.tabs[position].examName
        }
        tabLayoutMediator?.attach()
    }

    private fun initToolbar() {
        mBinding.toolbar.apply {
            toolbarTitle.text = "首页"
            toolbarLeftImageBack.apply{
                setImageDrawable(ContextCompat.getDrawable(this.context, R.drawable.group))
                setOnClickListener {
                    val intent = Intent(activity!!, ExamDetailActivity::class.java)
                    activity!!.startActivity(intent)
                }
            }
            toolbarRightImage.apply {
                setImageDrawable(ContextCompat.getDrawable(this.context, R.drawable.ic_white_search))
                setOnClickListener {
                    val intent = Intent(context, SearchActivity::class.java)
                    context.startActivity(intent)
                }
            }
        }
    }

    override fun initData() {
        mViewModel.bannerList.observe(activity!!) {
            if(!it.isNullOrEmpty()) {
                mViewModel.banners.addAll(it)
                bannerAdapter.notifyDataSetChanged()
            }
        }
        mBinding.viewpagerExam.offscreenPageLimit = mViewModel.tabs.size
        tabBind(mBinding.viewpagerExam)
        examTabAdapter.initFragments()
        mViewModel.getBannerList()
    }
}

