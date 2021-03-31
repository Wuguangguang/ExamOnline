package com.volcano.examonline.mvvm.exam.viewmodel

import androidx.lifecycle.*
import com.volcano.examonline.mvvm.exam.model.Banner
import com.volcano.examonline.mvvm.exam.model.Exam
import com.volcano.examonline.network.NetworkRepository

class ExamViewModel : ViewModel() {

    val tabs = arrayListOf(
            Exam(1, "考试列表"),
            Exam(2, "竞赛排名")
    )


    fun getBannerList() {
        mutableBannerList.value = 0
    }

    private var mutableBannerList : MutableLiveData<Int> = MutableLiveData()

    var bannerList : LiveData<List<Banner>> = Transformations.switchMap(mutableBannerList){ value ->
        NetworkRepository.getInstance().getBannerList()
    }

    var banners = ArrayList<Banner>()

}