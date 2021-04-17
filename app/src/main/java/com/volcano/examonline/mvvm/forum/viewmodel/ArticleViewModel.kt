package com.volcano.examonline.mvvm.forum.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.volcano.examonline.mvvm.study.model.Comment
import com.volcano.examonline.network.NetworkRepository

class ArticleViewModel: ViewModel() {

    var comments = arrayListOf<Comment>()

    private var mutableComments = MutableLiveData<Int>()

    fun getArticleComments(id: Int) {
        mutableComments.value = id
    }

    val liveComments: LiveData<List<Comment>> = Transformations.switchMap(mutableComments) {id ->
        NetworkRepository.getInstance().getArticleComments(id)
    }
}