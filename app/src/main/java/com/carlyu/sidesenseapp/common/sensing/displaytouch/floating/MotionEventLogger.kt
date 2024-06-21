package com.carlyu.sidesenseapp.common.sensing.displaytouch.floating

import android.util.Log
import android.view.MotionEvent

object MotionEventLogger {
    fun appendAxisValue(
        sb: StringBuilder,
        prefix: String,
        motionEvent: MotionEvent,
        axis: Int,
        pointerIndex: Int
    ): String {
        sb.append(prefix)
        sb.append(motionEvent.getAxisValue(axis, pointerIndex))
        return sb.toString()
    }

    fun logAxisValue(
        motionEvent: MotionEvent,
        axis: Int,
        pointerIndex: Int,
        sb: StringBuilder,
        tag: String,
        logPrefix: String
    ): StringBuilder {
        sb.append(motionEvent.getAxisValue(axis, pointerIndex))
        Log.d(tag, sb.toString())
        return StringBuilder().apply {
            append(logPrefix)
        }
    }

    fun logAndAppend(
        tag: String,
        message: String,
        prefix: String,
        value: Int,
        suffix: String
    ): StringBuilder {
        Log.d(tag, message)
        return StringBuilder().apply {
            append(prefix)
            append(value)
            append(suffix)
        }
    }

    fun appendBoolean(
        sb: StringBuilder,
        prefix: String,
        message: String,
        value: Boolean,
        suffix: String
    ) {
        sb.append(prefix)
        sb.append(message)
        sb.append(value)
        sb.append(suffix)
    }
}
