package com.volcano.examonline.network

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.volcano.examonline.base.CommentEntity
import com.volcano.examonline.base.Response
import com.volcano.examonline.base.transform
import com.volcano.examonline.mvvm.forum.model.Article
import com.volcano.examonline.mvvm.login.model.TokenBean
import com.volcano.examonline.mvvm.mine.model.UserInfo
import com.volcano.examonline.mvvm.mine.model.UserPwd
import com.volcano.examonline.mvvm.study.model.*
import com.volcano.examonline.util.ConstantData
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.OkHttpClient
import okhttp3.RequestBody
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.io.File
import java.util.concurrent.TimeUnit

object NetworkRepository {

    private val client = OkHttpClient.Builder()
            .connectTimeout(1, TimeUnit.SECONDS)
            .readTimeout(1, TimeUnit.SECONDS)
            .writeTimeout(1, TimeUnit.SECONDS)
            .build()

    private val api : API by lazy {
        Retrofit.Builder()
            .baseUrl("http://192.168.1.107:8080/")
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build()
            .create(API::class.java)
    }


    /**
     * 题库页
     */

    fun getQuestions(name: String): LiveData<Response<List<Question>>> {
        var result = MutableLiveData<Response<List<Question>>>()
        api.getQuestions(name).transform(result)
        return result
    }

    fun searchQuestion(content: String): LiveData<Response<List<Question>>> {
        var result = MutableLiveData<Response<List<Question>>>()
        api.searchQuestion(content).transform(result)
        return result
    }

    fun getQuestionComments(questionId: Int): LiveData<Response<List<Comment>>> {
        var result = MutableLiveData<Response<List<Comment>>>()
        api.getQuestionComments(questionId).transform(result)
        return result
    }

    fun getSubjects(): LiveData<Response<List<Subject>>> {
        val result = MutableLiveData<Response<List<Subject>>>()
        api.getSubjects().transform(result)
        return result
    }

    fun getCommendQuestions(subjectId: Int, questionId: Int, keywords: String): LiveData<Response<List<Question>>> {
        var result = MutableLiveData<Response<List<Question>>>()
        api.getCommendQuestions(subjectId,questionId, keywords).transform(result)
        return result
    }

    fun uploadQuestion(uploadBean: UploadBean): LiveData<Response<Any>> {
        var result = MutableLiveData<Response<Any>>()
        api.uploadQuestion(ConstantData.TOKEN!!, uploadBean).transform(result)
        return result
    }

    fun uploadQuestionComment(obj: CommentEntity): LiveData<Response<Any>> {
        var result = MutableLiveData<Response<Any>>()
        api.uploadQuestionComment(ConstantData.TOKEN!! ,obj).transform(result)
        return result
    }


    /**
     * 论坛页
     */

    fun getArticles(): LiveData<Response<List<Article>>> {
        var result = MutableLiveData<Response<List<Article>>>()
        api.getArticles().transform(result)
        return result
    }

    fun getHotArticles(): LiveData<Response<List<Article>>> {
        var result = MutableLiveData<Response<List<Article>>>()
        api.getHotArticles().transform(result)
        return result
    }

    fun getArticleComments(id: Int): LiveData<Response<List<Comment>>> {
        var result = MutableLiveData<Response<List<Comment>>>()
        api.getArticleComments(id).transform(result)
        return result
    }

    fun uploadArticle(img: File?, obj: Article): LiveData<Response<Any>> {
        val result = MutableLiveData<Response<Any>>()
        val body = if(img == null ) RequestBody.create(MediaType.parse("image/jpg"), "")
            else RequestBody.create(MediaType.parse("image/jpg"), img)
        val photo = MultipartBody.Part.createFormData("file",img?.name ?: "", body)
        val map = HashMap<String, RequestBody>().apply {
            put("title", RequestBody.create(null, obj.title!!))
            put("description", RequestBody.create(null, obj.description!!))
            put("field", RequestBody.create(null, obj.field!!))
        }
        api.uploadArticle(ConstantData.TOKEN!! ,map, photo).transform(result)
        return result
    }

    fun searchArticle(content: String): LiveData<Response<List<Article>>> {
        var result = MutableLiveData<Response<List<Article>>>()
        api.searchArticle(content).transform(result)
        return result
    }


    fun uploadArticleComment(obj: CommentEntity): LiveData<Response<Any>> {
        var result = MutableLiveData<Response<Any>>()
        api.uploadArticleComment(ConstantData.TOKEN!! ,obj).transform(result)
        return result
    }

    fun increaseArticleZan(id: Int): LiveData<Response<Any>> {
        var result = MutableLiveData<Response<Any>>()
        api.increaseArticleZan(id).transform(result)
        return result
    }

    fun decreaseArticleZan(id: Int): LiveData<Response<Any>> {
        var result = MutableLiveData<Response<Any>>()
        api.decreaseArticleZan(id).transform(result)
        return result
    }


    /**
     * 考试页
     */
    fun getRandomQuestions(name:String, num: Int): LiveData<Response<List<Question>>> {
        var result = MutableLiveData<Response<List<Question>>>()
        api.getRandomQuestions(name, num).transform(result)
        return result
    }

    /**
     * 用户相关
     */
    fun getUserInfoById(id: Int): LiveData<Response<UserInfo>> {
        val result = MutableLiveData<Response<UserInfo>>()
        api.getUserInfoById(id).transform(result)
        return result
    }

    fun editUserInfo(obj: UserInfo): LiveData<Response<Any>> {
        var result = MutableLiveData<Response<Any>>()
        api.editUserInfo(ConstantData.TOKEN!!, obj).transform(result)
        return result
    }

    fun uploadAvatar(file: File): LiveData<Response<Any>> {
        var result = MutableLiveData<Response<Any>>()
        val body = RequestBody.create(MediaType.parse("image/jpg"), file)
        val photo = MultipartBody.Part.createFormData("file", file.name, body)
        api.uploadAvatar(ConstantData.TOKEN!!,photo).transform(result)
        return result
    }

    fun editUserPwd(obj: UserPwd): LiveData<Response<Any>> {
        var result = MutableLiveData<Response<Any>>()
        api.editUserPwd(ConstantData.TOKEN!!, obj).transform(result)
        return result
    }

    fun register(userInfo: UserInfo): LiveData<Response<Any>> {
        var result = MutableLiveData<Response<Any>>()
        api.register(userInfo).transform(result)
        return result
    }

    fun login(userInfo: UserInfo): LiveData<Response<TokenBean>> {
        val result = MutableLiveData<Response<TokenBean>>()
        api.login(userInfo).transform(result)
        return result
    }

    fun getMyArticles(): LiveData<Response<List<Article>>> {
        var result = MutableLiveData<Response<List<Article>>>()
        api.getMyArticles(ConstantData.TOKEN!!).transform(result)
        return result
    }

    fun getRanking(): LiveData<Response<List<Ranking>>> {
        var result = MutableLiveData<Response<List<Ranking>>>()
        api.getRanking().transform(result)
        return result
    }


}