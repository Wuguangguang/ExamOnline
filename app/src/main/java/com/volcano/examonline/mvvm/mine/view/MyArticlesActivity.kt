package com.volcano.examonline.mvvm.mine.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.volcano.examonline.R
import com.volcano.examonline.base.BaseMvvmActivity
import com.volcano.examonline.databinding.ActivityMyArticlesBinding
import com.volcano.examonline.mvvm.mine.viewmodel.MyArticlesViewModel

class MyArticlesActivity : BaseMvvmActivity<ActivityMyArticlesBinding, MyArticlesViewModel>() {

    override fun initView() {
        initToolbar()
        contentView = mBinding.mslMyArticles
    }

    override fun initData() {
        TODO("Not yet implemented")
    }

    private fun initToolbar() {

    }

    override fun doRetry() {
        super.doRetry()

    }
}