package com.volcano.examonline.mvvm.forum.view

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
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
import kotlin.Comparator

class ArticleActivity : BaseMvvmActivity<ActivityArticleBinding, ArticleViewModel>() {

    private var article: Article? = null
    private val commentsAdapter: CommentsAdapter by lazy{ CommentsAdapter(this, mViewModel.comments) }
    private val orderDialog by lazy { CommonDialog(this) }
    private var zanFlag = false

    override fun onCreate(savedInstanceState: Bundle?) {
        article = intent.getSerializableExtra("article") as Article
        super.onCreate(savedInstanceState)
    }

    override fun initView() {
        initToolbar()
        initListener()
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
        mBinding.rvArticleCommentList.apply {
            isNestedScrollingEnabled = false
            layoutManager = LinearLayoutManager(context)
            adapter = commentsAdapter
        }
        mBinding.tvArticleTitle.text = article!!.title
        mBinding.tvArticleAuthorName.text = article!!.username
        mBinding.tvArticleDate.text = ConstantData.str2Timestamp(article!!.createat!!)
        mBinding.tvArticleContent.text = article!!.description
        mBinding.tvZanNums.text = "${article!!.zannums}"
    }

    private fun initListener() {
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
        mBinding.ivZan.setOnClickListener {
            zanFlag = !zanFlag
            mBinding.ivZan.setImageResource(if(zanFlag) R.drawable.ic_zaned else R.drawable.ic_zan)
            if(zanFlag) {
                mViewModel.increaseZan(article!!.id!!)
                val num = article!!.zannums!! + 1
                mBinding.tvZanNums.text = "$num"
            }else {
                mViewModel.decreaseZan(article!!.id!!)
                mBinding.tvZanNums.text = "${article!!.zannums}"
            }
        }
        mBinding.tvArticleCommentOrder.setOnClickListener {
            orderDialog.apply {
                show()
                setDatas(arrayListOf(ConstantData.ORDER_DEFAULT, ConstantData.ORDER_TIME, ConstantData.ORDER_HOT))
                setOnItemClickListener(object : CommonDialogOnItemClickListener {
                    override fun onCLick(item: String) {
                        when(item) {
                            ConstantData.ORDER_DEFAULT -> {
                                mViewModel.comments.sortWith(Comparator { o1, o2 ->
                                    o1.id!! - o2.id!!
                                })
                                mBinding.tvArticleCommentOrder.text = ConstantData.ORDER_DEFAULT
                                mBinding.ivArticleCommentOrder.setImageResource(R.drawable.ic_grey_search)
                            }
                            ConstantData.ORDER_TIME -> {
                                mViewModel.comments.sortWith(Comparator { o1, o2 ->
                                    o2.createat!!.compareTo(o1.createat!!)
                                })
                                mBinding.tvArticleCommentOrder.text = ConstantData.ORDER_TIME
                                mBinding.ivArticleCommentOrder.setImageResource(R.drawable.ic_timer)
                            }
                            else -> {
                                mViewModel.comments.sortWith(Comparator { o1, o2 ->
                                    o2.zan!! - o1.zan!!
                                })
                                mBinding.tvArticleCommentOrder.text = ConstantData.ORDER_HOT
                                mBinding.ivArticleCommentOrder.setImageResource(R.drawable.ic_zan)
                            }
                        }
                        commentsAdapter.notifyDataSetChanged()
                        orderDialog.dismiss()
                    }
                })
            }
        }
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