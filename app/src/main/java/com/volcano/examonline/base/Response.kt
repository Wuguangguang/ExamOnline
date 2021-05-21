package com.volcano.examonline.base

data class Response<T>(
    var code : Int? = null,
    var msg : String? = null,
    var data : T? = null
)

data class CommentEntity(
        var targetId: Int? = null,
        var type: String? = null,
        var description: String? = null,
        var img: String? = null
)