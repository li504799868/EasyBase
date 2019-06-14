package com.lzp.easybase.adapter

import android.content.Context
import android.util.SparseArray
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.IdRes
import androidx.recyclerview.widget.RecyclerView
import java.util.ArrayList

/**
 * @author li.zhipeng on 2019.6.13
 *
 *      RecyclerAdapter的基类，通过添加cell实现布局
 *
 *      泛型T的作用：如果是是单一布局，方便对OnItemClick做类型转换
 *                          如果是多类型布局,建议使用基类，然后根据需要自行转换
 * */
class BaseRecyclerAdapter<T : Any>(private val context: Context, private val list: List<T>) :
    RecyclerView.Adapter<BaseRecyclerAdapter.BaseRecyclerViewHolder>() {

    companion object {
        private const val TYPE_VIEW = 10000
    }

    /**头视图*/
    private val headerViews = ArrayList<View>()

    /**
     *  Footer
     * */
    private val footerViews = ArrayList<View>()

    private val headerViewTypes = ArrayList<Int>()
    private val footerViewTypes = ArrayList<Int>()

    private val cellMapByClass = hashMapOf<Class<*>, BaseRecyclerCell<*>>()

    private val cellMapByViewType = SparseArray<BaseRecyclerCell<*>>()

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
     * 可以添加多个头视图
     *
     * @param headerView
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
     *  @param clazz 如果是基本类型，请注意使用包装类
     * */
    fun registerRecyclerCell(clazz: Class<*>, cell: BaseRecyclerCell<*>) {
        cellMapByClass[clazz] = cell
        cellMapByViewType.put(cell.getItemViewType(), cell)
    }

    private fun getRecyclerCellByClass(position: Int): BaseRecyclerCell<*> {
        val clazz = (list[position])::class.java as Class<*>
        return cellMapByClass[clazz]!!
    }

    override fun getItemViewType(position: Int): Int {
        // 判断是否是header
        if (position < headerViews.size) {
            headerViewTypes.add(position * TYPE_VIEW)
            return position * TYPE_VIEW
        }

        // 判断是否是footer
        if (footerViews.size > 0 && position >= list.size + headerViews.size) {
            footerViewTypes.add(position * TYPE_VIEW)
            return position * TYPE_VIEW
        }
        // 返回数据Cell的type
        return getRecyclerCellByClass(position - headerViews.size).getItemViewType()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseRecyclerViewHolder {
        // 返回header
        if (headerViewTypes.contains(viewType)) {
            return BaseRecyclerViewHolder(headerViews[viewType / TYPE_VIEW])
        }

        // 返回footer
        if (footerViewTypes.contains(viewType)) {
            val index = viewType / TYPE_VIEW - list.size - headerViews.size
            return BaseRecyclerViewHolder(footerViews[index])
        }

        val layoutId = cellMapByViewType.get(viewType).getLayoutId()
        return BaseRecyclerViewHolder(LayoutInflater.from(context).inflate(layoutId, parent, false))
    }

    override fun getItemCount(): Int = list.size + headerViews.size + footerViews.size

    override fun onBindViewHolder(holder: BaseRecyclerViewHolder, position: Int) {
        // 如果是header或者footer，adapter不负责显示
        if (position < headerViews.size) {
            return
        }
        // 判断是否是footer
        if (footerViews.size > 0 && position >= list.size - 1 + headerViews.size) {
            return
        }
        // 显示cell
        getRecyclerCellByClass(position).convertViewWrapper(holder, list[position], position)
    }

    /**
     *  公用的RecyclerAdapter的ViewHolder
     * */
    class BaseRecyclerViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun <T : View> findViewById(@IdRes id: Int): T {
            return itemView.findViewById(id)
        }
    }

}