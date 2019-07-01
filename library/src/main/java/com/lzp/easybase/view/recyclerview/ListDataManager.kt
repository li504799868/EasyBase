package com.lzp.easybase.view.recyclerview

import java.util.ArrayList

/**
 * @author lizhipeng on 2019/6/17
 * @date 16/10/25
 *
 *
 * 列表数据和页表管理器，包含刷新，增加下一页，删除等操作
 */
class ListDataManager<T> {

    var isLastPage: Boolean = false

    /**
     * 当前的页数，String类型，如果是第一页。默认返回
     */
    var pageTag = FIRST_PAGE
        private set

    /**
     * 当前的页数，用于解决分页int 和 String 的问题
     */
    var page = 1
        private set

    private var mListener: ListDataListener<*>? = null

    var compareListener: ItemCompareListener<T>? = null

    var data: MutableList<T>? = ArrayList()

    internal val count: Int
        get() = data!!.size

    fun setListener(listener: ListDataListener<T>) {
        mListener = listener
    }

    fun refreshAllDatas(): String {
        page = 1
        return FIRST_PAGE
    }

    private fun addResultFinally(result: List<T>) {
        data!!.addAll(result)
    }

    internal fun reset() {
        data!!.clear()
        page = 1
        pageTag = FIRST_PAGE
        this.isLastPage = true
    }

    /**
     * 使用参数isLastPage控制是否是最后一页
     */
    @JvmOverloads
    fun onGetListComplete(rsp: List<T>?, pageTag: String, isLastPage: Boolean = rsp == null || rsp.isEmpty()) {
        this.pageTag = pageTag

        // 如果是第一页，要清除之前的数据
        if (FIRST_PAGE == pageTag) {
            //刷新
            data!!.clear()
        }
        //加载到末尾
        addData(rsp, isLastPage)
    }

    /**
     * 使用参数isLastPage控制是否是最后一页
     */
    @JvmOverloads
    fun onGetListComplete(rsp: List<T>?, page: Int, isLastPage: Boolean = rsp == null || rsp.isEmpty()) {
        this.page = page
        if (1 == page) {
            //刷新
            data!!.clear()
        }
        //加载到末尾
        addData(rsp, isLastPage)
    }

    private fun addData(rsp: List<T>?, isLastPage: Boolean) {
        //加载到末尾
        if (rsp != null && rsp.isNotEmpty()) {
            deduplication(data, rsp)
            addResultFinally(rsp)
        }
        this.isLastPage = isLastPage
    }

    private fun deduplication(
        source: MutableList<T>?,
        targets: List<T>?
    ) {
        if (compareListener == null) {
            return
        }
        if (source == null || source.isEmpty()) {
            return
        }
        if (targets == null || targets.isEmpty()) {
            return
        }
        for (targetInfo in targets) {
            for (itemInfo in source) {
                if (compareListener!!.isItemsEqual(targetInfo, itemInfo)) {
                    source.remove(targetInfo)
                    break
                }
            }
        }
    }

    fun hasData(): Boolean {
        return data != null && data!!.isNotEmpty()
    }

    interface ItemCompareListener<T> {
        fun isItemsEqual(a: T, b: T): Boolean
    }

    abstract class ListDataListener<T> {
        abstract fun getItemId(a: T): String
    }

    companion object {

        /**
         * 默认返回的第一页的String类型的页码
         */
        private const val FIRST_PAGE = "first"
    }
}
