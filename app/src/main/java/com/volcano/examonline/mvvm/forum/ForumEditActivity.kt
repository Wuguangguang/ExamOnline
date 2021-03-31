package com.volcano.examonline.mvvm.forum

import android.graphics.Color
import android.os.Build
import android.view.View
import android.view.WindowManager
import com.volcano.examonline.base.BaseMvvmActivity
import com.volcano.examonline.databinding.ActivityForumEditBinding
import com.volcano.examonline.mvvm.forum.viewmodel.ForumEditViewModel

class ForumEditActivity : BaseMvvmActivity<ActivityForumEditBinding, ForumEditViewModel>() {

    override fun initView() {
        setStatusBarStyle()
    }

    private fun setStatusBarStyle() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
            window.statusBarColor = Color.TRANSPARENT
        }
        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
    }
}