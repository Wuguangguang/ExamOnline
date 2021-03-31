package com.volcano.examonline.base

data class Response<T>(
    var code : Int? = null,
    var message : String? = null,
    var data : T? = null
)