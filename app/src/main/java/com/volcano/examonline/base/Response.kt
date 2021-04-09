package com.volcano.examonline.base

data class Response<T>(
    var code : Int? = null,
    var msg : String? = null,
    var data : T? = null
)