package com.volcano.examonline.mvvm.forum.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.volcano.examonline.mvvm.forum.model.Article
import com.volcano.examonline.network.NetworkRepository

class ForumDetailViewModel : ViewModel() {

    inner class Entity(val page : Int, val id : Int)

    var articles = arrayListOf<Article>()

    private val mutableArticlePage = MutableLiveData<Entity>()

    val articlePage : LiveData<List<Article>> = Transformations.switchMap(mutableArticlePage) { obj ->
        NetworkRepository.getInstance().getArticles(obj.page, obj.id)
    }

    fun getArticles(page: Int, id: Int) {
        val entity = Entity(page, id)
        mutableArticlePage.value = entity
    }
}