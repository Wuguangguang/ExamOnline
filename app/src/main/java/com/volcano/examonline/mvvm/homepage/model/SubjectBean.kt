package com.volcano.examonline.mvvm.homepage.model

import com.volcano.examonline.mvvm.exam.model.Question

data class Subject(
    var subjectId: Int? = null,
    var subjectName: String? = null
)

data class QuestionList(
        var datas: List<Question>? = null,
        var curPage: Int? = null,
        var totalPage: Int? = null,
        var size: Int? = null
)
