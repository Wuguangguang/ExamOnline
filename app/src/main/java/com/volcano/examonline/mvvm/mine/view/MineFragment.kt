package com.volcano.examonline.mvvm.mine.view

import android.content.Intent
import android.view.View
import com.bumptech.glide.Glide
import com.volcano.examonline.R
import com.volcano.examonline.base.BaseMvvmFragment
import com.volcano.examonline.databinding.FragmentMineBinding
import com.volcano.examonline.mvvm.login.view.LoginActivity
import com.volcano.examonline.mvvm.mine.viewmodel.MineViewModel
import com.volcano.examonline.util.ConstantData
import com.volcano.examonline.widget.EditDialog

class MineFragment : BaseMvvmFragment<FragmentMineBinding, MineViewModel>(ConstantData.VIEWMODEL_EXCLUSIVE) {

    companion object {
        fun newInstance() = MineFragment()
    }

    private val dialog by lazy { EditDialog(activity!!) }

    override fun initView() {
        mBinding.rlUserInfo.setOnClickListener {
            if(ConstantData.isLogin()) {
                val intent = Intent(activity, MyInfoActivity::class.java)
                startActivity(intent)
            } else {
                val intent = Intent(context, LoginActivity::class.java)
                startActivity(intent)
            }
        }
        mBinding.llMyArticles.setOnClickListener {
            if(ConstantData.isLogin()) {
                val intent = Intent(context, MyArticlesActivity::class.java)
                startActivity(intent)
            } else {
                val intent = Intent(context, LoginActivity::class.java)
                startActivity(intent)
            }
        }
        mBinding.llMyInfo.setOnClickListener {
            if(ConstantData.isLogin()) {
                val intent = Intent(activity, MyInfoActivity::class.java)
                startActivity(intent)
            } else {
                val intent = Intent(context, LoginActivity::class.java)
                startActivity(intent)
            }
        }
        mBinding.llExitLogin.setOnClickListener {
            dialog.apply {
                show()
                setTitle("退出登录")
                setContent("确定要退出登录吗？")
                setSureListener("确定") {
                    ConstantData.exitLogin()
                    mBinding.tvUserName.text = "点击登录"
                    mBinding.tvUserPhone.text = "请点击进行登录"
                    mBinding.llExitLogin.visibility = View.GONE
                    mBinding.ivUserAvatar.setImageResource(R.drawable.img_nomal_head)
                    dismiss()
                }
                setCancelListener("取消") {
                    cancel()
                    dismiss()
                }
            }
        }
        mBinding.llAboutMe.setOnClickListener {
            val intent = Intent(context, AboutMeActivity::class.java)
            startActivity(intent)
        }
    }

    override fun onResume() {
        super.onResume()
        if(ConstantData.isLogin()) {
            mBinding.llExitLogin.visibility = View.VISIBLE
            mViewModel.getUserInfo(ConstantData.ID!!)
        }
    }

    override fun initData() {
        if(ConstantData.isLogin()) {
            mBinding.llExitLogin.visibility = View.VISIBLE
            mViewModel.getUserInfo(ConstantData.ID!!)
        }
        setDataStatus(mViewModel.liveId, {

        }, {
            if(it != null) {
                mBinding.tvUserName.text = it.username
                mBinding.tvUserPhone.text = it.phone
                if(it.avatar != null) {
                    Glide.with(context).load(it.avatar).into(mBinding.ivUserAvatar)
                }
            }
        })
    }
}