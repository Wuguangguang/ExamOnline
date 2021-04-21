package com.volcano.examonline.mvvm.study.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.volcano.examonline.mvvm.study.model.Ranking
import com.volcano.examonline.network.NetworkRepository

class RankingViewModel: ViewModel() {

    var rankings = arrayListOf<Ranking>()

    private var mutableRanking = MutableLiveData<Boolean>()

    val liveRanking = Transformations.switchMap(mutableRanking){ _ ->
        NetworkRepository.getRanking()
    }

    fun getRankings() {
        mutableRanking.value = true
    }
}