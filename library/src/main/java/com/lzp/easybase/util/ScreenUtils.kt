package com.lzp.easybase.util


import android.annotation.SuppressLint
import android.app.Activity
import android.app.KeyguardManager
import android.content.Context
import android.content.Context.KEYGUARD_SERVICE
import android.graphics.Bitmap
import android.graphics.Rect
import android.os.Build
import android.os.PowerManager
import android.util.DisplayMetrics
import android.view.*

/**
 * @author lizhipeng
 * 获得屏幕相关的辅助类
 */
object ScreenUtils {

    /**
     * 屏幕是否点亮状态
     */
    @JvmStatic
    fun isScreenOn(context: Context): Boolean {
        val pm = context.getSystemService(Context.POWER_SERVICE) as PowerManager
        return pm.isInteractive
    }

    /**
     * 是否是锁屏界面
     */
    @JvmStatic
    fun isScrisScreenLockedeenOn(context: Context): Boolean {
        val keyguardManager = context.getSystemService(KEYGUARD_SERVICE) as KeyguardManager
        return keyguardManager.isKeyguardLocked
    }

    /**
     * 获得屏幕宽度
     *
     * @param context
     * @return
     */
    @JvmStatic
    fun getScreenWidth(context: Context): Int {
        val wm = context
            .getSystemService(Context.WINDOW_SERVICE) as WindowManager
        val outMetrics = DisplayMetrics()
        wm.defaultDisplay.getMetrics(outMetrics)
        return outMetrics.widthPixels
    }

    /**
     * 获得屏幕gao度
     *
     * @param context
     * @return
     */
    @JvmStatic
    fun getScreenHeight(context: Context): Int {
        val wm = context
            .getSystemService(Context.WINDOW_SERVICE) as WindowManager
        val outMetrics = DisplayMetrics()
        wm.defaultDisplay.getMetrics(outMetrics)
        return outMetrics.heightPixels
    }

    /**
     * 得到设备的密度
     */
    @JvmStatic
    fun getScreenDensity(context: Context): Float {
        return context.resources.displayMetrics.density
    }

    /**
     * 获得状态栏的高度
     *
     * @param context
     * @return
     */
    @SuppressLint("PrivateApi")
    @JvmStatic
    fun getStatusHeight(context: Context): Int {
        var statusHeight = -1
        try {
            val clazz = Class.forName("com.android.internal.R\$dimen")
            val `object` = clazz.newInstance()
            val height = Integer.parseInt(
                clazz.getField("status_bar_height")
                    .get(`object`).toString()
            )
            statusHeight = context.resources.getDimensionPixelSize(height)
        } catch (e: Exception) {
            e.printStackTrace()
        }

        return statusHeight
    }

    /**
     * 设置一个View的高度加上状态栏的高度
     */
    @JvmStatic
    fun addStatusHeightMargin(view: View) {
        val statusBarHeight = getStatusHeight(view.context)
        val params = view.layoutParams as ViewGroup.MarginLayoutParams
        params.topMargin += statusBarHeight
        view.layoutParams = params
    }

    /**
     * 设置一个View的高度加上状态栏的高度
     */
    @JvmStatic
    fun addStatusHeightPadding(view: View) {
        val statusBarHeight = getStatusHeight(view.context)
        view.setPadding(
            view.paddingLeft,
            view.paddingTop + statusBarHeight,
            view.paddingRight, view.paddingBottom
        )
        val params = view.layoutParams
        if (params.height != ViewGroup.LayoutParams.WRAP_CONTENT) {
            params.height += getStatusHeight(view.context)
            view.layoutParams = params
        }
    }

    /**
     * 获取当前屏幕截图，包含状态栏
     *
     * @param activity
     * @return
     */
    @JvmStatic
    fun snapShotWithStatusBar(activity: Activity): Bitmap? {
        val view = activity.window.decorView
        view.isDrawingCacheEnabled = true
        view.buildDrawingCache()
        val bmp = view.drawingCache
        val width = getScreenWidth(activity)
        val height = getScreenHeight(activity)
        var bp: Bitmap? = null
        bp = Bitmap.createBitmap(bmp, 0, 0, width, height)
        view.destroyDrawingCache()
        return bp

    }

    /**
     * 获取当前屏幕截图，不包含状态栏
     *
     * @param activity
     * @return
     */
    @JvmStatic
    fun snapShotWithoutStatusBar(activity: Activity): Bitmap? {
        val view = activity.window.decorView
        view.isDrawingCacheEnabled = true
        view.buildDrawingCache()
        val bmp = view.drawingCache
        val frame = Rect()
        activity.window.decorView.getWindowVisibleDisplayFrame(frame)
        val statusBarHeight = frame.top

        val width = getScreenWidth(activity)
        val height = getScreenHeight(activity)
        var bp: Bitmap? = null
        bp = Bitmap.createBitmap(bmp, 0, statusBarHeight, width, height - statusBarHeight)
        view.destroyDrawingCache()
        return bp

    }

    /**
     * 检查是否有虚拟按键
     */
    @JvmStatic
    fun checkDeviceHasNavigationBar(context: Context): Boolean {
        val hasMenuKey = ViewConfiguration.get(context).hasPermanentMenuKey()
        val hasBackKey = KeyCharacterMap.deviceHasKey(KeyEvent.KEYCODE_BACK)
        return !hasMenuKey && !hasBackKey
    }

    /**
     * 获取虚拟键盘的高度
     */
    @JvmStatic
    fun getNavigationHeight(context: Context): Int {
        if (checkDeviceHasNavigationBar(context)) {
            val resources = context.resources
            val resourceId = resources.getIdentifier("navigation_bar_height", "dimen", "android")
            return resources.getDimensionPixelSize(resourceId)
        }
        return 0
    }

    /**
     * 唤醒手机屏幕并解锁
     */
    @SuppressLint("MissingPermission")
    @JvmStatic
    fun wakeUpAndUnlock(context: Context) {
        // 获取电源管理器对象
        val pm = context.getSystemService(Context.POWER_SERVICE) as PowerManager
        val screenOn = pm.isInteractive

        if (!screenOn) {
            // 获取PowerManager.WakeLock对象,后面的参数|表示同时传入两个值,最后的是LogCat里用的Tag
            @SuppressLint("InvalidWakeLockTag") val wl = pm.newWakeLock(
                PowerManager.ACQUIRE_CAUSES_WAKEUP or PowerManager.SCREEN_BRIGHT_WAKE_LOCK, "bright"
            )
            wl.acquire(10000) // 点亮屏幕
            wl.release() // 释放
        }
        // 屏幕解锁
        val keyguardManager = context.getSystemService(KEYGUARD_SERVICE) as KeyguardManager
        val keyguardLock = keyguardManager.newKeyguardLock("unLock")
        // 屏幕锁定
        keyguardLock.reenableKeyguard()
        keyguardLock.disableKeyguard() // 解锁
    }

    @JvmStatic
    fun lightenScreen(context: Context) {
        // 获取电源管理器对象
        val pm = context.getSystemService(Context.POWER_SERVICE) as PowerManager
        val screenOn = pm.isInteractive
        if (!screenOn) {
            // 获取PowerManager.WakeLock对象,后面的参数|表示同时传入两个值,最后的是LogCat里用的Tag
            @SuppressLint("InvalidWakeLockTag") val wl = pm.newWakeLock(
                PowerManager.ACQUIRE_CAUSES_WAKEUP or PowerManager.SCREEN_BRIGHT_WAKE_LOCK, "bright"
            )
            wl.acquire(10000) // 点亮屏幕
            wl.release() // 释放
        }

    }

    /**
     * 设置状态栏为暗色
     */
    @JvmStatic
    fun setStateBarIconDark(activity: Activity) {
        // 设置状态栏的图标颜色为暗色
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            activity.window.decorView.systemUiVisibility =
                View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
        }
    }

    /**
     * 设置状态栏为亮色
     */
    @JvmStatic
    fun setStateBarIconLight(activity: Activity) {
        // 设置状态栏的图标颜色为暗色
        activity.window.decorView.systemUiVisibility =
            View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or View.SYSTEM_UI_FLAG_LAYOUT_STABLE
    }

    /**
     * 显示虚拟键盘
     */
    @JvmStatic
    fun showNavigationBar(activity: Activity) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            val flag = View.SYSTEM_UI_FLAG_VISIBLE or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
            //判断当前版本在4.0以上并且存在虚拟按键，否则不做操作
            if (checkDeviceHasNavigationBar(activity)) {
                activity.window.decorView.systemUiVisibility = flag
            }
        }
    }

    /**
     * 隐藏虚拟键盘
     */
    @JvmStatic
    fun hideNavigationBar(activity: Activity) {
        if (Build.VERSION.SDK_INT < 19) {
            val view = activity.window.decorView
            view.systemUiVisibility = View.GONE
        } else {
            val decorView = activity.window.decorView
            val uiOptions = (View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                    or View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN)
            decorView.systemUiVisibility = uiOptions
        }
    }

    @JvmStatic
    fun requestActivityOrientation(activity: Activity, orientation: Int) {
        activity.requestedOrientation = orientation
    }

}
