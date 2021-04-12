package com.volcano.examonline.mvvm.login.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.volcano.examonline.mvvm.login.model.TokenBean
import com.volcano.examonline.mvvm.mine.model.UserInfo
import com.volcano.examonline.network.NetworkRepository


class LoginViewModel : ViewModel() {

    private var mutableRegisterFlag = MutableLiveData<UserInfo>()

    val registerFlag : LiveData<Int> = Transformations.switchMap(mutableRegisterFlag) { obj ->
        NetworkRepository.getInstance().register(obj)
    }

    fun register(phone: String, username: String, pwd: String) {
        mutableRegisterFlag.value = UserInfo(null, phone, username, pwd, null, null)
    }

    private var mutableLoginFlag = MutableLiveData<UserInfo>()

    val loginFlag:LiveData<TokenBean> = Transformations.switchMap(mutableLoginFlag) { obj ->
        NetworkRepository.getInstance().login(obj)
    }

    fun login(phone: String, pwd: String) {
        mutableLoginFlag.value = UserInfo(null, phone, null, pwd, null, null)
    }






}