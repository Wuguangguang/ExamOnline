package com.volcano.examonline.mvvm.exam.view

import android.content.Intent
import androidx.core.content.ContextCompat
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.volcano.examonline.R
import com.volcano.examonline.base.BaseMvvmFragment
import com.volcano.examonline.databinding.ExamFragmentBinding
import com.volcano.examonline.mvvm.detail.view.ExamDetailActivity
import com.volcano.examonline.mvvm.exam.adapter.ExamTabAdapter
import com.volcano.examonline.mvvm.exam.viewmodel.ExamViewModel

class ExamFragment : BaseMvvmFragment<ExamFragmentBinding, ExamViewModel>() {

    companion object {
        @JvmStatic
        fun newInstance() = ExamFragment()
    }

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
    }

    private fun tabBind(pager : ViewPager2) {
        tabLayoutMediator?.detach()
        tabLayoutMediator = TabLayoutMediator(mBinding.tabExam,pager) { tab, position ->
            tab.text = mViewModel.tabs[position].name
        }
        tabLayoutMediator?.attach()
    }

    private fun initToolbar() {
        mBinding.toolbar.apply {
            toolbarTitle.text = "考试"
            toolbarLeftImageBack.apply{
                setImageDrawable(ContextCompat.getDrawable(this.context, R.drawable.group))
                setOnClickListener {
                    val intent = Intent(activity!!, ExamDetailActivity::class.java)
                    activity!!.startActivity(intent)
                }
            }
        }
    }

    override fun initData() {
        mBinding.viewpagerExam.offscreenPageLimit = mViewModel.tabs.size
        tabBind(mBinding.viewpagerExam)
        examTabAdapter.initFragments()
    }
}

