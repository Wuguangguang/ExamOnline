package com.volcano.examonline.mvvm.forum.view

import android.app.AlertDialog
import android.content.Intent
import android.graphics.Color
import android.os.Build
import android.view.View
import android.view.WindowManager
import android.widget.Toast
import com.volcano.examonline.R
import com.volcano.examonline.base.BaseMvvmActivity
import com.volcano.examonline.databinding.ActivityArticleUploadBinding
import com.volcano.examonline.mvvm.exam.view.ExamActivity
import com.volcano.examonline.mvvm.forum.viewmodel.ArticleUploadViewModel

class ArticleUploadActivity : BaseMvvmActivity<ActivityArticleUploadBinding, ArticleUploadViewModel>() {

    private val items = arrayOf("生活日常","面试求职","学习心得")

    override fun initView() {
        initToolbar()
        mBinding.llArticleField.setOnClickListener {
            val builder = AlertDialog.Builder(this)
            builder.setTitle("请选择话题~")
            builder.setItems(items) { _, which ->
               mBinding.tvArticleType.text = items[which]
            }
            builder.show()
        }
        mBinding.btnArticleEdit.setOnClickListener {
            val title = mBinding.etArticleTitle.text.toString()
            val desc = mBinding.etArticleContent.text.toString()
            if(title == null || title == "" || desc == null || desc == "") {
                Toast.makeText(this, "内容不可为空！", Toast.LENGTH_SHORT).show()
            }else {
                mViewModel.uploadArticle(title, desc, mBinding.tvArticleType.text.toString())
            }
        }
    }

    private fun initToolbar() {
        mBinding.toolbarArticleEdit.apply {
            toolbarLeftImageBack.setImageResource(R.drawable.ic_black_back)
            toolbarLeftImageBack.setOnClickListener {
                finish()
            }
            toolbarTitle.text = "发布讨论"
        }

    }

    override fun initData() {
        setDataStatus(mViewModel.uploadArticle, {}, {
            Toast.makeText(this, "发布成功！", Toast.LENGTH_LONG).show()
            finish()
        })
    }

}