package com.volcano.examonline.mvvm.homepage.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.volcano.examonline.mvvm.homepage.model.Question
import com.volcano.examonline.mvvm.homepage.model.QuestionList
import com.volcano.examonline.network.NetworkRepository

class HomepageDetailViewModel : ViewModel() {

    class Entity(val page : Int, val id : Int)

    var questions = arrayListOf<Question>()

    private val mutableQuestionPage = MutableLiveData<Entity>()

    fun getWxArticles(page: Int, id: Int) {
        val entity = Entity(page,id)
        mutableQuestionPage.value = entity
    }

    var questionPage : LiveData<QuestionList> = Transformations.switchMap(mutableQuestionPage){ obj ->
        NetworkRepository.getInstance().getQuestionList(obj.page,obj.id)
    }

}