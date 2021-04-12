package com.volcano.examonline.mvvm.mine.model

data class FooterEntity(
    var drawableId: Int? = null,
    var text: Int? = null,
)

data class UserInfo(
    var id: Int? = null,
    var phone: String? = null,
    var username: String? = null,
    var pwd: String? = null,
    var avatar: String?? = null,
    var createat: String?? = null
)