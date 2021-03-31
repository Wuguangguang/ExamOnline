package com.volcano.examonline.network

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.volcano.examonline.base.ArticleBean
import com.volcano.examonline.base.ArticleListBean
import com.volcano.examonline.base.transform
import com.volcano.examonline.mvvm.exam.model.Banner
import com.volcano.examonline.mvvm.exam.model.Hotkey
import com.volcano.examonline.mvvm.homepage.model.Chapter
import com.volcano.examonline.mvvm.homepage.model.Question
import com.volcano.examonline.mvvm.homepage.model.QuestionList
import com.volcano.examonline.mvvm.homepage.model.Subject
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

class NetworkRepository {

    private val TAG = "NetworkRepository"

    private val api : API by lazy {
        Retrofit.Builder()
            .baseUrl("https://www.wanandroid.com/")
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

    fun getArticleList(page : Int, id : Int, flag : Int) : MutableLiveData<ArticleListBean> {
        val result = MutableLiveData<ArticleListBean>()
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

    fun getTopArticles() : MutableLiveData<List<ArticleBean>> {
        val result = MutableLiveData<List<ArticleBean>>()
        api.getTopArticles().transform(result)
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

//    fun getProjectTrees() : MutableLiveData<List<ProjectTree>> {
//        var result = MutableLiveData<List<ProjectTree>>()
//        api.getProjectTrees().transform(result)
//        return result
//    }

    fun getSearchWords() : LiveData<List<Hotkey>> {
        var result = MutableLiveData<List<Hotkey>>()
        api.getSearchWords().transform(result)
        return result
    }

    fun getSearchResult(page: Int, key: String) : LiveData<ArticleListBean> {
        var result = MutableLiveData<ArticleListBean>()
        api.getSearchResult(page,key).transform(result)
        return result
    }

    fun getSubjectList(): LiveData<List<Subject>> {
        var result = MutableLiveData<List<Subject>>()
//        api.getSubjectList().trans(result)
        result.value = subjects
        return result
    }

    fun getQuestionList(page: Int, subjectId: Int): LiveData<QuestionList> {
        var result = MutableLiveData<QuestionList>()
//        api.getQuestionList(page, subjectId)
        result.value = QuestionList(questions, 1, 3, 10)
        return result
    }

    private val subjects = arrayListOf(
        Subject(1,"计算机网络"),
        Subject(2,"数据结构"),
        Subject(3,"算法"),
        Subject(4,"Java"),
        Subject(5,"Android"),
        Subject(6,"软件工程"),
        Subject(7,"数据库系统概论")
    )

    private val questions = arrayListOf(
        Question(1,"寻找两个正序数组的中位数","单选题","请选择正确答案",null,null,"困难","《计算机网络》2015-16春)",null),
        Question(1,"寻找两个正序数组的中位数","单选题","请选择正确答案",null,null,"困难","《计算机网络》2015-16春)",null),
        Question(1,"寻找两个正序数组的中位数","单选题","请选择正确答案",null,null,"困难","《计算机网络》2015-16春)",null),
        Question(1,"寻找两个正序数组的中位数","单选题","请选择正确答案",null,null,"困难","《计算机网络》2015-16春)",null),
        Question(1,"寻找两个正序数组的中位数","单选题","请选择正确答案",null,null,"困难","《计算机网络》2015-16春)",null),
        Question(1,"寻找两个正序数组的中位数","单选题","请选择正确答案",null,null,"困难","《计算机网络》2015-16春)",null),
        Question(1,"寻找两个正序数组的中位数","单选题","请选择正确答案",null,null,"困难","《计算机网络》2015-16春)",null),
        Question(1,"寻找两个正序数组的中位数","单选题","请选择正确答案",null,null,"困难","《计算机网络》2015-16春)",null),
        Question(1,"寻找两个正序数组的中位数","单选题","请选择正确答案",null,null,"困难","《计算机网络》2015-16春)",null),
        Question(1,"寻找两个正序数组的中位数","单选题","请选择正确答案",null,null,"困难","《计算机网络》2015-16春)",null)
    )
}