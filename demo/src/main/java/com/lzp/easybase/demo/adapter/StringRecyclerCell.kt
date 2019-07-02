package com.lzp.easybase.demo.adapter

import android.widget.TextView
import com.lzp.easybase.adapter.CommonRecyclerAdapter
import com.lzp.easybase.adapter.BaseRecyclerCell
import com.lzp.easybase.demo.R

class StringRecyclerCell : BaseRecyclerCell<String> {

    override fun getLayoutId(): Int = R.layout.cell_string

    override fun convertView(holder: CommonRecyclerAdapter.BaseRecyclerViewHolder, item: String, position: Int) {
        holder.findViewById<TextView>(R.id.text).text = item
    }
}