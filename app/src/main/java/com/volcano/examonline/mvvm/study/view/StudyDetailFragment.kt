package com.volcano.examonline.mvvm.study.view

import androidx.lifecycle.observe
import androidx.recyclerview.widget.LinearLayoutManager
import com.volcano.examonline.R
import com.volcano.examonline.base.BaseMvvmFragment
import com.volcano.examonline.databinding.FragmentStudyDetailBinding
import com.volcano.examonline.mvvm.study.adapter.QuestionListAdapter
import com.volcano.examonline.mvvm.study.viewmodel.StudyViewModel

class StudyDetailFragment(private val subjectId : Int) : BaseMvvmFragment<FragmentStudyDetailBinding, StudyViewModel>() {

    companion object {
        fun newInstance(id : Int) = StudyDetailFragment(id)
    }

    private val questionAdapter by lazy { QuestionListAdapter(activity!! , mViewModel.questionData) }

    override fun initView() {
        mBinding.srlQuestionDetail.apply{
            setColorSchemeResources(R.color.colorAccent)
        }
        mBinding.rcvQuestionDetail.apply {
            layoutManager = LinearLayoutManager(activity!!)
            adapter = questionAdapter
        }
    }

    override fun initData() {
        mViewModel.questions.observe(activity!!) {
            if(!it.isNullOrEmpty()) {
                mViewModel.questionData.addAll(it)
                questionAdapter.notifyDataSetChanged()
            }
        }
        mViewModel.getQuestions(subjectId)
    }
}
