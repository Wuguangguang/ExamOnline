package com.volcano.examonline.mvvm.exam.view

import android.content.Intent
import android.widget.AdapterView
import androidx.lifecycle.observe
import androidx.recyclerview.widget.GridLayoutManager
import com.volcano.examonline.R
import com.volcano.examonline.base.BaseMvvmActivity
import com.volcano.examonline.databinding.ActivityCommitResultBinding
import com.volcano.examonline.mvvm.exam.adapter.CommitResultAdapter
import com.volcano.examonline.mvvm.question.view.QuestionActivity
import com.volcano.examonline.mvvm.exam.viewmodel.CommitResultViewModel
import com.volcano.examonline.mvvm.study.model.Question
import com.volcano.examonline.util.ConstantData
import java.lang.StringBuilder
import java.math.BigDecimal

class CommitResultActivity : BaseMvvmActivity<ActivityCommitResultBinding, CommitResultViewModel>() {


    private val commitResultAdapter by lazy { CommitResultAdapter(this)  }

    override fun initView() {
        initToolbar()
        mBinding.rvQuestionCard.apply {
            layoutManager = GridLayoutManager(context , 5)
            adapter = commitResultAdapter
        }
        // 单一题解
        commitResultAdapter.setOnClickListener(AdapterView.OnItemClickListener { _, _, position, _ ->
            val sb = StringBuilder("")
            mViewModel.myAnswers[position]!!.forEach {
                sb.append(it)
            }
            QuestionActivity.actionStart(
                this,
                ConstantData.SINGLE_QUESTION,
                arrayListOf(mViewModel.questions[position]),
                arrayListOf(sb.toString())
                )
        })
        // 错题解析
        mBinding.tvWrongAnalysis.setOnClickListener {
            val questions = arrayListOf<Question>()
            val myAnswers = arrayListOf<String>()
            for(i in mViewModel.results.value!!.indices) {
                //错题及其答案加入questions
                if(mViewModel.results.value!![i] == 0) {
                    val sb = StringBuilder("")
                    questions.add(mViewModel.questions[i])
                    if(mViewModel.myAnswers[i].isNullOrEmpty()) {
                        myAnswers.add(sb.toString())
                    }else {
                        mViewModel.myAnswers[i]!!.forEach {
                            sb.append(it)
                        }
                        myAnswers.add(sb.toString())
                    }
                }
            }
            QuestionActivity.actionStart(this, ConstantData.MULTI_QUESTION, questions, myAnswers)
        }
        // 全部解析
        mBinding.tvAllAnalysis.setOnClickListener {
            val myAnswers = arrayListOf<String>()
            mViewModel.myAnswers.forEach {
                val sb = StringBuilder("")
                if(it.value.isNullOrEmpty()) {
                    myAnswers.add(sb.toString())
                }else {
                    it.value.forEach {
                        sb.append(it)
                    }
                    myAnswers.add(sb.toString())
                }
            }
            QuestionActivity.actionStart(this, ConstantData.MULTI_QUESTION, mViewModel.questions, myAnswers)
        }
    }

    private fun initToolbar() {
        mBinding.toolbarCommitResult.apply {
            toolbarTitle.text = "结果报告"
            toolbarLeftImageBack.setImageResource(R.drawable.ic_black_back)
            toolbarLeftImageBack.setOnClickListener {
                finish()
            }
        }
    }

    override fun initData() {
        mBinding.tvCommitTimes.text = intent.getSerializableExtra("time") as String
        mViewModel.questions = intent.getSerializableExtra("questions") as ArrayList<Question>
        mViewModel.myAnswers = intent.getSerializableExtra("answers") as HashMap<Int, List<String>>
        mViewModel.results.observe(this) {
            if(!it.isNullOrEmpty()) {
                commitResultAdapter.setResults(it)
                commitResultAdapter.notifyDataSetChanged()
            }
        }
        mViewModel.correctCount.observe(this) {
            mBinding.tvCommitCorrects.text = "$it/${mViewModel.questions.size}"
            val percent = BigDecimal(it).divide(BigDecimal(mViewModel.questions.size), 2,BigDecimal.ROUND_HALF_UP).toDouble() * 100
            mBinding.tvCorrectPercent.text = "$percent%"
        }
        mViewModel.calculateAccuracy()
    }
}