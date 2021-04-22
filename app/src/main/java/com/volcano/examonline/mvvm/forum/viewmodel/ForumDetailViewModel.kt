package com.volcano.examonline.mvvm.forum.viewmodel

import android.net.Network
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.volcano.examonline.mvvm.forum.model.Article
import com.volcano.examonline.network.NetworkRepository

class ForumDetailViewModel : ViewModel() {


    var articles = arrayListOf<Article>()

    private val mutableArticlePage = MutableLiveData<Int>()

    val articlePage = Transformations.switchMap(mutableArticlePage) {id ->
        if(id == 1) NetworkRepository.getArticles() else NetworkRepository.getHotArticles()
    }

    fun getArticles(mId: Int) {
        mutableArticlePage.value = mId
    }

}