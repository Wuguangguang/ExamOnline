package com.volcano.examonline.mvvm.study.view

import com.volcano.examonline.R
import com.volcano.examonline.base.BaseMvvmActivity
import com.volcano.examonline.databinding.ActivityQuestionUploadBinding
import com.volcano.examonline.mvvm.study.viewmodel.StudyViewModel

class QuestionUploadActivity : BaseMvvmActivity<ActivityQuestionUploadBinding, StudyViewModel>() {

    override fun initView() {
        initToolbar()
    }

    private fun initToolbar() {
        mBinding.toolbarQuestionUpload.apply {
            toolbarTitle.text = "上传试题"
            toolbarLeftImageBack.setImageResource(R.drawable.ic_black_back)
            toolbarLeftImageBack.setOnClickListener { finish() }
        }
    }

    override fun initData() {
    }
}