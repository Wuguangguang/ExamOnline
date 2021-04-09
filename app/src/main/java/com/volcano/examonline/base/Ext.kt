package com.volcano.examonline.base

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.lifecycle.MutableLiveData
import io.reactivex.Observable
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import java.lang.reflect.ParameterizedType

fun <T> Observable<Response<T>>.transform(result : MutableLiveData<T>) {
    this.map {
        it.data!!
    }
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe(object : Observer<T> {
            override fun onSubscribe(d: Disposable) {
            }
            override fun onNext(t: T) {
                result.value = t
            }
            override fun onError(e: Throwable) {
                e.printStackTrace()
            }
            override fun onComplete() {
            }
        })
}

/**
 * 隐藏软键盘
 */
fun hideSoftInput(view: View, context: Context) {
    val methodManager = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    methodManager.hideSoftInputFromWindow(
        view.windowToken,
        InputMethodManager.HIDE_NOT_ALWAYS
    )
}

fun <VB> getVbClazz(obj: Any) : VB {
    return (obj.javaClass.genericSuperclass as ParameterizedType).actualTypeArguments[0] as VB
}

fun <T> Class<T>.inflateMethod() = getMethod("inflate",LayoutInflater::class.java)

fun <T> Class<T>.inflateMethods() = getMethod("inflate", LayoutInflater::class.java, ViewGroup::class.java, Boolean::class.java)

fun <VM> getVmClazz(obj: Any) : VM {
    return (obj.javaClass.genericSuperclass as ParameterizedType).actualTypeArguments[1] as VM
}

