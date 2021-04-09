package com.volcano.examonline.mvvm.exam

import com.volcano.examonline.base.BaseMvvmFragment
import com.volcano.examonline.databinding.ExamDetailFragmentBinding
import com.volcano.examonline.mvvm.exam.model.Question
import com.volcano.examonline.mvvm.exam.viewmodel.ExamDetailViewModel

class ExamDetailFragment(private val question: Question) : BaseMvvmFragment<ExamDetailFragmentBinding, ExamDetailViewModel>() {


    companion object {
        fun newInstance(question: Question) = ExamDetailFragment(question)
    }


    override fun initView() {
        mBinding.tvQuestionType.text = question.type
        mBinding.tvQuestionSource.text = question.source
        mBinding.tvQuestionLevel.text = question.level
        mBinding.tvQuestionDesc.text = question.desc
        mBinding.tvQuestionAnalysis.text = question.analysis
    }

    override fun initData() {

    }

}