package com.volcano.examonline.mvvm.forum.view

import android.content.Intent
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.volcano.examonline.base.BaseMvvmFragment
import com.volcano.examonline.databinding.ForumFragmentBinding
import com.volcano.examonline.mvvm.forum.adapter.ForumAdapter
import com.volcano.examonline.mvvm.forum.viewmodel.ForumViewModel
import com.volcano.examonline.mvvm.login.view.LoginActivity
import com.volcano.examonline.mvvm.search.SearchActivity
import com.volcano.examonline.util.ConstantData

class ForumFragment : BaseMvvmFragment<ForumFragmentBinding, ForumViewModel>() {

    companion object {
        fun newInstance() = ForumFragment()
    }

    private var tabLayoutMediator : TabLayoutMediator? = null
    private val forumAdapter : ForumAdapter by lazy { ForumAdapter(this, mViewModel.forums) }

    override fun initView() {
        mBinding.projectTab.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener{
            override fun onTabSelected(tab: TabLayout.Tab?) {
                mBinding.projectViewpager.currentItem = tab!!.position
            }
            override fun onTabUnselected(tab: TabLayout.Tab?) {}
            override fun onTabReselected(tab: TabLayout.Tab?) {}
        })
        mBinding.projectViewpager.apply {
            adapter = forumAdapter
        }
        mBinding.tvArticleEdit.setOnClickListener {
            if(ConstantData.TOKEN != null && ConstantData.TOKEN != "") {
                val intent = Intent(activity, ForumEditActivity::class.java)
                activity!!.startActivity(intent)
            }else {
                val intent = Intent(activity, LoginActivity::class.java)
                activity!!.startActivity(intent)
            }
        }
        mBinding.llSearchArea.setOnClickListener {
            val intent = Intent(activity, SearchActivity::class.java)
            intent.putExtra("keywords", mBinding.tvSearch.text)
            activity!!.startActivity(intent)
        }
    }

    private fun tabBind(pager : ViewPager2) {
        tabLayoutMediator?.detach()
        tabLayoutMediator = TabLayoutMediator(mBinding.projectTab,pager) { tab, position ->
            tab.text = mViewModel.forums[position].name
        }
        tabLayoutMediator?.attach()
    }

    override fun initData() {
        mBinding.projectViewpager.offscreenPageLimit = mViewModel.forums.size
        tabBind(mBinding.projectViewpager)
        forumAdapter.initFragments()
        mBinding.projectTab.getTabAt(1)!!.select()
    }


}