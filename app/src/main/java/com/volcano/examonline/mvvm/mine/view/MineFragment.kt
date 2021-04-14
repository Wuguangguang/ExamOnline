package com.volcano.examonline.mvvm.mine.view

import android.app.Activity
import android.content.Intent
import android.util.Log
import androidx.lifecycle.observe
import androidx.recyclerview.widget.LinearLayoutManager
import com.volcano.examonline.base.BaseMvvmFragment
import com.volcano.examonline.databinding.FragmentMineBinding
import com.volcano.examonline.mvvm.login.view.LoginActivity
import com.volcano.examonline.mvvm.mine.adapter.FooterAdapter
import com.volcano.examonline.mvvm.mine.viewmodel.MineViewModel
import com.volcano.examonline.util.ConstantData

class MineFragment : BaseMvvmFragment<FragmentMineBinding, MineViewModel>() {

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
            startActivityForResult(intent, 1)
        }
        mBinding.ivUserAvatar.setOnClickListener {
            val intent = Intent(context, LoginActivity::class.java)
            startActivityForResult(intent, 1)
        }
    }

    override fun initData() {
        mViewModel.phone.observe(activity!!) {
            if(it != null) {
                mBinding.tvUserName.text = it.username
                mBinding.tvUserPhone.text = it.phone
            }
        }

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when(requestCode) {
            1 -> if(resultCode == Activity.RESULT_OK) {
                val token = data?.getStringExtra("token")
                val phone = data?.getStringExtra("phone")
                Log.d("Test", "onActivityResult: $token , $phone")
                ConstantData.TOKEN = token
                ConstantData.PHONE = phone
                if(ConstantData.PHONE != null && ConstantData.TOKEN != null) {
                    mViewModel.getUserInfo(phone!!)
                }
            }
        }
    }


}