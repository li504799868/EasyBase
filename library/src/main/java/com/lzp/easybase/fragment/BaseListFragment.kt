package com.lzp.easybase.fragment

import com.lzp.easybase.R
import com.lzp.easybase.adapter.BaseRecyclerCell
import com.lzp.easybase.adapter.CommonRecyclerAdapter
import com.lzp.easybase.view.recyclerview.ListDataManager
import com.lzp.easybase.view.recyclerview.LoadMoreRecyclerView
import kotlinx.android.synthetic.main.layout_root_list.*

/***
 *  @author  li.zhipeng on 2019.6.14
 *
 *      可以下拉刷新并且可以加载下一页的列表Fragment
 */
abstract class BaseListFragment<T : Any> : BasePullToRefreshFragment(), LoadMoreRecyclerView.OnLoadMoreListener {

    protected lateinit var recyclerView: LoadMoreRecyclerView<T>
    protected lateinit var commonRecyclerAdapter: CommonRecyclerAdapter<T>

    override fun initLayout(): Int = R.layout.layout_root_list

    override fun initView() {
        super.initView()
        recyclerView = findViewById(R.id.recycler_view)!!
        recyclerView.setListener(this)
        recyclerView.setHasFixedSize(true)
        commonRecyclerAdapter = CommonRecyclerAdapter(requireContext(), recyclerView.data)
        initRecyclerView()
        recyclerView.adapter = commonRecyclerAdapter
    }

    abstract fun initRecyclerView()

    fun registerRecyclerCell(viewType: Int, clazz: Class<*>, cell: BaseRecyclerCell<*>) {
        commonRecyclerAdapter.registerRecyclerCell(viewType, clazz, cell)
    }

    override fun lazyLoad() {
        super.lazyLoad()
        onRefresh()
    }

    override fun onRefresh() {
        requestPageIndex(1, ListDataManager.FIRST_PAGE)
    }

    fun onGetData(list: List<T>, page: String) {
        recyclerView.onGetData(list, page)
        refreshComplete()
    }

    fun onGetData(list: List<T>, page: String, isLast: Boolean) {
        recyclerView.onGetData(list, page, isLast)
        refreshComplete()
    }


    fun onGetData(list: List<T>, page: Int) {
        recyclerView.onGetData(list, page)
        refreshComplete()
    }

    fun onGetData(list: List<T>, page: Int, isLast: Boolean) {
        recyclerView.onGetData(list, page, isLast)
        refreshComplete()
    }

}