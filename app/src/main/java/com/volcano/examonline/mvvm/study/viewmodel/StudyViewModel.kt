package com.volcano.examonline.mvvm.study.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.volcano.examonline.mvvm.study.model.Question
import com.volcano.examonline.mvvm.study.model.Subject
import com.volcano.examonline.mvvm.study.model.UploadBean
import com.volcano.examonline.network.NetworkRepository

class StudyViewModel : ViewModel() {

    var subjectData = arrayListOf<Subject>()

    private var mutableSubjects = MutableLiveData<Boolean>()

    val subjects = Transformations.switchMap(mutableSubjects){
        NetworkRepository.getSubjects()
    }

    fun getSubjects() {
        mutableSubjects.value = true
    }

    var questionData = arrayListOf<Question>()

    private var mutableQuestions = MutableLiveData<String>()

    val questions = Transformations.switchMap(mutableQuestions) { name ->
        NetworkRepository.getQuestions(name)
    }

    fun getQuestions(subjectName: String) {
        mutableQuestions.value = subjectName
    }

    private var mutableUploadBean = MutableLiveData<UploadBean>()

    fun uploadQuestion(uploadBean: UploadBean) {
        mutableUploadBean.value = uploadBean
    }

    val liveUploadBean = Transformations.switchMap(mutableUploadBean) { obj ->
        NetworkRepository.uploadQuestion(obj)
    }
}