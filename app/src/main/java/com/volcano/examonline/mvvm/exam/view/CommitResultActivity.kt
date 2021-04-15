package com.volcano.examonline.mvvm.exam.view

import android.content.Intent
import android.widget.AdapterView
import androidx.lifecycle.observe
import androidx.recyclerview.widget.GridLayoutManager
import com.volcano.examonline.R
import com.volcano.examonline.base.BaseMvvmActivity
import com.volcano.examonline.databinding.ActivityCommitResultBinding
import com.volcano.examonline.mvvm.exam.adapter.CommitResultAdapter
import com.volcano.examonline.mvvm.detail.view.QuestionActivity
import com.volcano.examonline.mvvm.exam.viewmodel.CommitResultViewModel
import com.volcano.examonline.mvvm.study.model.Question
import com.volcano.examonline.util.ConstantData
import java.math.BigDecimal

class CommitResultActivity : BaseMvvmActivity<ActivityCommitResultBinding, CommitResultViewModel>() {


    private val commitResultAdapter by lazy {
        CommitResultAdapter(
            this
        )
    }

    override fun initView() {
        initToolbar()
        mBinding.rvQuestionCard.apply {
            layoutManager = GridLayoutManager(context , 5)
            adapter = commitResultAdapter
        }
        commitResultAdapter.setOnClickListener(AdapterView.OnItemClickListener { _, _, position, _ ->
            val intent = Intent(this, QuestionActivity::class.java).apply {
                putExtra("detailType", ConstantData.QUESTION_DETAIL)
                putExtra("question", mViewModel.questions[position])
            }
            startActivity(intent)
        })
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