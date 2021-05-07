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


class Question : Serializable {
    /**
     * id : 7
     * subjectid : 1
     * description : 步态分析是利用力学概念和已经掌握的人体解剖、生理学知识对人体行走功能的状况进行客观的定性分析和定量分析，并为临床及康复治疗提供有益的指导和疗效评价。（）
     * type : 判断题
     * img : null
     * level : 简单
     * source : 医学
     * analysis : null
     * correctanswer : A
     * optiona : 对
     * optionb : 错
     * optionc : null
     * optiond : null
     * optione : null
     */
    var id: Int? = null
    var subjectid: Int? = null
    var description: String? = null
    var type: String? = null
    var img: String? = null
    var keywords: String? = null
    var level: String? = null
    var source: String? = null
    var analysis: String? = null
    var correctanswer: String? = null
    var optiona: String? = null
    var optionb: String? = null
    var optionc: String? = null
    var optiond: String? = null
    var optione: String? = null
    var commentnums: Int? = null
}

data class Ranking(
    var id: Int? = null,
    var userphone: String? = null,
    var username: String? = null,
    var avatar: String? = null,
    var accumulate: Int? = null
)
