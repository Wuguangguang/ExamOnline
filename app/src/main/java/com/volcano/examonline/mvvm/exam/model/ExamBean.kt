package com.volcano.examonline.mvvm.exam.model

data class Exam(
        var examId: Int? = null,
        var examName: String? = null
)

/**
 * id : 1
 * desc : 物理治疗不包含（  ）
 * type : 单选题
 * img : null
 * level : 中等
 * source : 医学2021-2022春
 * analysis : 提示：C。物理治疗分成运动疗法和物理因子疗法，其中物理因子疗法包括力、电、光、声、水及温度等物理因子。
 * correctanswer : C
 * optiona : 超声波疗法
 * optionb : 运动疗法
 * optionc : 环境改造
 * optiond : 热疗法
 * optione : 磁疗法
 * isselect : 1
 */

data class Question(
        var id: Int? = null,
        var desc: String? = null,
        var type: String? = null,
        var img: Any? = null,
        var level: String? = null,
        var source: String? = null,
        var analysis: String? = null,
        var correctanswer: String? = null,
        var optiona: String? = null,
        var optionb: String? = null,
        var optionc: String? = null,
        var optiond: String? = null,
        var optione: String? = null,
        var isselect: Int? = null
)


/*
{
    "data": [
        {
            "desc": "扔物线",
            "id": 29,
            "imagePath": "https://wanandroid.com/blogimgs/8690f5f9-733a-476a-8ad2-2468d043c2d4.png",
            "isVisible": 1,
            "order": 0,
            "title": "Kotlin 的 Lambda，大部分人学得连皮毛都不算",
            "type": 0,
            "url": "http://i0k.cn/5jhSp"
        }
    ],
    "errorCode": 0,
    "errorMsg": ""
}
 */
data class Banner(
    var desc: String? = null,
    var id: Int? = null,
    var imagePath: String? = null,
    var isVisible: Int? = null,
    var order: Int? = null,
    var title: String? = null,
    var type: Int? = null,
    var url: String? = null,
)

/*
{
    "data": [
        {
            "id": 6,
            "link": "",
            "name": "面试",
            "order": 1,
            "visible": 1
        }
    ],
    "errorCode": 0,
    "errorMsg": ""
}
*/
data class Hotkey (
    var id: Int? = null,
    var link: String? = null,
    var name: String? = null,
    var order: Int? = null,
    var visible: Int? = null,
)
