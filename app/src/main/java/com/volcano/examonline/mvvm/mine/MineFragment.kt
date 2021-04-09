package com.volcano.examonline.mvvm.mine

import android.content.Intent
import androidx.recyclerview.widget.LinearLayoutManager
import com.volcano.examonline.base.BaseMvvmFragment
import com.volcano.examonline.databinding.MineFragmentBinding
import com.volcano.examonline.mvvm.login.LoginActivity
import com.volcano.examonline.mvvm.mine.adapter.FooterAdapter
import com.volcano.examonline.mvvm.mine.viewmodel.MineViewModel

class MineFragment : BaseMvvmFragment<MineFragmentBinding, MineViewModel>() {

    companion object {
        fun newInstance() = MineFragment()
    }

    private val footerAdapter : FooterAdapter by lazy { FooterAdapter(activity!!,mViewModel.footers) }


    override fun initView() {
        mBinding.mineFooterRcv.apply {
            layoutManager = LinearLayoutManager(activity!!)
            adapter = footerAdapter
        }
        mBinding.llUserInfo.setOnClickListener {
            val intent = Intent(context, LoginActivity::class.java)
            startActivity(intent)
        }

    }

    override fun initData() {

    }


}