package com.lzp.easybase.fragment

import com.lzp.easybase.R
import kotlinx.android.synthetic.main.layout_root_list.*

/***
 *  @author  li.zhipeng on 2019.6.14
 *
 *      可以下拉刷新并且可以加载下一页的列表Fragment
 */
class BaseListFragment : BasePullToRefreshFragment() {

    override fun initLayout(): Int = R.layout.layout_root_list

    override fun initView() {
        super.initView()
    }

    open fun initRecyclerView(){
    }

}