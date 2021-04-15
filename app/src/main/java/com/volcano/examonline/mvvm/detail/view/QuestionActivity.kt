package com.volcano.examonline.mvvm.detail.view

import android.os.Bundle
import com.volcano.examonline.R
import com.volcano.examonline.base.BaseMvvmActivity
import com.volcano.examonline.databinding.ActivityDetailBinding
import com.volcano.examonline.mvvm.detail.viewmodel.DetailViewModel
import com.volcano.examonline.mvvm.study.model.Question
import com.volcano.examonline.util.ConstantData

class QuestionActivity : BaseMvvmActivity<ActivityDetailBinding, DetailViewModel>() {

    override fun initView() {
        initToolbar()
        val question = intent.getSerializableExtra("question") as Question
        supportFragmentManager.beginTransaction()
            .add(R.id.fl_detail_frag, QuestionDetailFragment.newInstance(question, -1))
            .commit()
        mBinding.fabEditComment.setOnClickListener{
            //发表评论
        }
    }

    private fun initToolbar() {
        mBinding.toolbarDetail.toolbarLeftImageBack.apply {
            setImageResource(R.drawable.ic_black_back)
            setOnClickListener { finish() }
        }
        mBinding.toolbarDetail.toolbarTitle.text = "试题详情"
    }
}