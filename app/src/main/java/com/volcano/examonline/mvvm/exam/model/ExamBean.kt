package com.volcano.examonline.mvvm.exam.model

import java.io.Serializable

data class ExamTab (
    var id:Int? = null,
    var name:String? = null
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
