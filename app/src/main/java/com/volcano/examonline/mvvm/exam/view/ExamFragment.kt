package com.volcano.examonline.mvvm.exam.view

import androidx.recyclerview.widget.LinearLayoutManager
import com.volcano.examonline.R
import com.volcano.examonline.base.BaseMvvmFragment
import com.volcano.examonline.databinding.FragmentExamBinding
import com.volcano.examonline.mvvm.exam.adapter.OnItemClickListener
import com.volcano.examonline.mvvm.exam.adapter.OptionsAdapter
import com.volcano.examonline.mvvm.exam.viewmodel.ExamViewModel
import com.volcano.examonline.mvvm.study.model.Question
import com.volcano.examonline.util.ConstantData

class ExamFragment(private val question: Question, private val currentPos: Int)
    : BaseMvvmFragment<FragmentExamBinding, ExamViewModel>(ConstantData.VIEWMODEL_SHARED) {

    companion object {
        fun newInstance(question: Question, i: Int) = ExamFragment(question, i)
    }

    private val optionsAdapter by lazy { OptionsAdapter(activity!!, options!!, question.type!!, ConstantData.MODE_NO_RESPONSE, null, null) }
    private var options = arrayListOf<String>()

    override fun initView() {
        mBinding.rvOptions.apply {
            layoutManager = LinearLayoutManager(activity)
            adapter = optionsAdapter
        }
        initOptions()
        initListener()
    }

    private fun initOptions() {
        options.apply {
            add(question.optiona!!)
            add(question.optionb!!)
            if(question.optionc != null) add(question.optionc!!)
            if(question.optiond != null) add(question.optiond!!)
            if(question.optione != null) add(question.optione!!)
        }
    }

    private fun initListener() {
        optionsAdapter.setOnClickListener(object : OnItemClickListener {
            override fun onClick(myAnswer: ArrayList<String>) {
                mViewModel.setMyAnswer(currentPos, myAnswer)
            }
        })
    }

    override fun initData() {
        when(question.level) {
            "困难" -> mBinding.tvQuestionLevel.setTextColor(resources.getColor(R.color.COLOR_FFBE5043))
            "中等" -> mBinding.tvQuestionLevel.setTextColor(resources.getColor(R.color.COLOR_FFE4A264))
            else -> mBinding.tvQuestionLevel.setTextColor(resources.getColor(R.color.COLOR_FF57B769))
        }
        mBinding.tvQuestionLevel.text = question.level
        mBinding.tvQuestionType.text = question.type
        mBinding.tvQuestionSource.text = question.source
        mBinding.tvQuestionDesc.text = question.description
    }
}