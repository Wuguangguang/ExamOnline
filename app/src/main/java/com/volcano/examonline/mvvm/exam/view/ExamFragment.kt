package com.volcano.examonline.mvvm.exam.view

import androidx.recyclerview.widget.LinearLayoutManager
import com.volcano.examonline.base.BaseMvvmFragment
import com.volcano.examonline.databinding.FragmentExamBinding
import com.volcano.examonline.mvvm.exam.adapter.OnItemClickListener
import com.volcano.examonline.mvvm.exam.adapter.OptionsAdapter
import com.volcano.examonline.mvvm.exam.viewmodel.ExamViewModel
import com.volcano.examonline.mvvm.study.model.Question

class ExamFragment(private val question: Question, private val currentPos: Int)
    : BaseMvvmFragment<FragmentExamBinding, ExamViewModel>() {

    companion object {
        fun newInstance(question: Question, i: Int) = ExamFragment(question, i)
    }

    private val optionsAdapter by lazy { OptionsAdapter(activity!!, options!!, question.type!!) }
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
        mBinding.tvQuestionLevel.text = question.level
        mBinding.tvQuestionType.text = question.type
        mBinding.tvQuestionSource.text = question.source
        mBinding.tvQuestionDesc.text = question.description
    }
}