package com.volcano.examonline.mvvm.study.view

import android.content.Intent
import androidx.viewpager2.widget.ViewPager2
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.volcano.examonline.R
import com.volcano.examonline.base.BaseMvvmFragment
import com.volcano.examonline.databinding.FragmentStudyBinding
import com.volcano.examonline.mvvm.exam.view.ExamActivity
import com.volcano.examonline.mvvm.login.view.LoginActivity
import com.volcano.examonline.mvvm.question.view.QuestionActivity
import com.volcano.examonline.mvvm.search.view.SearchActivity
import com.volcano.examonline.mvvm.study.adapter.SubjectAdapter
import com.volcano.examonline.mvvm.study.viewmodel.StudyViewModel
import com.volcano.examonline.util.ConstantData
import com.volcano.examonline.widget.CommonDialog
import com.volcano.examonline.widget.CommonDialogOnItemClickListener
import com.youth.banner.adapter.BannerImageAdapter
import com.youth.banner.holder.BannerImageHolder
import com.youth.banner.indicator.CircleIndicator

class StudyFragment : BaseMvvmFragment<FragmentStudyBinding, StudyViewModel>(ConstantData.VIEWMODEL_EXCLUSIVE) {

    companion object {
        fun newInstance() = StudyFragment()
    }

    private val subjectAdapter : SubjectAdapter by lazy { SubjectAdapter(this , mViewModel.subjectData) }
    private var tabLayoutMediator: TabLayoutMediator? = null
    private var subjectNames = arrayListOf<String>()
    private val subjectSelectDialog by lazy { CommonDialog(activity!!) }

    override fun initView() {
        contentView = mBinding.loading
        initListener()
        mBinding.bannerHomepage.apply {
            adapter = object : BannerImageAdapter<Int>(ConstantData.banners) {
                override fun onBindView(holder: BannerImageHolder, data: Int, position: Int, size: Int) {
//                    holder.imageView.setImageResource(data)
                    Glide.with(activity).load(data)
                            .apply(RequestOptions().transforms(CenterCrop(), RoundedCorners(50)))
                            .into(holder.imageView)
                }
            }
            addBannerLifecycleObserver(activity)
            indicator = CircleIndicator(activity)

        }
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
            subjectSelectDialog.show()
            subjectSelectDialog.setDatas(subjectNames)
            subjectSelectDialog.setOnItemClickListener(object : CommonDialogOnItemClickListener {
                override fun onCLick(item: String) {
                    QuestionActivity.actionStart(activity!!, ConstantData.ORDERLY_MODE, item)
                    subjectSelectDialog.dismiss()
                }
            })
        }
        mBinding.llSimulationExam.setOnClickListener {
            subjectSelectDialog.show()
            subjectSelectDialog.setDatas(subjectNames)
            subjectSelectDialog.setOnItemClickListener(object : CommonDialogOnItemClickListener {
                override fun onCLick(item: String) {
                    ExamActivity.actionStart(activity!!, item)
                    subjectSelectDialog.dismiss()
                }
            })
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

    private fun refresh() {
        contentView?.showLoading()
        mViewModel.getSubjects()
    }

    override fun doRetry() {
        super.doRetry()
        refresh()
    }

    private fun tabBind(pager: ViewPager2){
        tabLayoutMediator?.detach()
        tabLayoutMediator = TabLayoutMediator(mBinding.tlSubjects,pager){ tab, position ->
            tab.text = mViewModel.subjectData[position].subjectname
        }
        tabLayoutMediator?.attach()
    }

}

