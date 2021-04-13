package com.volcano.examonline.mvvm.homepage.model

/**
 * id : 1
 * subjectname : 医学
 */
data class Subject(
    var id: Int? = null,
    var subjectname: String? = null,
)

/**
 * userName : Volcano Paul
 * description : 为人民服务！
 * img : null
 * avatar : null
 * createat : 2021-04-13T10:41:32.000+00:00
 */
data class Comment(
    var userName: String? = null,
    var description: String? = null,
    var img: Any? = null,
    var avatar: Any? = null,
    var createat: String? = null
)
