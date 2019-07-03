package com.lzp.easybase.demo.adapter

import android.widget.ImageView
import com.lzp.easybase.adapter.BaseAdapterCell
import com.lzp.easybase.adapter.CommonViewHolder
import com.lzp.easybase.demo.R

/**
 *  图片cell
 * */
class ImageAdapterCell : BaseAdapterCell<Int> {

    override fun getLayoutId(): Int = R.layout.cell_image

    override fun convertView(holder: CommonViewHolder, item: Int, position: Int) {
        holder.findViewById<ImageView>(R.id.image).setImageResource(item)
    }

    override fun onItemClickListener(holder: CommonViewHolder, item: Int, position: Int) {
        super.onItemClickListener(holder, item, position)
    }
}