package com.volcano.examonline.mvvm.mine.view

import android.app.Activity
import android.app.AlertDialog
import android.content.Intent
import android.util.Log
import android.view.View
import androidx.lifecycle.observe
import com.volcano.examonline.base.BaseMvvmFragment
import com.volcano.examonline.databinding.FragmentMineBinding
import com.volcano.examonline.mvvm.login.view.LoginActivity
import com.volcano.examonline.mvvm.mine.viewmodel.MineViewModel
import com.volcano.examonline.util.ConstantData

class MineFragment : BaseMvvmFragment<FragmentMineBinding, MineViewModel>(ConstantData.VIEWMODEL_EXCLUSIVE) {

    companion object {
        fun newInstance() = MineFragment()
    }

    override fun initView() {
        mBinding.llUserInfo.setOnClickListener {
            val intent = Intent(context, LoginActivity::class.java)
            startActivityForResult(intent, 1)
        }
        mBinding.ivUserAvatar.setOnClickListener {
            val intent = Intent(context, LoginActivity::class.java)
            startActivityForResult(intent, 1)
        }
        mBinding.llMyArticles.setOnClickListener {
            if(ConstantData.isLogin()) {
                val intent = Intent(context, MyArticlesActivity::class.java)
                startActivity(intent)
            } else {
                val intent = Intent(context, LoginActivity::class.java)
                startActivityForResult(intent, 1)
            }
        }
        mBinding.llMyInfo.setOnClickListener {
            if(ConstantData.isLogin()) {
                val intent = Intent(context, MyInfoActivity::class.java)
                startActivity(intent)
            } else {
                val intent = Intent(context, LoginActivity::class.java)
                startActivityForResult(intent, 1)
            }
        }
        mBinding.llExitLogin.setOnClickListener {
            AlertDialog.Builder(context).apply {
                setTitle("退出登录")
                setMessage("确定要退出登录吗？")
                setCancelable(false)
                setPositiveButton("确定") { dialog, which ->
                    ConstantData.exitLogin()
                    mBinding.tvUserName.text = "点击登录"
                    mBinding.tvUserPhone.text = "请点击进行登录"
                    mBinding.llExitLogin.visibility = View.GONE
                }
                setNegativeButton("取消") { dialog, which ->
                }
                show()
            }
        }
        mBinding.llAboutMe.setOnClickListener {
            val intent = Intent(context, AboutMeActivity::class.java)
            startActivity(intent)
        }
    }

    override fun initData() {
        setDataStatus(mViewModel.phone, {

        }, {
            if(it != null) {
                mBinding.tvUserName.text = it.username
                mBinding.tvUserPhone.text = it.phone
            }
        })
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when(requestCode) {
            1 -> if(resultCode == Activity.RESULT_OK) {
                mBinding.llExitLogin.visibility = View.VISIBLE
                mViewModel.getUserInfo(ConstantData.PHONE!!)
            }
        }
    }
}