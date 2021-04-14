package com.volcano.examonline.mvvm.study.adapter

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.volcano.examonline.mvvm.study.model.Subject
import com.volcano.examonline.mvvm.study.view.StudyDetailFragment

class SubjectAdapter(mContext: Fragment, private val subjects: ArrayList<Subject>) : FragmentStateAdapter(mContext) {

    private val fragments = mutableListOf<Fragment>()

    override fun getItemCount(): Int = subjects.size

    override fun createFragment(position: Int): Fragment = fragments[position]

    fun initFragments() {
        fragments.clear()
        subjects.forEach{
            fragments.add(StudyDetailFragment.newInstance(it.id!!))
        }

    }
}