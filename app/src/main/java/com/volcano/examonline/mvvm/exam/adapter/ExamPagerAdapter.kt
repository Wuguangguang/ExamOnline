package com.volcano.examonline.mvvm.exam.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.volcano.examonline.mvvm.exam.ExamDetailFragment
import com.volcano.examonline.mvvm.exam.model.Question
import com.volcano.examonline.mvvm.homepage.view.HomepageDetailFragment

class ExamPagerAdapter(private val mContext : FragmentActivity, val questions : List<Question>) : FragmentStateAdapter(mContext) {
    private val fragments = mutableListOf<Fragment>()

    override fun getItemCount(): Int = questions.size

    override fun createFragment(position: Int): Fragment = fragments[position]

    fun initFragments(){
        fragments.clear()
        questions.forEach{
            fragments.add(ExamDetailFragment.newInstance(it!!))
        }
    }
}