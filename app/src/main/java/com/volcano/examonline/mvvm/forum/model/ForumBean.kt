package com.volcano.examonline.mvvm.forum.model

import java.io.Serializable

data class Forum(
    var forumId: Int? = null,
    var name: String? = null
)


/**
 * id : 1
 * userphone : 13072860573
 * username : Jena Dunn
 * title : 念奴娇·北戴河
 * description : 往事越千年，魏武挥鞭，东临碣石有遗篇
 * img : null
 * createat : 2021-04-09T15:18:40.000+00:00
 */

class Article : Serializable {
    var id: Int? = null
    var userid: Int? = null
    var username: String? = null
    var title: String? = null
    var description: String? = null
    var field: String? = null
    var img: String? = null
    var commentnums: Int? = null
    var zannums: Int? = null
    var createat: String? = null
}