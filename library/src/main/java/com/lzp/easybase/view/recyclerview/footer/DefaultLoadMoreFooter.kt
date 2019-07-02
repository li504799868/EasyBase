package com.lzp.easybase.view.recyclerview.footer

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.lzp.easybase.R

/**
 *   默认的RecyclerView底部加载更多的Footer
 * */
class DefaultLoadMoreFooter(val context: Context, private val recyclerView: RecyclerView) : LoadMoreFooter() {

    private lateinit var loadingView: View

    private lateinit var loadingTextView: TextView

    override fun getContentView(): View {
        val contentView = LayoutInflater.from(context).inflate(R.layout.footer_default_load_more, recyclerView, false)
        loadingView = contentView.findViewById(R.id.load_more_loading)
        loadingTextView = contentView.findViewById(R.id.load_more_text)
        showLoadNormal()
        return contentView
    }

    override fun showLoadNormal() {
        loadingView.visibility = View.GONE
        loadingTextView.visibility = View.GONE
    }

    override fun showLoading() {
        loadingView.visibility = View.VISIBLE
        loadingTextView.visibility = View.VISIBLE
        loadingTextView.text = context.getString(R.string.loading)
    }

    override fun showLoadSuccess() {
        loadingView.visibility = View.GONE
        loadingTextView.visibility = View.VISIBLE
        loadingTextView.text = context.getString(R.string.load_success)
    }

    override fun showLoadFailed() {
        loadingView.visibility = View.GONE
        loadingTextView.visibility = View.VISIBLE
        loadingTextView.text = context.getString(R.string.load_error)
    }

    override fun showLoadEnd() {
        loadingView.visibility = View.GONE
        loadingTextView.visibility = View.VISIBLE
        loadingTextView.text = context.getString(R.string.load_end)
    }


}