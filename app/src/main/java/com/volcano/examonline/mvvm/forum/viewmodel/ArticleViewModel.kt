package com.volcano.examonline.mvvm.forum.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.volcano.examonline.base.CommentEntity
import com.volcano.examonline.mvvm.study.model.Comment
import com.volcano.examonline.network.NetworkRepository

class ArticleViewModel: ViewModel() {

    var comments = arrayListOf<Comment>()

    private var mutableComments = MutableLiveData<Int>()

    fun getArticleComments(id: Int) {
        mutableComments.value = id
    }

    val liveComments = Transformations.switchMap(mutableComments) {id ->
        NetworkRepository.getArticleComments(id)
    }

    //发表评论
    private var mutableEditComment = MutableLiveData<CommentEntity>()

    fun editComment(targetId: Int, comments: String) {
        mutableEditComment.value = CommentEntity(targetId, "文章", comments, null)
    }

    val liveEditComment = Transformations.switchMap(mutableEditComment) {obj ->
        NetworkRepository.uploaArticleComment(obj)
    }
}