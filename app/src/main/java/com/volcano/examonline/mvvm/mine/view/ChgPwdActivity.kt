package com.volcano.examonline.mvvm.mine.view

import android.view.View
import android.widget.Toast
import com.volcano.examonline.R
import com.volcano.examonline.base.BaseMvvmActivity
import com.volcano.examonline.databinding.ActivityChgPwdBinding
import com.volcano.examonline.mvvm.mine.viewmodel.MineViewModel
import com.volcano.examonline.util.ToastUtils

class ChgPwdActivity : BaseMvvmActivity<ActivityChgPwdBinding, MineViewModel>() {

    private var account: String? = null

    override fun initView() {
        initToolbar()
        account = intent.getStringExtra("account")
        mBinding.tvMyAccount.text = account
    }

    private fun initToolbar() {
        mBinding.toolbarChgpwd.apply {
            toolbarTitle.text = "设置密码"
            toolbarLeftImageBack.setImageResource(R.drawable.ic_black_back)
            toolbarLeftImageBack.setOnClickListener { finish() }
            toolbarRightImage.visibility = View.GONE
            toolbarRightTv.apply {
                visibility = View.VISIBLE
                toolbarRightTv.text = "完成"
                setOnClickListener {
                    if(mBinding.etMyOriginPwd.text.toString().isNullOrEmpty() || mBinding.etMyNewPwd.text.toString().isNullOrEmpty()
                        || mBinding.etMyNewPwdSecond.text.toString().isNullOrEmpty()
                        || mBinding.etMyNewPwd.text.toString() != mBinding.etMyNewPwdSecond.text.toString()) {
                        ToastUtils.show(context, "输入有误，请检查！")
                    }else {
                        mViewModel.editUserPwd(mBinding.etMyOriginPwd.text.toString(), mBinding.etMyNewPwd.text.toString())
                    }
                }
            }
        }
    }

    override fun initData() {
        setDataStatus(mViewModel.liveUserPwd, {
            ToastUtils.show(this, "原密码错误，修改失败！")
        }, {
            ToastUtils.show(this, "修改密码成功！")
            finish()
        })
    }
}