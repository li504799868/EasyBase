package com.lzp.easybase.view.recyclerview

import android.view.View
import androidx.annotation.NonNull
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager

/**
 * Created by cundong on 2015/10/9.
 *
 *
 * 继承自RecyclerView.OnScrollListener，可以监听到是否滑动到页面最低部
 */
open class EndlessRecyclerOnScrollListener : RecyclerView.OnScrollListener() {

    /**
     * 当前RecyclerView类型
     */
    private var layoutManagerType: LayoutManagerType? = null

    /**
     * 最后一个的位置
     */
    private var lastPositions: IntArray? = null

    /**
     * 最后一个可见的item的位置
     */
    private var lastVisibleItemPosition: Int = 0

    override fun onScrolled(@NonNull recyclerView: RecyclerView, dx: Int, dy: Int) {
        super.onScrolled(recyclerView, dx, dy)

        val layoutManager = recyclerView.layoutManager

        if (layoutManagerType == null) {
            if (layoutManager is GridLayoutManager) {
                layoutManagerType = LayoutManagerType.GridLayout
            } else if (layoutManager is LinearLayoutManager) {
                layoutManagerType = LayoutManagerType.LinearLayout
            } else if (layoutManager is StaggeredGridLayoutManager) {
                layoutManagerType = LayoutManagerType.StaggeredGridLayout
            } else {
                throw RuntimeException(
                    "Unsupported LayoutManager used. Valid ones are LinearLayoutManager, GridLayoutManager and StaggeredGridLayoutManager"
                )
            }
        }

        when (layoutManagerType) {
            LayoutManagerType.LinearLayout -> lastVisibleItemPosition =
                (layoutManager as LinearLayoutManager).findLastVisibleItemPosition()
            LayoutManagerType.GridLayout -> lastVisibleItemPosition =
                (layoutManager as GridLayoutManager).findLastVisibleItemPosition()
            LayoutManagerType.StaggeredGridLayout -> {
                val staggeredGridLayoutManager = layoutManager as StaggeredGridLayoutManager?
                if (lastPositions == null) {
                    lastPositions = IntArray(staggeredGridLayoutManager!!.getSpanCount())
                }
                staggeredGridLayoutManager!!.findLastVisibleItemPositions(lastPositions)
                lastVisibleItemPosition = findMax(lastPositions!!)
            }
        }
    }

    override fun onScrollStateChanged(@NonNull recyclerView: RecyclerView, newState: Int) {
        super.onScrollStateChanged(recyclerView, newState)
        val layoutManager = recyclerView.layoutManager
        val visibleItemCount = layoutManager!!.childCount
        val totalItemCount = layoutManager.itemCount
        // 只有在滚动停止状态才加载下一页
        if (visibleItemCount > 0 && newState == RecyclerView.SCROLL_STATE_IDLE && lastVisibleItemPosition >= totalItemCount - 1 - LOAD_NEXT_PAGE_OFFSET) {
            onLoadNextPage(recyclerView)
        }
    }

    /**
     * 取数组中最大值
     *
     * @param lastPositions
     * @return
     */
    private fun findMax(lastPositions: IntArray): Int {
        var max = lastPositions[0]
        for (value in lastPositions) {
            if (value > max) {
                max = value
            }
        }

        return max
    }

    open fun onLoadNextPage(view: View) {

    }

    enum class LayoutManagerType {
        LinearLayout,
        StaggeredGridLayout,
        GridLayout
    }

    companion object {

        /**
         * 滑动底部的数量的差值
         *
         *
         * 例如当等于1时，加载下一页的时机就是倒数第二个
         */
        private const val LOAD_NEXT_PAGE_OFFSET = 0
    }
}
