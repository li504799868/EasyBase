package com.lzp.base.activity

import android.os.Bundle
import android.support.annotation.LayoutRes
import android.support.v7.app.AppCompatActivity
import android.view.KeyEvent
import android.view.View
import android.view.ViewGroup
import com.lzp.base.R
import com.lzp.base.fragment.BaseFragment
import com.lzp.base.view.RootLoadingLayout

/**
 * Created by li.zhipeng on 2019-06-05.
 *
 *      Activity的最低基类
 */
abstract class BaseActivity : AppCompatActivity() {

    protected lateinit var rootView: ViewGroup

    /**
     * 是否需要点击返回有提示
     * */
    protected var showBackTip = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // 初始化View
        initView()
        // 初始化数据
        initData()
    }

    /**
     * 调用此方法会把内容布局设置在RootLoadingLayout中，
     * 如果不需要RootLoadingLayout，请调用[setContentView]删除RootLoadingLayout
     * 此方法和[setContentView]请选择其中一个
     * */
    fun setBaseContentView(@LayoutRes id: Int) {
        setContentView(R.layout.layout_root)
        rootView = findViewById(R.id.root_loading_layout)
        (rootView as RootLoadingLayout).setContentView(id)
    }

    /**
     * 调用此方法会把内容布局设置在RootLoadingLayout中，
     * 如果不需要RootLoadingLayout，请调用[setContentView]删除RootLoadingLayout
     * 此方法和[setContentView]请选择其中一个
     * */
    fun setBaseContentView(view: View) {
        setContentView(R.layout.layout_root)
        rootView = findViewById(R.id.root_loading_layout)
        (rootView as RootLoadingLayout).setContentView(view)
    }

    override fun setContentView(layoutResID: Int) {
        super.setContentView(layoutResID)
        rootView = findViewById(android.R.id.content)
    }

    override fun setContentView(view: View) {
        super.setContentView(view)
        rootView = findViewById(android.R.id.content)
    }

    override fun setContentView(view: View, params: ViewGroup.LayoutParams) {
        super.setContentView(view, params)
        rootView = findViewById(android.R.id.content)
    }

    /**
     * 初始化View
     * */
    abstract fun initView()

    /**
     * 初始化数据
     * */
    abstract fun initData()

    /**
     * 如果需要点击返回有提示，会回调此方法进行操作
     * */
    protected fun confirmBack() {}

    /**
     * 移除所有的Fragment
     * */
    protected fun removeAllFragment() {
        val fm = supportFragmentManager
        val fragments = fm.fragments
        val ft = fm.beginTransaction()
        for (fragment in fragments) {
            if (fragment != null) {
                ft.remove(fragment)
            }
        }
        ft.commit()
        fm.executePendingTransactions()
    }


    /**
     * 重写finish方法，让Fragment可以响应finish方法
     * */
    override fun finish() {
        val fm = supportFragmentManager
        val fragments = fm.fragments
        if (fragments.size > 0) {
            for (f in fragments) {
                if (f is BaseFragment) {
                    f.finish()
                }
            }
        }
        super.finish()
    }

    /**
     * 重写返回键方法，优先内部Fragment消耗掉此事件
     * */
    override fun onBackPressed() {

        // Fragment的消耗优先于Activity
        // 如果被Fragment消耗掉，事件结束
        val fm = supportFragmentManager
        val fragments = fm.fragments
        if (fragments.size > 0) {
            for (f in fragments) {
                if (f is BaseFragment) {
                    if (f.onBackPress()) {
                        return
                    }
                }
            }
        }

        // activity得到返回事件
        if (showBackTip) {
            confirmBack()
        } else {
            super.onBackPressed()
        }
    }

    override fun onKeyDown(keyCode: Int, event: KeyEvent): Boolean {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (showBackTip) {
                confirmBack()
                return true
            }
        }
        return super.onKeyDown(keyCode, event)

    }

}