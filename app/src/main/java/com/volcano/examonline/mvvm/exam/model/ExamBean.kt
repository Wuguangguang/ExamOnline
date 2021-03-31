package com.volcano.examonline.mvvm.exam.model

data class Exam(
        var examId: Int? = null,
        var examName: String? = null
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
