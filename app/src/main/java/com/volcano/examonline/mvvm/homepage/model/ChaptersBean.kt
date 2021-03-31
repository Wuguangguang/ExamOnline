package com.volcano.examonline.mvvm.homepage.model

/*
{
    "data": [
        {
            "children": [],
            "courseId": 13,
            "id": 408,
            "name": "鸿洋",
            "order": 190000,
            "parentChapterId": 407,
            "userControlSetTop": false,
            "visible": 1
        },
    ],
    "errorCode": 0,
    "errorMsg": ""
}
 */
// 公众号列表
data class Chapter(
    var courseId: Int? = null,
    var id: Int? = null,
    var name: String? = null,
    var order: Int? = null,
    var parentChapterId: Int? = null,
    var userControlSetTop: Boolean? = null,
    var visible: Int? = null,
    var children: List<*>? = null
)


