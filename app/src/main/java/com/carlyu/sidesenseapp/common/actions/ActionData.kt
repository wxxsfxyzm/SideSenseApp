package com.carlyu.sidesenseapp.common.actions

import android.os.Bundle
import android.view.MotionEvent

data class ActionData(
    val gesture: String?,
    val isLeftSide: Boolean,
    val firstMotionEvent: MotionEvent?,
    val lastMotionEvent: MotionEvent?,
    val isAnimationFeedbackEnabled: Boolean,
    val isVibrationFeedbackEnabled: Boolean,
    val extras: Bundle?,
    val entryPoint: String?
) {

    class Builder {
        var gesture: String? = null
            private set
        var isLeftSide: Boolean = true
            private set
        var firstMotionEvent: MotionEvent? = null
            private set
        var lastMotionEvent: MotionEvent? = null
            private set
        var isAnimationFeedbackEnabled: Boolean = true
            private set
        var isVibrationFeedbackEnabled: Boolean = true
            private set
        var extras: Bundle? = null
            private set
        var entryPoint: String? = null
            private set

        fun build(): ActionData {
            return ActionData(
                gesture,
                isLeftSide,
                firstMotionEvent,
                lastMotionEvent,
                isAnimationFeedbackEnabled,
                isVibrationFeedbackEnabled,
                extras,
                entryPoint
            )
        }

        fun gesture(gesture: String?) = apply { this.gesture = gesture }
        fun isLeftSide(isLeftSide: Boolean) = apply { this.isLeftSide = isLeftSide }
        fun firstMotionEvent(firstMotionEvent: MotionEvent?) =
            apply { this.firstMotionEvent = firstMotionEvent }

        fun lastMotionEvent(lastMotionEvent: MotionEvent?) =
            apply { this.lastMotionEvent = lastMotionEvent }

        fun isAnimationFeedbackEnabled(isAnimationFeedbackEnabled: Boolean) =
            apply { this.isAnimationFeedbackEnabled = isAnimationFeedbackEnabled }

        fun isVibrationFeedbackEnabled(isVibrationFeedbackEnabled: Boolean) =
            apply { this.isVibrationFeedbackEnabled = isVibrationFeedbackEnabled }

        fun extras(extras: Bundle?) = apply { this.extras = extras }
        fun entryPoint(entryPoint: String?) = apply { this.entryPoint = entryPoint }
    }

    override fun toString(): String {
        val sb = StringBuilder("ActionData {")
        for (field in this::class.java.declaredFields) {
            try {
                sb.append(" ")
                sb.append(field.name)
                sb.append(":")
                sb.append(field.get(this))
            } catch (e: IllegalAccessException) {
                // handle exception
            }
        }
        sb.append(" }")
        return sb.toString()
    }
}
