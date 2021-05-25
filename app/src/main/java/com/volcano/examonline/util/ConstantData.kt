package com.volcano.examonline.util

import com.volcano.examonline.R
import java.text.SimpleDateFormat
import java.util.*

object ConstantData {

    var TOKEN: String? = null
    var ID: Int? = null

    const val VIEWMODEL_SHARED = 0x1
    const val VIEWMODEL_EXCLUSIVE = 0x2

    const val SINGLE_QUESTION:Int = 0x1 //单题解析、查看单题
    const val MULTI_QUESTION:Int = 0x2  //全部解析、错题解析
    const val ORDERLY_MODE:Int = 0x3

    const val MODE_ANALYSIS:Int = 0x1   //选项不可点击，初始化答案 （解析）
    const val MODE_IMMEDIATELY:Int = 0x2    //选项点击后立即响应对错 （单题详情、顺序练习）
    const val MODE_NO_RESPONSE:Int = 0x3    //选项点击后不响应对错（模拟考试）

    const val TYPE_SINGLE_SELECT = "单选题"
    const val TYPE_MULTI_SELECT = "多选题"
    const val TYPE_JUDGE = "判断题"

    const val ORDER_DEFAULT = "默认排序"
    const val ORDER_TIME = "时间排序"
    const val ORDER_HOT = "热度排序"

    val normalImages = arrayListOf(
        R.drawable.ic_option_a_normal,
        R.drawable.ic_option_b_normal,
        R.drawable.ic_option_c_normal,
        R.drawable.ic_option_d_normal,
        R.drawable.ic_option_e_normal,
    )
    val correctImages = arrayListOf(
        R.drawable.ic_option_a_selected,
        R.drawable.ic_option_b_selected,
        R.drawable.ic_option_c_selected,
        R.drawable.ic_option_d_selected,
        R.drawable.ic_option_e_selected
    )
    val incorrectImages = arrayListOf(
        R.drawable.ic_option_a_incorrect,
        R.drawable.ic_option_b_incorrect,
        R.drawable.ic_option_c_incorrect,
        R.drawable.ic_option_d_incorrect,
        R.drawable.ic_option_e_incorrect
    )
    val answers = arrayListOf(
        "A","B","C","D","E"
    )

    val banners = arrayListOf(
        R.drawable.ic_banner_placeholder,
        R.drawable.ic_banner_placeholder2,
        R.drawable.ic_banner_placeholder3,
        R.drawable.ic_banner_placeholder4
    )


    fun isLogin(): Boolean {
        return TOKEN != null
    }

    fun exitLogin() {
        TOKEN = null
        ID = null
    }

    fun str2Timestamp(str: String): String {
        return str.substring(0,10)+" "+str.substring(11,19)
    }
}