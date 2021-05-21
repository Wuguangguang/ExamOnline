package com.volcano.examonline.mvvm.mine.view

import android.view.View
import android.widget.Toast
import com.volcano.examonline.R
import com.volcano.examonline.base.BaseMvvmActivity
import com.volcano.examonline.databinding.ActivityChgPwdBinding
import com.volcano.examonline.mvvm.mine.viewmodel.MineViewModel

class ChgPwdActivity : BaseMvvmActivity<ActivityChgPwdBinding, MineViewModel>() {

    override fun initView() {
        initToolbar()
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
                        Toast.makeText(context, "输入有误，请检查！", Toast.LENGTH_SHORT).show()
                    }else {
                        mViewModel.editUserPwd(mBinding.etMyOriginPwd.text.toString(), mBinding.etMyNewPwd.text.toString())
                    }
                }
            }
        }
    }

    override fun initData() {
        setDataStatus(mViewModel.liveUserPwd, {
            Toast.makeText(this, "原密码错误，修改失败！", Toast.LENGTH_SHORT).show()
        }, {
            Toast.makeText(this, "修改密码成功！", Toast.LENGTH_SHORT).show()
            finish()
        })
    }
}