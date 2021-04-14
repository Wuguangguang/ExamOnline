package com.volcano.examonline.mvvm.forum.view

import android.content.Intent
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.volcano.examonline.R
import com.volcano.examonline.base.BaseMvvmFragment
import com.volcano.examonline.databinding.FragmentForumBinding
import com.volcano.examonline.mvvm.forum.adapter.ForumAdapter
import com.volcano.examonline.mvvm.forum.viewmodel.ForumViewModel
import com.volcano.examonline.mvvm.login.view.LoginActivity
import com.volcano.examonline.mvvm.search.view.SearchActivity
import com.volcano.examonline.util.ConstantData

class ForumFragment : BaseMvvmFragment<FragmentForumBinding, ForumViewModel>() {

    companion object {
        fun newInstance() = ForumFragment()
    }

    private var tabLayoutMediator : TabLayoutMediator? = null
    private val forumAdapter : ForumAdapter by lazy { ForumAdapter(this, mViewModel.forums) }

    override fun initView() {
        initToolbar()
        mBinding.fabArticleUpload.setOnClickListener {
            val intent = Intent(activity, ArticleUploadActivity::class.java)
            startActivity(intent)
        }
        mBinding.tlForum.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener{
            override fun onTabSelected(tab: TabLayout.Tab?) {
                mBinding.vpForumDetail.currentItem = tab!!.position
            }
            override fun onTabUnselected(tab: TabLayout.Tab?) {}
            override fun onTabReselected(tab: TabLayout.Tab?) {}
        })
        mBinding.vpForumDetail.apply {
            adapter = forumAdapter
        }
    }

    private fun initToolbar() {
        mBinding.toolbarForum.apply {
            toolbarTitle.text = "论坛"
            toolbarRightImage.setImageResource(R.drawable.ic_grey_search)
            toolbarRightImage.setOnClickListener {
                val intent = Intent(activity, SearchActivity::class.java)
                startActivity(intent)
            }
        }
    }


    private fun tabBind(pager : ViewPager2) {
        tabLayoutMediator?.detach()
        tabLayoutMediator = TabLayoutMediator(mBinding.tlForum,pager) { tab, position ->
            tab.text = mViewModel.forums[position].name
        }
        tabLayoutMediator?.attach()
    }

    override fun initData() {
        mBinding.vpForumDetail.offscreenPageLimit = mViewModel.forums.size
        tabBind(mBinding.vpForumDetail)
        forumAdapter.initFragments()
    }

}