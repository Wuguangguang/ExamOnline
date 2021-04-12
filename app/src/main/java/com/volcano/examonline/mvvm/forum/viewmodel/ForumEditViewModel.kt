package com.volcano.examonline.mvvm.forum.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.volcano.examonline.mvvm.forum.model.Article
import com.volcano.examonline.network.NetworkRepository

class ForumEditViewModel : ViewModel() {

    var mutableUploadArticle = MutableLiveData<Article>()

    val uploadArticle: LiveData<Int> = Transformations.switchMap(mutableUploadArticle) {obj ->
        NetworkRepository.getInstance().uploadArticle(obj)
    }

    // 发布讨论文章
    fun uploadArticle(title: String, desc: String) {
        var article = Article()
        article.description = desc
        article.title = title
        mutableUploadArticle.value = article
    }
}