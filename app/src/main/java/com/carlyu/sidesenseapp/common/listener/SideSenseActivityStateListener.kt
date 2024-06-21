package com.sonymobile.sidesenseapp.common.listener

import android.annotation.SuppressLint
import android.content.BroadcastReceiver
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import com.carlyu.sidesenseapp.common.util.LogUtil
import com.sonymobile.sidesenseapp.common.activities.ActivityStateChangedReceiver


/* loaded from: classes.dex */
class SideSenseActivityStateListener private constructor(
    private val mContext: Context,
    callback: BaseListener.Callback?
) :
    BaseListener(mContext, callback) {
    private val mReceiver: BroadcastReceiver = object : BroadcastReceiver() {
        // from class: com.sonymobile.sidesenseapp.common.listener.SideSenseActivityStateListener.1
        // android.content.BroadcastReceiver
        override fun onReceive(context2: Context, intent: Intent) {
            if (intent.action != ACTION_ACTIVITY_STATE_CHANGED) {
                LogUtil.e(
                    LogUtil.LOG_TAG,
                    TAG + ".onReceive intent.getAction is invalid value"
                )
                return
            }
            val action = intent.action
            val stringExtra = intent.getStringExtra(EXTRA_ACTIVITY_STATE)
            LogUtil.d(
                LogUtil.LOG_TAG,
                TAG + ".onReceive action=" + action + " state=" + stringExtra
            )
            this@SideSenseActivityStateListener.sendEvent(EVENT_ACTIVITY_STATE_CHANGED)
        }
    }

    fun notifyActivityStateChanged(context: Context, componentName: ComponentName?, str: String?) {
        val intent = Intent(
            context,
            ActivityStateChangedReceiver::class.java
        )
        intent.setAction(ACTION_ACTIVITY_STATE_CHANGED)
        intent.putExtra(EXTRA_ACTIVITY_STATE, str)
        intent.putExtra(EXTRA_ACTIVITY_COMPONENT_NAME, componentName)
        context.sendOrderedBroadcast(intent, null, this.mReceiver, null, 0, null, null)
    }

    // com.sonymobile.sidesenseapp.common.listener.BaseListener
    override fun register(): Boolean {
        return super.register()
    }

    // com.sonymobile.sidesenseapp.common.listener.BaseListener
    override fun unregister(): Boolean {
        return super.unregister()
    }

    companion object {
        const val ACTION_ACTIVITY_STATE_CHANGED: String =
            "com.carlyu.sidesenseapp.action.ACTIVITY_STATE_CHANGED"
        const val ACTIVITY_STATE_PAUSED: String = "paused"
        const val ACTIVITY_STATE_RESUMED: String = "resumed"
        const val EVENT_ACTIVITY_STATE_CHANGED: String = "activity_state_changed"
        const val EXTRA_ACTIVITY_COMPONENT_NAME: String =
            "com.carlyu.sidesenseapp.extra.ACTIVITY_COMPONENT_NAME"
        const val EXTRA_ACTIVITY_STATE: String = "com.sonymobile.sidesenseapp.extra.ACTIVITY_STATE"
        private const val TAG = "SideSenseActivityStateListener"

        @SuppressLint("StaticFieldLeak")
        private var sInstance: SideSenseActivityStateListener? = null

        @Synchronized
        fun getInstance(context: Context): SideSenseActivityStateListener? {
            var sideSenseActivityStateListener: SideSenseActivityStateListener?
            synchronized(SideSenseActivityStateListener::class.java) {
                if (sInstance == null) {
                    sInstance =
                        SideSenseActivityStateListener(context, null)
                }
                sideSenseActivityStateListener = sInstance
            }
            return sideSenseActivityStateListener
        }
    }
}