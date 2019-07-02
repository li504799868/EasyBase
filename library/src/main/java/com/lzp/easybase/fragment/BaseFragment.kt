package com.lzp.easybase.fragment

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.IdRes
import androidx.fragment.app.Fragment
import com.lzp.easybase.R
import com.lzp.easybase.view.RootLoadingLayout

/**
 * Created by li.zhipeng on 2019-06-05.
 *
 *      Fragment的基类
 */
abstract class BaseFragment : Fragment(), RootLoadingLayout.OnRootLoadingLayoutListener {

    protected lateinit var rootView: View

    protected lateinit var rootLoadingLayout: RootLoadingLayout

    protected var hasVisible = false
    /**
     * 是否懒加载。默认是true
     *
     *      如果是true，会在Fragment对用户第一次可见时加载数据，建议设置为true，非特殊情况不要修改
     *      如果是false，会在Fragment添加到页面中立刻加载
     */
    protected var isLazyLoad = true

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        if (!this::rootView.isInitialized) {
            rootView = inflater.inflate(R.layout.layout_root, container, false)
            rootLoadingLayout = rootView.findViewById(R.id.root_loading_layout)
            rootLoadingLayout.onRootLoadingLayoutListener = this
            rootLoadingLayout.setContentView(initLayout())
        } else {
            val parent = rootView.parent as ViewGroup
            parent.removeView(rootView)
        }
        return rootView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        // 如果不是懒加载，创建就加载数据
        // 如果对用户可见，加载数据
        if (!isLazyLoad || userVisibleHint) {
            isLazyLoad = false
            lazyLoad()
        }
    }

    /**
     * 懒加载
     *
     * @param isVisibleToUser 是否对用户可见
     */
    override fun setUserVisibleHint(isVisibleToUser: Boolean) {
        super.setUserVisibleHint(isVisibleToUser)
        if (isVisibleToUser) {
            if (!hasVisible) {
                hasVisible = true
                onVisible()
            }

            if (isLazyLoad && this::rootView.isInitialized) {
                isLazyLoad = false
                lazyLoad()
            }
        } else {
            if (hasVisible) {
                hasVisible = false
                if (hasViewAdded()) {
                    onInVisible()
                }
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode != Activity.RESULT_OK) {
            return
        }

        val fm = childFragmentManager
        val fragments = fm.fragments
        if (fragments.size > 0) {
            for (f in fragments) {
                f.onActivityResult(requestCode, resultCode, data)
            }
        }
    }

    private fun hasViewAdded(): Boolean {
        return (isAdded && view != null && view!!.windowToken != null)
    }

    fun <T : View> findViewById(@IdRes id: Int): T? = rootView.findViewById(id)

    fun showBaseLoading() {
        rootLoadingLayout.showLoading()
    }

    fun showBaseContent() {
        rootLoadingLayout.showContent()
    }

    fun showBaseEmpty() {
        rootLoadingLayout.showEmpty()
    }

    fun showLoadError() {
        rootLoadingLayout.showLoadError()
    }

    fun isContentShow(): Boolean {
        return rootLoadingLayout.isContentShow()
    }

    override fun onLoadingViewClick() {
    }

    override fun onLoadErrorViewClick() {
        lazyLoad()
    }

    override fun onLoadEmptyViewClick() {
        lazyLoad()
    }

    abstract fun initLayout(): Int

    open fun initView() {
    }

    open fun lazyLoad() {
    }

    /**
     * Fragment由对用户不可见变为可见
     * */
    open fun onVisible() {
    }

    /**
     * Fragment由对用户可见变为不可见
     * */
    open fun onInVisible() {
    }

    /**
     * 默认不拦截返回键，如果拦截请重写此方法
     * */
    fun onBackPress(): Boolean = false

    /**
     * activity调用finish会回调此方法
     * */
    fun finish() {

    }
}