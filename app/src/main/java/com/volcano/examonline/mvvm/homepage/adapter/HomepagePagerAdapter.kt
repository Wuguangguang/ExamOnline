package com.volcano.examonline.mvvm.homepage.adapter

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.volcano.examonline.mvvm.homepage.HomepageDetailFragment
import com.volcano.examonline.mvvm.homepage.model.Subject

class HomepagePagerAdapter(private val fragment : Fragment, val subjects : List<Subject>) : FragmentStateAdapter(fragment) {
    private val fragments = mutableListOf<Fragment>()

    override fun getItemCount(): Int = subjects.size

    override fun createFragment(position: Int): Fragment = fragments[position]

    fun initFragments(){
        fragments.clear()
        subjects.forEach{
            fragments.add(HomepageDetailFragment.newInstance(it.subjectId!!))
        }
    }
}