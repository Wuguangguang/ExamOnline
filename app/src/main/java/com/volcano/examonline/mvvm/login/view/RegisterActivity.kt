package com.volcano.examonline.mvvm.login.view

import android.util.Log
import android.widget.Toast
import androidx.lifecycle.observe
import com.volcano.examonline.R
import com.volcano.examonline.base.BaseMvvmActivity
import com.volcano.examonline.databinding.ActivityRegisterBinding
import com.volcano.examonline.mvvm.login.viewmodel.LoginViewModel
import com.volcano.examonline.util.ToastUtils

class RegisterActivity : BaseMvvmActivity<ActivityRegisterBinding, LoginViewModel>() {

    override fun initView() {
        initToolbar()
        mBinding.btnRegister.setOnClickListener {
            val phone = mBinding.tvLoginPhone.text.toString()
            val username = mBinding.tvLoginUsername.text.toString()
            val pwd = mBinding.tvLoginPassword.text.toString()
            if(phone == null || phone == "" || username == null || username == "" || pwd == null || pwd == "") {
                ToastUtils.show(this, "内容不可为空，请检查输入！",)
            }else {
                mViewModel.register(phone, username, pwd)
            }

        }
    }

    private fun initToolbar() {
        mBinding.toolbarRegister.toolbarLeftImageBack.apply{
            setImageResource(R.drawable.ic_black_back)
            setOnClickListener {
                finish()
            }
        }
        mBinding.toolbarRegister.toolbarTitle.text = "注册账号"
    }

    override fun initData() {
        setDataStatus(mViewModel.registerFlag, {
            ToastUtils.show(this, "该用户已存在！")
        }, {
            ToastUtils.show(this, "注册成功！")
            finish()
        })
    }
}

