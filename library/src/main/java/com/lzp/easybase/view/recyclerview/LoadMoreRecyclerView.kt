package com.lzp.easybase.view.recyclerview

import android.content.Context
import android.util.AttributeSet
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.lzp.easybase.adapter.BaseRecyclerAdapter

/**
 * @author li.zhipeng on 2017/10/19.
 *
 *
 * 底部加载的RecyclerView
 */

class LoadMoreRecyclerView<T : Any> @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyle: Int = 0
) : RecyclerView(context, attrs, defStyle) {

    private var mListDataManager: ListDataManager<T> = ListDataManager()
    private var adapter: BaseRecyclerAdapter<T>? = null
    private var listener: OnLoadMoreListener? = null

    /**
     * 是否开启滑动到底部自动刷新
     */
    private var scrollToLoad = true

    /**
     * 加载更多的Footer
     */
    var loadMoreFooter: LoadMoreFooter? = null

    private// 如果不需要滑动到底部加载，直接返回
    // 如果是正在加载中，不需要加载下一页
    // 如果已经是最后一页，就不需要加载最后一页
    val endlessRecyclerOnScrollListener: EndlessRecyclerOnScrollListener
        get() = object : EndlessRecyclerOnScrollListener() {
            override fun onLoadNextPage(view: View) {
                super.onLoadNextPage(view)
                if (!scrollToLoad) {
                    return
                }
                if (loadMoreFooter != null) {
                    if (loadMoreFooter!!.getLoadStatus() == LoadMoreFooter.LOADING) {
                        return
                    }
                }
                if (mListDataManager.isLastPage) {
                    return
                }

                if (listener != null) {
                    loadMoreFooter!!.showLoadStatus(LoadMoreFooter.LOADING)
                    listener!!.requestPageIndex(mListDataManager.page, mListDataManager.pageTag)
                }
            }

        }

    val data: List<T>?
        get() = mListDataManager.data

    init {

        init()
        setHasFixedSize(true)
        addOnScrollListener(endlessRecyclerOnScrollListener)
    }

    private fun init() {

    }

    override fun setAdapter(adapter: Adapter<*>?) {
        if (adapter is BaseRecyclerAdapter<*>) {
            this.adapter = adapter as BaseRecyclerAdapter<T>
        }
        super.setAdapter(adapter)
    }

    fun onGetData(list: List<T>, page: String) {
        mListDataManager.onGetListComplete(list, page)
        adapter!!.notifyDataSetChanged()
        if (mListDataManager.isLastPage) {
            loadMoreFooter?.showLoadStatus(LoadMoreFooter.LOAD_END)
        } else {
            loadMoreFooter?.showLoadStatus(LoadMoreFooter.LOAD_SUCCESS)
        }
    }

    fun onGetData(list: List<T>, page: String, isLast: Boolean) {
        mListDataManager.onGetListComplete(list, page, isLast)
        adapter!!.notifyDataSetChanged()
        if (isLast) {
            loadMoreFooter?.showLoadStatus(LoadMoreFooter.LOAD_END)
        } else {
            loadMoreFooter?.showLoadStatus(LoadMoreFooter.LOAD_SUCCESS)
        }
    }


    fun setLastPage(list: List<T>, page: Int) {
        mListDataManager.onGetListComplete(list, page)
        adapter!!.notifyDataSetChanged()
        if (mListDataManager.isLastPage) {
            loadMoreFooter?.showLoadStatus(LoadMoreFooter.LOAD_END)
        } else {
            loadMoreFooter?.showLoadStatus(LoadMoreFooter.LOAD_SUCCESS)
        }
    }

    fun setLastPage(list: List<T>, page: Int, isLast: Boolean) {
        mListDataManager.onGetListComplete(list, page)
        adapter!!.notifyDataSetChanged()
        if (isLast) {
            loadMoreFooter?.showLoadStatus(LoadMoreFooter.LOAD_END)
        } else {
            loadMoreFooter?.showLoadStatus(LoadMoreFooter.LOAD_SUCCESS)
        }
    }

    override fun getAdapter(): BaseRecyclerAdapter<T>? {
        return adapter
    }

    fun setListener(listener: OnLoadMoreListener) {
        this.listener = listener
    }

    fun setScrollToLoad(scrollToLoad: Boolean) {
        this.scrollToLoad = scrollToLoad
    }

    interface OnLoadMoreListener {
        fun requestPageIndex(page: Int, i: String)
    }

}
