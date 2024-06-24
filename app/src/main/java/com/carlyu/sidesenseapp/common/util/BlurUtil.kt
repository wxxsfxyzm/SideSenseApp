package com.carlyu.sidesenseapp.common.util

import android.annotation.SuppressLint
import android.content.Context
import android.view.Window
import android.view.WindowManager
import com.carlyu.sidesenseapp.common.preferences.Preferences

class BlurUtil private constructor(context: Context) {

    private val mContext: Context = context.applicationContext

    fun isBlurEnable(): Boolean {
        val windowManager = mContext.getSystemService(Context.WINDOW_SERVICE) as WindowManager
        return windowManager.isCrossWindowBlurEnabled
    }

    fun setBlur(window: Window) {
        val windowManager = window.windowManager
        val attributes = window.attributes
        if (windowManager.isCrossWindowBlurEnabled) {
            attributes.flags = WindowManager.LayoutParams.FLAG_BLUR_BEHIND
            attributes.blurBehindRadius =
                Preferences.getInstance(mContext)!!.getInt(Preferences.KEY_INT_BLUR_RADIUS, 25)
        }
    }

    companion object {
        private const val TAG = "BlurUtil"

        @SuppressLint("StaticFieldLeak")
        @Volatile
        private var sInstance: BlurUtil? = null

        @Synchronized
        fun getInstance(context: Context): BlurUtil {
            return sInstance ?: synchronized(this) {
                sInstance ?: BlurUtil(context).also { sInstance = it }
            }
        }
    }
}
