package com.lzp.easybase.demo.adapter

import android.widget.TextView
import com.lzp.easybase.adapter.BaseAdapterCell
import com.lzp.easybase.adapter.CommonViewHolder
import com.lzp.easybase.demo.R

class StringAdapterCell : BaseAdapterCell<String> {

    override fun getLayoutId(): Int = R.layout.cell_string

    override fun convertView(holder: CommonViewHolder, item: String, position: Int) {
        holder.findViewById<TextView>(R.id.text).text = item
    }
}