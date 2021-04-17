package com.volcano.examonline.mvvm.question.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.volcano.examonline.mvvm.study.model.Comment
import com.volcano.examonline.mvvm.study.model.Question
import com.volcano.examonline.network.NetworkRepository

class QuestionViewModel : ViewModel() {

    var questions = arrayListOf<Question>()
    var myAnswers = arrayListOf<String>()


    private var mutableLiveComments = MutableLiveData<Int>()

    fun getQuestionComments(id: Int) {
        mutableLiveComments.value = id
    }


}
