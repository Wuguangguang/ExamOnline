package com.volcano.examonline.mvvm.question.view

import android.content.Context
import android.content.Intent
import android.view.View
import android.widget.Toast
import androidx.viewpager2.widget.ViewPager2
import com.volcano.examonline.R
import com.volcano.examonline.base.BaseMvvmActivity
import com.volcano.examonline.databinding.ActivityQuestionBinding
import com.volcano.examonline.mvvm.login.view.LoginActivity
import com.volcano.examonline.mvvm.question.adapter.QuestionAdapter
import com.volcano.examonline.mvvm.question.viewmodel.QuestionViewModel
import com.volcano.examonline.mvvm.study.model.Question
import com.volcano.examonline.util.ConstantData
import com.volcano.examonline.util.ToastUtils
import com.volcano.examonline.widget.EditDialog

class QuestionActivity : BaseMvvmActivity<ActivityQuestionBinding, QuestionViewModel>() {

    companion object {
        fun actionStart(context: Context, type: Int, question: ArrayList<Question>, myAnswers: ArrayList<String>) {
            val intent = Intent(context, QuestionActivity::class.java).apply {
                putExtra("type", type)
                putExtra("question", question)
                putExtra("myAnswers", myAnswers)
            }
            context.startActivity(intent)
        }

        fun actionStart(context:Context, type: Int, subjectName: String) {
            val intent = Intent(context, QuestionActivity::class.java).apply {
                putExtra("type", type)
                putExtra("subject", subjectName)
            }
            context.startActivity(intent)
        }
    }

    private var type:Int? = null
    private val questionAdapter by lazy { QuestionAdapter(this, mViewModel.questions)}
    private val commentDialog by lazy { EditDialog(this) }

    override fun initView() {
        type = intent.getIntExtra("type", ConstantData.SINGLE_QUESTION)
        initToolbar()
        // 顺序练习
        if(type != ConstantData.ORDERLY_MODE) {
            mViewModel.myAnswers = intent.getSerializableExtra("myAnswers") as ArrayList<String>
            mViewModel.questions = intent.getSerializableExtra("question") as ArrayList<Question>
        }
        mBinding.fabEditComment.setOnClickListener{
            //发表评论
            if(ConstantData.isLogin()) {
                commentDialog.apply {
                    show()
                    setTitle("发表评论")
                    setEtVisibility(View.VISIBLE)
                    setSureListener("发表评论") {
                        if(etContent.isNullOrEmpty()) {
                            ToastUtils.show(context, "评论内容不可为空！")
                        }else {
                            mViewModel.editComment(mViewModel.questions[mBinding.vpDetail.currentItem].id!!, etContent)
                            dismiss()
                        }
                    }
                    setCancelListener("取消") {
                        cancel()
                        dismiss()
                    }
                }
            }else {
                val intent = Intent(this, LoginActivity::class.java)
                startActivity(intent)
            }
        }
        mBinding.vpDetail.apply {
            adapter = questionAdapter
            isUserInputEnabled = true
            registerOnPageChangeCallback(object: ViewPager2.OnPageChangeCallback() {
                override fun onPageSelected(position: Int) {
                    super.onPageSelected(position)
                    mBinding.toolbarDetail.toolbarRightTv.text = "${position+1}/${mViewModel.questions.size}"
                }
            })
        }
    }

    private fun initToolbar() {
        mBinding.toolbarDetail.toolbarLeftImageBack.apply {
            setImageResource(R.drawable.ic_black_back)
            setOnClickListener { finish() }
        }
        mBinding.toolbarDetail.toolbarTitle.text = when(type) {
            ConstantData.SINGLE_QUESTION -> "试题详情"
            ConstantData.MULTI_QUESTION -> "试题解析"
            else -> "顺序练习"
        }
        if(type != ConstantData.SINGLE_QUESTION) {
            mBinding.toolbarDetail.toolbarRightImage.visibility = View.GONE
            mBinding.toolbarDetail.toolbarRightTv.visibility = View.VISIBLE
        }
    }

    override fun initData() {
        setDataStatus(mViewModel.liveEditComment, {
            Toast.makeText(this, "发表失败，请稍后再试！", Toast.LENGTH_SHORT).show()
        }, {
            Toast.makeText(this, "发表评论成功！", Toast.LENGTH_SHORT).show()
            mViewModel.getQuestionComments(mViewModel.questions[mBinding.vpDetail.currentItem].id!!)
        })
        if(type == ConstantData.ORDERLY_MODE) {
            setDataStatus(mViewModel.liveQuestion, {}, {
                if(!it.isNullOrEmpty()) {
                    mViewModel.questions.clear()
                    mViewModel.questions.addAll(it)
                    refreshQuestions()
                }
            })
            mViewModel.getQuestions(intent.getStringExtra("subject")!!)
        }else {
            refreshQuestions()
        }
    }

    private fun refreshQuestions() {
        questionAdapter.initFragments()
        mBinding.vpDetail.offscreenPageLimit = mViewModel.questions.size
        questionAdapter.notifyDataSetChanged()
        mBinding.vpDetail.currentItem = 0
        mBinding.toolbarDetail.toolbarRightTv.text = "0/${mViewModel.questions.size}"
    }
}