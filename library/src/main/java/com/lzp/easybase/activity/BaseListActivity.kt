package com.lzp.easybase.activity

import com.lzp.easybase.R
import com.lzp.easybase.adapter.BaseRecyclerCell
import com.lzp.easybase.adapter.CommonRecyclerAdapter
import com.lzp.easybase.view.recyclerview.ListDataManager
import com.lzp.easybase.view.recyclerview.LoadMoreRecyclerView

abstract class BaseListActivity<T : Any> : BasePullToRefreshActivity(), LoadMoreRecyclerView.OnLoadMoreListener {

    protected lateinit var recyclerView: LoadMoreRecyclerView<T>
    protected lateinit var commonRecyclerAdapter: CommonRecyclerAdapter<T>

    override fun initView() {
        setBaseContentView(R.layout.layout_root_list)
        super.initView()
        recyclerView = findViewById(R.id.recycler_view)
        recyclerView.setListener(this)
        recyclerView.setHasFixedSize(true)
        commonRecyclerAdapter = CommonRecyclerAdapter(this, recyclerView.data)
        initRecyclerView()
        recyclerView.adapter = commonRecyclerAdapter
    }

    abstract fun initRecyclerView()

    fun registerRecyclerCell(viewType: Int, clazz: Class<*>, cell: BaseRecyclerCell<*>) {
        commonRecyclerAdapter.registerRecyclerCell(viewType, clazz, cell)
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