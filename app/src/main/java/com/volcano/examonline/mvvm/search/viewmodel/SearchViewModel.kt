package com.volcano.examonline.mvvm.search.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.volcano.examonline.base.Response
import com.volcano.examonline.mvvm.forum.model.Article
import com.volcano.examonline.mvvm.study.model.Question
import com.volcano.examonline.network.NetworkRepository

class SearchViewModel : ViewModel() {

    lateinit var type: String

    fun searchQuestions(content: String) {
        mutableQuestionContent.value = content
    }

    fun searchArticles(content: String) {
        mutableArticleContent.value = content
    }

    private val mutableQuestionContent = MutableLiveData<String>()
    private val mutableArticleContent = MutableLiveData<String>()

    val liveQuestionResult = Transformations.switchMap(mutableQuestionContent){ content ->
        NetworkRepository.searchQuestion(content)
    }

    val liveArticleResult = Transformations.switchMap(mutableArticleContent) { content ->
        NetworkRepository.searchArticle(content)
    }

    val articlesRes = arrayListOf<Article>()
    val questionsRes = arrayListOf<Question>()

}