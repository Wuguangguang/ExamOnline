package com.volcano.examonline.mvvm.exam.adapter

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.volcano.examonline.mvvm.exam.ExamListFragment
import com.volcano.examonline.mvvm.exam.model.Exam

class ExamTabAdapter(val mContext: Fragment, val tabs : ArrayList<Exam>) : FragmentStateAdapter(mContext) {
    private val fragments = mutableListOf<Fragment>()

    override fun getItemCount(): Int = tabs.size

    override fun createFragment(position: Int): Fragment = fragments[position]

    fun initFragments() {
        fragments.clear()
        tabs.forEach{
            fragments.add(ExamListFragment.newInstance(it.examId!!))
        }

    }
}