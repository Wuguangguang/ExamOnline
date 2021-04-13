package com.volcano.examonline.mvvm.forum.adapter

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.volcano.examonline.mvvm.forum.view.ForumDetailFragment
import com.volcano.examonline.mvvm.forum.model.Forum

class ForumAdapter(val mContext: Fragment, val forums : ArrayList<Forum>) : FragmentStateAdapter(mContext) {
    private val fragments = mutableListOf<Fragment>()

    override fun getItemCount(): Int = forums.size

    override fun createFragment(position: Int): Fragment = fragments[position]

    fun initFragments() {
        fragments.clear()
        forums.forEach{
            fragments.add(ForumDetailFragment.newInstance(it.forumId!!))
        }

    }
}