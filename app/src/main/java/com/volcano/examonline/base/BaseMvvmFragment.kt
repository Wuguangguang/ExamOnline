package com.volcano.examonline.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.viewbinding.ViewBinding
import java.lang.Exception

abstract class BaseMvvmFragment<VB: ViewBinding, VM: ViewModel> : Fragment() {
    lateinit var mBinding: VB
    lateinit var mViewModel: VM

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        try {
            val classes: Class<VB> = getVbClazz(this)
            val method = classes.inflateMethods()
            mBinding = method.invoke(null, inflater, container, false) as VB
        }catch (e : Exception) {
            e.printStackTrace()
        }
        return mBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mViewModel = createViewModel()
        initView()
        initData()
    }

    private fun createViewModel() : VM {
        return ViewModelProvider(activity!!).get(getVmClazz(this))
    }

    abstract fun initView()
    abstract fun initData()

}