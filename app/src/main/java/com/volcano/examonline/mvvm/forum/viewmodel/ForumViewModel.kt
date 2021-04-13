package com.volcano.examonline.mvvm.forum.viewmodel

import androidx.lifecycle.ViewModel
import com.volcano.examonline.mvvm.forum.model.Forum

class ForumViewModel : ViewModel() {

    var forums = arrayListOf(
            Forum(1, "推荐"),
            Forum(2, "热榜"),
    )

}