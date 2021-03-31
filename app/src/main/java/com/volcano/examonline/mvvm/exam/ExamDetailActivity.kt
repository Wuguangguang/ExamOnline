package com.volcano.examonline.mvvm.exam

import android.graphics.Color
import android.os.Build
import android.view.View
import android.view.WindowManager
import androidx.fragment.app.Fragment
import androidx.lifecycle.observe
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.volcano.examonline.R
import com.volcano.examonline.base.BaseMvvmActivity
import com.volcano.examonline.databinding.ActivityExamDetailBinding
import com.volcano.examonline.mvvm.exam.viewmodel.ExamDetailViewModel
import java.text.SimpleDateFormat
import java.util.*


class ExamDetailActivity : BaseMvvmActivity<ActivityExamDetailBinding, ExamDetailViewModel>() {

    private val fragments = mutableListOf<Fragment>()
    private var currentPos = 0

    override fun initView() {
        setStatusBarStyle()
        /// 初始化计时器
        mBinding.examDetailTimer.apply {
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
        mBinding.examDetailBackImg.setOnClickListener {
            finish()
        }
        repeat(10) {
            fragments.add(ExamDetailFragment.newInstance())
        }
        mBinding.examDetailViewpager2.apply {
            adapter = object : FragmentStateAdapter(supportFragmentManager, lifecycle) {
                override fun getItemCount(): Int {
                    return fragments.size
                }
                override fun createFragment(position: Int): Fragment {
                    return fragments[position]
                }
            }
            offscreenPageLimit = 10
            isUserInputEnabled = false
        }
        mBinding.examDetailNextQuest.setOnClickListener {
            if(currentPos < fragments.size - 1) {
                mViewModel.setCurrentPos(++currentPos)
            }
        }
        mBinding.examDetailLastQuest.setOnClickListener {
            if(currentPos > 0) {
                mViewModel.setCurrentPos(--currentPos)
            }
        }
    }

    override fun initData() {
        mViewModel.currentPos.observe(this){
            mBinding.examDetailLastQuest.setTextColor(if(currentPos == 0) Color.GRAY else resources.getColor(R.color.colorAccent))
            mBinding.examDetailNextQuest.setTextColor(if(currentPos == fragments.size - 1) Color.GRAY else resources.getColor(R.color.colorAccent))
            mBinding.examDetailViewpager2.currentItem = currentPos
        }
        mViewModel.setCurrentPos(currentPos)
    }

    private fun setStatusBarStyle() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
            window.statusBarColor = Color.TRANSPARENT
        }
        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
    }

    override fun onDestroy() {
        super.onDestroy()
        mBinding.examDetailTimer.stop()
    }
}