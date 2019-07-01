package com.lzp.easybase.view.recyclerview

/**
 *  @author  li.zhipeng on 2019/06/17
 *
 *          滑动到底部的Footer，显示加载过程的各种状态
 * */
abstract class LoadMoreFooter {

    companion object{

        const val NORMAL = 0

        const val LOADING = 1

        const val LOAD_SUCCESS = 2

        const val LOAD_FAILED = 3

        /**
         * 此状态表示加载已经结束，不会再出发加载下一页的动作，显示已经是最后一页的状态
         * */
        const val LOAD_END = 4
    }

    private var currentStatus = NORMAL

    /**
     *  返回目前的加载状态
     * */
    fun getLoadStatus() = currentStatus

    /**
     *  显示Footer的各种状态
     * */
    fun showLoadStatus(status: Int) {
        this.currentStatus = status
        when (status) {
            NORMAL -> showLoadNormal()
            LOADING -> showLoading()
            LOAD_SUCCESS -> showLoadSuccess()
            LOAD_FAILED -> showLoadFailed()
            LOAD_END -> showLoadEnd()
        }
    }

    abstract fun showLoadNormal()

    abstract fun showLoading()

    abstract fun showLoadSuccess()

    abstract fun showLoadFailed()

    abstract fun showLoadEnd()

}