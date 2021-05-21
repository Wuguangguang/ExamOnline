package com.volcano.examonline.mvvm.mine.view

import android.view.View
import android.widget.Toast
import com.volcano.examonline.R
import com.volcano.examonline.base.BaseMvvmActivity
import com.volcano.examonline.databinding.ActivityAboutMeBinding
import com.volcano.examonline.mvvm.mine.viewmodel.MineViewModel

class AboutMeActivity : BaseMvvmActivity<ActivityAboutMeBinding, MineViewModel>() {
    override fun initView() {

    }

    override fun initData() {
        mBinding.toolbarAboutMe.apply {
            toolbarTitle.text = "关于我"
            toolbarLeftImageBack.setImageResource(R.drawable.ic_black_back)
            toolbarLeftImageBack.setOnClickListener { finish() }
        }
    }
}