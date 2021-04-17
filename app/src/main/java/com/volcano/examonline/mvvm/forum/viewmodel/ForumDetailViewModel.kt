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

    private val mutableArticlePage = MutableLiveData<Boolean>()

    val articlePage : LiveData<List<Article>> = Transformations.switchMap(mutableArticlePage) {
        NetworkRepository.getInstance().getArticles()
    }

    fun getArticles() {
        mutableArticlePage.value = true
    }

    fun getHotArticles() {

    }
}