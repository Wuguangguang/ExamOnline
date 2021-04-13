package com.volcano.examonline.network

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.volcano.examonline.base.getCode
import com.volcano.examonline.base.transform
import com.volcano.examonline.mvvm.exam.model.Question
import com.volcano.examonline.mvvm.forum.model.Article
import com.volcano.examonline.mvvm.homepage.model.Comment
import com.volcano.examonline.mvvm.homepage.model.Subject
import com.volcano.examonline.mvvm.login.model.TokenBean
import com.volcano.examonline.mvvm.mine.model.UserInfo
import com.volcano.examonline.util.ConstantData
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

class NetworkRepository {

    private val TAG = "NetworkRepository"

    private val api : API by lazy {
        Retrofit.Builder()
            .baseUrl("http://192.168.1.107:8080/")
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build()
            .create(API::class.java)
    }

    companion object {
        private val repository = NetworkRepository()
        fun getInstance() : NetworkRepository {
            return repository
        }
    }

    /**
     * 题库页
     */

    fun getQuestions(id: Int): LiveData<List<Question>> {
        var result = MutableLiveData<List<Question>>()
        api.getQuestions(id).transform(result)
        return result
    }

    fun searchQuestion(content: String): LiveData<List<Question>> {
        var result = MutableLiveData<List<Question>>()
        api.searchQuestion(content).transform(result)
        return result
    }

    fun getQuestionComments(questionId: Int): LiveData<List<Comment>> {
        var result = MutableLiveData<List<Comment>>()
        api.getQuestionComments(questionId).transform(result)
        return result
    }

    fun getQuestionHotKey(): LiveData<List<Any>> {
        var result = MutableLiveData<List<Any>>()
        api.getQuestionHotKey().transform(result)
        return result
    }

    fun getSubjects(): LiveData<List<Subject>> {
        val result = MutableLiveData<List<Subject>>()
        api.getSubjects().transform(result)
        return result
    }


    /**
     * 论坛页
     */

    fun getArticles(): LiveData<List<Article>> {
        var result = MutableLiveData<List<Article>>()
        api.getArticles().transform(result)
        return result
    }

    fun getHotArticles(): LiveData<List<Article>> {
        var result = MutableLiveData<List<Article>>()
        api.getHotArticles().transform(result)
        return result
    }

    fun getArticleComments(id: Int): LiveData<Comment> {
        var result = MutableLiveData<Comment>()
        api.getArticleComments(id).transform(result)
        return result
    }

    fun uploadArticle(obj: Article): LiveData<Int> {
        val result = MutableLiveData<Int>()
        api.uploadArticle(ConstantData.TOKEN!! ,obj).getCode(result)
        return result
    }

    fun getArticleHotKey(): LiveData<List<Any>> {
        var result = MutableLiveData<List<Any>>()
        api.getArticleHotKey().transform(result)
        return result
    }

    /**
     * 考试页
     */


    fun getRandomQuestions(id:Int, num: Int): LiveData<List<Question>> {
        var result = MutableLiveData<List<Question>>()
        api.getRandomQuestions(id, num).transform(result)
        return result
    }

    /**
     * 我的页
     */

    fun register(userInfo: UserInfo): LiveData<Int> {
        var result = MutableLiveData<Int>()
        api.register(userInfo).getCode(result)
        return result
    }

    fun login(userInfo: UserInfo): LiveData<TokenBean> {
        val result = MutableLiveData<TokenBean>()
        api.login(userInfo).transform(result)
        return result
    }

    fun getUserInfo(phone: String): LiveData<UserInfo> {
        val result = MutableLiveData<UserInfo>()
        api.getUserInfo(phone).transform(result)
        return result
    }

}