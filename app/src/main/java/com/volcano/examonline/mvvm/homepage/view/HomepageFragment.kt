package com.volcano.examonline.mvvm.homepage.view

import android.content.Intent
import androidx.lifecycle.observe
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.volcano.examonline.base.BaseMvvmFragment
import com.volcano.examonline.databinding.HomepageFragmentBinding
import com.volcano.examonline.mvvm.homepage.adapter.SubjectAdapter
import com.volcano.examonline.mvvm.homepage.viewmodel.HomepageViewModel
import com.volcano.examonline.mvvm.search.SearchActivity

class HomepageFragment : BaseMvvmFragment<HomepageFragmentBinding, HomepageViewModel>() {

    companion object {
        fun newInstance() = HomepageFragment()
    }

    private val subjectAdapter : SubjectAdapter by lazy { SubjectAdapter(this , mViewModel.subjectData) }
    private var tabLayoutMediator: TabLayoutMediator? = null

    override fun initView() {
        mBinding.tlSubjects.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab?) {
                mBinding.vpQuestions.currentItem = tab!!.position
            }
            override fun onTabUnselected(tab: TabLayout.Tab?) {
            }
            override fun onTabReselected(tab: TabLayout.Tab?) {
            }
        })
        mBinding.vpQuestions.apply {
            adapter = subjectAdapter
        }
        mBinding.tvSearch.setOnClickListener {
            val intent = Intent(activity, SearchActivity::class.java)
            startActivity(intent)
        }
        mBinding.tvUploadQuestion.setOnClickListener {
            val intent = Intent(activity, QuestionUploadActivity::class.java)
            startActivity(intent)
        }
    }

    override fun initData() {
        mViewModel.subjects.observe(activity!!) {
            if(!it.isNullOrEmpty()) {
                mBinding.vpQuestions.offscreenPageLimit = it.size
                tabBind(mBinding.vpQuestions)
                mViewModel.subjectData.addAll(it)
                subjectAdapter.notifyDataSetChanged()
                subjectAdapter.initFragments()
            }
        }
        mViewModel.getSubjects()
    }

    private fun tabBind(pager: ViewPager2){
        tabLayoutMediator?.detach()
        tabLayoutMediator = TabLayoutMediator(mBinding.tlSubjects,pager){ tab, position ->
            tab.text = mViewModel.subjects.value!![position].subjectname
        }
        tabLayoutMediator?.attach()
    }

}