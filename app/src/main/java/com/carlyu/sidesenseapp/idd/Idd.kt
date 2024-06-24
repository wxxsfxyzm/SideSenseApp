package com.carlyu.sidesenseapp.idd

import android.content.ComponentName
import android.content.Context
import android.os.SystemClock
import com.carlyu.sidesenseapp.common.actions.ActionData
import com.carlyu.sidesenseapp.common.blacklist.BlackListController
import com.carlyu.sidesenseapp.common.preferences.Preferences
import com.carlyu.sidesenseapp.common.preferences.SettingsSystem
import com.carlyu.sidesenseapp.common.util.GameEnhancerUtil
import com.carlyu.sidesenseapp.common.util.LogUtil
import com.carlyu.sidesenseapp.idd.events.*
import com.carlyu.sidesenseapp.launcher.apps.suggestion.list.CustomAppsListController

object Idd {

    class Action private constructor() {
        companion object {
            private var instance: Action? = null

            fun getInstance(): Action {
                if (instance == null) {
                    instance = Action()
                }
                return instance!!
            }
        }

        fun send(context: Context, actionData: ActionData, i: Int) {
            IddManager.getInstance().sendEvent(
                ActionEvent(context).setGestureAction(context, actionData.gesture(), i)
                    .entryPoint(actionData.entryPoint())
            )
        }
    }

    class Dashboard private constructor() : Menu() {
        companion object {
            private const val TAG = "Dashboard"
            private var instance: Dashboard? = null

            fun getInstance(): Dashboard {
                if (instance == null) {
                    instance = Dashboard()
                }
                return instance!!
            }
        }

        private var dashboardEvent: DashboardEvent? = null

        private fun countDashboardShowCountUseOrNot(context: Context) {
            val actions = getMenuEvent()?.actions ?: return
            var used = false

            for (action in actions) {
                when (action.itemType) {
                    "WIFI", "MOBILE_DATA", "BLUETOOTH", "AUTO_ROTATE", "EXTRA_DIM",
                    "FLASHLIGHT", "BRIGHTNESS", "MDC_WIDGET", "HPC_WIDGET" -> {
                        used = true
                        break
                    }
                }
            }

            val preferences = Preferences.getInstance(context)
            if (used) {
                preferences.incrementDashboardShowCountUse()
            } else {
                preferences.incrementDashboardShowCountUseNot()
            }
        }

        override fun close(context: Context) {
            if (Preferences.getInstance(context).dashboardShowCount > 0) {
                countDashboardShowCountUseOrNot(context)
            }
            super.close(context)
        }

        override fun createMenuEvent(context: Context) {
            dashboardEvent = DashboardEvent(context)
        }

        override fun destroyMenuEvent() {
            dashboardEvent = null
        }

        override fun getMenuEvent(): MenuEvent? {
            return dashboardEvent
        }

        override fun getTag(): String {
            return TAG
        }
    }

    abstract class Menu {
        private var openingTime: Long = 0

        fun addAction(
            str: String,
            str2: String,
            str3: String,
            str4: String?,
            componentName: ComponentName,
            i: Int
        ) {
            getMenuEvent()?.apply {
                addAction(
                    MenuEvent.MenuAction()
                        .elapsedTime(SystemClock.elapsedRealtime() - openingTime)
                        .categoryType(str)
                        .itemType(str2)
                        .interactionType(str3)
                        .suggestType(str4)
                        .itemName(componentName.packageName)
                        .itemNameClassName(componentName.className)
                        .rank(i)
                )
            } ?: LogUtil.d(LogUtil.LOG_TAG, "$tag.addAction() event is null")
        }

        fun addAction(
            str: String,
            str2: String,
            str3: String,
            str4: String?,
            str5: String,
            i: Int
        ) {
            getMenuEvent()?.apply {
                addAction(
                    MenuEvent.MenuAction()
                        .elapsedTime(SystemClock.elapsedRealtime() - openingTime)
                        .categoryType(str)
                        .itemType(str2)
                        .interactionType(str3)
                        .suggestType(str4)
                        .itemName(str5)
                        .rank(i)
                )
            } ?: LogUtil.d(LogUtil.LOG_TAG, "$tag.addAction() event is null")
        }

        open fun close(context: Context) {
            getMenuEvent()?.apply {
                duration(SystemClock.elapsedRealtime() - openingTime)
                setWindowsOnClose(context)
                IddManager.getInstance().sendEvent(this)
                destroyMenuEvent()
            } ?: LogUtil.e(LogUtil.LOG_TAG, "$tag.close() event is null")
        }

        abstract fun createMenuEvent(context: Context)
        abstract fun destroyMenuEvent()
        abstract fun getMenuEvent(): MenuEvent?
        abstract fun getTag(): String

        open fun open(context: Context) {
            if (getMenuEvent() == null) {
                createMenuEvent(context)
                openingTime = SystemClock.elapsedRealtime()
            } else {
                LogUtil.d(LogUtil.LOG_TAG, "$tag.open() event is not null")
            }
        }

        fun setAction(context: Context, actionData: ActionData, i: Int) {
            getMenuEvent()?.apply {
                setGestureAction(
                    context,
                    actionData.gesture(),
                    i
                ).entryPoint(actionData.entryPoint())
            } ?: LogUtil.d(LogUtil.LOG_TAG, "$tag.setAction() event is null")
        }

        fun setCloseReason(reason: String) {
            getMenuEvent()?.apply {
                closeReason(reason)
            } ?: LogUtil.d(LogUtil.LOG_TAG, "$tag.setCloseReason() event is null")
        }

        fun setCloseReason(reason: String, additionalInfo: String) {
            getMenuEvent()?.apply {
                setCloseReason(reason, additionalInfo)
            } ?: LogUtil.d(LogUtil.LOG_TAG, "$tag.setCloseReason() event is null")
        }
    }

    class MwMenu private constructor() : Menu() {
        companion object {
            private const val TAG = "MwMenu"
            private var instance: MwMenu? = null

            fun getInstance(): MwMenu {
                if (instance == null) {
                    instance = MwMenu()
                }
                return instance!!
            }
        }

        private var mwMenuEvent: MwMenuEvent? = null

        private fun countMenuShowCountUseOrNot(context: Context) {
            val lastAction = getMenuEvent()?.lastAction
            val preferences = Preferences.getInstance(context)
            if (lastAction == null || lastAction.categoryType == "SETTINGS" || lastAction.categoryType == null) {
                preferences.incrementMwMenuShowCountUseNot()
            } else {
                preferences.incrementMwMenuShowCountUse()
            }
        }

        override fun close(context: Context) {
            if (Preferences.getInstance(context).mwMenuShowCount > 0) {
                countMenuShowCountUseOrNot(context)
            }
            super.close(context)
        }

        override fun createMenuEvent(context: Context) {
            mwMenuEvent = MwMenuEvent(context).apply {
                lastTouchedBox(0)
            }
        }

        override fun destroyMenuEvent() {
            mwMenuEvent = null
        }

        override fun getMenuEvent(): MenuEvent? {
            return mwMenuEvent
        }

        override fun getTag(): String {
            return TAG
        }

        fun setLastTouchedBox(box: Int) {
            mwMenuEvent?.apply {
                lastTouchedBox(box)
            } ?: LogUtil.d(LogUtil.LOG_TAG, "$tag.setLastTouchBox() event is null")
        }
    }

    class MwOobe private constructor() : Oobe() {
        companion object {
            private const val TAG = "MwOobe"
            private var instance: MwOobe? = null

            fun getInstance(): MwOobe {
                if (instance == null) {
                    instance = MwOobe()
                }
                return instance!!
            }
        }

        private var mwOobeEvent: MwOobeEvent? = null

        override fun close(context: Context) {
            mwOobeEvent?.apply {
                started(Preferences.getInstance(context).isMultiOobeDone())
                super.close(context)
            } ?: LogUtil.e(LogUtil.LOG_TAG, "$TAG.close() event is null")
        }

        override fun createOobeEvent(context: Context) {
            mwOobeEvent = MwOobeEvent(context)
        }

        override fun destroyOobeEvent() {
            mwOobeEvent = null
        }

        override fun getOobeEvent(): OobeEvent? {
            return mwOobeEvent
        }

        override fun getTag(): String {
            return TAG
        }
    }

    abstract class Oobe {
        private var openingTime: Long = 0

        open fun close(context: Context) {
            getOobeEvent()?.apply {
                duration(SystemClock.elapsedRealtime() - openingTime)
                IddManager.getInstance().sendEvent(this)
                destroyOobeEvent()
            } ?: LogUtil.e(LogUtil.LOG_TAG, "$tag.close() event is null")
        }

        abstract fun createOobeEvent(context: Context)
        abstract fun destroyOobeEvent()
        abstract fun getOobeEvent(): OobeEvent?
        abstract fun getTag(): String

        open fun open(context: Context) {
            if (getOobeEvent() == null) {
                createOobeEvent(context)
                openingTime = SystemClock.elapsedRealtime()
            } else {
                LogUtil.d(LogUtil.LOG_TAG, "$tag.open() event is not null")
            }
        }

        fun setAction(context: Context, actionData: ActionData, i: Int) {
            getOobeEvent()?.apply {
                setGestureAction(
                    context,
                    actionData.gesture(),
                    i
                ).entryPoint(actionData.entryPoint())
            } ?: LogUtil.d(LogUtil.LOG_TAG, "$tag.setAction() event is null")
        }
    }

    class SettingsStatus private constructor() {
        companion object {
            private var instance: SettingsStatus? = null

            fun getInstance(): SettingsStatus {
                if (instance == null) {
                    instance = SettingsStatus()
                }
                return instance!!
            }
        }

        fun getEvent(context: Context): SettingsStatusEvent {
            val settingsSystem = SettingsSystem.getInstance(context)
            val preferences = Preferences.getInstance(context)
            val appsCustomizedByUser =
                CustomAppsListController.getInstance(context).appsCustomizedByUser
            val shortcutsNumCustomizedByUser =
                CustomAppsListController.getInstance(context).shortcutsNumCustomizedByUser
            val appsBlockedByUser = BlackListController.getInstance(context).appsBlockedByUser

            return SettingsStatusEvent(context)
                .sidesenseEnabled(settingsSystem.getBoolean("somc.side_sense"))
                .appCustomNum(appsCustomizedByUser.size)
                .appCustomList(appsCustomizedByUser)
                .appCustomNumShortcut(shortcutsNumCustomizedByUser)
                .appSuppressionNum(appsBlockedByUser.size)
                .appSuppressionList(appsBlockedByUser)
                .onLockscreenEnabled(preferences.isLockScreenSettingsEnabled())
                .notificationStatus(IddUtil.getNotificationStatusType(context))
        }

        fun send(context: Context) {
            IddManager.getInstance().sendEvent(getEvent(context))
        }

        fun send(event: SettingsStatusEvent) {
            IddManager.getInstance().sendEvent(event)
        }
    }

    class SsMenu private constructor() : Menu() {
        companion object {
            private const val TAG = "SsMenu"
            private var instance: SsMenu? = null

            fun getInstance(): SsMenu {
                if (instance == null) {
                    instance = SsMenu()
                }
                return instance!!
            }
        }

        private var isTwMode: Boolean = false
        private var ssMenuEvent: SsMenuEvent? = null

        private fun countMenuShowCountUseOrNot(context: Context) {
            val lastAction = getMenuEvent()?.lastAction
            val isGameEnhancerEnabled = GameEnhancerUtil.isGameEnhancerEnabled(context)
            val preferences = Preferences.getInstance(context)

            if (isTwMode && lastAction != null && lastAction.categoryType == "APP") {
                preferences.incrementTwMenuShowCountUse()
                if (isGameEnhancerEnabled) {
                    preferences.incrementTwMenuShowCountUseOnGe()
                }
            } else if (lastAction == null || lastAction.categoryType == "SETTINGS" || lastAction.categoryType == null) {
                preferences.incrementMenuShowCountUseNot()
            } else {
                preferences.incrementMenuShowCountUse()
                if (isGameEnhancerEnabled) {
                    preferences.incrementMenuShowCountUseOnGe()
                }
            }
        }

        override fun close(context: Context) {
            ssMenuEvent?.apply {
                assistType(IddUtil.getAssistState(context))
                assistShowCount(IddUtil.getAssistShowCount(context))
                if (Preferences.getInstance(context).menuShowCount > 0) {
                    countMenuShowCountUseOrNot(context)
                }
                super.close(context)
            } ?: LogUtil.e(LogUtil.LOG_TAG, "$TAG.close() event is null")
        }

        override fun createMenuEvent(context: Context) {
            ssMenuEvent = SsMenuEvent(context).apply {
                geEnabled(GameEnhancerUtil.isGameEnhancerEnabled(context))
            }
            isTwMode = false
        }

        override fun destroyMenuEvent() {
            ssMenuEvent = null
            isTwMode = false
        }

        override fun getMenuEvent(): MenuEvent? {
            return ssMenuEvent
        }

        override fun getTag(): String {
            return TAG
        }

        fun setIsTwMode(isTwMode: Boolean) {
            this.isTwMode = isTwMode
        }

        fun setWindowOnCloseForTw(context: Context, componentName: ComponentName) {
            getMenuEvent()?.setWindowOnCloseForTw(context, componentName)
        }
    }

    class SsOobe private constructor() : Oobe() {
        companion object {
            private const val TAG = "SsOobe"
            private var instance: SsOobe? = null

            fun getInstance(): SsOobe {
                if (instance == null) {
                    instance = SsOobe()
                }
                return instance!!
            }
        }

        private var ssOobeEvent: SsOobeEvent? = null

        override fun close(context: Context) {
            ssOobeEvent?.apply {
                started(Preferences.getInstance(context).isOobeDone())
                super.close(context)
            } ?: LogUtil.e(LogUtil.LOG_TAG, "$TAG.close() event is null")
        }

        override fun createOobeEvent(context: Context) {
            ssOobeEvent = SsOobeEvent(context)
        }

        override fun destroyOobeEvent() {
            ssOobeEvent = null
        }

        override fun getOobeEvent(): OobeEvent? {
            return ssOobeEvent
        }

        override fun getTag(): String {
            return TAG
        }
    }

    class Tutorial private constructor() {
        companion object {
            private const val DOUBLE_TAP_E_NOT_SAME_POSITION = 1
            private const val DOUBLE_TAP_E_TIMEOUT = 2
            private const val DOUBLE_TAP_E_TOO_FAST = 3
            private const val DOUBLE_TAP_E_TOO_MUCH_INSIDE = 4
            private const val DOUBLE_TAP_E_TOO_SLOW = 5
            private const val DOUBLE_TAP_NUM = 6
            private const val DOUBLE_TAP_OK = 0
            private const val SCROLL_DOWN_E_TIMEOUT = 1
            private const val SCROLL_DOWN_E_TOO_MUCH_INSIDE = 2
            private const val SCROLL_DOWN_E_TOO_SHORT = 3
            private const val SCROLL_DOWN_NUM = 4
            private const val SCROLL_DOWN_OK = 0
            private const val SCROLL_UP_E_TIMEOUT = 1
            private const val SCROLL_UP_E_TOO_MUCH_INSIDE = 2
            private const val SCROLL_UP_E_TOO_SHORT = 3
            private const val SCROLL_UP_NUM = 4
            private const val SCROLL_UP_OK = 0
            private const val TAG = "Tutorial"
            private var instance: Tutorial? = null

            fun getInstance(): Tutorial {
                if (instance == null) {
                    instance = Tutorial()
                }
                return instance!!
            }
        }

        private var prefs: Preferences? = null
        private val doubleTapValues = IntArray(6)
        private val scrollDownValues = IntArray(4)
        private val scrollUpValues = IntArray(4)

        private fun clear(values: IntArray) {
            for (i in values.indices) {
                values[i] = 0
            }
        }

        private fun sum(values: IntArray): Int {
            return values.sum()
        }

        fun setDoubleTapStatus(status: Int) {
            when (status) {
                1 -> doubleTapValues[0]++
                2 -> doubleTapValues[5]++
                3 -> doubleTapValues[3]++
                4 -> doubleTapValues[1]++
                6 -> doubleTapValues[2]++
                7 -> doubleTapValues[4]++
            }
        }

        fun setScrollDownStatus(status: Int) {
            when (status) {
                1 -> scrollDownValues[0]++
                5 -> scrollDownValues[3]++
                6 -> scrollDownValues[1]++
                7 -> scrollDownValues[2]++
            }
        }

        fun setScrollUpStatus(status: Int) {
            when (status) {
                1 -> scrollUpValues[0]++
                5 -> scrollUpValues[3]++
                6 -> scrollUpValues[1]++
                7 -> scrollUpValues[2]++
            }
        }

        fun start(context: Context, str: String) {
            prefs = Preferences.getInstance(context)
            clear(doubleTapValues)
            clear(scrollDownValues)
            clear(scrollUpValues)
        }

        fun stop() {
            prefs ?: LogUtil.e(LogUtil.LOG_TAG, "$TAG.stop() prefs is null")
        }
    }
}
