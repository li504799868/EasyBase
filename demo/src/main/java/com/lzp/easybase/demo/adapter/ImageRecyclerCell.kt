package com.lzp.easybase.demo.adapter

import android.widget.ImageView
import com.lzp.easybase.adapter.BaseRecyclerAdapter
import com.lzp.easybase.adapter.BaseRecyclerCell
import com.lzp.easybase.demo.R

/**
 *  图片cell
 * */
class ImageRecyclerCell : BaseRecyclerCell<Int> {

    override fun getLayoutId(): Int = R.layout.cell_image

    override fun convertView(holder: BaseRecyclerAdapter.BaseRecyclerViewHolder, item: Int, position: Int) {
        holder.findViewById<ImageView>(R.id.image).setImageResource(item)
    }
}