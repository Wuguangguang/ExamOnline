package com.volcano.examonline.mvvm.login.view

import android.app.Activity
import android.content.Intent
import android.widget.Toast
import androidx.lifecycle.observe
import com.volcano.examonline.R
import com.volcano.examonline.base.BaseMvvmActivity
import com.volcano.examonline.databinding.ActivityLoginBinding
import com.volcano.examonline.mvvm.login.viewmodel.LoginViewModel

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
                Toast.makeText(this, "内容不可为空，请检查输入！", Toast.LENGTH_SHORT).show()
            }else {
                mViewModel.login(phone, pwd)
            }
        }

    }

    private fun initToolbar() {
        mBinding.toolbarLogin.toolbarLeftImageBack.apply {
            setImageResource(R.drawable.left_triangle)
            setOnClickListener {
                finish()
            }
        }
        mBinding.toolbarLogin.toolbarTitle.text = "登录注册"
    }

    override fun initData() {
        mViewModel.loginFlag.observe(this) {
            if(it != null) {
                Toast.makeText(this, "登录成功！", Toast.LENGTH_SHORT).show()
                val token = it.token!!
                val intent = Intent()
                intent.putExtra("token", token)
                intent.putExtra("phone", mBinding.tvLoginPhone.text.toString())
                setResult(Activity.RESULT_OK, intent)
                finish()
            }else {
                Toast.makeText(this, "密码错误或用户不存在！", Toast.LENGTH_SHORT).show()
            }
        }
    }

}