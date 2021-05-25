package com.volcano.examonline.network

import com.volcano.examonline.base.CommentEntity
import com.volcano.examonline.base.Response
import com.volcano.examonline.mvvm.forum.model.Article
import com.volcano.examonline.mvvm.login.model.TokenBean
import com.volcano.examonline.mvvm.mine.model.UserInfo
import com.volcano.examonline.mvvm.mine.model.UserPwd
import com.volcano.examonline.mvvm.study.model.*
import io.reactivex.Observable
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.http.*
import java.io.File

interface API {

    // 获取学科列表
    @GET("api/v1/questions/subject")
    fun getSubjects(): Observable<Response<List<Subject>>>

    // 获取试题列表
    @GET("api/v1/questions")
    fun getQuestions(@Query("subjectName") name: String): Observable<Response<List<Question>>>

    @GET("api/v1/questions/random")
    fun getRandomQuestions(@Query("subjectName")name: String, @Query("num") num: Int): Observable<Response<List<Question>>>

    // 搜索试题
    @GET("api/v1/questions/search")
    fun searchQuestion(@Query("content") content: String): Observable<Response<List<Question>>>

    // 根据试题id搜索评论
    @GET("api/v1/questions/comments")
    fun getQuestionComments(@Query("id") id: Int): Observable<Response<List<Comment>>>

    // 上传试题
    @Headers("Content-Type: application/json;charset=UTF-8")
    @POST("api/v1/questions/edit")
    fun uploadQuestion(@Header("Authorization") token: String,@Body obj: UploadBean): Observable<Response<Any>>

    @GET("api/v1/questions/commend")
    fun getCommendQuestions(@Query("subjectId") id: Int, @Query("keywords") keywords: String): Observable<Response<List<Question>>>

    @Headers("Content-Type: application/json;charset=UTF-8")
    @POST("api/v1/questions/uploadcomment")
    fun uploadQuestionComment(@Header("Authorization") token: String ,@Body obj: CommentEntity): Observable<Response<Any>>


    // 获取文章列表
    @GET("api/v1/articles")
    fun getArticles(): Observable<Response<List<Article>>>

    // 获取热榜文章列表
    @GET("api/v1/articles/hot")
    fun getHotArticles(): Observable<Response<List<Article>>>

    // 根据文章id获取评论列表
    @GET("api/v1/articles/comments")
    fun getArticleComments(@Query("id") id: Int): Observable<Response<List<Comment>>>

    // 上传文章
    @Multipart
    @POST("api/v1/articles/edit")
    @JvmSuppressWildcards
    fun uploadArticle(@Header("Authorization") token: String, @PartMap map: Map<String, RequestBody>, @Part img: MultipartBody.Part): Observable<Response<Any>>

    @GET("api/v1/articles/search")
    fun searchArticle(@Query("content") content: String): Observable<Response<List<Article>>>

    @Headers("Content-Type: application/json;charset=UTF-8")
    @POST("api/v1/articles/uploadcomment")
    fun uploadArticleComment(@Header("Authorization") token: String ,@Body obj: CommentEntity): Observable<Response<Any>>

    @Headers("Content-Type: application/json;charset=UTF-8")
    @POST("api/v1/articles/increaseZan")
    fun increaseArticleZan(@Query("articleId") id: Int) : Observable<Response<Any>>

    @Headers("Content-Type: application/json;charset=UTF-8")
    @POST("api/v1/articles/decreaseZan")
    fun decreaseArticleZan(@Query("articleId") id: Int) : Observable<Response<Any>>

    /**
     * 用户 相关API
     */
    @GET("api/v1/userinfo/id")
    fun getUserInfoById(@Query("param") id: Int): Observable<Response<UserInfo>>

    @Headers("Content-Type: application/json;charset=UTF-8")
    @POST("api/v1/userinfo/edit")
    fun editUserInfo(@Header("Authorization") token: String, @Body obj: UserInfo): Observable<Response<Any>>


    @Multipart
    @POST("api/v1/userinfo/uploadAvatar")
    fun uploadAvatar(@Header("Authorization")token: String, @Part file: MultipartBody.Part): Observable<Response<Any>>

    @Headers("Content-Type: application/json;charset=UTF-8")
    @POST("api/v1/userinfo/pwd")
    fun editUserPwd(@Header("Authorization") token: String, @Body obj: UserPwd): Observable<Response<Any>>

    @Headers("Content-Type: application/json;charset=UTF-8")
    @POST("api/v1/userinfo/register")
    fun register(@Body userinfo: UserInfo): Observable<Response<Any>>

    @Headers("Content-Type: application/json;charset=UTF-8")
    @POST("api/v1/userinfo/login")
    fun login(@Body userinfo: UserInfo): Observable<Response<TokenBean>>

    @GET("api/v1/userinfo/articles")
    fun getMyArticles(@Header("Authorization") token: String): Observable<Response<List<Article>>>

    @GET("api/v1/userinfo/ranking")
    fun getRanking(): Observable<Response<List<Ranking>>>


}