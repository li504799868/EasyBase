package com.lzp.easybase.demo

import android.content.Intent
import androidx.recyclerview.widget.LinearLayoutManager
import com.lzp.easybase.CommonAdapterActivity
import com.lzp.easybase.activity.BaseNavigationActivity
import com.lzp.easybase.adapter.CommonRecyclerAdapter
import com.lzp.easybase.demo.adapter.ImageRecyclerCell
import com.lzp.easybase.demo.adapter.StringRecyclerCell
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : BaseNavigationActivity() {

    override fun initView() {
        setBaseContentView(R.layout.activity_main)

        adapter.setOnClickListener {
            startActivity(Intent(this@MainActivity, CommonAdapterActivity::class.java))
        }

        list_activity.setOnClickListener {
            startActivity(Intent(this@MainActivity, ListActivity::class.java))
        }

    }

    override fun initData() {
    }

}
