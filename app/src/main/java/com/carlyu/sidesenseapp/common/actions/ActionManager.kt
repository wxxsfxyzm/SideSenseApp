package com.carlyu.sidesenseapp.common.actions

import android.content.Context
import com.carlyu.sidesenseapp.common.preferences.Preferences
import com.carlyu.sidesenseapp.common.util.LogUtil
import java.util.Stack

enum class ActionManager {
    INSTANCE;

    private var mPrefs: Preferences? = null
    private val mActionStack: Stack<Action> = Stack()
    private var mContext: Context? = null

    private fun dump() {
        LogUtil.v(LogUtil.LOG_TAG, "$TAG.dump <<<<<<<<<< size=${mActionStack.size}")
        for (action in mActionStack) {
            LogUtil.v(LogUtil.LOG_TAG, "$TAG.dump $action")
        }
        LogUtil.v(LogUtil.LOG_TAG, "$TAG.dump >>>>>>>>>>")
    }

    companion object {
        private const val TAG = "ActionManager"

        fun getInstance(context: Context): ActionManager {
            val actionManager = INSTANCE
            if (actionManager.mPrefs == null) {
                actionManager.mPrefs = Preferences.getInstance(context)
            }
            if (actionManager.mContext == null) {
                actionManager.mContext = context
            }
            return actionManager
        }
    }

    private fun removeNonExecutingActions() {
        mActionStack.removeIf { !it.isExecuting }
    }

    fun addAction(action: Action?): Boolean {
        LogUtil.v(LogUtil.LOG_TAG, "$TAG.addAction $action")
        dump()

        if (action == null || !action.isExecutable) {
            return false
        }

        removeNonExecutingActions()

        val currentAction = getCurrentAction()
        LogUtil.v(LogUtil.LOG_TAG, "$TAG.addAction currentAction $currentAction nextAction=$action")

        if (currentAction != null && currentAction.isExecuting) {
            when (currentAction.onNextAction(action)) {
                0 -> return false
                1 -> currentAction.stop()
            }
        }

        removeNonExecutingActions()

        mActionStack.push(action)
        dump()
        return true
    }

    fun getCurrentAction(): Action? {
        return if (mActionStack.isEmpty()) {
            LogUtil.v(LogUtil.LOG_TAG, "$TAG.getCurrentAction null")
            null
        } else {
            LogUtil.v(LogUtil.LOG_TAG, "$TAG.getCurrentAction ${mActionStack.peek()}")
            mActionStack.peek()
        }
    }

    fun startAction(actionType: ActionType, actionData: ActionData): Boolean {
        LogUtil.v(LogUtil.LOG_TAG, "$TAG.startAction type=$actionType data=$actionData")

        val currentAction = getInstance(mContext!!).getCurrentAction()
        if (actionData.gesture() == null && currentAction != null) {
            val key = currentAction.getType().key
            mPrefs?.putLeftHandStatus(actionType.key, mPrefs!!.getLeftHandStatus(key))
            mPrefs?.putThumPositionY(actionType.key, mPrefs!!.getThumPositionY(key))
        }

        return if (addAction(
                ActionFactory.getInstance(mContext!!).create(actionType, actionData)
            )
        ) {
            getCurrentAction()?.start() ?: false
        } else {
            LogUtil.v(LogUtil.LOG_TAG, "$TAG.startAction failed to add action")
            false
        }
    }
}
