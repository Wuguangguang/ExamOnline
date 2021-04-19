package com.volcano.examonline

import android.view.KeyEvent
import android.view.animation.*
import android.widget.Toast
import com.volcano.examonline.base.BaseMvvmActivity
import com.volcano.examonline.databinding.ActivitySplashBinding


class SplashActivity : BaseMvvmActivity<ActivitySplashBinding, MainViewModel>() {

    private var animationTime: Long = 2000

    private val animationListener = object : Animation.AnimationListener {
        override fun onAnimationStart(animation: Animation) {}
        override fun onAnimationEnd(animation: Animation) {
            //跳转到登陆界面
            jumpToMainActivity()
        }
        override fun onAnimationRepeat(animation: Animation) {}
    }

    private fun initAnimation() {
        val rotateAnimation = RotateAnimation(
                0f, 360f,
                Animation.RELATIVE_TO_SELF, 0.5f,
                Animation.RELATIVE_TO_SELF, 0.5f
        )
        rotateAnimation.duration = animationTime
        rotateAnimation.fillAfter = true
        val scaleAnimation = ScaleAnimation(
                0f, 1f, 0f, 1f,
                Animation.RELATIVE_TO_SELF, 0.5f,
                Animation.RELATIVE_TO_SELF, 0.5f
        )
        scaleAnimation.duration = animationTime
        scaleAnimation.fillAfter = true
        val alphaAnimation = AlphaAnimation(0f, 1f)
        alphaAnimation.duration = animationTime
        alphaAnimation.fillAfter = true
        val animationSet = AnimationSet(true)
        animationSet.addAnimation(alphaAnimation)
        mBinding.rlWelcomeBg.startAnimation(animationSet)
        animationSet.setAnimationListener(animationListener)
    }


    private fun jumpToMainActivity() {
        MainActivity.actionStart(this)
        finish()
    }

    override fun initView() {
        initAnimation()
        mBinding.rlWelcomeBg.setOnClickListener{ jumpToMainActivity() }
    }

    override fun initData() {
    }

}
