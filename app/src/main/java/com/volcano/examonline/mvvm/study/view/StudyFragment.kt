package com.volcano.examonline.mvvm.study.view

import android.content.Intent
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.volcano.examonline.base.BaseMvvmFragment
import com.volcano.examonline.databinding.FragmentStudyBinding
import com.volcano.examonline.mvvm.exam.view.ExamActivity
import com.volcano.examonline.mvvm.login.view.LoginActivity
import com.volcano.examonline.mvvm.mine.view.MyInfoActivity
import com.volcano.examonline.mvvm.search.view.SearchActivity
import com.volcano.examonline.mvvm.study.adapter.SubjectAdapter
import com.volcano.examonline.mvvm.study.viewmodel.StudyViewModel
import com.volcano.examonline.util.ConstantData
import com.volcano.examonline.widget.CommonDialog
import com.volcano.examonline.widget.CommonDialogOnItemClickListener

class StudyFragment : BaseMvvmFragment<FragmentStudyBinding, StudyViewModel>(ConstantData.VIEWMODEL_EXCLUSIVE) {

    companion object {
        fun newInstance() = StudyFragment()
    }

    private val subjectAdapter : SubjectAdapter by lazy { SubjectAdapter(this , mViewModel.subjectData) }
    private var tabLayoutMediator: TabLayoutMediator? = null
    private var subjectNames = arrayListOf<String>()
    private val subjectSelectDialog by lazy { CommonDialog(activity!!) }

    override fun initView() {
        initListener()
        contentView = mBinding.mslVpQuestions
        mBinding.tlSubjects.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab?) {
                mBinding.vpQuestions.currentItem = tab!!.position
            }
            override fun onTabUnselected(tab: TabLayout.Tab?) {
            }
            override fun onTabReselected(tab: TabLayout.Tab?) {
            }
        })
        mBinding.vpQuestions.apply {
            adapter = subjectAdapter
        }
    }

    private fun initListener() {
        mBinding.tvSearch.setOnClickListener {
            val intent = Intent(activity, SearchActivity::class.java).putExtra("type","试题")
            startActivity(intent)
        }
        mBinding.llOrderlyPractice.setOnClickListener {
            goToExamActivity(ConstantData.ORDERLY_MODE)
        }
        mBinding.llSimulationExam.setOnClickListener {
            goToExamActivity(ConstantData.SIMULATION_MODE)
        }
        mBinding.llRanking.setOnClickListener {
            val intent = Intent(activity, RankingActivity::class.java)
            startActivity(intent)
        }
        mBinding.llUploadQuestion.setOnClickListener {
            if(ConstantData.isLogin()) {
                val intent = Intent(activity, QuestionUploadActivity::class.java)
                startActivity(intent)
            } else {
                val intent = Intent(context, LoginActivity::class.java)
                startActivity(intent)
            }
        }
    }

    private fun goToExamActivity(mode: Int) {
        subjectSelectDialog.show()
        subjectSelectDialog.setDatas(subjectNames)
        subjectSelectDialog.setOnItemClickListener(object : CommonDialogOnItemClickListener {
            override fun onCLick(item: String) {
                val intent = Intent(activity, ExamActivity::class.java)
                intent.putExtra("mode", mode)
                intent.putExtra("subject", item)
                startActivity(intent)
                subjectSelectDialog.dismiss()
            }
        })
    }

    override fun initData() {
        setDataStatus(mViewModel.subjects, {
        }, {
            if(!it.isNullOrEmpty()) {
                mBinding.vpQuestions.offscreenPageLimit = it.size
                mViewModel.subjectData.addAll(it)
                subjectAdapter.notifyDataSetChanged()
                subjectAdapter.initFragments()
                tabBind(mBinding.vpQuestions)
                it.forEach{
                    subjectNames.add(it.subjectname!!)
                }
            }
        })
        refresh()
    }

    override fun doRetry() {
        super.doRetry()
        refresh()
    }

    private fun refresh() {
        contentView?.showLoading()
        mViewModel.getSubjects()
    }

    private fun tabBind(pager: ViewPager2){
        tabLayoutMediator?.detach()
        tabLayoutMediator = TabLayoutMediator(mBinding.tlSubjects,pager){ tab, position ->
            tab.text = mViewModel.subjectData[position].subjectname
        }
        tabLayoutMediator?.attach()
    }

}

