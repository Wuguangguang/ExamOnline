package com.volcano.examonline

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.volcano.examonline.base.BaseMvvmActivity
import com.volcano.examonline.databinding.ActivityMainBinding
import com.volcano.examonline.mvvm.exam.ExamFragment
import com.volcano.examonline.mvvm.forum.ForumFragment
import com.volcano.examonline.mvvm.homepage.view.HomepageFragment
import com.volcano.examonline.mvvm.mine.MineFragment

class MainActivity : BaseMvvmActivity<ActivityMainBinding, MainViewModel>() {

    private val fragments = arrayOf(
        HomepageFragment.newInstance(),
        ForumFragment.newInstance(),
        ExamFragment.newInstance(),
        MineFragment.newInstance()
    )

    private val itemIds = arrayOf(
        R.id.navigation_first,
        R.id.navigation_second,
        R.id.navigation_third,
        R.id.navigation_fourth
    )


    override fun initView() {
        mBinding.mainViewPager.apply {
            adapter = object : FragmentStateAdapter(supportFragmentManager,lifecycle) {
                override fun getItemCount(): Int = fragments.size

                override fun createFragment(position: Int): Fragment = fragments[position]
            }
            offscreenPageLimit = fragments.size
            isUserInputEnabled = false
        }
        mBinding.navView.setOnNavigationItemSelectedListener {
            mBinding.mainViewPager.setCurrentItem(itemIds.indexOf(it.itemId),false)
            true
        }
    }

}