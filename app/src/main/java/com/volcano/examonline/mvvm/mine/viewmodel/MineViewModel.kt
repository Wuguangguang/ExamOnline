package com.volcano.examonline.mvvm.mine.viewmodel

import androidx.lifecycle.ViewModel
import com.volcano.examonline.R
import com.volcano.examonline.mvvm.mine.model.FooterEntity

class MineViewModel : ViewModel() {

    val footers = arrayListOf(
        FooterEntity(R.drawable.ic_message_black_24dp,R.string.mine_rank),
        FooterEntity(R.drawable.ic_collections_black_24dp,R.string.mine_collect),
        FooterEntity(R.drawable.ic_account_blog_black_24dp,R.string.mine_blog),
        FooterEntity(R.drawable.ic_baseline_history_24,R.string.mine_history),
        FooterEntity(R.drawable.ic_bug_report_black_24dp,R.string.mine_juejin),
        FooterEntity(R.drawable.ic_github_black_24dp,R.string.mine_github),
        FooterEntity(R.drawable.ic_account_circle_black_24dp,R.string.mine_exit)
    )
}