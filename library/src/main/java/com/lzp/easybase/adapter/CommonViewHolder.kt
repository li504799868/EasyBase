package com.lzp.easybase.adapter

import android.util.SparseArray
import android.view.View
import androidx.annotation.IdRes
import androidx.recyclerview.widget.RecyclerView

/**
 *  公用的ViewHolder，RecyclerAdapter和BaseAdapter都可以使用
 * */
class CommonViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    private val sparseArray = SparseArray<View>()

    @Suppress("UNCHECKED_CAST")
    fun <T : View> findViewById(@IdRes id: Int): T {
        var result = sparseArray[id]
        if (result == null) {
            result = itemView.findViewById(id)
            sparseArray.put(id, result)
        }
        return result as T
    }
}