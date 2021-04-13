package com.volcano.examonline.mvvm.homepage.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentPagerAdapter
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.volcano.examonline.mvvm.forum.view.ForumDetailFragment
import com.volcano.examonline.mvvm.homepage.model.Subject
import com.volcano.examonline.mvvm.homepage.view.HomepageDetailFragment
import com.volcano.examonline.mvvm.homepage.view.HomepageFragment

class SubjectAdapter(mContext: Fragment, private val subjects: ArrayList<Subject>) : FragmentStateAdapter(mContext) {

    private val fragments = mutableListOf<Fragment>()

    override fun getItemCount(): Int = subjects.size

    override fun createFragment(position: Int): Fragment = fragments[position]

    fun initFragments() {
        fragments.clear()
        subjects.forEach{
            fragments.add(HomepageDetailFragment.newInstance(it.id!!))
        }

    }
}