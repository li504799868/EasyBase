package com.lzp.base.fragment

import androidx.fragment.app.Fragment

/**
 * Created by li.zhipeng on 2019-06-05.
 *
 *      Fragment的基类
 */
abstract class BaseFragment : Fragment() {

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