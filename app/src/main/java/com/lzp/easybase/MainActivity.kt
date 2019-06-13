package com.lzp.easybase

import androidx.recyclerview.widget.LinearLayoutManager
import com.lzp.base.activity.BaseNavigationActivity
import com.lzp.base.adapter.BaseRecyclerAdapter
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : BaseNavigationActivity() {

    override fun initView() {
        setBaseContentView(R.layout.activity_main)

        recyclerView.layoutManager = LinearLayoutManager(this)
        val adapter = BaseRecyclerAdapter(
            this,
            arrayListOf(
                "1111", "1111", "1111", "1111", "1111", R.mipmap.ic_launcher_round,
                "1111", "1111", "1111", "1111", "1111", R.mipmap.ic_launcher_round,
                "1111", "1111", "1111", "1111", "1111", R.mipmap.ic_launcher_round,
                "1111", "1111", "1111", "1111", "1111", R.mipmap.ic_launcher_round
            )
//            arrayListOf(
//                R.mipmap.ic_launcher_round,
//                R.mipmap.ic_launcher_round,
//                R.mipmap.ic_launcher_round,
//                R.mipmap.ic_launcher_round
//            )

        )
        adapter.registerRecyclerCell(String::class.java, StringRecyclerCell())
        adapter.registerRecyclerCell(Integer::class.java, ImageRecyclerCell())
        recyclerView.adapter = adapter
    }

    override fun initData() {
    }

}
