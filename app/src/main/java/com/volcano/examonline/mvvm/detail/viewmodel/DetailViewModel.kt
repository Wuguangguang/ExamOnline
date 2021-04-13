package com.volcano.examonline.mvvm.detail.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.volcano.examonline.mvvm.homepage.model.Comment
import com.volcano.examonline.network.NetworkRepository

class DetailViewModel : ViewModel() {

    var mutableId = MutableLiveData<Int>()

    val observeId: LiveData<Comment> = Transformations.switchMap(mutableId){ id ->
        NetworkRepository.getInstance().getArticleComments(id)
    }

    fun getComments(id: Int) {
        mutableId.value = id
    }


}
