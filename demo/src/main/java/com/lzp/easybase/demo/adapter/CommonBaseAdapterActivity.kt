package com.lzp.easybase.demo.adapter

import com.lzp.easybase.activity.BaseNavigationActivity
import com.lzp.easybase.adapter.CommonBaseAdapter
import com.lzp.easybase.demo.R
import kotlinx.android.synthetic.main.activity_common_base_adapter.*

class CommonBaseAdapterActivity : BaseNavigationActivity() {

    override fun initView() {
        setBaseContentView(R.layout.activity_common_base_adapter)
        val adapter = CommonBaseAdapter(
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
        list_view.addHeaderView(layoutInflater.inflate(R.layout.header, list_view, false))
        list_view.addFooterView(layoutInflater.inflate(R.layout.footer, list_view, false))
        adapter.registerRecyclerCell(0, String::class.java, StringAdapterCell())
        adapter.registerRecyclerCell(1, Integer::class.java, ImageAdapterCell())
        list_view.adapter = adapter
    }

    override fun initData() {
    }
}
