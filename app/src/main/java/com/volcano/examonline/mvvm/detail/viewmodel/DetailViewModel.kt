package com.volcano.examonline.mvvm.detail.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.volcano.examonline.mvvm.study.model.Comment
import com.volcano.examonline.mvvm.study.model.Question
import com.volcano.examonline.network.NetworkRepository

class DetailViewModel : ViewModel() {

    var questions = arrayListOf<Question>()

    var mutableQuestions = MutableLiveData<ArrayList<Question>>()

    var myAnswers = arrayListOf<String>()

    var mutableId = MutableLiveData<Int>()

//    val observeId: LiveData<Comment> = Transformations.switchMap(mutableId){ id ->
//        NetworkRepository.getInstance().getArticleComments(id)
//    }

    fun getComments(id: Int) {
        mutableId.value = id
    }


}
