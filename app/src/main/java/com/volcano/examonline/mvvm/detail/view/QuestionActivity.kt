package com.volcano.examonline.mvvm.detail.view

import android.view.View
import androidx.lifecycle.observe
import com.volcano.examonline.R
import com.volcano.examonline.base.BaseMvvmActivity
import com.volcano.examonline.databinding.ActivityQuestionBinding
import com.volcano.examonline.mvvm.detail.adapter.QuestionAdapter
import com.volcano.examonline.mvvm.detail.viewmodel.DetailViewModel
import com.volcano.examonline.mvvm.study.model.Question
import com.volcano.examonline.util.ConstantData

class QuestionActivity : BaseMvvmActivity<ActivityQuestionBinding, DetailViewModel>() {

    private var type:Int? = null
    private val questionAdapter by lazy { QuestionAdapter(this, mViewModel.questions)}

    override fun initView() {
        type = intent.getIntExtra("type", ConstantData.SINGLE_QUESTION)
        mViewModel.myAnswers = intent.getSerializableExtra("myAnswers") as ArrayList<String>
        initToolbar()
        mBinding.fabEditComment.setOnClickListener{
            //发表评论
        }
        mBinding.vpDetail.apply {
            adapter = questionAdapter
            isUserInputEnabled = true
        }
    }

    private fun initToolbar() {
        mBinding.toolbarDetail.toolbarLeftImageBack.apply {
            setImageResource(R.drawable.ic_black_back)
            setOnClickListener { finish() }
        }
        mBinding.toolbarDetail.toolbarTitle.text = if(type == ConstantData.SINGLE_QUESTION) "试题详情" else "试题解析"
        if(type == ConstantData.MULTI_QUESTION) {
            mBinding.toolbarDetail.toolbarRightImage.visibility = View.GONE
            mBinding.toolbarDetail.toolbarRightTv.visibility = View.VISIBLE
        }
    }

    override fun initData() {
        mViewModel.mutableQuestions.observe(this) {
            if(!it.isNullOrEmpty()) {
                mViewModel.questions.addAll(it)
                questionAdapter.notifyDataSetChanged()
                questionAdapter.initFragments()
                mBinding.vpDetail.currentItem = 0
                mBinding.vpDetail.offscreenPageLimit = it.size
            }
        }

        val questions = intent.getSerializableExtra("question") as ArrayList<Question>
        mViewModel.mutableQuestions.value = questions
    }
}