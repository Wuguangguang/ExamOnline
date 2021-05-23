package com.volcano.examonline.base

import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.observe
import androidx.viewbinding.ViewBinding
import com.volcano.examonline.R
import com.volcano.examonline.util.ActivityCollector
import com.volcano.examonline.util.ToastUtils
import ezy.ui.layout.LoadingLayout

abstract class BaseMvvmActivity<VB : ViewBinding, VM : ViewModel> : AppCompatActivity() {

    lateinit var mBinding: VB
    lateinit var mViewModel: VM

    var contentView: LoadingLayout? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        ActivityCollector.addActivity(this)
        setStatusBarStyle()
        initEventAndData()
    }

    override fun onDestroy() {
        super.onDestroy()
        ActivityCollector.removeActivity(this)
    }

    private fun setStatusBarStyle() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
            window.statusBarColor = Color.TRANSPARENT
        }
        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
    }

    private fun initEventAndData() {
        initViewBinding()
        mViewModel = createViewModel()
        initView()
        contentView?.setLoading(R.layout.include_anim_loading)
        contentView?.setEmpty(R.layout.include_empty_view)
        contentView?.setErrorImage(R.drawable.bad_network_image)
        contentView?.setErrorText("系统开小差~")
        contentView?.setRetryText("点击重试")
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

    fun <T> setDataStatus(dataLiveData: LiveData<Response<T>>,onDataError: () -> Unit = {}, onDataStatus: (T?) -> Unit) {
        dataLiveData.observe(this) {
            when {
                it == null -> {
                    ToastUtils.show(this, "网络异常，请点击屏幕重试")
                    contentView?.showError()
                    contentView?.setRetryListener { doRetry() }
                    onDataError.invoke()
                }
                it.code == 1 -> {
                    val dataList = it.data ?: null
                    contentView?.showContent()
                    onDataStatus(dataList)
                }
                else -> {
                    ToastUtils.show(this, "${it.msg}")
                    contentView?.showEmpty()
                    onDataError.invoke()
                }
            }
        }
    }

    abstract fun initView()

    abstract fun initData()

    open fun doRetry() {

    }
}