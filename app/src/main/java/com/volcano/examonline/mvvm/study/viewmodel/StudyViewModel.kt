package com.volcano.examonline.mvvm.study.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.volcano.examonline.mvvm.study.model.Question
import com.volcano.examonline.mvvm.study.model.Subject
import com.volcano.examonline.network.NetworkRepository

class StudyViewModel : ViewModel() {

    var subjectData = arrayListOf<Subject>()

    private var mutableSubjects = MutableLiveData<Boolean>()

    val subjects = Transformations.switchMap(mutableSubjects){
        NetworkRepository.getInstance().getSubjects()
    }

    fun getSubjects() {
        mutableSubjects.value = true
    }

    var questionData = arrayListOf<Question>()

    private var mutableQuestions = MutableLiveData<Int>()

    val questions = Transformations.switchMap(mutableQuestions) { id ->
        NetworkRepository.getInstance().getQuestions(id)
    }

    fun getQuestions(subjectId: Int) {
        mutableQuestions.value = subjectId
    }
}