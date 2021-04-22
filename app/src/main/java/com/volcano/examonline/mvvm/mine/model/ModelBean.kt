package com.volcano.examonline.mvvm.mine.model

data class UserInfo(
    var id: Int? = null,
    var phone: String? = null,
    var username: String? = null,
    var pwd: String? = null,
    var accumulate: Int? = null,
    var createat: String?? = null,
    var avatar: String? = null
)

data class UserPwd(
    var oldPwd: String?=null,
    var newPwd: String?=null
)