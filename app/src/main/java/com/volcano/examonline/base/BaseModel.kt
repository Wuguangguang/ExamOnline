package com.volcano.examonline.base

data class BaseResponse<T>(
    var errorCode : Int? = null,
    var errorMsg : String? = null,
    var data : T? = null
)

/*
/*
{   "data": {
        "curPage": 2,
        "datas": [
            {
                "apkLink": "",
                "audit": 1,
                "author": "yangchong211",
                "canEdit": false,
                "chapterId": 539,
                "chapterName": "未分类",
                "collect": false,
                "courseId": 13,
                "desc": "基于腾讯x5封源库，提高webView开发效率，大概要节约你百分之六十的时间成本。该案例支持处理js的交互逻辑且无耦合、同时暴露进度条加载进度、可以监听异常error状态、支持视频播放并且可以全频、支持加载word，xls，ppt，pdf，txt等文件文档、发短信、打电话、发邮件、打开文件操作上传图片、唤起原生App、x5库为更新版本，功能强大。",
                "descMd": "",
                "envelopePic": "https://www.wanandroid.com/resources/image/pc/default_project_img.jpg",
                "fresh": false,
                "host": "",
                "id": 17070,
                "link": "https://www.wanandroid.com/blog/show/2924",
                "niceDate": "2天前",
                "niceShareDate": "2天前",
                "origin": "",
                "prefix": "",
                "projectLink": "https://github.com/yangchong211/YCWebView",
                "publishTime": 1611676302000,
                "realSuperChapterId": 293,
                "selfVisible": 0,
                "shareDate": 1611676302000,
                "shareUser": "",
                "superChapterId": 294,
                "superChapterName": "开源项目主Tab",
                "tags": [{
                    "name": "项目",
                    "url": "/project/list/1?cid=539"
                }],
                "title": "基于腾讯x5开源库，提高webView开发效率，大概要节约你百分之六十的时间成本。",
                "type": 0,
                "userId": -1,
                "visible": 1,
                "zan": 0
            },
        ],
        "offset": 20,
        "over": false,
        "pageCount": 498,
        "size": 20,
        "total": 9948
    },
    "errorCode": 0,
    "errorMsg": ""
}
*/
 */
data class ArticleListBean(
        var curPage : Int? = null,
        var datas : List<ArticleBean>? = null,
        var offset : Int? = null,
        var over : Boolean? = null,
        var pageCount : Int? = null,
        var size : Int? = null,
        var total: Int? = null
)
data class ArticleBean(
    var apkLink: String? = null,
    var audit: Int? = null,
    var author: String? = null,
    var isCanEdit: Boolean? = null,
    var chapterId: Int? = null,
    var chapterName: String? = null,
    var isCollect: Boolean? = null,
    var courseId: Int? = null,
    var desc: String? = null,
    var descMd: String? = null,
    var envelopePic: String? = null,
    var isFresh: Boolean? = null,
    var host: String? = null,
    var id: Int? = null,
    var link: String? = null,
    var niceDate: String? = null,
    var niceShareDate: String? = null,
    var origin: String? = null,
    var prefix: String? = null,
    var projectLink: String? = null,
    var publishTime: Long? = null,
    var realSuperChapterId: Int? = null,
    var selfVisible: Int? = null,
    var shareDate: Long? = null,
    var shareUser: String? = null,
    var superChapterId: Int? = null,
    var superChapterName: String? = null,
    var title: String? = null,
    var type: Int? = null,
    var userId: Int? = null,
    var visible: Int? = null,
    var zan: Int? = null,
    var tags: List<TagBean>? = null
)
data class TagBean (
    var name: String? = null,
    var url: String? = null
)