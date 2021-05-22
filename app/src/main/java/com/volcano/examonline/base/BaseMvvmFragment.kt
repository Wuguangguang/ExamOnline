package com.volcano.examonline.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.observe
import androidx.viewbinding.ViewBinding
import com.volcano.examonline.widget.MultipleStatusLayout
import java.lang.Exception

abstract class BaseMvvmFragment<VB: ViewBinding, VM: ViewModel>(private val isActivitySharedVM: Int?) : Fragment(){
    lateinit var mBinding: VB
    lateinit var mViewModel: VM

    var contentView: MultipleStatusLayout? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        try {
            val classes: Class<VB> = getVbClazz(this)
            val method = classes.inflateMethods()
            mBinding = method.invoke(null, inflater, container, false) as VB
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return mBinding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        mViewModel = createViewModel()
        initView()
        if (contentView != null) {
            contentView!!.setRetryListener{ doRetry() }
        }
        initData()
    }

    private fun createViewModel() : VM {
        return if(isActivitySharedVM == 0x1) {
            ViewModelProvider(activity!!).get(getVmClazz(this))
        }
        else {
            ViewModelProvider(this).get(getVmClazz(this))
        }
    }

    /**
     * 设置 LiveData 的状态，根据不同状态显示不同页面
     *
     * @param dataLiveData LiveData
     * @param onDataStatus 数据回调进行使用
     */
    fun <T> setDataStatus(dataLiveData: LiveData<Response<T>>,
                          onBadNetwork: () -> Unit = {},
                          onDataStatus: (T) -> Unit) {
        dataLiveData.observe(this) {
            when {
                it == null -> {
                    Toast.makeText(activity, "网络异常，请点击屏幕重试", Toast.LENGTH_SHORT).show()
                    contentView?.showError{ doRetry() }
                    onBadNetwork.invoke()
                }
                it.code == 1 -> {
                    val dataList = it.data!!
                    contentView?.hideLoading()
                    onDataStatus(dataList)
                }
                else -> {
                    Toast.makeText(activity, "${it.msg}", Toast.LENGTH_SHORT).show()
                    contentView?.showEmpty()
                    onBadNetwork.invoke()
                }
            }
        }
    }


    abstract fun initView()
    abstract fun initData()
    open fun doRetry() {}

}