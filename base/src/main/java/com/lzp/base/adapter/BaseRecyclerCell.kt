package com.lzp.base.adapter

interface BaseRecyclerCell<T> {

    fun getItemViewType(): Int

    fun getLayoutId(): Int

    @Suppress("UNCHECKED_CAST")
    fun convertViewWrapper(holder: BaseRecyclerAdapter.BaseRecyclerViewHolder, item: Any, position: Int) {
        convertView(holder, item as T, position)
    }

    fun convertView(holder: BaseRecyclerAdapter.BaseRecyclerViewHolder, item: T, position: Int)
}