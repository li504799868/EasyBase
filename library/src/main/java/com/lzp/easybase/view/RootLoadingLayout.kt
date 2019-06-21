package com.lzp.easybase.view

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.FrameLayout
import androidx.annotation.LayoutRes
import com.lzp.easybase.R

/**
 * Created by li.zhipeng on 2019-06-05.
 *
 *      自定义View，具有加载中/加载失败/内容为空等状态，
 *      适合使用在需要发起网络请求，并显示对应状态提示的场景
 */
class RootLoadingLayout @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : FrameLayout(context, attrs, defStyleAttr) {

    private val loadingViewId: Int

    private val loadingEmptyViewId: Int

    private val loadingErrorViewId: Int

    /**
     * 加载中的布局
     * */
    private lateinit var loadingView: View

    /**
     * 加载为空的布局
     * */
    private lateinit var loadingEmptyView: View

    /**
     * 加载失败的布局
     * */
    private lateinit var loadingErrorView: View

    /**
     * 内部布局
     * */
    private lateinit var contentView: View

    var onRootLoadingLayoutListener: OnRootLoadingLayoutListener? = null

    init {
        val typedArray = context.obtainStyledAttributes(attrs, R.styleable.RootLoadingLayout)
        // 获取自定义的布局
        loadingViewId =
            typedArray.getResourceId(R.styleable.RootLoadingLayout_loadingViewId, R.layout.root_layout_loading)
        loadingEmptyViewId =
            typedArray.getResourceId(R.styleable.RootLoadingLayout_loadingEmptyViewId, R.layout.root_layout_empty)
        loadingErrorViewId =
            typedArray.getResourceId(R.styleable.RootLoadingLayout_loadingErrorViewId, R.layout.root_layout_load_error)

        typedArray.recycle()

    }

    /**
     * 设置内容布局
     * */
    fun setContentView(@LayoutRes id: Int) {
        contentView = View.inflate(context, id, this)
    }

    /**
     * 设置内容布局
     * */
    fun setContentView(contentView: View) {
        addView(contentView)
    }

    fun getContentView() = contentView

    fun showLoading() {
        if (!this::loadingView.isInitialized) {
            loadingView = View.inflate(context, loadingViewId, this)
            loadingView.setOnClickListener {
                onRootLoadingLayoutListener?.onLoadingViewClick()
            }
        }
        loadingView.visibility = View.VISIBLE

        if (this::loadingEmptyView.isInitialized) {
            loadingEmptyView.visibility = View.GONE
        }

        if (this::loadingErrorView.isInitialized) {
            loadingErrorView.visibility = View.GONE
        }

        if (this::contentView.isInitialized) {
            contentView.visibility = View.GONE
        }

    }

    fun showEmpty() {
        // 初始化加载为空的布局
        if (!this::loadingEmptyView.isInitialized) {
            loadingEmptyView = View.inflate(context, loadingEmptyViewId, this)
            loadingEmptyView.setOnClickListener {
                onRootLoadingLayoutListener?.onLoadEmptyViewClick()
            }
        }
        loadingEmptyView.visibility = View.VISIBLE

        if (this::loadingView.isInitialized) {
            loadingView.visibility = View.GONE
        }

        if (this::loadingErrorView.isInitialized) {
            loadingErrorView.visibility = View.GONE
        }

        if (this::contentView.isInitialized) {
            contentView.visibility = View.GONE
        }

    }

    fun showLoadError() {
        // 初始化加载为空的布局
        if (!this::loadingErrorView.isInitialized) {
            loadingErrorView = View.inflate(context, loadingErrorViewId, this)
            loadingErrorView.setOnClickListener {
                onRootLoadingLayoutListener?.onLoadErrorViewClick()
            }
        }
        loadingErrorView.visibility = View.VISIBLE

        if (this::loadingView.isInitialized) {
            loadingView.visibility = View.GONE
        }

        if (this::loadingEmptyView.isInitialized) {
            loadingEmptyView.visibility = View.GONE
        }

        if (this::contentView.isInitialized) {
            contentView.visibility = View.GONE
        }

    }

    fun showContent() {
        if (this::contentView.isInitialized) {
            contentView.visibility = View.VISIBLE
        }

        if (this::loadingView.isInitialized) {
            loadingView.visibility = View.GONE
        }

        if (this::loadingEmptyView.isInitialized) {
            loadingEmptyView.visibility = View.GONE
        }

        if (this::loadingErrorView.isInitialized) {
            contentView.visibility = View.GONE
        }

    }

    /**
     *  是否正在显示加载中
     * */
    fun isLoadingShow() = this::loadingView.isInitialized && loadingView.visibility == View.VISIBLE

    /**
     *  是否正在显示加载错误
     * */
    fun isLoadErrorShow() = this::loadingErrorView.isInitialized && loadingErrorView.visibility == View.VISIBLE

    /**
     *  是否正在显示加载为空
     * */
    fun isLoadEmptyShow() = this::loadingEmptyView.isInitialized && loadingEmptyView.visibility == View.VISIBLE

    /**
     *  是否正在显示内容
     * */
    fun isContentShow() = this::contentView.isInitialized && contentView.visibility == View.VISIBLE

    /**
     * 回调监听，可以在点击对应布局进行重试操作
     * */
    interface OnRootLoadingLayoutListener {

        fun onLoadingViewClick()

        fun onLoadErrorViewClick()

        fun onLoadEmptyViewClick()

    }


}