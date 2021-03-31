package com.volcano.examonline

import android.os.Bundle
import android.webkit.WebViewClient
import com.volcano.examonline.base.BaseMvvmActivity
import com.volcano.examonline.databinding.ActivityWebviewBinding

class WebviewActivity : BaseMvvmActivity<ActivityWebviewBinding, WebviewViewModel>() {
    private lateinit var title : String
    private lateinit var link : String

    override fun onCreate(savedInstanceState: Bundle?) {
        link = intent.getStringExtra("link").toString()
        title = intent.getStringExtra("title").toString()
        super.onCreate(savedInstanceState)
    }

    override fun initView() {
        mBinding.webview.settings.javaScriptEnabled = true
        mBinding.webview.webViewClient = WebViewClient()
        initToolbar()
        mBinding.webview.loadUrl(link)
    }

    private fun initToolbar() {
        mBinding.toolbar.apply {
            if(title.length > 14){
                toolbarTitle.text = title.substring(0,14) + "..."
            }else{
                toolbarTitle.text = title
            }
            toolbarLeftImageBack.apply {
                setImageResource(R.drawable.left_triangle)
                setOnClickListener {
                    finish()
                }
            }
            toolbarRightImage.apply {
                setImageResource(R.drawable.three_points)
                setOnClickListener {
                }
            }
        }
    }
}