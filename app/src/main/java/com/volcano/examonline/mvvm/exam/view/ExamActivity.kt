package com.volcano.examonline.mvvm.exam.view

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.lifecycle.observe
import androidx.viewpager2.widget.ViewPager2
import com.volcano.examonline.R
import com.volcano.examonline.base.BaseMvvmActivity
import com.volcano.examonline.databinding.ActivityExamBinding
import com.volcano.examonline.mvvm.exam.adapter.ExamPagerAdapter
import com.volcano.examonline.mvvm.exam.viewmodel.ExamViewModel
import com.volcano.examonline.mvvm.login.view.LoginActivity
import com.volcano.examonline.util.ConstantData
import com.volcano.examonline.util.ToastUtils
import com.volcano.examonline.widget.EditDialog
import java.text.SimpleDateFormat
import java.util.*


class ExamActivity : BaseMvvmActivity<ActivityExamBinding, ExamViewModel>() {

    private var currentPos = 0
    private val examAdapter: ExamPagerAdapter by lazy { ExamPagerAdapter(this, mViewModel.questions) }
    private var subject: String? = null
    private val dialog by lazy { EditDialog(this) }

    companion object {
        fun actionStart(context: Context, param1: String) {
            context.startActivity(Intent(context, ExamActivity::class.java).putExtra("subject", param1))
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        subject = intent.getStringExtra("subject")
        super.onCreate(savedInstanceState)
    }

    override fun initView() {
        mBinding.apply {
            rlExamSimulation.visibility = View.VISIBLE
            examDetailTimer.apply {
                visibility = View.VISIBLE
                setOnChronometerTickListener {
                    val time = System.currentTimeMillis() - it.base
                    val date = Date(time)
                    val sdf = SimpleDateFormat("HH:mm:ss", Locale.US)
                    sdf.timeZone = TimeZone.getTimeZone("UTC")
                    text = sdf.format(date)
                }
                base = System.currentTimeMillis()
                start()
            }
            examDetailViewpager2.adapter = examAdapter
            examDetailViewpager2.isUserInputEnabled = false
        }
        initListener()
    }

    private fun initListener() {
        mBinding.examDetailBackImg.setOnClickListener {
            showExitDialog()
        }
        mBinding.examDetailNextQuest.setOnClickListener {
            if(currentPos < mViewModel.questions.size - 1) {
                mViewModel.setCurrentPos(++currentPos)
            }else {
                showCommitDialog()
            }
        }
        mBinding.examDetailLastQuest.setOnClickListener {
            if(currentPos > 0) {
                mViewModel.setCurrentPos(--currentPos)
            }
        }
    }

    private fun showCommitDialog() {
        mBinding.examDetailTimer.stop()
        dialog.apply {
            show()
            setTitle("提交结果")
            setContent("您确定要提交并查看结果吗？")
            setSureListener("确定提交") {
                val intent = Intent(context, CommitResultActivity::class.java).apply {
                    putExtra("questions", mViewModel.questions)
                    putExtra("answers", mViewModel.myAnswers.value)
                    putExtra("time", mBinding.examDetailTimer.text.toString())
                }
                startActivity(intent)
                finish()
            }
            setCancelListener("我再想想") {
                mBinding.examDetailTimer.start()
                cancel()
                dismiss()
            }
        }
    }

    private fun showExitDialog() {
        mBinding.examDetailTimer.stop()
        dialog.apply {
            show()
            setTitle("退出练习")
            setContent("您要结束本次模拟考试吗？")
            setSureListener("确定") {
                finish()
            }
            setCancelListener("我再想想") {
                mBinding.examDetailTimer.start()
                cancel()
                dismiss()
            }
        }
    }

    override fun initData() {
        mViewModel.currentPos.observe(this){
            mBinding.examDetailLastQuest.setTextColor(if(currentPos == 0) Color.GRAY else resources.getColor(R.color.colorAccent))
            mBinding.examDetailNextQuest.text = if(currentPos == mViewModel.questions.size - 1) "提交" else "下一题"
            mBinding.examDetailViewpager2.currentItem = currentPos
        }
        setDataStatus(mViewModel.liveQuestion, {}, {
            if(!it.isNullOrEmpty()) {
                mBinding.examDetailViewpager2.offscreenPageLimit = it.size
                mViewModel.questions.addAll(it)
                examAdapter.notifyDataSetChanged()
                examAdapter.initFragments()
                mBinding.examDetailViewpager2.currentItem = 0
                mBinding.examDetailRecord.text = "0/${mViewModel.questions.size}"
            }
        })
        mViewModel.myAnswers.observe(this) {
            mBinding.examDetailRecord.text = "${it.size}/${mViewModel.questions.size}"
        }
        mViewModel.getRandomQuestions(subject!!, 5)
        mViewModel.setCurrentPos(currentPos)
    }

    override fun onDestroy() {
        super.onDestroy()
        mBinding.examDetailTimer.stop()
    }

    override fun onBackPressed() {
        showExitDialog()
    }
}