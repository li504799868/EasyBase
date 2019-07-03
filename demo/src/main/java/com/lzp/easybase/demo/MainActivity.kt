package com.lzp.easybase.demo

import android.content.Intent
import com.lzp.easybase.activity.BaseNavigationActivity
import com.lzp.easybase.demo.adapter.CommonBaseAdapterActivity
import com.lzp.easybase.demo.adapter.CommonRecyclerAdapterActivity
import com.lzp.easybase.demo.fragment.ListFragmentActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : BaseNavigationActivity() {

    override fun initView() {
        setBaseContentView(R.layout.activity_main)

        adapter.setOnClickListener {
            startActivity(Intent(this@MainActivity, CommonRecyclerAdapterActivity::class.java))
        }

        base_adapter.setOnClickListener {
            startActivity(Intent(this@MainActivity, CommonBaseAdapterActivity::class.java))
        }

        list_activity.setOnClickListener {
            startActivity(Intent(this@MainActivity, ListActivity::class.java))
        }

        list_fragment.setOnClickListener {
            startActivity(Intent(this@MainActivity, ListFragmentActivity::class.java))
        }

    }

    override fun initData() {
    }

}
