package com.volcano.examonline.mvvm.detail.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.volcano.examonline.mvvm.study.model.Question
import com.volcano.examonline.network.NetworkRepository

class ExamDetailViewModel : ViewModel() {

    var currentPos = MutableLiveData<Int>()

    fun setCurrentPos(pos : Int) {
        currentPos.value = pos
    }

    // 试题列表
    var questions = arrayListOf<Question>()

    private var mutableQuestionNum : MutableLiveData<Int> = MutableLiveData()

    fun getRandomQuestions(num: Int) {
        mutableQuestionNum.value = num
    }

    val question : LiveData<List<Question>> = Transformations.switchMap(mutableQuestionNum) { num ->
        NetworkRepository.getInstance().getRandomQuestions(1, num)
    }


}