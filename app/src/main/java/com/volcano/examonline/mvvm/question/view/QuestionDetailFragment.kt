package com.volcano.examonline.mvvm.question.view

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import com.volcano.examonline.R
import com.volcano.examonline.base.BaseMvvmFragment
import com.volcano.examonline.databinding.FragmentQuestionDetailBinding
import com.volcano.examonline.mvvm.exam.adapter.OnItemClickListener
import com.volcano.examonline.mvvm.question.viewmodel.QuestionViewModel
import com.volcano.examonline.mvvm.exam.adapter.OptionsAdapter
import com.volcano.examonline.mvvm.forum.adapter.CommentsAdapter
import com.volcano.examonline.mvvm.study.adapter.QuestionListAdapter
import com.volcano.examonline.mvvm.study.model.Question
import com.volcano.examonline.util.ConstantData
import com.volcano.examonline.widget.CommonDialog
import com.volcano.examonline.widget.CommonDialogOnItemClickListener
import java.lang.StringBuilder

/**
 * 单题详情、解析、顺序练习
 * 单题详情、顺序练习 -> 点击立即响应  mViewModel.myAnswers为空
 * 解析 -> 不可点击   mViewModel.myAnswers不空
 */
class QuestionDetailFragment(private val question: Question, private val currentPos: Int)
    : BaseMvvmFragment<FragmentQuestionDetailBinding, QuestionViewModel>(ConstantData.VIEWMODEL_SHARED) {

    private val optionsAdapter by lazy { OptionsAdapter(activity!!, options!!, question.type!!, mode!!, mySelect, correctSelect) }
    private val commentsAdapter by lazy { CommentsAdapter(activity!!, mViewModel.comments) }
    private val commendQuestionsAdapter by lazy { QuestionListAdapter(activity!!, mViewModel.commendQuestions) }
    private var mySelect = arrayListOf<Int>()
    private var correctSelect = arrayListOf<Int>()
    private var options = arrayListOf<String>()
    private var mode:Int? = null
    private val orderDialog by lazy { CommonDialog(activity!!) }

    companion object {
        fun newInstance(question: Question, i: Int) = QuestionDetailFragment(question, i)
    }

    override fun initView() {
        // 单题解析/多题解析时 myAnswers不为空
        correctSelect.clear()
        question.correctanswer!!.split("").forEach {
            if(ConstantData.answers.contains(it)) {
                correctSelect.add(ConstantData.answers.indexOf(it))
            }
        }
        if(!mViewModel.myAnswers.isNullOrEmpty()) {
            mode = ConstantData.MODE_ANALYSIS
            mBinding.llCorrectAnswer.visibility = View.VISIBLE
            mBinding.tvCorrectAnswer.text = question.correctanswer
            val sign = if(question.correctanswer.equals(mViewModel.myAnswers[currentPos])) " （正确）" else " （错误）"
            mBinding.tvMyAnswer.text = "${mViewModel.myAnswers[currentPos]}$sign"
            mBinding.tvMyAnswer.setTextColor(context!!.resources.getColor(if(sign == " （正确）") R.color.colorAccent else R.color.COLOR_RED))
            mViewModel.myAnswers[currentPos].split("").forEach {
                mySelect.add(ConstantData.answers.indexOf(it))
            }
        }else {
            mode = ConstantData.MODE_IMMEDIATELY
        }
        initOptions()
        initListener()
        mBinding.rvOptions.apply {
            layoutManager = LinearLayoutManager(activity)
            adapter = optionsAdapter
        }
        mBinding.rvCommentList.apply {
            layoutManager = LinearLayoutManager(activity)
            adapter = commentsAdapter
        }
        mBinding.rvCommendList.apply {
            layoutManager = LinearLayoutManager(activity)
            adapter = commendQuestionsAdapter
        }
    }

    private fun initOptions() {
        options.apply {
            add(question.optiona!!)
            add(question.optionb!!)
            if(question.optionc != null) add(question.optionc!!)
            if(question.optiond != null) add(question.optiond!!)
            if(question.optione != null) add(question.optione!!)
        }
    }

    private fun initListener() {
        mBinding.tvAnalysis.setOnClickListener {
            mBinding.tvAnalysis.visibility = View.GONE
            mBinding.llQuestionAnalysis.visibility = View.VISIBLE
            mViewModel.getQuestionComments(question.id!!)
            mViewModel.getCommendQuestions(question.subjectid!!, question.id!!, question.keywords!!)
            if(question.type == ConstantData.TYPE_MULTI_SELECT && mode == ConstantData.MODE_IMMEDIATELY) {
                mBinding.llCorrectAnswer.visibility = View.VISIBLE
                mBinding.tvCorrectAnswer.text = question.correctanswer
                val myAnswer = StringBuilder("")
                mySelect.forEach {
                    myAnswer.append(ConstantData.answers[it])
                }
                val sign = if(question.correctanswer == myAnswer.toString()) " （正确）" else " （错误）"
                mBinding.tvMyAnswer.setTextColor(context!!.resources.getColor(if(sign == " （正确）") R.color.colorAccent else R.color.COLOR_RED))
                mBinding.tvMyAnswer.text = "$myAnswer$sign"
                correctSelect.forEach {
                    mBinding.rvOptions.layoutManager!!.findViewByPosition(it)!!.apply {
                        findViewById<ImageView>(R.id.iv_option).setImageResource(ConstantData.correctImages[it])
                        findViewById<TextView>(R.id.tv_option).setTextColor(context!!.resources.getColor(R.color.colorAccent))
                    }
                }
                mySelect.forEach {
                    if(!correctSelect.contains(it)) {
                        mBinding.rvOptions.layoutManager!!.findViewByPosition(it)!!.apply {
                            findViewById<ImageView>(R.id.iv_option).setImageResource(ConstantData.incorrectImages[it])
                            findViewById<TextView>(R.id.tv_option).setTextColor(context!!.resources.getColor(R.color.COLOR_RED))
                        }
                    }
                }
            }
        }
        mBinding.tvQuestionCommentOrder.setOnClickListener {
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
                                mBinding.tvQuestionCommentOrder.text = ConstantData.ORDER_DEFAULT
                                mBinding.ivQuestionCommentOrder.setImageResource(R.drawable.ic_grey_search)
                            }
                            ConstantData.ORDER_TIME -> {
                                mViewModel.comments.sortWith(Comparator { o1, o2 ->
                                    o2.createat!!.compareTo(o1.createat!!)
                                })
                                mBinding.tvQuestionCommentOrder.text = ConstantData.ORDER_TIME
                                mBinding.ivQuestionCommentOrder.setImageResource(R.drawable.ic_timer)
                            }
                            else -> {
                                mViewModel.comments.sortWith(Comparator { o1, o2 ->
                                    o2.zan!! - o1.zan!!
                                })
                                mBinding.tvQuestionCommentOrder.text = ConstantData.ORDER_HOT
                                mBinding.ivQuestionCommentOrder.setImageResource(R.drawable.ic_zan)
                            }
                        }
                        commentsAdapter.notifyDataSetChanged()
                        orderDialog.dismiss()
                    }
                })
            }
        }
        optionsAdapter.setOnClickListener(object : OnItemClickListener {
            override fun onClick(myAnswer: ArrayList<String>) {
                if(question.type != ConstantData.TYPE_MULTI_SELECT) {
                    mBinding.llCorrectAnswer.visibility = View.VISIBLE
                    mBinding.tvCorrectAnswer.text = question.correctanswer
                    val sign = if(question.correctanswer.equals(myAnswer[0])) " （正确）" else " （错误）"
                    mBinding.tvMyAnswer.setTextColor(context!!.resources.getColor(if(sign == " （正确）") R.color.colorAccent else R.color.COLOR_RED))
                    mBinding.tvMyAnswer.text = "${myAnswer[0]}$sign"
                    mBinding.rvOptions.layoutManager!!.findViewByPosition(correctSelect[0])!!.apply {
                        findViewById<ImageView>(R.id.iv_option).setImageResource(ConstantData.correctImages[correctSelect[0]])
                        findViewById<TextView>(R.id.tv_option).setTextColor(context!!.resources.getColor(R.color.colorAccent))
                    }
                    if(myAnswer[0] != question.correctanswer) {
                        mBinding.rvOptions.layoutManager!!.findViewByPosition(ConstantData.answers.indexOf(myAnswer[0]))!!.apply {
                            findViewById<ImageView>(R.id.iv_option).setImageResource(ConstantData.incorrectImages[ConstantData.answers.indexOf(myAnswer[0])])
                            findViewById<TextView>(R.id.tv_option).setTextColor(context!!.resources.getColor(R.color.COLOR_RED))
                        }
                    }
                    optionsAdapter.setSelected()
                }else {
                    mySelect.clear()
                    myAnswer.forEach {
                        mySelect.add(ConstantData.answers.indexOf(it))
                    }
                }
            }
        })
    }


    override fun initData() {
        mBinding.tvQuestionLevel.text = question.level
        mBinding.tvQuestionType.text = question.type
        mBinding.tvQuestionSource.text = question.source
        mBinding.tvQuestionDesc.text = question.description
        val analysis = if(question.analysis == null || question.analysis == "") "暂无解析" else question.analysis as String
        mBinding.tvQuestionAnalysis.text = analysis
        setDataStatus(mViewModel.liveComments, {
        }, {
            mViewModel.comments.clear()
            if(!it.isNullOrEmpty()) {
                mBinding.tvQuestionCommentNum.text = "${it.size}"
                mViewModel.comments.addAll(it)
            }else {
                mBinding.tvQuestionCommentNum.text = "0"
            }
            commentsAdapter.notifyDataSetChanged()
        })
        setDataStatus(mViewModel.liveCommendQuestions, {

        }, {
            mViewModel.commendQuestions.clear()
            if(!it.isNullOrEmpty()) {
                mViewModel.commendQuestions.addAll(it)
                mBinding.tvQuestionCommendNum.text = "${it.size}"
            }else {
                mBinding.tvQuestionCommendNum.text = "0"
            }
            commendQuestionsAdapter.notifyDataSetChanged()
        })
    }

}