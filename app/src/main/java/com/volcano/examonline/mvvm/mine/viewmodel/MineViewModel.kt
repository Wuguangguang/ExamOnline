package com.volcano.examonline.mvvm.mine.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.volcano.examonline.mvvm.mine.model.UserInfo
import com.volcano.examonline.mvvm.mine.model.UserPwd
import com.volcano.examonline.network.NetworkRepository

class MineViewModel : ViewModel() {

    var mutableId = MutableLiveData<Int>()

    fun getUserInfo(id: Int) {
        mutableId.value = id
    }

    val liveId = Transformations.switchMap(mutableId) {id ->
        NetworkRepository.getUserInfoById(id)
    }

    var editFlag = MutableLiveData<Boolean>()

    fun setEditFlag() {
        editFlag.value = true
    }

    private var mutableUserInfo = MutableLiveData<UserInfo>()

    fun editUserInfo(userInfo: UserInfo) {
        mutableUserInfo.value = userInfo
    }

    val liveUserInfo = Transformations.switchMap(mutableUserInfo) { obj ->
        NetworkRepository.editUserInfo(obj)
    }

    //修改密码
    private var mutableUserPwd = MutableLiveData<UserPwd>()

    fun editUserPwd(origin: String, newPwd: String) {
        mutableUserPwd.value = UserPwd(origin, newPwd)
    }

    val liveUserPwd = Transformations.switchMap(mutableUserPwd) {obj ->
        NetworkRepository.editUserPwd(obj)
    }
}