package com.lzp.easybase.activity

import `in`.srain.cube.views.ptr.PtrDefaultHandler
import `in`.srain.cube.views.ptr.PtrFrameLayout
import `in`.srain.cube.views.ptr.PtrUIHandlerHook
import com.lzp.easybase.R
import com.lzp.easybase.util.NetUtil

/***
 * @author li.zhipeng on 2019.6.14
 *
 * 可以下拉刷新的Activity
 *
 */
abstract class BasePullToRefreshActivity : BaseNavigationActivity() {

    companion object {
        private const val REFRESH_COMPLETE_DELAY = 1000L
    }

    protected var ptrFrameLayout: PtrFrameLayout? = null

    override fun initView() {
        initPtrFrameLayout()
    }

    open fun initPtrFrameLayout() {
        ptrFrameLayout = findViewById(R.id.ptr_frame_layout)
        if (ptrFrameLayout != null) {
            ptrFrameLayout!!.setPtrHandler(object : PtrDefaultHandler() {
                override fun onRefreshBegin(frame: PtrFrameLayout) {
                    // 如果有网络，刷新数据
                    if (NetUtil.isNetConnected(this@BasePullToRefreshActivity)) {
                        onRefresh()
                    } else {
                        ptrFrameLayout!!.postDelayed({
                            refreshComplete()
                        }, REFRESH_COMPLETE_DELAY)
                    }
                }
            })
            ptrFrameLayout!!.isPullToRefresh = false
            ptrFrameLayout!!.isKeepHeaderWhenRefresh = true
            ptrFrameLayout!!.setRefreshCompleteHook(object : PtrUIHandlerHook() {
                override fun run() {
                    ptrFrameLayout!!.postDelayed({
                        resume()
                    }, REFRESH_COMPLETE_DELAY)
                }
            })
            ptrFrameLayout!!.disableWhenHorizontalMove(true)
        }
    }

    fun refreshComplete() {
        ptrFrameLayout?.refreshComplete()
    }

    abstract fun onRefresh()

}