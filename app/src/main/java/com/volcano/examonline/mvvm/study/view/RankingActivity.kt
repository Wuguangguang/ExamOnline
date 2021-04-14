package com.volcano.examonline.mvvm.study.view

import com.volcano.examonline.R
import com.volcano.examonline.base.BaseMvvmActivity
import com.volcano.examonline.databinding.ActivityRankingBinding
import com.volcano.examonline.mvvm.study.viewmodel.RankingViewModel

class RankingActivity : BaseMvvmActivity<ActivityRankingBinding, RankingViewModel>() {

    override fun initView() {
        initToolbar()
    }

    private fun initToolbar() {
        mBinding.toolbarRanking.apply {
            toolbarTitle.text = "数据排名"
            toolbarLeftImageBack.setImageResource(R.drawable.ic_black_back)
            toolbarLeftImageBack.setOnClickListener {
                finish()
            }
        }
    }

}