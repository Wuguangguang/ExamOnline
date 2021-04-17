package com.volcano.examonline.mvvm.mine.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.volcano.examonline.R
import com.volcano.examonline.mvvm.mine.model.FooterEntity
import com.volcano.examonline.mvvm.mine.model.UserInfo
import com.volcano.examonline.network.NetworkRepository

class MineViewModel : ViewModel() {

    var mutablePhone = MutableLiveData<String>()

    val phone: LiveData<UserInfo> = Transformations.switchMap(mutablePhone) {phone ->
        NetworkRepository.getInstance().getUserInfo(phone)
    }

    // 获取用户信息
    fun getUserInfo(phone: String) {
        mutablePhone.value = phone
    }

}