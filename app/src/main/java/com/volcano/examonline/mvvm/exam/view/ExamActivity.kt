package com.volcano.examonline.mvvm.exam.view

import android.app.AlertDialog
import android.content.DialogInterface
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.widget.EditText
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
import java.text.SimpleDateFormat
import java.util.*


class ExamActivity : BaseMvvmActivity<ActivityExamBinding, ExamViewModel>() {

    private var currentPos = 0
    private val examAdapter: ExamPagerAdapter by lazy { ExamPagerAdapter(this, mViewModel.questions, mode) }
    private var mode : Int? = null
    private var subject: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        mode = intent.getIntExtra("mode", ConstantData.ORDERLY_MODE)
        subject = intent.getStringExtra("subject")
        super.onCreate(savedInstanceState)
    }

    override fun initView() {
        mBinding.apply {
            when(mode) {
                ConstantData.SIMULATION_MODE -> {
                    rlExamSimulation.visibility = View.VISIBLE
                    tvExamTitle.visibility = View.GONE
                    fabEditComment.visibility = View.GONE
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
                }
                else -> {
                    tvExamTitle.visibility = View.VISIBLE
                    examDetailTimer.visibility = View.GONE
                    rlExamSimulation.visibility = View.GONE
                    fabEditComment.apply {
                        visibility = View.VISIBLE
                        setOnClickListener {

                        }
                    }
                    examDetailViewpager2.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
                        override fun onPageSelected(position: Int) {
                            super.onPageSelected(position)
                            mBinding.examDetailRecord.text = "${position+1}/${mViewModel.questions.size}"
                        }
                    })
                }
            }
        }
        mBinding.examDetailViewpager2.adapter = examAdapter
        mBinding.examDetailViewpager2.isUserInputEnabled = mode != ConstantData.SIMULATION_MODE
        initListener()
    }

    private fun initListener() {
        mBinding.examDetailBackImg.setOnClickListener {
            showExitDialog()
        }
        mBinding.fabEditComment.setOnClickListener {
            //发表评论
            if(ConstantData.isLogin()) {
                val editText = EditText(this)
                AlertDialog.Builder(this).apply {
                    setTitle("发表评论")
                    setCancelable(false)
                    setView(editText)
                    setNegativeButton("取消",null)
                    setPositiveButton("发表") { _: DialogInterface, _: Int ->
                        mViewModel.editComment(mViewModel.questions[mBinding.examDetailViewpager2.currentItem].id!!, editText.text.toString())
                    }
                    show()
                }
            }else {
                val intent = Intent(this, LoginActivity::class.java)
                startActivity(intent)
            }
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
        AlertDialog.Builder(this).apply {
            setTitle("提交结果")
            setMessage("您确定要提交并查看结果吗")
            setCancelable(false)
            setPositiveButton("确定提交") { _, _ ->
                val intent = Intent(context, CommitResultActivity::class.java).apply {
                    putExtra("questions", mViewModel.questions)
                    putExtra("answers", mViewModel.myAnswers.value)
                    putExtra("time", mBinding.examDetailTimer.text.toString())
                }
                startActivity(intent)
                finish()
            }
            setNegativeButton("我再想想") { _, _ ->
                mBinding.examDetailTimer.start()
            }
            show()
        }
    }

    private fun showExitDialog() {
        mBinding.examDetailTimer.stop()
        AlertDialog.Builder(this).apply {
            setTitle("退出练习")
            setMessage(if(mode == ConstantData.SIMULATION_MODE) "您要结束本次模拟考试吗？" else "您要结束本次顺序练习吗？")
            setCancelable(false)
            setPositiveButton("确定") { _, _ ->
                finish()
            }
            setNegativeButton("我再想想") { _, _ ->
                mBinding.examDetailTimer.start()
            }
            show()
        }
    }

    override fun initData() {
        mViewModel.currentPos.observe(this){
            mBinding.examDetailLastQuest.setTextColor(if(currentPos == 0) Color.GRAY else resources.getColor(R.color.colorAccent))
            mBinding.examDetailNextQuest.text = if(currentPos == mViewModel.questions.size - 1) "提交" else "下一题"
            mBinding.examDetailViewpager2.currentItem = currentPos
        }
        setDataStatus(mViewModel.liveEditComment, {
            Toast.makeText(this, "发表失败，请稍后再试！", Toast.LENGTH_SHORT).show()
        }, {
            Toast.makeText(this, "发表评论成功！", Toast.LENGTH_SHORT).show()
        })
        setDataStatus(mViewModel.question) {
            if(!it.isNullOrEmpty()) {
                mBinding.examDetailViewpager2.offscreenPageLimit = it.size
                mViewModel.questions.addAll(it)
                examAdapter.notifyDataSetChanged()
                examAdapter.initFragments()
                mBinding.examDetailViewpager2.currentItem = 0
                mBinding.examDetailRecord.text = "0/${mViewModel.questions.size}"
            }
        }
        mViewModel.myAnswers.observe(this) {
            mBinding.examDetailRecord.text = "${it.size}/${mViewModel.questions.size}"
        }
        when(mode) {
            ConstantData.ORDERLY_MODE -> {
                mViewModel.getQuestions(subject!!)
            }
            ConstantData.SIMULATION_MODE -> {
                mViewModel.getRandomQuestions(subject!!, 5)
            }
        }
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