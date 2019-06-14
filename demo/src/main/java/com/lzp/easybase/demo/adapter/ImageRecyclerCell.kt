package com.lzp.easybase.demo.adapter

import android.widget.TextView
import com.lzp.easybase.adapter.BaseRecyclerAdapter
import com.lzp.easybase.adapter.BaseRecyclerCell
import com.lzp.easybase.demo.R

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