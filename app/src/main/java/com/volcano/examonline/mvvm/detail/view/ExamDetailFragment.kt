package com.volcano.examonline.mvvm.detail.view

import com.volcano.examonline.base.BaseMvvmFragment
import com.volcano.examonline.databinding.FragmentExamDetailBinding
import com.volcano.examonline.mvvm.detail.viewmodel.ExamDetailViewModel
import com.volcano.examonline.mvvm.study.model.Question

class ExamDetailFragment(private val question: Question) : BaseMvvmFragment<FragmentExamDetailBinding, ExamDetailViewModel>() {


    companion object {
        fun newInstance(question: Question) =
            ExamDetailFragment(question)
    }


    override fun initView() {
        mBinding.tvQuestionType.text = question.type
        mBinding.tvQuestionSource.text = question.source
        mBinding.tvQuestionLevel.text = question.level
        mBinding.tvQuestionDesc.text = question.description
        mBinding.tvQuestionAnalysis.text = question.description
    }

    override fun initData() {

    }

}