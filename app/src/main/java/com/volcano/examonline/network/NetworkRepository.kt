package com.volcano.examonline.network

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.volcano.examonline.base.getCode
import com.volcano.examonline.base.transform
import com.volcano.examonline.mvvm.exam.model.Banner
import com.volcano.examonline.mvvm.exam.model.Hotkey
import com.volcano.examonline.mvvm.exam.model.Question
import com.volcano.examonline.mvvm.forum.model.Article
import com.volcano.examonline.mvvm.homepage.model.Chapter
import com.volcano.examonline.mvvm.homepage.model.QuestionList
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
        const val ARTICLE_HOMEPAGE = 0x1
        const val ARTICLE_WEXIN = 0x2
        const val ARTICLE_PROJECT = 0x4
        fun getInstance() : NetworkRepository {
            return repository
        }
    }

    fun getArticleList(page : Int, id : Int, flag : Int) : MutableLiveData<Article> {
        val result = MutableLiveData<Article>()
        when (flag) {
            ARTICLE_HOMEPAGE -> {
                api.getArticleList(page).transform(result)
            }
            ARTICLE_WEXIN -> {
                api.getWxArticles(id, page).transform(result)
            }
            ARTICLE_PROJECT -> {
                api.getProjectArticles(page, id).transform(result)
            }
        }
        return result
    }


    fun getBannerList() : MutableLiveData<List<Banner>> {
        val result = MutableLiveData<List<Banner>>()
        api.getBannerList().transform(result)
        return result
    }

    fun getWxChapters() : MutableLiveData<List<Chapter>> {
        var result = MutableLiveData<List<Chapter>>()
        api.getWxChapters().transform(result)
        return result
    }

    fun getSearchWords() : LiveData<List<Hotkey>> {
        var result = MutableLiveData<List<Hotkey>>()
        api.getSearchWords().transform(result)
        return result
    }

    fun getSearchResult(page: Int, key: String) : LiveData<Article> {
        var result = MutableLiveData<Article>()
        api.getSearchResult(page,key).transform(result)
        return result
    }

    fun getSubjectList(): LiveData<List<Subject>> {
        var result = MutableLiveData<List<Subject>>()
        api.getSubjectList().transform(result)
        return result
    }

    fun getQuestionList(page: Int, subjectId: Int): LiveData<QuestionList> {
        var result = MutableLiveData<QuestionList>()
        api.getQuestionList().transform(result)
        return result
    }

    fun getRandomQuestions(num: Int): LiveData<List<Question>> {
        var result = MutableLiveData<List<Question>>()
        api.getRandomQuestions(num).transform(result)
        return result
    }

    //获取全部文章
    fun getArticles(page: Int, id: Int): LiveData<List<Article>> {
        var result = MutableLiveData<List<Article>>()
        api.getArticles().transform(result)
        return result
    }

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

    fun getUserInfo(phone: String): LiveData<UserInfo>? {
        val result = MutableLiveData<UserInfo>()
        api.getUserInfo(phone).transform(result)
        return result
    }

    fun uploadArticle(obj: Article): LiveData<Int> {
        val result = MutableLiveData<Int>()
        api.uploadArticle(ConstantData.TOKEN!! ,obj).getCode(result)
        return result
    }
}