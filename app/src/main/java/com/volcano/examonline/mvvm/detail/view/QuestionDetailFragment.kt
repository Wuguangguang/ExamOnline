package com.volcano.examonline.mvvm.detail.view

import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.volcano.examonline.base.BaseMvvmFragment
import com.volcano.examonline.databinding.FragmentQuestionDetailBinding
import com.volcano.examonline.mvvm.exam.adapter.OnItemClickListener
import com.volcano.examonline.mvvm.exam.adapter.OptionsAdapter
import com.volcano.examonline.mvvm.exam.viewmodel.ExamViewModel
import com.volcano.examonline.mvvm.study.model.Question

class QuestionDetailFragment(private val question: Question, private val currentPos: Int)
    : BaseMvvmFragment<FragmentQuestionDetailBinding, ExamViewModel>() {

    private val optionsAdapter by lazy { OptionsAdapter(activity!!, options!!, question.type!!) }
    private var options = arrayListOf<String>()

    companion object {
        fun newInstance(question: Question, i: Int) = QuestionDetailFragment(question, i)
    }

    override fun initView() {
        mBinding.rvOptions.apply {
            layoutManager = LinearLayoutManager(activity)
            adapter = optionsAdapter
        }
        initOptions()
        initListener()
    }

    private fun initListener() {
        mBinding.tvAnalysis.setOnClickListener {
            mBinding.tvAnalysis.visibility = View.GONE
            mBinding.llQuestionAnalysis.visibility = View.VISIBLE
        }
        optionsAdapter.setOnClickListener(object :
            OnItemClickListener {
            override fun onClick(myAnswer: ArrayList<String>) {
                mViewModel.setMyAnswer(currentPos, myAnswer)
            }
        })
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

    override fun initData() {
        mBinding.tvQuestionLevel.text = question.level
        mBinding.tvQuestionType.text = question.type
        mBinding.tvQuestionSource.text = question.source
        mBinding.tvQuestionDesc.text = question.description
        val analysis = if(question.analysis == null || question.analysis == "") "暂无解析" else question.analysis as String
        mBinding.tvQuestionAnalysis.text = analysis
    }

}