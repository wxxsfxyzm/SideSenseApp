package com.carlyu.sidesenseapp.common.actions

import android.content.Context
import com.carlyu.sidesenseapp.common.preferences.Preferences
import com.carlyu.sidesenseapp.common.util.DeviceProperty
import com.carlyu.sidesenseapp.common.util.PowerUtil
import com.carlyu.sidesenseapp.idd.Idd
import com.carlyu.sidesenseapp.idd.types.EntryPointType

abstract class Action(
    val mContext: Context,
    val mType: ActionType,
    val mData: ActionData
) {
    val ACCEPT_NEXT_ACTION = 0
    val ACCEPT_NEXT_ACTION_WITH_STOP_SELF = 1
    val REJECT_NEXT_ACTION = 2

    open fun getPositionX(): Float {
        return 1.0f
    }

    open fun getPositionY(): Float {
        return 1.0f
    }

    open fun isExecutable(): Boolean {
        return true
    }

    open fun isExecuting(): Boolean {
        return false
    }

    open fun needFeedbackAnimation(): Boolean {
        return true
    }

    open fun needFeedbackVibration(): Boolean {
        return true
    }

    open fun onNextAction(action: Action): Int {
        return 1
    }

    open fun onProgressUpdate(z: Boolean, f: Float) {}

    open fun onStart(): Boolean {
        return false
    }

    open fun onStop() {}

    fun publishProgress(z: Boolean, f: Float) {
        onProgressUpdate(z, f)
    }

    fun start(): Boolean {
        if (isExecutable()) {
            if (!DeviceProperty.isDisplayTouchMode()) {
                PowerUtil.extendScreenOnTime(mContext)
            }
            Idd.Action.getInstance().send(mContext, getData(), getType().key)
            if (getData().entryPoint() == EntryPointType.GESTURE) {
                Preferences.getInstance(mContext).incrementGestureCount(getType().key)
            }
            return onStart()
        }
        return false
    }

    fun stop() {
        onStop()
    }

    override fun toString(): String {
        return "Action { type:$mType data:$mData }"
    }
}
