package com.carlyu.sidesenseapp.common.listener

import android.content.Context
import android.os.Bundle
import com.carlyu.sidesenseapp.common.util.LogUtil

/* loaded from: classes.dex */
abstract class BaseListener(context: Context?, private val mCallback: Callback?) {
    private var mCallbacks: MutableList<Callback?> = ArrayList()
    private var isRegistered: Boolean = false

    /* loaded from: classes.dex */
    interface Callback {
        fun onEvent(str: String)
    }

    fun addCallback(callback: Callback?) {
        mCallbacks.add(callback)
    }

    fun getData(str: String?, bundle: Bundle?): Boolean {
        return false
    }

    open fun register(): Boolean {
        LogUtil.d(LogUtil.LOG_TAG, TAG + ".register mIsRegistered=" + this.isRegistered)
        if (this.isRegistered) {
            return false
        }
        this.isRegistered = true
        return true
    }

    fun removeCallback(callback: Callback?) {
        mCallbacks.remove(callback)
    }

    fun sendEvent(str: String) {
        if (this.isRegistered) {
            mCallback?.onEvent(str)
            for (callback2 in this.mCallbacks) {
                callback2!!.onEvent(str)
            }
        }
    }

    open fun unregister(): Boolean {
        LogUtil.d(LogUtil.LOG_TAG, TAG + ".unregister mIsRegistered=" + this.isRegistered)
        if (this.isRegistered) {
            this.isRegistered = false
            return true
        }
        return false
    }

    companion object {
        private const val TAG = "BaseListener"
    }
}