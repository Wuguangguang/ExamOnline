package com.volcano.examonline.mvvm.forum.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.volcano.examonline.base.ArticleBean
import com.volcano.examonline.base.ArticleListBean
import com.volcano.examonline.network.NetworkRepository

class ForumDetailViewModel : ViewModel() {

    inner class Entity(val page : Int, val id : Int)

    var articles = arrayListOf<ArticleBean>()

    private val mutableArticlePage = MutableLiveData<Entity>()

    val articlePage : LiveData<ArticleListBean> = Transformations.switchMap(mutableArticlePage) { obj ->
        NetworkRepository.getInstance().getArticleList(obj.page, obj.id, NetworkRepository.ARTICLE_HOMEPAGE)
    }

    fun getProjectArticles(page: Int, id : Int) {
        val entity = Entity(page, id)
        mutableArticlePage.value = entity
    }
}