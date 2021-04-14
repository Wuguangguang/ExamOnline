package com.volcano.examonline.mvvm.study.model

import java.io.Serializable

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


/**
 * id : 6
 * subjectid : 1
 * description : 运动的潜在危险有：（  ）
 * type : 多选题
 * img : null
 * level : 简单
 * source : 医学2021-2022春
 * analysis : null
 * correctanswer : ABD
 * optiona : 运动损伤
 * optionb : 诱发心血管事件
 * optionc : 促进内分泌
 * optiond : 脏器功能过负荷或者衰竭
 * optione : 导致挫折感
 * isselect : 1
 */
class Question : Serializable {
    var id: Int? = null
    var subjectid: Int? = null
    var description: String? = null
    var type: String? = null
    var img: Any? = null
    var level: String? = null
    var source: String? = null
    var analysis: Any? = null
    var correctanswer: String? = null
    var optiona: String? = null
    var optionb: String? = null
    var optionc: String? = null
    var optiond: String? = null
    var optione: String? = null
    var isselect: Int? = null
}
