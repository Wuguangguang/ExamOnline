package com.volcano.examonline.util

import android.content.Context
import android.widget.Toast
import java.lang.Exception

object ToastUtils {

    private var toast: Toast? = null
    private var oneTime: Long = 0
    private var twoTime: Long = 0
    private var info: CharSequence = ""

    fun show(context: Context, text: CharSequence) {
        show(context, text, Toast.LENGTH_SHORT)
    }

    fun show(context: Context, text: CharSequence, duration: Int) {
        try {
            if(toast == null) {
                toast = Toast.makeText(context, text, duration)
                toast!!.show()
                oneTime = System.currentTimeMillis()
            }else {
                twoTime = System.currentTimeMillis()
                if(info == text && twoTime - oneTime > 2) {
                    toast!!.show()
                }else {
                    info = text
                    toast!!.setText(info)
                    toast!!.show()
                }
            }
            oneTime = twoTime
        }catch (e: Exception) {
            e.printStackTrace()
        }
    }
}