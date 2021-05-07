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
    var comments = arrayListOf<Comment>()
    var commendQuestions = arrayListOf<Question>()


    private var mutableLiveComments = MutableLiveData<Int>()

    fun getQuestionComments(id: Int) {
        mutableLiveComments.value = id
    }

    val liveComments = Transformations.switchMap(mutableLiveComments) { id ->
        NetworkRepository.getQuestionComments(id)
    }

    data class Entity(
        var subjectId: Int? = null,
        var keywords: String? = null
    )

    private var mutableKeywords = MutableLiveData<Entity>()

    fun getCommendQuestions(subjectId: Int, keywords: String) {
        mutableKeywords.value = Entity(subjectId, keywords)
    }

    val liveCommendQuestions = Transformations.switchMap(mutableKeywords) { obj ->
        NetworkRepository.getCommendQuestions(obj.subjectId!!, obj.keywords!!)
    }

}
