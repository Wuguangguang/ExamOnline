package com.volcano.examonline.mvvm.homepage.view

import android.os.Bundle
import com.volcano.examonline.R
import com.volcano.examonline.base.BaseMvvmActivity
import com.volcano.examonline.databinding.ActivityQuestionBinding
import com.volcano.examonline.mvvm.homepage.viewmodel.QuestionViewModel

class QuestionActivity : BaseMvvmActivity<ActivityQuestionBinding, QuestionViewModel>() {

    private var questionId: Int? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        questionId = intent.getIntExtra("questionId", -1)
        super.onCreate(savedInstanceState)
    }

    override fun initView() {
        initToolbar()
    }

    private fun initToolbar() {
        mBinding.toolbar.apply {
            if(title.length > 14){
                toolbarTitle.text = title.substring(0,14) + "..."
            }else{
                toolbarTitle.text = title
            }
            toolbarLeftImageBack.apply {
                setImageResource(R.drawable.left_triangle)
                setOnClickListener {
                    finish()
                }
            }
            toolbarRightImage.apply {
                setImageResource(R.drawable.three_points)
                setOnClickListener {
                }
            }
        }
    }
}