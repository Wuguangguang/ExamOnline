package com.volcano.examonline.mvvm.homepage

import android.content.Intent
import androidx.lifecycle.observe
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.volcano.examonline.base.BaseMvvmFragment
import com.volcano.examonline.databinding.HomepageFragmentBinding
import com.volcano.examonline.mvvm.homepage.adapter.HomepagePagerAdapter
import com.volcano.examonline.mvvm.homepage.viewmodel.HomepageViewModel
import com.volcano.examonline.mvvm.search.SearchActivity

class HomepageFragment : BaseMvvmFragment<HomepageFragmentBinding, HomepageViewModel>() {

    companion object {
        fun newInstance() = HomepageFragment()
    }

    private val homepageAdapter : HomepagePagerAdapter by lazy { HomepagePagerAdapter(this , mViewModel.subjects) }
    private var tabLayoutMediator: TabLayoutMediator? = null

    override fun initView() {
        mBinding.discoverTabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab?) {
                mBinding.discoverViewPager.currentItem = tab!!.position
            }
            override fun onTabUnselected(tab: TabLayout.Tab?) {
            }
            override fun onTabReselected(tab: TabLayout.Tab?) {
            }
        })
        mBinding.discoverViewPager.apply {
            adapter = homepageAdapter
        }
        mBinding.discoverTopSearch.setOnClickListener {
            val intent = Intent(activity, SearchActivity::class.java)
            startActivity(intent)
        }
    }

    override fun initData() {
        mViewModel.subjectList.observe(activity!!) {
            if(!it.isNullOrEmpty()) {
                mBinding.discoverViewPager.offscreenPageLimit = it.size
                tabBind(mBinding.discoverViewPager)
                mViewModel.subjects.addAll(it)
                homepageAdapter.notifyDataSetChanged()
                homepageAdapter.initFragments()
            }
        }
        mViewModel.getSubjectList()
    }



    private fun tabBind(pager: ViewPager2){
        tabLayoutMediator?.detach()
        tabLayoutMediator = TabLayoutMediator(mBinding.discoverTabLayout,pager){ tab, position ->
            tab.text = mViewModel.subjects[position].subjectName
        }
        tabLayoutMediator?.attach()
    }

}