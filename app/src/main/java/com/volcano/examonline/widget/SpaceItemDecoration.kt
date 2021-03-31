package com.volcano.examonline.widget

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ItemDecoration

class SpaceItemDecoration
@JvmOverloads constructor(
    private val space: Int,
    private val spaceStart: Int = -1,
    private val spaceTop: Int = -1,
    private val spaceEnd: Int = -1,
    private val spaceBottom: Int = -1,
) : ItemDecoration() {
    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        outRect.top = if (spaceTop == -1) space else spaceTop
        outRect.left = if (spaceStart == -1) space else spaceStart
        outRect.right = if (spaceEnd == -1) space else spaceEnd
        outRect.bottom = if (spaceBottom == -1) space else spaceBottom
    }

}