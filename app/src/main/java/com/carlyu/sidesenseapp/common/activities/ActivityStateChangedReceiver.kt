package com.carlyu.sidesenseapp.common.activities

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.carlyu.sidesenseapp.common.util.LogUtil

/* loaded from: classes.dex */
class ActivityStateChangedReceiver : BroadcastReceiver() {
    // android.content.BroadcastReceiver
    override fun onReceive(context: Context, intent: Intent) {
        val sb = StringBuilder()
        val str = TAG
        sb.append(str)
        sb.append(".onReceive ")
        sb.append(intent.action)
        LogUtil.d(LogUtil.LOG_TAG, sb.toString())
        if (intent.action == "com.sonymobile.sidesenseapp.action.ACTIVITY_STATE_CHANGED") {
            return
        }
        LogUtil.e(
            LogUtil.LOG_TAG,
            "$str.onReceive intent.getAction is invalid value"
        )
        abortBroadcast()
    }

    companion object {
        private const val ACTION_ACTIVITY_STATE_CHANGED =
            "com.carlyu.sidesenseapp.action.ACTIVITY_STATE_CHANGED"
        private const val TAG = "ActivityStateChangedReceiver"
    }
}