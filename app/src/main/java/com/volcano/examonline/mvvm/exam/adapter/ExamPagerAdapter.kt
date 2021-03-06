package com.volcano.examonline.mvvm.exam.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.volcano.examonline.mvvm.question.view.QuestionDetailFragment
import com.volcano.examonline.mvvm.exam.view.ExamFragment
import com.volcano.examonline.mvvm.study.model.Question
import com.volcano.examonline.util.ConstantData

class ExamPagerAdapter(private val mContext: FragmentActivity, private val questions: List<Question>) : FragmentStateAdapter(mContext) {
    private val fragments = mutableListOf<Fragment>()

    override fun getItemCount(): Int = questions.size

    override fun createFragment(position: Int): Fragment = fragments[position]

    fun initFragments(){
        fragments.clear()
        var i = 0
        questions.forEach{
            fragments.add(ExamFragment(it!!, i++))
        }
    }
}