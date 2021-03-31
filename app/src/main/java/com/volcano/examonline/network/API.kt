package com.volcano.examonline.network

import com.volcano.examonline.base.ArticleBean
import com.volcano.examonline.base.ArticleListBean
import com.volcano.examonline.base.BaseResponse
import com.volcano.examonline.base.Response
import com.volcano.examonline.mvvm.exam.model.Banner
import com.volcano.examonline.mvvm.exam.model.Hotkey
import com.volcano.examonline.mvvm.homepage.model.Chapter
import com.volcano.examonline.mvvm.homepage.model.QuestionList
import com.volcano.examonline.mvvm.homepage.model.Subject
import io.reactivex.Observable
import retrofit2.http.*

interface API {

    @GET("subjectList")
    fun getSubjectList() : Observable<Response<List<Subject>>>

    @GET("subject")
    fun getQuestionList(@Query("subjectId") subjectId : Int) : Observable<Response<QuestionList>>

    // 首页文章列表
    @GET("article/list/{page}/json")
    fun getArticleList(@Path("page") page : Int) : Observable<BaseResponse<ArticleListBean>>

    // 首页banner
    @GET("banner/json")
    fun getBannerList() : Observable<BaseResponse<List<Banner>>>

    // 常用网站
    @GET("friend/json")
    fun getCommonSites()

    //搜索热词
    @GET("hotkey/json")
    fun getSearchWords() : Observable<BaseResponse<List<Hotkey>>>

    //置顶文章
    @GET("article/top/json")
    fun getTopArticles() : Observable<BaseResponse<List<ArticleBean>>>

    //公众号列表
    @GET("wxarticle/chapters/json")
    fun getWxChapters() : Observable<BaseResponse<List<Chapter>>>

    //获取某公众号文章列表
    @GET("wxarticle/list/{id}/{page}/json")
    fun getWxArticles(@Path("id") id : Int, @Path("page") page : Int) : Observable<BaseResponse<ArticleListBean>>

//    //项目分类列表
//    @GET("project/tree/json")
//    fun getProjectTrees() : Observable<BaseResponse<List<ProjectTree>>>

    //项目列表数据
    @GET("project/list/{page}/json")
    fun getProjectArticles(@Path("page") page: Int, @Query("cid") id: Int) : Observable<BaseResponse<ArticleListBean>>

    @POST("article/query/{page}/json")
    @FormUrlEncoded
    fun getSearchResult(@Path("page") page: Int, @Field("k") key: String) : Observable<BaseResponse<ArticleListBean>>
}