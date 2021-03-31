package com.volcano.examonline.base

import android.os.Bundle
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
        initView()
        initData()
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