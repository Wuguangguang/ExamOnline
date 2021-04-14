package com.volcano.examonline.mvvm.detail.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.volcano.examonline.mvvm.study.model.Question
import com.volcano.examonline.network.NetworkRepository

class ExamDetailViewModel : ViewModel() {

    data class Entity(
        var id:Int? = null,
        var nums: Int? = null
    )

    var currentPos = MutableLiveData<Int>()

    fun setCurrentPos(pos : Int) {
        currentPos.value = pos
    }

    // 试题列表
    var questions = arrayListOf<Question>()

    private var mutableQuestionNum : MutableLiveData<Entity> = MutableLiveData()

    fun getRandomQuestions(subjectId: Int, num: Int) {
        mutableQuestionNum.value = Entity(subjectId, num)
    }

    fun getQuestions(subject: Int) {
        mutableQuestionNum.value = Entity(subject, -1)
    }

    val question : LiveData<List<Question>> = Transformations.switchMap(mutableQuestionNum) { obj ->
        when(obj.nums) {
            -1 -> NetworkRepository.getInstance().getQuestions(obj.id!!)
            else -> NetworkRepository.getInstance().getRandomQuestions(obj.id!!, obj.nums!!)
        }
    }


}
