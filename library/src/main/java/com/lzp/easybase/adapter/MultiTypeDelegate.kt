package com.lzp.easybase.adapter

import android.util.SparseArray
import java.util.ArrayList

/**
 *  @author li.zhipeng
 *
 *          Adapter的布局类型管理器
 * */
class MultiTypeDelegate {

    companion object {
        private const val TYPE_VIEW = 10000
    }

    val headerViewTypes = ArrayList<Int>()
    val footerViewTypes = ArrayList<Int>()

    private val cellMapClassToCell = hashMapOf<Class<*>, BaseRecyclerCell<*>>()
    private val cellMapClassToType = hashMapOf<Class<*>, Int>()
    private val cellMapTypeToCell = SparseArray<BaseRecyclerCell<*>>()

    fun getHeaderViewType(position: Int): Int {
        headerViewTypes.add(position * TYPE_VIEW)
        return position * TYPE_VIEW
    }


    fun getFooterViewType(position: Int): Int {
        footerViewTypes.add(position * TYPE_VIEW)
        return position * TYPE_VIEW
    }

    fun getHeaderIndexByViewType(viewType: Int) = viewType / TYPE_VIEW

    fun getFooterIndexByViewType(viewType: Int) = viewType / TYPE_VIEW

    /**
     * 注册Adapter中要使用的cell
     *
     *  @param viewType 因为要添加header，所以0已经被占用，请不要使用0作为type，请从1开始
     *  @param clazz 如果是基本类型，请注意使用包装类
     * */
    fun registerRecyclerCell(viewType: Int, clazz: Class<*>, cell: BaseRecyclerCell<*>) {
        cellMapClassToCell[clazz] = cell
        cellMapClassToType[clazz] = viewType
        cellMapTypeToCell.put(viewType, cell)
    }

    fun getRecyclerCellViewType(clazz: Class<*>): Int {
        return cellMapClassToType[clazz]!!
    }

    fun getLayoutIdByViewType(viewType: Int): Int {
        return cellMapTypeToCell.get(viewType).getLayoutId()
    }

    fun getRecyclerCell(clazz: Class<*>): BaseRecyclerCell<*> {
        return cellMapClassToCell[clazz]!!
    }
}