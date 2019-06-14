package com.lzp.easybase.demo.adapter

import android.widget.TextView
import com.lzp.easybase.adapter.BaseRecyclerAdapter
import com.lzp.easybase.adapter.BaseRecyclerCell
import com.lzp.easybase.demo.R

class StringRecyclerCell : BaseRecyclerCell<String> {

    override fun getItemViewType(): Int = 0

    override fun getLayoutId(): Int = R.layout.cell_string

    override fun convertView(holder: BaseRecyclerAdapter.BaseRecyclerViewHolder, item: String, position: Int) {
        holder.findViewById<TextView>(R.id.text).text = item
    }
}