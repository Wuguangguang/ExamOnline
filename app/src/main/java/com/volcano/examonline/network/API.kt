package com.volcano.examonline.network

import com.volcano.examonline.base.Response
import com.volcano.examonline.mvvm.exam.model.Banner
import com.volcano.examonline.mvvm.exam.model.Hotkey
import com.volcano.examonline.mvvm.exam.model.Question
import com.volcano.examonline.mvvm.forum.model.Article
import com.volcano.examonline.mvvm.homepage.model.Chapter
import com.volcano.examonline.mvvm.homepage.model.QuestionList
import com.volcano.examonline.mvvm.homepage.model.Subject
import io.reactivex.Observable
import retrofit2.http.*

interface API {

    @GET("subject.json")
    fun getSubjectList() : Observable<Response<List<Subject>>>

    @GET("questionList.json")
    fun getQuestionList() : Observable<Response<QuestionList>>

    // 首页文章列表
    @GET("article/list/{page}/json")
    fun getArticleList(@Path("page") page : Int) : Observable<Response<Article>>

    // 首页banner
    @GET("banner/json")
    fun getBannerList() : Observable<Response<List<Banner>>>

    // 常用网站
    @GET("friend/json")
    fun getCommonSites()

    //搜索热词
    @GET("hotkey/json")
    fun getSearchWords() : Observable<Response<List<Hotkey>>>

    //置顶文章
    @GET("article/top/json")
    fun getTopArticles() : Observable<Response<List<Article>>>

    //公众号列表
    @GET("wxarticle/chapters/json")
    fun getWxChapters() : Observable<Response<List<Chapter>>>

    //获取某公众号文章列表
    @GET("wxarticle/list/{id}/{page}/json")
    fun getWxArticles(@Path("id") id : Int, @Path("page") page : Int) : Observable<Response<Article>>

//    //项目分类列表
//    @GET("project/tree/json")
//    fun getProjectTrees() : Observable<BaseResponse<List<ProjectTree>>>

    //项目列表数据
    @GET("project/list/{page}/json")
    fun getProjectArticles(@Path("page") page: Int, @Query("cid") id: Int) : Observable<Response<Article>>

    @POST("article/query/{page}/json")
    @FormUrlEncoded
    fun getSearchResult(@Path("page") page: Int, @Field("k") key: String) : Observable<Response<Article>>

    @GET("questions/random")
    fun getRandomQuestions(@Query("num") num: Int): Observable<Response<List<Question>>>

    @GET("articles")
    fun getArticles(): Observable<Response<List<Article>>>
}