package com.lzp.easybase.demo.adapter

import androidx.recyclerview.widget.LinearLayoutManager
import com.lzp.easybase.activity.BaseNavigationActivity
import com.lzp.easybase.adapter.CommonRecyclerAdapter
import com.lzp.easybase.demo.R
import kotlinx.android.synthetic.main.activity_common_adapter.*

class CommonAdapterActivity : BaseNavigationActivity() {

    override fun initView() {
        setBaseContentView(R.layout.activity_common_adapter)

        recyclerView.layoutManager = LinearLayoutManager(this)
        val adapter = CommonRecyclerAdapter(
            this,
            arrayListOf(
                "1111", "2222", "3333", "4444", "5555", R.mipmap.ic_launcher_round,
                "6666", "7777", "8888", "9999", "0000", R.mipmap.ic_launcher_round,
                "aaaa", "bbbb", "cccc", "dddd", "eeee", R.mipmap.ic_launcher_round,
                "ffff", "gggg", "hhhh", "iiii", "jjjj", R.mipmap.ic_launcher_round
            )
//            arrayListOf(
//                R.mipmap.ic_launcher_round,
//                R.mipmap.ic_launcher_round,
//                R.mipmap.ic_launcher_round,
//                R.mipmap.ic_launcher_round
//            )

        )
        adapter.addHeaderView(layoutInflater.inflate(R.layout.header, recyclerView, false))
        adapter.addFooterView(layoutInflater.inflate(R.layout.footer, recyclerView, false))
        adapter.registerRecyclerCell(0, String::class.java, StringRecyclerCell())
        adapter.registerRecyclerCell(1, Integer::class.java, ImageRecyclerCell())
        recyclerView.adapter = adapter
    }

    override fun initData() {
    }
}
