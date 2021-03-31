package com.volcano.examonline.mvvm.exam.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class ExamDetailViewModel : ViewModel() {

    var currentPos = MutableLiveData<Int>()

    fun setCurrentPos(pos : Int) {
        currentPos.value = pos
    }

}
