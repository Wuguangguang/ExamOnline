package com.volcano.examonline.util

import android.app.Activity

object ActivityCollector {

    private val activities = arrayListOf<Activity>()

    fun addActivity(activity: Activity) {
        activities.add(activity)
    }

    fun removeActivity(activity: Activity) {
        activities.remove(activity)
    }

    fun finishAll() {
        activities.forEach {
            if(!it.isFinishing) {
                it.finish()
            }
        }
        activities.clear()
    }
}