package com.volcano.examonline.mvvm.forum.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.volcano.examonline.mvvm.forum.model.Article
import com.volcano.examonline.network.NetworkRepository
import java.io.File

class ArticleUploadViewModel : ViewModel() {

    data class Entity(
        var article: Article? = null,
        var img: File? = null,
    )

    var mutableUploadArticle = MutableLiveData<Entity>()

    val uploadArticle = Transformations.switchMap(mutableUploadArticle) {obj ->
        NetworkRepository.uploadArticle(if(obj.img == null) null else obj.img!! , obj.article!!)
    }

    // 发布讨论文章
    fun uploadArticle(title: String, desc: String, field: String, img: File?) {
        var entity = Entity()
        var article = Article()
        article.description = desc
        article.title = title
        article.field = field
        entity.article = article
        if(img != null) {
            entity.img = img
        }
        mutableUploadArticle.value = entity
    }
}