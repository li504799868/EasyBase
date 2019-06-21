package com.lzp.easybase.demo

import androidx.recyclerview.widget.LinearLayoutManager
import com.lzp.easybase.activity.BaseNavigationActivity
import com.lzp.easybase.adapter.BaseRecyclerAdapter
import com.lzp.easybase.demo.adapter.ImageRecyclerCell
import com.lzp.easybase.demo.adapter.StringRecyclerCell
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : BaseNavigationActivity() {

    override fun initView() {
        setBaseContentView(R.layout.activity_main)

        recyclerView.layoutManager = LinearLayoutManager(this)
        val adapter = BaseRecyclerAdapter(
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
        adapter.registerRecyclerCell(1, String::class.java, StringRecyclerCell())
        adapter.registerRecyclerCell(2, Integer::class.java, ImageRecyclerCell())
        recyclerView.adapter = adapter
    }

    override fun initData() {
    }

}
