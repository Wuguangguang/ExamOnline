package com.volcano.examonline.mvvm.homepage.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.volcano.examonline.mvvm.homepage.model.Subject
import com.volcano.examonline.network.NetworkRepository

class HomepageViewModel : ViewModel() {

    var subjects = arrayListOf<Subject>()

    private var mutableSubjectList = MutableLiveData<Boolean>()

    var subjectList : LiveData<List<Subject>> = Transformations.switchMap(mutableSubjectList){
        NetworkRepository.getInstance().getSubjectList()
    }

    fun getSubjectList() {
        mutableSubjectList.value = true
    }
}