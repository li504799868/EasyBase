package com.lzp.easybase.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter

/**
 * @author li.zhipeng on 2019.6.13
 *
 *      BaseAdapter，通过添加cell实现布局
 *
 *      泛型T的作用：如果是是单一布局，方便对OnItemClick做类型转换
 *                          如果是多类型布局,建议使用基类，然后根据需要自行转换
 * */
open class CommonBaseAdapter(private val context: Context, private val list: List<Any>) :
    BaseAdapter() {

    var multiTypeDelegate = CommonMultiTypeDelegate()

    override fun getItem(position: Int): Any = list[position]

    override fun getItemId(position: Int): Long = position.toLong()

    override fun getCount(): Int = list.size

    private fun getBaseAdapterCellViewType(position: Int): Int {
        val clazz = (list[position])::class.java
        return multiTypeDelegate.getRecyclerCellViewType(clazz)
    }

    override fun getViewTypeCount(): Int {
        return multiTypeDelegate.getViewTypeCount()
    }

    override fun getItemViewType(position: Int): Int {
        // 返回数据Cell的type
        return getBaseAdapterCellViewType(position)
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val cell = multiTypeDelegate.getRecyclerCell(list[position]::class.java)
        var view = convertView
        val viewHolder = if (convertView == null) {
            view = LayoutInflater.from(context).inflate(cell.getLayoutId(), parent, false)
            CommonViewHolder(view)
        } else {
            view!!.tag as CommonViewHolder
        }
        view!!.tag = viewHolder
        cell.convertViewWrapper(viewHolder, getItem(position), position)
        return view
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

}