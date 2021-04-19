package com.volcano.examonline.mvvm.study.view

import android.util.Log
import com.volcano.examonline.R
import com.volcano.examonline.base.BaseMvvmActivity
import com.volcano.examonline.databinding.ActivityRankingBinding
import com.volcano.examonline.mvvm.study.adapter.RankingAdapter
import com.volcano.examonline.mvvm.study.model.Ranking
import com.volcano.examonline.mvvm.study.viewmodel.RankingViewModel

class RankingActivity : BaseMvvmActivity<ActivityRankingBinding, RankingViewModel>() {

    private val rankingAdapter by lazy{ RankingAdapter(this, mViewModel.rankings) }

    override fun initView() {
        initToolbar()
        contentView = mBinding.mslRanking
    }

    override fun initData() {
        setDataStatus(mViewModel.liveRanking) {
            if(!it.isNullOrEmpty()) {
                mViewModel.rankings.addAll(it.subList(3, it.size))
                rankingAdapter.notifyDataSetChanged()
                refreshTopThree(it.subList(0,3))
            }
        }
        refresh()
    }

    private fun refreshTopThree(subList: List<Ranking>) {
        mBinding.ivNo1Avatar
        mBinding.tvNo1Name.text = subList[0].username
        mBinding.tvNo1Accu.text = "刷题${subList[0].accumulate}道"
        mBinding.ivNo2Avatar
        mBinding.tvNo2Name.text = subList[1].username
        mBinding.tvNo3Accu.text = "刷题${subList[1].accumulate}道"
        mBinding.ivNo3Avatar
        mBinding.tvNo3Name.text = subList[2].username
        mBinding.tvNo3Accu.text = "刷题${subList[2].accumulate}道"
    }

    override fun doRetry() {
        super.doRetry()
        refresh()
    }

    private fun refresh() {
        Log.d("TEST", "refresh: executed")
        contentView?.showLoading()
        mViewModel.getRankings()
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