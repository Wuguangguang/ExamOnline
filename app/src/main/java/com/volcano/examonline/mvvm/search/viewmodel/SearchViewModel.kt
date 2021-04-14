package com.volcano.examonline.mvvm.search.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.volcano.examonline.mvvm.forum.model.Article

class SearchViewModel : ViewModel() {

    private val mutableHotkeyFlag = MutableLiveData<Boolean>()

//    val hotkeys = arrayListOf<Hotkey>()

//    val hotkeyFlag : LiveData<List<Hotkey>> = Transformations.switchMap(mutableHotkeyFlag) {
//        NetworkRepository.getInstance().getSearchWords()
//    }

    fun getSearchWords() {
        mutableHotkeyFlag.value = true
    }

    class Entity(val page:Int, val key: String)

    fun getSearchResult(key : String) {
        mutableSearchKey.value =
            Entity(
                0,
                key
            )
    }

    private val mutableSearchKey = MutableLiveData<Entity>()

//    val searchKey : LiveData<Article> = Transformations.switchMap(mutableSearchKey){ obj ->
//        NetworkRepository.getInstance().getSearchResult(obj.page,obj.key)
//    }

    val results = arrayListOf<Article>()
}