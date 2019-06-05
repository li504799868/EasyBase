package com.lzp.base.activity

import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import com.lzp.base.util.ScreenUtils

/**
 * Created by li.zhipeng on 2019-06-05.
 */
abstract class BaseNavigationActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            //5.0
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                val window = window
                window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
                window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
                window.statusBarColor = Color.TRANSPARENT
                // 设置状态栏的图标颜色为暗色
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    ScreenUtils.setStateBarIconLight(this)
                } else {
                    window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                }
            } else {
                window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS) //判断当前版本在4.0以上并且存在虚拟按键，否则不做操作
                //判断当前版本在4.0以上并且存在虚拟按键，否则不做操作
                if (ScreenUtils.checkDeviceHasNavigationBar(this)) {
                    val flag = View.SYSTEM_UI_FLAG_LAYOUT_STABLE or View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                    window.decorView.systemUiVisibility = flag
                }
            }
        }
        super.onCreate(savedInstanceState)
        rootView.clipToPadding = true
        rootView.fitsSystemWindows = true
    }

    fun setRootClipPadding(clipPadding: Boolean) {
        rootView.clipToPadding = clipPadding
        rootView.fitsSystemWindows = clipPadding
    }
}