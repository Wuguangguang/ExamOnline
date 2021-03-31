package com.volcano.examonline.mvvm.exam.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.volcano.examonline.base.ArticleBean
import com.volcano.examonline.base.ArticleListBean
import com.volcano.examonline.network.NetworkRepository

class ExamListViewModel : ViewModel() {

    private var mutableArticleListPage : MutableLiveData<Int> = MutableLiveData()

    fun getArticleList(page : Int) {
        mutableArticleListPage.value = page
    }

    var articleList : LiveData<ArticleListBean> = Transformations.switchMap(mutableArticleListPage){ page ->
        NetworkRepository.getInstance().getArticleList(page,0, NetworkRepository.ARTICLE_HOMEPAGE)
    }

    var articles = ArrayList<ArticleBean>()


}