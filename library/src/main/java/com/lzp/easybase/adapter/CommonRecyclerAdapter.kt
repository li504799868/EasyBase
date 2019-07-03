package com.lzp.easybase.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import java.util.*

/**
 * @author li.zhipeng on 2019.6.13
 *
 *      RecyclerAdapter的基类，通过添加cell实现布局
 *
 *      泛型T的作用：如果是是单一布局，方便对OnItemClick做类型转换
 *                          如果是多类型布局,建议使用基类，然后根据需要自行转换
 * */
class CommonRecyclerAdapter(private val context: Context, private val list: List<Any>) :
    RecyclerView.Adapter<CommonViewHolder>() {

    var multiTypeDelegate = CommonMultiTypeDelegate()

    /**头视图*/
    private val headerViews = ArrayList<View>()

    /**
     *  Footer
     * */
    private val footerViews = ArrayList<View>()

    /**
     * 可以添加多个头视图
     *
     * @param headerView
     */
    fun addHeaderView(headerView: View) {
        if (headerViews.contains(headerView)) {
            return
        }
        headerViews.add(headerView)
    }

    /**
     * 可以添加多个头视图
     *
     * @param headerView
     */
    fun addHeaderView(headerView: View, position: Int) {
        if (headerViews.contains(headerView)) {
            return
        }
        headerViews.add(position, headerView)
    }

    fun removeHeaderView(headerView: View) {
        headerViews.remove(headerView)
    }

    /**
     * 移除所有headers
     */
    fun removeAllHeaders() {
        headerViews.clear()
        notifyDataSetChanged()
    }

    /**
     * 可以添加多个尾视图
     *
     * @param footerView 尾视图
     */
    fun addFooterView(footerView: View) {
        footerViews.add(footerView)
    }

    /**
     * 可以添加Footer
     *
     * @param footerView
     */
    fun addFooterView(footerView: View, position: Int) {
        if (footerViews.contains(footerView)) {
            return
        }
        footerViews.add(position, footerView)
    }

    fun removeFooterView(footerView: View) {
        footerViews.remove(footerView)
    }

    /**
     * 注册Adapter中要使用的cell
     *
     *  @param viewType 因为要添加header，所以0已经被占用，请不要使用0作为type，请从1开始
     *  @param clazz 如果是基本类型，请注意使用包装类
     * */
    fun registerRecyclerCell(viewType: Int, clazz: Class<*>, cell: BaseAdapterCell<*>) {
        multiTypeDelegate.registerRecyclerCell(viewType, clazz, cell)
    }

    private fun getRecyclerCellViewType(position: Int): Int {
        val clazz = (list[position])::class.java
        return multiTypeDelegate.getRecyclerCellViewType(clazz)
    }

    override fun getItemViewType(position: Int): Int {
        // 判断是否是header
        if (position < headerViews.size) {
            return multiTypeDelegate.getHeaderViewType(position)
        }

        // 判断是否是footer
        if (footerViews.size > 0 && position >= list.size + headerViews.size) {
            return multiTypeDelegate.getFooterViewType(position)
        }
        // 返回数据Cell的type
        return getRecyclerCellViewType(position - headerViews.size)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CommonViewHolder {
        // 返回header
        if (multiTypeDelegate.headerViewTypes.contains(viewType)) {
            return CommonViewHolder(headerViews[multiTypeDelegate.getHeaderIndexByViewType(viewType)])
        }

        // 返回footer
        if (multiTypeDelegate.footerViewTypes.contains(viewType)) {
            val index = multiTypeDelegate.getFooterIndexByViewType(viewType) - list.size - headerViews.size
            return CommonViewHolder(footerViews[index])
        }

        val layoutId = multiTypeDelegate.getLayoutIdByViewType(viewType)
        return CommonViewHolder(LayoutInflater.from(context).inflate(layoutId, parent, false))
    }

    override fun getItemCount(): Int = list.size + headerViews.size + footerViews.size

    override fun onBindViewHolder(holder: CommonViewHolder, position: Int) {
        // 如果是header或者footer，adapter不负责显示
        if (position < headerViews.size) {
            return
        }
        // 判断是否是footer
        if (footerViews.size > 0 && position >= list.size + headerViews.size) {
            return
        }
        // 显示cell
        val clazz = (list[position - headerViews.size])::class.java
        multiTypeDelegate.getRecyclerCell(clazz)
            .convertViewWrapper(holder, list[position - headerViews.size], position - headerViews.size)
    }

}