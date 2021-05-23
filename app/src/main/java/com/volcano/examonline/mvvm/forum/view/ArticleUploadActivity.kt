package com.volcano.examonline.mvvm.forum.view

import android.widget.Toast
import com.volcano.examonline.R
import com.volcano.examonline.base.BaseMvvmActivity
import com.volcano.examonline.databinding.ActivityArticleUploadBinding
import com.volcano.examonline.mvvm.forum.viewmodel.ArticleUploadViewModel
import com.volcano.examonline.widget.CommonDialog
import com.volcano.examonline.widget.CommonDialogOnItemClickListener

class ArticleUploadActivity : BaseMvvmActivity<ActivityArticleUploadBinding, ArticleUploadViewModel>() {

    private val items = arrayListOf("生活日常","面试求职","学习心得")
    private val fieldDialog by lazy { CommonDialog(this) }

    override fun initView() {
        initToolbar()
        mBinding.llArticleField.setOnClickListener {
            fieldDialog.apply {
                show()
                setDatas(items)
                setOnItemClickListener(object : CommonDialogOnItemClickListener {
                    override fun onCLick(item: String) {
                        mBinding.tvArticleType.text = item
                        dismiss()
                    }
                })
            }
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