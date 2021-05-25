package com.volcano.examonline.mvvm.exam.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.volcano.examonline.base.CommentEntity
import com.volcano.examonline.mvvm.study.model.Question
import com.volcano.examonline.network.NetworkRepository

class ExamViewModel : ViewModel() {

    data class Entity(
        var name:String? = null,
        var nums: Int? = null
    )

    var currentPos = MutableLiveData<Int>()

    fun setCurrentPos(pos : Int) {
        currentPos.value = pos
    }

    // 试题列表
    var questions = arrayListOf<Question>()

    private var mutableQuestion = MutableLiveData<String>()

    fun getRandomQuestions(subject: String, num: Int) {
        mutableQuestion.value = subject
    }

    val liveQuestion = Transformations.switchMap(mutableQuestion) { subject ->
        NetworkRepository.getRandomQuestions(subject, 5)
    }

    val myAnswers = MutableLiveData<HashMap<Int, List<String>>>()

    fun setMyAnswer(currentPos: Int, myAnswer: List<String>) {
        var map = myAnswers.value
        if(map == null) {
            map = HashMap()
        }
        if(myAnswer.isEmpty()) {
            map.remove(currentPos)
        }else {
            map[currentPos] = myAnswer
        }
        myAnswers.value = map
    }




    //发表评论
    private var mutableEditComment = MutableLiveData<CommentEntity>()

    fun editComment(subjectId: Int, comments: String) {
        mutableEditComment.value = CommentEntity(subjectId, "试题", comments, null)
    }

    val liveEditComment = Transformations.switchMap(mutableEditComment) {obj ->
        NetworkRepository.uploadQuestionComment(obj)
    }


}
