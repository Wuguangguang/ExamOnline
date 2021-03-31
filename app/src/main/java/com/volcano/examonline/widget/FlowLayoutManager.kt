package com.volcano.examonline.widget

import android.graphics.Rect
import android.util.SparseArray
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.Recycler
import java.util.*

/**
 * 流式布局
 */
class FlowLayoutManager : RecyclerView.LayoutManager() {
    private val self: FlowLayoutManager = this
    private var flowWidth = 0
    private var flowHeight = 0
    private var left = 0
    private var top = 0
    private var right = 0

    //最大容器的宽度
    private var usedMaxWidth = 0

    //竖直方向上的偏移量
    private var verticalScrollOffset = 0

    //计算显示的内容的高度
    private var totalHeight = 0

    private var row = Row()
    private val lineRows: MutableList<Row> = ArrayList()

    //保存所有的Item的上下左右的偏移量信息
    private val allItemFrames = SparseArray<Rect>()

    //每个item的定义
    inner class Item(
        var useHeight: Int,
        var view: View,
        var rect: Rect
    )

    //行信息的定义
    inner class Row {

        //每一行的头部坐标
        var cuTop = 0f

        //每一行需要占据的最大高度
        var maxHeight = 0f

        //每一行存储的item
        var views: MutableList<Item> =
            ArrayList()

        fun addViews(view: Item) {
            views.add(view)
        }
    }

    override fun generateDefaultLayoutParams(): RecyclerView.LayoutParams {
        return RecyclerView.LayoutParams(
            ViewGroup.LayoutParams.WRAP_CONTENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )
    }

    //该方法主要用来获取每一个item在屏幕上占据的位置
    override fun onLayoutChildren(
        recycler: Recycler,
        state: RecyclerView.State
    ) {
        totalHeight = 0
        var cuLineTop = top
        //当前行使用的宽度
        var cuLineWidth = 0
        var itemLeft: Int
        var itemTop: Int
        var maxHeightItem = 0
        row = Row()
        lineRows.clear()
        allItemFrames.clear()
        removeAllViews()
        if (itemCount == 0) {
            detachAndScrapAttachedViews(recycler)
            verticalScrollOffset = 0
            return
        }
        if (childCount == 0 && state.isPreLayout) {
            return
        }
        //onLayoutChildren方法在RecyclerView 初始化时 会执行两遍
        detachAndScrapAttachedViews(recycler)
        if (childCount == 0) {
            flowWidth = width
            flowHeight = height
            left = paddingLeft
            right = paddingRight
            top = paddingTop
            usedMaxWidth = flowWidth - left - right
        }
        for (i in 0 until itemCount) {
            val childAt = recycler.getViewForPosition(i)
            if (View.GONE == childAt.visibility) {
                continue
            }
            measureChildWithMargins(childAt, 0, 0)
            val childWidth = getDecoratedMeasuredWidth(childAt)
            val childHeight = getDecoratedMeasuredHeight(childAt)
            //如果加上当前的item还小于最大的宽度的话就增加，否则就换行
            if (cuLineWidth + childWidth <= usedMaxWidth) {
                itemLeft = left + cuLineWidth
                itemTop = cuLineTop
                var frame = allItemFrames[i]
                if (frame == null) {
                    frame = Rect()
                }
                frame[itemLeft, itemTop, itemLeft + childWidth] = itemTop + childHeight
                allItemFrames.put(i, frame)
                cuLineWidth += childWidth
                maxHeightItem = maxHeightItem.coerceAtLeast(childHeight)
                row.addViews(
                    Item(
                        childHeight,
                        childAt,
                        frame
                    )
                )
                row.cuTop = cuLineTop.toFloat()
                row.maxHeight = maxHeightItem.toFloat()
            } else {
                //换行
                formatAboveRow()
                cuLineTop += maxHeightItem
                totalHeight += maxHeightItem
                itemTop = cuLineTop
                itemLeft = left
                var frame = allItemFrames[i]
                if (frame == null) {
                    frame = Rect()
                }
                frame[itemLeft, itemTop, itemLeft + childWidth] = itemTop + childHeight
                allItemFrames.put(i, frame)
                cuLineWidth = childWidth
                maxHeightItem = childHeight
                row.addViews(
                    Item(
                        childHeight,
                        childAt,
                        frame
                    )
                )
                row.cuTop = (cuLineTop.toFloat())
                row.maxHeight = (maxHeightItem.toFloat())
            }
            //不要忘了最后一行进行刷新下布局
            if (i == itemCount - 1) {
                formatAboveRow()
                totalHeight += maxHeightItem
            }
        }
        totalHeight = Math.max(totalHeight, verticalSpace)
        fillLayout(recycler, state)
    }

    //对出现在屏幕上的item进行展示，超出屏幕的item回收到缓存中
    private fun fillLayout(recycler: Recycler, state: RecyclerView.State) {
        if (state.isPreLayout || itemCount == 0) { // 跳过preLayout，preLayout主要用于支持动画
            return
        }

        // 当前scroll offset状态下的显示区域
        val displayFrame = Rect(
            paddingLeft,
            paddingTop + verticalScrollOffset,
            width - paddingRight,
            verticalScrollOffset + (height - paddingBottom)
        )

        //对所有的行信息进行遍历
        for (j in lineRows.indices) {
            val row = lineRows[j]
            val lineTop = row.cuTop
            val lineBottom = lineTop + row.maxHeight
            val views: List<Item> =
                row.views
            for (i in views.indices) {
                val scrap = views[i].view
                measureChildWithMargins(scrap, 0, 0)
                addView(scrap)
                val frame = views[i].rect
                //将这个item布局出来
                layoutDecoratedWithMargins(
                    scrap,
                    frame.left,
                    frame.top - verticalScrollOffset,
                    frame.right,
                    frame.bottom - verticalScrollOffset
                )
            }
        }
    }

    /**
     * 计算每一行没有居中的viewgroup，让居中显示
     */
    private fun formatAboveRow() {
        val views =
            row.views
        for (i in views.indices) {
            val item = views[i]
            val view = item.view
            val position = getPosition(view)
            //如果该item的位置不在该行中间位置的话，进行重新放置
            if (allItemFrames[position].top < row.cuTop + (row.maxHeight - views[i].useHeight) / 2) {
                var frame = allItemFrames[position]
                if (frame == null) {
                    frame = Rect()
                }
                frame[allItemFrames[position].left, (row.cuTop + (row.maxHeight - views[i].useHeight) / 2).toInt(), allItemFrames[position].right] =
                    (row.cuTop + (row.maxHeight - views[i].useHeight) / 2 + getDecoratedMeasuredHeight(
                        view
                    )).toInt()
                allItemFrames.put(position, frame)
                item.rect = frame
                views[i] = item
            }
        }
        row.views = views
        lineRows.add(row)
        row = Row()
    }

    /**
     * 竖直方向需要滑动的条件
     *
     * @return
     */
    override fun canScrollVertically(): Boolean {
        return true
    }

    //监听竖直方向滑动的偏移量
    override fun scrollVerticallyBy(
        dy: Int, recycler: Recycler,
        state: RecyclerView.State
    ): Int {
        //实际要滑动的距离
        var travel = dy

        //如果滑动到最顶部
        if (verticalScrollOffset + dy < 0) { //限制滑动到顶部之后，不让继续向上滑动了
            travel = -verticalScrollOffset //verticalScrollOffset=0
        } else if (verticalScrollOffset + dy > totalHeight - verticalSpace) { //如果滑动到最底部
            travel =
                totalHeight - verticalSpace - verticalScrollOffset //verticalScrollOffset=totalHeight - getVerticalSpace()
        }

        //将竖直方向的偏移量+travel
        verticalScrollOffset += travel

        // 平移容器内的item
        offsetChildrenVertical(-travel)
        fillLayout(recycler, state)
        return travel
    }

    private val verticalSpace: Int
        get() = self.height - self.paddingBottom - self.paddingTop

    val horizontalSpace: Int
        get() = self.width - self.paddingLeft - self.paddingRight


    init {
        //设置主动测量规则,适应recyclerView高度为wrap_content
        isAutoMeasureEnabled = true
    }
}