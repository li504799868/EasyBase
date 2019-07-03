package com.lzp.easybase.demo

import android.os.Handler
import androidx.recyclerview.widget.LinearLayoutManager
import com.lzp.easybase.activity.BaseListActivity
import com.lzp.easybase.demo.adapter.StringAdapterCell

/**
 *
 * */
class ListActivity : BaseListActivity<Any>() {

    override fun initRecyclerView() {
        recyclerView.layoutManager = LinearLayoutManager(this)
        registerRecyclerCell(1, String::class.java, StringAdapterCell())
    }

    override fun initData() {
        onRefresh()
    }

    override fun requestPageIndex(page: Int, i: String) {
        Handler().postDelayed({
            onGetData(
                arrayListOf(
                    "1111", "2222", "3333", "4444", "5555",
                    "6666", "7777", "8888", "9999", "0000",
                    "aaaa", "bbbb", "cccc", "dddd", "eeee",
                    "ffff", "gggg", "hhhh", "iiii", "jjjj"
                ), page
            )
        }, 1000)
    }


}
