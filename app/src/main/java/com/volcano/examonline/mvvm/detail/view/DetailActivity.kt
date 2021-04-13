package com.volcano.examonline.mvvm.detail.view

import android.os.Bundle
import com.volcano.examonline.R
import com.volcano.examonline.base.BaseMvvmActivity
import com.volcano.examonline.databinding.ActivityDetailBinding
import com.volcano.examonline.mvvm.detail.viewmodel.DetailViewModel
import com.volcano.examonline.mvvm.forum.model.Article

class DetailActivity : BaseMvvmActivity<ActivityDetailBinding, DetailViewModel>() {

    private var type: Int? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        type = intent.getIntExtra("detailType",0x1)
        super.onCreate(savedInstanceState)
    }

    override fun initView() {
        initToolbar()
        when(type) {
            0x1 -> {
                val article = intent.getSerializableExtra("article") as Article
                supportFragmentManager.beginTransaction()
                    .add(R.id.fl_detail_frag, ArticleDetailFragment.newInstance(article))
                    .commit()
            }
            0x2 -> {
                supportFragmentManager.beginTransaction()
                    .add(R.id.fl_detail_frag, QuestionDetailFragment.newInstance())
                    .commit()
            }
        }
    }

    private fun initToolbar() {
        mBinding.toolbarDetail.toolbarLeftImageBack.apply {
            setImageResource(R.drawable.left_triangle)
            setOnClickListener { finish() }
        }
        when(type) {
            0x1 -> mBinding.toolbarDetail.toolbarTitle.text = "帖子详情"
            0x2 -> mBinding.toolbarDetail.toolbarTitle.text = "试题详情"
        }
    }


}