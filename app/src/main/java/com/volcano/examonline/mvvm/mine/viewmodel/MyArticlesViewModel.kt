package com.volcano.examonline.mvvm.mine.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.volcano.examonline.mvvm.forum.model.Article
import com.volcano.examonline.network.NetworkRepository

class MyArticlesViewModel: ViewModel() {

    var articles = arrayListOf<Article>()

    private val mutableArticle = MutableLiveData<Boolean>()

    fun getMyArticles() {
        mutableArticle.value =  true
    }

    val liveArticle = Transformations.switchMap(mutableArticle) { _ ->
        NetworkRepository.getMyArticles()
    }
}