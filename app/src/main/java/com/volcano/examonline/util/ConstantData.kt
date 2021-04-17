package com.volcano.examonline.util

object ConstantData {

    var TOKEN: String? = null
    var PHONE: String? = null

    const val SINGLE_QUESTION:Int = 0x1 //单题解析、查看单题
    const val MULTI_QUESTION:Int = 0x2  //全部解析、错题解析
    const val SIMULATION_MODE:Int = 0x1 //模拟考试 : 载入ExamFragment
    const val ORDERLY_MODE: Int = 0x2   //顺序联系 : 载入QuestionFragment

    fun isLogin(): Boolean {
        return TOKEN != null
    }

    fun exitLogin() {
        TOKEN = null
        PHONE = null
    }
}