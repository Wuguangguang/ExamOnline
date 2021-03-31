package com.volcano.examonline.mvvm.homepage.model

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

data class Question(
    var questionId: Int? = null,
    var questionTitle: String? = null,
    var questionType: String? = null,
    var questionDesc: String? = null,
    var questionImg: String? = null,
    var questionOptions: List<QuestionOptions>? = null,
    var difficultyLevel: String? = null,
    var source: String? = null,
    var correctOption: String? = null
)

data class QuestionOptions(
    var optionSign: String? = null,
    var optionDesc: String? = null
)