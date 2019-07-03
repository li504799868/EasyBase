package com.lzp.easybase.adapter

interface BaseAdapterCell<T : Any> {

    fun getLayoutId(): Int

    /**
     *  此方法为内部的类型转换方法，方便用户直接对泛型类型的item进行操作
     * */
    @Suppress("UNCHECKED_CAST")
    fun convertViewWrapper(holder: CommonViewHolder, item: Any, position: Int) {
        convertView(holder, item as T, position)
        holder.itemView.setOnClickListener {
            onItemClickListener(holder, item, position)
        }
    }

    /**
     *  子类要实现的ItemView的显示方法
     * */
    fun convertView(holder: CommonViewHolder, item: T, position: Int)

    /**
     *  ItemViem的点击事件，默认实现为空，如需实现请重写此方法
     * */
    fun onItemClickListener(
        holder: CommonViewHolder,
        item: T,
        position: Int
    ) {
    }


}