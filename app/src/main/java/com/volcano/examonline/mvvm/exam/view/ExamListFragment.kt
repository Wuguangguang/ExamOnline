package com.volcano.examonline.mvvm.exam.view

import com.volcano.examonline.base.BaseMvvmFragment
import com.volcano.examonline.databinding.ExamListFragmentBinding
import com.volcano.examonline.mvvm.exam.viewmodel.ExamListViewModel

class ExamListFragment(private val examId: Int) : BaseMvvmFragment<ExamListFragmentBinding, ExamListViewModel>() {

    companion object {
        fun newInstance(id: Int) =
            ExamListFragment(id)
    }

    override fun initView() {

    }

    override fun initData() {
    }

}