package com.lzp.easybase.demo.fragment

import android.os.Handler
import androidx.recyclerview.widget.LinearLayoutManager
import com.lzp.easybase.demo.adapter.StringRecyclerCell
import com.lzp.easybase.fragment.BaseListFragment

class ListFragment : BaseListFragment<Any>() {

    override fun initRecyclerView() {
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        registerRecyclerCell(1, String::class.java, StringRecyclerCell())
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