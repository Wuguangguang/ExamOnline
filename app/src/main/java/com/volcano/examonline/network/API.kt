package com.volcano.examonline.network

import com.volcano.examonline.base.Response
import com.volcano.examonline.mvvm.forum.model.Article
import com.volcano.examonline.mvvm.study.model.Comment
import com.volcano.examonline.mvvm.study.model.Subject
import com.volcano.examonline.mvvm.login.model.TokenBean
import com.volcano.examonline.mvvm.mine.model.UserInfo
import com.volcano.examonline.mvvm.study.model.Question
import com.volcano.examonline.mvvm.study.model.Ranking
import io.reactivex.Observable
import retrofit2.http.*

interface API {

    /**
     * 题库页 考试页 相关API
     */
    // 获取学科列表
    @GET("api/v1/subjects")
    fun getSubjects(): Observable<Response<List<Subject>>>

    // 获取试题列表
    @GET("api/v1/questions")
    fun getQuestions(@Query("subjectId") id: Int): Observable<Response<List<Question>>>

    // 搜索试题
    @GET("api/v1/question")
    fun searchQuestion(@Query("content") content: String): Observable<Response<List<Question>>>

    // 根据试题id搜索评论
    @GET("api/v1/question/comment")
    fun getQuestionComments(@Query("id") id: Int): Observable<Response<List<Comment>>>

    // 搜索框热词
    @GET("api/v1/question/hotkey")
    fun getQuestionHotKey() : Observable<Response<List<Any>>>

    // 上传试题
    @Headers("Content-Type: application/json;charset=UTF-8")
    @POST("api/v1/question/edit")
    fun uploadQuestion(@Header("Authorization") token: String,@Body obj: Question): Observable<Response<Any>>

    @GET("api/v1/question/random")
    fun getRandomQuestions(@Query("subjectId") id: Int, @Query("num") num: Int): Observable<Response<List<Question>>>

    @GET("api/v1/question/ranking")
    fun getRanking(): Observable<Response<List<Ranking>>>

    /**
     * 论坛页 相关API
     */

    // 获取文章列表
    @GET("api/v1/articles")
    fun getArticles(): Observable<Response<List<Article>>>

    // 获取热榜文章列表
    @GET("api/v1/articles/hot")
    fun getHotArticles(): Observable<Response<List<Article>>>

    // 根据文章id获取评论列表
    @GET("api/v1/article")
    fun getArticleComments(@Query("id") id: Int): Observable<Response<List<Comment>>>

    // 上传文章
    @Headers("Content-Type: application/json;charset=UTF-8")
    @POST("api/v1/article/edit")
    fun uploadArticle(@Header("Authorization") token: String,@Body obj: Article): Observable<Response<Any>>

    // 搜索框热词
    @GET("api/v1/article/hotkey")
    fun getArticleHotKey() : Observable<Response<List<Any>>>

    /**
     * 我的页 相关API
     */

    // 注册
    @Headers("Content-Type: application/json;charset=UTF-8")
    @POST("api/v1/userinfo/register")
    fun register(@Body userinfo: UserInfo): Observable<Response<Any>>

    // 登录
    @Headers("Content-Type: application/json;charset=UTF-8")
    @POST("api/v1/userinfo/login")
    fun login(@Body userinfo: UserInfo): Observable<Response<TokenBean>>

    // 根据phone获取用户信息
    @GET("api/v1/userinfo")
    fun getUserInfo(@Query("phone") phone: String): Observable<Response<UserInfo>>
}