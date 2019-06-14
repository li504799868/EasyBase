package com.lzp.easybase.adapter

import android.content.Context
import android.util.SparseArray
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.IdRes
import androidx.recyclerview.widget.RecyclerView

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

    private val cellMapByClass = hashMapOf<Class<*>, BaseRecyclerCell<*>>()

    private val cellMapByViewType = SparseArray<BaseRecyclerCell<*>>()

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
        return getRecyclerCellByClass(position).getItemViewType()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseRecyclerViewHolder {
        val layoutId = cellMapByViewType.get(viewType).getLayoutId()
        return BaseRecyclerViewHolder(LayoutInflater.from(context).inflate(layoutId, parent, false))
    }

    override fun getItemCount(): Int = list.size

    override fun onBindViewHolder(holder: BaseRecyclerViewHolder, position: Int) {
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