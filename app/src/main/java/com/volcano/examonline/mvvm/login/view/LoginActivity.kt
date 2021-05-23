package com.volcano.examonline.mvvm.login.view

import android.content.Intent
import android.widget.Toast
import com.volcano.examonline.MainActivity
import com.volcano.examonline.R
import com.volcano.examonline.base.BaseMvvmActivity
import com.volcano.examonline.databinding.ActivityLoginBinding
import com.volcano.examonline.mvvm.login.viewmodel.LoginViewModel
import com.volcano.examonline.util.ActivityCollector
import com.volcano.examonline.util.ConstantData
import com.volcano.examonline.util.ToastUtils

class LoginActivity : BaseMvvmActivity<ActivityLoginBinding, LoginViewModel>() {


    override fun initView() {
        initToolbar()
        mBinding.tvRegister.setOnClickListener {
            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
        }
        mBinding.btnLogin.setOnClickListener {
            val phone = mBinding.tvLoginPhone.text.toString()
            val pwd = mBinding.tvLoginPassword.text.toString()
            if(phone == null || phone == "" || pwd == null || pwd == "") {
                ToastUtils.show(this, "内容不可为空，请检查输入！")
            }else {
                mViewModel.login(phone, pwd)
            }
        }

    }

    private fun initToolbar() {
        mBinding.toolbarLogin.toolbarLeftImageBack.apply {
            setImageResource(R.drawable.ic_black_back)
            setOnClickListener {
                finish()
            }
        }
        mBinding.toolbarLogin.toolbarTitle.text = "登录注册"
    }

    override fun initData() {
        setDataStatus(mViewModel.loginFlag, {
            Toast.makeText(this, "密码错误或用户不存在！", Toast.LENGTH_SHORT).show()
        }, {
            if(it != null) {
                Toast.makeText(this, "登录成功！", Toast.LENGTH_SHORT).show()
                ConstantData.TOKEN = it.token!!
                ConstantData.ID = it.id!!.toInt()
                ActivityCollector.finishAll()
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
            }
        })
    }

}