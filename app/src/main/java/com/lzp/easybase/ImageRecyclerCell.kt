package com.lzp.easybase

import android.widget.TextView
import com.lzp.base.adapter.BaseRecyclerAdapter
import com.lzp.base.adapter.BaseRecyclerCell

/**
 *  图片cell
 * */
class ImageRecyclerCell : BaseRecyclerCell<Int> {

    override fun getItemViewType(): Int = 1

    override fun getLayoutId(): Int = R.layout.cell_string

    override fun convertView(holder: BaseRecyclerAdapter.BaseRecyclerViewHolder, item: Int, position: Int) {
        holder.findViewById<TextView>(R.id.text).setBackgroundResource(item)
    }
}