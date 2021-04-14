package com.volcano.examonline.base

import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.viewbinding.ViewBinding


abstract class BaseMvvmActivity<VB : ViewBinding, VM : ViewModel> : AppCompatActivity() {

    lateinit var mBinding: VB
    lateinit var mViewModel: VM

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initEventAndData()
    }


    private fun initEventAndData() {
        initViewBinding()
        mViewModel = createViewModel()
        setStatusBarStyle()
        initView()
        initData()
    }

     private fun setStatusBarStyle() {
         if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
             window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
             window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
             window.statusBarColor = Color.TRANSPARENT
         }
         window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
    }

    private fun initViewBinding() {
        try {
            val classes: Class<VB> = getVbClazz(this)
            val method = classes.inflateMethod()
            mBinding = method.invoke(null, layoutInflater) as VB
        }catch (e: Exception) {
            e.printStackTrace()
        }
        setContentView(mBinding.root)
    }

    private fun createViewModel() : VM {
        return ViewModelProvider(this).get(getVmClazz(this))
    }

    abstract fun initView()

    open fun initData(){

    }
}