package com.volcano.examonline.mvvm.forum

import android.graphics.Color
import android.os.Build
import android.view.View
import android.view.WindowManager
import android.widget.Toast
import androidx.lifecycle.observe
import com.volcano.examonline.R
import com.volcano.examonline.base.BaseMvvmActivity
import com.volcano.examonline.databinding.ActivityForumEditBinding
import com.volcano.examonline.mvvm.forum.viewmodel.ForumEditViewModel

class ForumEditActivity : BaseMvvmActivity<ActivityForumEditBinding, ForumEditViewModel>() {

    override fun initView() {
        setStatusBarStyle()
        initToolbar()
        mBinding.btnArticleEdit.setOnClickListener {
            val title = mBinding.etArticleTitle.text.toString()
            val desc = mBinding.etArticleContent.text.toString()
            if(title == null || title == "" || desc == null || desc == "") {
                Toast.makeText(this, "内容不可为空！", Toast.LENGTH_SHORT).show()
            }else {
                mViewModel.uploadArticle(title, desc)
            }
        }
    }

    private fun setStatusBarStyle() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
            window.statusBarColor = Color.TRANSPARENT
        }
        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
    }

    private fun initToolbar() {
        mBinding.toolbarArticleEdit.apply {
            toolbarLeftImageBack.setImageResource(R.drawable.grey_left_triangle)
            toolbarLeftImageBack.setOnClickListener {
                finish()
            }
            toolbarTitle.text = "发布讨论"
        }

    }

    override fun initData() {
        mViewModel.uploadArticle.observe(this) {
            when(it) {
                1 -> {
                    Toast.makeText(this, "发布成功！", Toast.LENGTH_LONG).show()
                    finish()
                }
                40001 -> {
                    Toast.makeText(this, "系统繁忙，请稍后重试！", Toast.LENGTH_LONG).show()
                }
            }
        }
    }

}