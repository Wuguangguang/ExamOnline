package com.volcano.examonline.mvvm.exam.viewmodel

import androidx.lifecycle.*
import com.volcano.examonline.mvvm.exam.model.ExamTab

class ExamViewModel : ViewModel() {

    val tabs = arrayListOf(
        ExamTab(1, "考试列表"),
        ExamTab(2, "竞赛排名")
    )

}