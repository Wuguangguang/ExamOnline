package com.volcano.examonline.mvvm.forum.viewmodel

import androidx.lifecycle.ViewModel
import com.volcano.examonline.mvvm.forum.model.Forum

class ForumViewModel : ViewModel() {

    var forums = arrayListOf<Forum>(
            Forum(1, "关注"),
            Forum(2, "推荐"),
            Forum(3, "热榜"),
    )

}