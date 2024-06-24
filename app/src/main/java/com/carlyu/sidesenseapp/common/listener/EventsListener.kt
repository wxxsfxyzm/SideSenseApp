package com.carlyu.sidesenseapp.common.listener

import android.content.Context
import android.os.Bundle
import com.carlyu.sidesenseapp.common.util.LogUtil

abstract class EventsListener(
    val context: Context,
    private var callbackToUser: Callback
) {
    private val listeners = mutableListOf<BaseListener>()
    private val callbackFromListener = object : BaseListener.Callback {
        override fun onEvent(event: String) {
            callbackToUser.onEvent(event)
        }
    }

    interface Callback {
        fun onEvent(event: String)
    }

    init {
        LogUtil.d(LogUtil.LOG_TAG, "${getTAG()}.EventsListener")
    }

    private fun registerListener(context: Context) {
        if (listeners.isEmpty()) {
            registerListener(context, listeners)
            val sb = StringBuilder()
            sb.append(getTAG())
            sb.append(".registerListener create listeners. size=")
            LogUtil.d(LogUtil.LOG_TAG, sb.append(listeners.size).toString())
        }
        listeners.forEach { listener ->
            LogUtil.d(
                LogUtil.LOG_TAG,
                "${getTAG()}.registerListener listener=${listener.javaClass.name}"
            )
            listener.register()
        }
    }

    private fun unregisterListener() {
        listeners.forEach { listener ->
            LogUtil.d(
                LogUtil.LOG_TAG,
                "${getTAG()}.unregisterListener listener=${listener.javaClass.name}"
            )
            try {
                listener.unregister()
            } catch (e: IllegalArgumentException) {
                LogUtil.e(
                    LogUtil.LOG_TAG,
                    "${getTAG()}.unregisterListener Unable to unregister because this receiver not registered",
                    e
                )
            }
        }
    }

    fun create() {
        LogUtil.i(LogUtil.LOG_TAG, "${getTAG()}.create")
        onCreate()
        registerListener(context)
    }

    fun destroy() {
        LogUtil.i(LogUtil.LOG_TAG, "${getTAG()}.destroy")
        onDestroy()
        unregisterListener()
    }

    fun getCallbackFromListener(): BaseListener.Callback = callbackFromListener

    fun getData(key: String): Bundle {
        val bundle = Bundle()
        getData(key, bundle)
        return bundle
    }

    fun getData(key: String, bundle: Bundle) {
        listeners.forEach { listener ->
            if (listener.getData(key, bundle)) {
                LogUtil.d(LogUtil.LOG_TAG, "${getTAG()}.getData bundleName=$key")
            }
        }
    }

    abstract fun getTAG(): String

    open fun onCreate() {}

    open fun onDestroy() {}

    abstract fun registerListener(context: Context, list: MutableList<BaseListener>)

    fun setCallback(callback: Callback) {
        this.callbackToUser = callback
    }
}
