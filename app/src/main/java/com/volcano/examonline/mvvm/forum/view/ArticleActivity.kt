package com.volcano.examonline.mvvm.forum.view

import android.app.AlertDialog
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.view.MotionEvent
import android.view.View
import android.widget.EditText
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.observe
import androidx.recyclerview.widget.LinearLayoutManager
import com.volcano.examonline.R
import com.volcano.examonline.base.BaseMvvmActivity
import com.volcano.examonline.base.hideSoftInput
import com.volcano.examonline.databinding.ActivityArticleBinding
import com.volcano.examonline.mvvm.forum.adapter.CommentsAdapter
import com.volcano.examonline.mvvm.forum.viewmodel.ArticleViewModel
import com.volcano.examonline.mvvm.forum.model.Article
import com.volcano.examonline.mvvm.login.view.LoginActivity
import com.volcano.examonline.util.ConstantData
import com.volcano.examonline.widget.CommonDialog
import com.volcano.examonline.widget.CommonDialogOnItemClickListener

class ArticleActivity : BaseMvvmActivity<ActivityArticleBinding, ArticleViewModel>() {

    private var article: Article? = null
    private val commentsAdapter: CommentsAdapter by lazy{ CommentsAdapter(this, mViewModel.comments) }
    private val orderDialog by lazy { CommonDialog(this) }

    override fun onCreate(savedInstanceState: Bundle?) {
        article = intent.getSerializableExtra("article") as Article
        super.onCreate(savedInstanceState)
    }

    override fun initView() {
        initToolbar()
        mBinding.etComment.apply {
            addTextChangedListener {
                if(!it.isNullOrEmpty()) {
                    mBinding.rlZanAndComments.visibility = View.GONE
                    mBinding.tvCommentEdit.visibility = View.VISIBLE
                }else {
                    mBinding.rlZanAndComments.visibility = View.VISIBLE
                    mBinding.tvCommentEdit.visibility = View.GONE
                }
            }
        }
        mBinding.tvCommentEdit.setOnClickListener {
            if(ConstantData.isLogin()) {
                mViewModel.editComment(article!!.id!!, mBinding.etComment.text.toString())
                hideSoftInput(mBinding.root, this)
                mBinding.etComment.clearFocus()
                mBinding.etComment.setText("")
                mBinding.rlZanAndComments.visibility = View.VISIBLE
                mBinding.tvCommentEdit.visibility = View.GONE
            }else {
                val intent = Intent(this, LoginActivity::class.java)
                startActivity(intent)
            }
        }
        mBinding.tvArticleCommentOrder.setOnClickListener {
            orderDialog.apply {
                show()
                setDatas(arrayListOf("默认排序","时间排序","热度排序"))
                setOnItemClickListener(object : CommonDialogOnItemClickListener {
                    override fun onCLick(item: String) {
                        when(item) {
                            "默认排序" -> {

                            }
                            "时间排序" -> {
                            }
                            else -> {

                            }
                        }
                        orderDialog.dismiss()
                    }
                })
            }
        }
        mBinding.rvArticleCommentList.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = commentsAdapter
        }
        mBinding.tvArticleTitle.text = article!!.title
        mBinding.tvArticleAuthorName.text = article!!.username
        mBinding.tvArticleDate.text = ConstantData.str2Timestamp(article!!.createat!!)
        mBinding.tvArticleContent.text = article!!.description
        mBinding.tvZanNums.text = "${article!!.zannums}"
    }

    private fun initToolbar() {
        mBinding.toolbarArticle.toolbarLeftImageBack.apply {
            setImageResource(R.drawable.ic_black_back)
            setOnClickListener { finish() }
        }
        mBinding.toolbarArticle.toolbarTitle.text = article!!.field
    }

    override fun initData() {
        setDataStatus(mViewModel.liveComments, {

        }, {
            if(!it.isNullOrEmpty()) {
                mViewModel.comments.clear()
                mViewModel.comments.addAll(it)
                mBinding.tvCommentNums.text = "${it.size}"
                mBinding.tvArticleCommentNum.text = "${it.size}"
                commentsAdapter.notifyDataSetChanged()
            }
        })
        setDataStatus(mViewModel.liveEditComment, {
            Toast.makeText(this, "发表失败，请稍后再试！", Toast.LENGTH_SHORT).show()
        }, {
            Toast.makeText(this, "发表评论成功！", Toast.LENGTH_SHORT).show()
            mViewModel.getArticleComments(article!!.id!!)
        })
        mViewModel.getArticleComments(article!!.id!!)
    }
}