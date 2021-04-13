package com.volcano.examonline.mvvm.homepage.view

import androidx.lifecycle.observe
import androidx.recyclerview.widget.LinearLayoutManager
import com.volcano.examonline.R
import com.volcano.examonline.base.BaseMvvmFragment
import com.volcano.examonline.databinding.HomepageDetailFragmentBinding
import com.volcano.examonline.mvvm.homepage.adapter.QuestionListAdapter
import com.volcano.examonline.mvvm.homepage.viewmodel.HomepageViewModel

class HomepageDetailFragment(private val subjectId : Int) : BaseMvvmFragment<HomepageDetailFragmentBinding, HomepageViewModel>() {

    companion object {
        fun newInstance(id : Int) = HomepageDetailFragment(id)
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
