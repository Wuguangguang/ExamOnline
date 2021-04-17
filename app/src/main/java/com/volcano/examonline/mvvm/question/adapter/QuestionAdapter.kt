package com.volcano.examonline.mvvm.question.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.volcano.examonline.mvvm.question.view.QuestionDetailFragment
import com.volcano.examonline.mvvm.study.model.Question

class QuestionAdapter(private val mContext: FragmentActivity, private val questions: ArrayList<Question>) : FragmentStateAdapter(mContext) {

    private val fragments = mutableListOf<Fragment>()

    override fun getItemCount(): Int = questions.size

    override fun createFragment(position: Int): Fragment = fragments[position]

    fun initFragments() {
        fragments.clear()
        for(i in questions.indices) {
            fragments.add(QuestionDetailFragment.newInstance(questions[i], i))
        }

    }
}