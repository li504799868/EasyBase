package com.lzp.easybase

import android.widget.TextView
import com.lzp.base.adapter.BaseRecyclerAdapter
import com.lzp.base.adapter.BaseRecyclerCell

class StringRecyclerCell : BaseRecyclerCell<String> {

    override fun getItemViewType(): Int = 0

    override fun getLayoutId(): Int = R.layout.cell_string

    override fun convertView(holder: BaseRecyclerAdapter.BaseRecyclerViewHolder, item: String, position: Int) {
        holder.findViewById<TextView>(R.id.text).text = item
    }
}