package com.volcano.examonline.mvvm.exam.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.volcano.examonline.mvvm.study.model.Question

class CommitResultViewModel: ViewModel() {

    var questions = arrayListOf<Question>()


    var myAnswers = HashMap<Int, List<String>>()

    // 计算正确率，给出List<Int> results数组
    fun calculateAccuracy() {
        var count :Int = 0
        var list = ArrayList<Int>()
        val answers = myAnswers
        for(i in questions.indices) {
            if(answers!![i].isNullOrEmpty()){
                list.add(0)
            }else if (questions[i].type == "多选题") {
                    val corrects = questions[i].correctanswer!!.split("")
                    var flag = true
                    for (j in corrects.indices) {
                        if (!answers[i]!!.contains(corrects[j])) {
                            flag = false
                            break
                        }
                    }
                    if (answers[i]!!.size > corrects.size) {
                        flag = false
                    }
                    list.add(if(flag) 1 else 0)
            } else if (!questions[i].correctanswer.equals(answers!![i]!![0])) {
                    list.add(0)
            } else {
                list.add(1)
                count++
            }
        }
        results.value = list
        correctCount.value = count
    }

    var results = MutableLiveData<ArrayList<Int>>()

    var correctCount = MutableLiveData<Int>()

}