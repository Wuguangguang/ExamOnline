package com.volcano.examonline.mvvm.detail.view


import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.volcano.examonline.base.BaseMvvmFragment
import com.volcano.examonline.databinding.FragmentQuestionDetailBinding
import com.volcano.examonline.mvvm.detail.adapter.OptionsAdapter
import com.volcano.examonline.mvvm.detail.viewmodel.DetailViewModel
import com.volcano.examonline.mvvm.study.model.Question

class QuestionDetailFragment(private val question: Question) : BaseMvvmFragment<FragmentQuestionDetailBinding, DetailViewModel>() {


    companion object {
        fun newInstance(question: Question) = QuestionDetailFragment(question)
    }

    private var options = arrayListOf<String>()
    private lateinit var type: String
    private val optionsAdapter by lazy { OptionsAdapter(activity!!, options, type!!) }

    override fun initView() {
        mBinding.tvQuestionLevel.text = question.level
        mBinding.tvQuestionType.text = question.type
        mBinding.tvQuestionSource.text = question.source
        mBinding.tvQuestionDesc.text = question.description
        type = question.type!!
        options.apply {
            add(question.optiona!!)
            add(question.optionb!!)
            add(question.optionc!!)
            add(question.optiond!!)
            add(question.optione!!)
        }
        mBinding.rvQuestionOptions.apply {
            layoutManager = LinearLayoutManager(activity)
            adapter = optionsAdapter
        }
        mBinding.btnAnalysis.setOnClickListener {
            val analysis = if(question.analysis == null || question.analysis == "") "暂无解析" else question.analysis as String
            mBinding.tvQuestionAnalysis.text = analysis
            mBinding.btnAnalysis.visibility = View.GONE
            mBinding.llQuestionAnalysis.visibility = View.VISIBLE
            mBinding.tvCommentList.visibility = View.VISIBLE
            mBinding.rvCommentList.visibility = View.VISIBLE
        }
    }

    override fun initData() {
    }

}