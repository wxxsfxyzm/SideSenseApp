package com.carlyu.sidesenseapp.common.util

import android.util.Log
import android.view.MotionEvent
import com.carlyu.sidesenseapp.common.sensing.displaytouch.floating.MotionEventLogger

object LogUtil {
    private const val DEBUG = false
    const val ME_TAG = "MotionEvent"
    const val LOG_TAG = "SideSenseApp"
    private val IS_LOGGABLE_VERBOSE = Log.isLoggable(LOG_TAG, Log.VERBOSE)
    private val IS_LOGGABLE_DEBUG = Log.isLoggable(LOG_TAG, Log.DEBUG)
    private val IS_LOGGABLE_INFO = Log.isLoggable(LOG_TAG, Log.INFO)
    private val IS_LOGGABLE_WARN = Log.isLoggable(LOG_TAG, Log.WARN)
    private val IS_LOGGABLE_ERROR = Log.isLoggable(LOG_TAG, Log.ERROR)

    private fun concatenate(strArr: Array<out String>): String {
        val stringBuffer = StringBuffer()
        strArr.forEach { stringBuffer.append(it) }
        return stringBuffer.toString()
    }

    fun d(str: String, z: Boolean, vararg strArr: String): Int {
        return if (z && IS_LOGGABLE_DEBUG) {
            Log.d(str, concatenate(strArr))
        } else 0
    }

    fun d(str: String, vararg strArr: String): Int {
        return d(str, true, *strArr)
    }

    private fun dumpAction(motionEvent: MotionEvent) {
        val action = motionEvent.action
        Log.d(ME_TAG, "A action=$action")
        Log.d(ME_TAG, "A actionButton=${motionEvent.actionButton}")
        Log.d(ME_TAG, "A actionIndex=${motionEvent.actionIndex}")
        Log.d(ME_TAG, "A actionMasked=${motionEvent.actionMasked}")
        Log.d(ME_TAG, "A action-pointerIndex=${(65280 and action) shr 8}")

        val str = when (action and 255) {
            MotionEvent.ACTION_DOWN -> "A action-type=ACTION_DOWN"
            MotionEvent.ACTION_UP -> "A action-type=ACTION_UP"
            MotionEvent.ACTION_MOVE -> "A action-type=ACTION_MOVE"
            MotionEvent.ACTION_CANCEL -> "A action-type=ACTION_CANCEL"
            MotionEvent.ACTION_OUTSIDE -> "A action-type=ACTION_OUTSIDE"
            MotionEvent.ACTION_POINTER_DOWN -> "A action-type=ACTION_POINTER_DOWN"
            MotionEvent.ACTION_POINTER_UP -> "A action-type=ACTION_POINTER_UP"
            MotionEvent.ACTION_HOVER_MOVE -> "A action-type=ACTION_HOVER_MOVE"
            MotionEvent.ACTION_SCROLL -> "A action-type=ACTION_SCROLL"
            MotionEvent.ACTION_HOVER_ENTER -> "A action-type=ACTION_HOVER_ENTER"
            MotionEvent.ACTION_HOVER_EXIT -> "A action-type=ACTION_HOVER_EXIT"
            MotionEvent.ACTION_BUTTON_PRESS -> "A action-type=ACTION_BUTTON_PRESS"
            MotionEvent.ACTION_BUTTON_RELEASE -> "A action-type=ACTION_BUTTON_RELEASE"
            else -> return
        }
        Log.d(ME_TAG, str)
    }

    private fun dumpAxis(motionEvent: MotionEvent, i: Int) {
        val sb = StringBuilder().apply {
            append(" X P")
            append(i)
            append(" AXIS_BRAKE=")
        }
        val sb2 = MotionEventLogger.logAxisValue(motionEvent, 23, i, sb, ME_TAG, " X P").apply {
            append(i)
        }
        val sb3 = MotionEventLogger.logAxisValue(
            motionEvent,
            22,
            i,
            MotionEventLogger.logAndAppend(
                ME_TAG,
                MotionEventLogger.appendAxisValue(sb2, " AXIS_DISTANCE=", motionEvent, 24, i),
                " X P",
                i,
                " AXIS_GAS="
            ),
            ME_TAG,
            " X P"
        ).apply {
            append(i)
        }
        val sb4 = MotionEventLogger.logAxisValue(
            motionEvent,
            33,
            i,
            MotionEventLogger.logAndAppend(
                ME_TAG,
                MotionEventLogger.appendAxisValue(sb3, " AXIS_GENERIC_1=", motionEvent, 32, i),
                " X P",
                i,
                " AXIS_GENERIC_2="
            ),
            ME_TAG,
            " X P"
        ).apply {
            append(i)
        }
        val sb5 = MotionEventLogger.logAxisValue(
            motionEvent,
            35,
            i,
            MotionEventLogger.logAndAppend(
                ME_TAG,
                MotionEventLogger.appendAxisValue(sb4, " AXIS_GENERIC_3=", motionEvent, 34, i),
                " X P",
                i,
                " AXIS_GENERIC_4="
            ),
            ME_TAG,
            " X P"
        ).apply {
            append(i)
        }
        val sb6 = MotionEventLogger.logAxisValue(
            motionEvent,
            37,
            i,
            MotionEventLogger.logAndAppend(
                ME_TAG,
                MotionEventLogger.appendAxisValue(sb5, " AXIS_GENERIC_5=", motionEvent, 36, i),
                " X P",
                i,
                " AXIS_GENERIC_6="
            ),
            ME_TAG,
            " X P"
        ).apply {
            append(i)
        }
        val sb7 = MotionEventLogger.logAxisValue(
            motionEvent,
            39,
            i,
            MotionEventLogger.logAndAppend(
                ME_TAG,
                MotionEventLogger.appendAxisValue(sb6, " AXIS_GENERIC_7=", motionEvent, 38, i),
                " X P",
                i,
                " AXIS_GENERIC_8="
            ),
            ME_TAG,
            " X P"
        ).apply {
            append(i)
        }
        val sb8 = MotionEventLogger.logAxisValue(
            motionEvent,
            41,
            i,
            MotionEventLogger.logAndAppend(
                ME_TAG,
                MotionEventLogger.appendAxisValue(sb7, " AXIS_GENERIC_9=", motionEvent, 40, i),
                " X P",
                i,
                " AXIS_GENERIC_10="
            ),
            ME_TAG,
            " X P"
        ).apply {
            append(i)
        }
        val sb9 = MotionEventLogger.logAxisValue(
            motionEvent,
            43,
            i,
            MotionEventLogger.logAndAppend(
                ME_TAG,
                MotionEventLogger.appendAxisValue(sb8, " AXIS_GENERIC_11=", motionEvent, 42, i),
                " X P",
                i,
                " AXIS_GENERIC_12="
            ),
            ME_TAG,
            " X P"
        ).apply {
            append(i)
        }
        val sb10 = MotionEventLogger.logAxisValue(
            motionEvent,
            45,
            i,
            MotionEventLogger.logAndAppend(
                ME_TAG,
                MotionEventLogger.appendAxisValue(sb9, " AXIS_GENERIC_13=", motionEvent, 44, i),
                " X P",
                i,
                " AXIS_GENERIC_14="
            ),
            ME_TAG,
            " X P"
        ).apply {
            append(i)
        }
        val sb11 = MotionEventLogger.logAxisValue(
            motionEvent,
            47,
            i,
            MotionEventLogger.logAndAppend(
                ME_TAG,
                MotionEventLogger.appendAxisValue(sb10, " AXIS_GENERIC_15=", motionEvent, 46, i),
                " X P",
                i,
                " AXIS_GENERIC_16="
            ),
            ME_TAG,
            " X P"
        ).apply {
            append(i)
        }
        val sb12 = MotionEventLogger.logAxisValue(
            motionEvent,
            16,
            i,
            MotionEventLogger.logAndAppend(
                ME_TAG,
                MotionEventLogger.appendAxisValue(sb11, " AXIS_HAT_X=", motionEvent, 15, i),
                " X P",
                i,
                " AXIS_HAT_Y="
            ),
            ME_TAG,
            " X P"
        ).apply {
            append(i)
        }
        val sb13 = MotionEventLogger.logAxisValue(
            motionEvent,
            17,
            i,
            MotionEventLogger.logAndAppend(
                ME_TAG,
                MotionEventLogger.appendAxisValue(sb12, " AXIS_HSCROLL=", motionEvent, 18, i),
                " X P",
                i,
                " AXIS_LTRIGGER="
            ),
            ME_TAG,
            " X P"
        ).apply {
            append(i)
        }
        val sb14 = MotionEventLogger.logAxisValue(
            motionEvent,
            21,
            i,
            MotionEventLogger.logAndAppend(
                ME_TAG,
                MotionEventLogger.appendAxisValue(sb13, " AXIS_ORIENTATION=", motionEvent, 8, i),
                " X P",
                i,
                " AXIS_PRESSURE="
            ),
            ME_TAG,
            " X P"
        ).apply {
            append(i)
        }
        val sb15 = MotionEventLogger.logAxisValue(
            motionEvent,
            20,
            i,
            MotionEventLogger.logAndAppend(
                ME_TAG,
                MotionEventLogger.appendAxisValue(sb14, " AXIS_RTRIGGER=", motionEvent, 9, i),
                " X P",
                i,
                " AXIS_RUDDER="
            ),
            ME_TAG,
            " X P"
        ).apply {
            append(i)
        }
        val sb16 = MotionEventLogger.logAxisValue(
            motionEvent,
            11,
            i,
            MotionEventLogger.logAndAppend(
                ME_TAG,
                MotionEventLogger.appendAxisValue(sb15, " AXIS_RX=", motionEvent, 12, i),
                " X P",
                i,
                " AXIS_RY="
            ),
            ME_TAG,
            " X P"
        ).apply {
            append(i)
        }
        val sb17 = MotionEventLogger.logAxisValue(
            motionEvent,
            13,
            i,
            MotionEventLogger.logAndAppend(
                ME_TAG,
                MotionEventLogger.appendAxisValue(sb16, " AXIS_SIZE=", motionEvent, 19, i),
                " X P",
                i,
                " AXIS_THROTTLE="
            ),
            ME_TAG,
            " X P"
        ).apply {
            append(i)
        }
        val sb18 = MotionEventLogger.logAxisValue(
            motionEvent,
            4,
            i,
            MotionEventLogger.logAndAppend(
                ME_TAG,
                MotionEventLogger.appendAxisValue(sb17, " AXIS_TILT=", motionEvent, 14, i),
                " X P",
                i,
                " AXIS_TOOL_MAJOR="
            ),
            ME_TAG,
            " X P"
        ).apply {
            append(i)
        }
        val sb19 = MotionEventLogger.logAxisValue(
            motionEvent,
            6,
            i,
            MotionEventLogger.logAndAppend(
                ME_TAG,
                MotionEventLogger.appendAxisValue(sb18, " AXIS_TOOL_MINOR=", motionEvent, 5, i),
                " X P",
                i,
                " AXIS_TOUCH_MAJOR="
            ),
            ME_TAG,
            " X P"
        ).apply {
            append(i)
        }
        val sb20 = MotionEventLogger.logAxisValue(
            motionEvent,
            7,
            i,
            MotionEventLogger.logAndAppend(
                ME_TAG,
                MotionEventLogger.appendAxisValue(sb19, " AXIS_TOUCH_MINOR=", motionEvent, 1, i),
                " X P",
                i,
                " AXIS_VSCROLL="
            ),
            ME_TAG,
            " X P"
        ).apply {
            append(i)
        }
        Log.d(ME_TAG, MotionEventLogger.appendAxisValue(sb20, " AXIS_WHEEL=", motionEvent, 21, i))
    }

    fun dumpMotionEvent(motionEvent: MotionEvent) {
        val pointerCount = motionEvent.pointerCount
        Log.d(ME_TAG, "A pointer-count=$pointerCount")
        dumpAction(motionEvent)
        for (i in 0 until pointerCount) {
            Log.d(ME_TAG, " X P$i pointerId=${motionEvent.getPointerId(i)}")
            dumpAxis(motionEvent, i)
        }
    }

    fun i(str: String, vararg strArr: String): Int {
        return if (IS_LOGGABLE_INFO) {
            Log.i(str, concatenate(strArr))
        } else 0
    }

    fun isLoggable(i: Int): Boolean {
        return Log.isLoggable(LOG_TAG, i)
    }


    fun v(str: String, z: Boolean, vararg strArr: String): Int {
        return if (z && IS_LOGGABLE_VERBOSE) {
            Log.v(str, concatenate(strArr))
        } else 0
    }

    fun v(str: String, vararg strArr: String): Int {
        return v(str, true, *strArr)
    }

    fun w(str: String, z: Boolean, vararg strArr: String): Int {
        return if (z && IS_LOGGABLE_WARN) {
            Log.w(str, concatenate(strArr))
        } else 0
    }

    fun w(str: String, vararg strArr: String): Int {
        return w(str, true, *strArr)
    }

    fun w(str: String, str2: String, th: Throwable): Int {
        return if (IS_LOGGABLE_WARN) {
            Log.w(str, str2, th)
        } else 0
    }

    fun e(str: String, z: Boolean, vararg strArr: String): Int {
        return if (z && IS_LOGGABLE_ERROR) {
            Log.e(str, concatenate(strArr))
        } else 0
    }

    fun e(str: String, vararg strArr: String): Int {
        return e(str, true, *strArr)
    }

    fun e(str: String, str2: String, th: Throwable): Int {
        return if (IS_LOGGABLE_ERROR) {
            Log.e(str, str2, th)
        } else 0
    }
}