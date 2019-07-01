package com.lzp.easybase.fragment

import `in`.srain.cube.views.ptr.PtrDefaultHandler
import `in`.srain.cube.views.ptr.PtrFrameLayout
import `in`.srain.cube.views.ptr.PtrUIHandlerHook
import com.lzp.easybase.R
import com.lzp.easybase.util.NetUtil

/***
 * @author li.zhipeng on 2019.6.14
 *
 * 可以下拉刷新的Fragment
 *
 */
open class BasePullToRefreshFragment : BaseFragment() {

    companion object {
        private const val REFRESH_COMPLETE_DELAY = 1000L
    }

    protected var ptrFrameLayout: PtrFrameLayout? = null

    override fun initLayout(): Int = R.layout.layout_root_pull_to_refresh

    override fun initView() {
        super.initView()
        initPtrFrameLayout()
    }

    open fun initPtrFrameLayout() {
        ptrFrameLayout = findViewById(R.id.ptr_frame_layout)
        if (ptrFrameLayout != null) {
            ptrFrameLayout!!.setPtrHandler(object : PtrDefaultHandler() {
                override fun onRefreshBegin(frame: PtrFrameLayout) {
                    // 如果有网络，刷新数据
                    if (NetUtil.isNetConnected(context)) {
                        lazyLoad()
                    } else {
                        ptrFrameLayout!!.postDelayed({
                            ptrFrameLayout!!.refreshComplete()
                        }, REFRESH_COMPLETE_DELAY)
                    }
                }
            })
            ptrFrameLayout!!.isPullToRefresh = true
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

}