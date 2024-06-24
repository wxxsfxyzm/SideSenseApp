package com.carlyu.sidesenseapp.common.preferences

import android.content.Context
import android.content.SharedPreferences
import android.preference.PreferenceManager
import android.view.ViewConfiguration
import com.carlyu.sidesenseapp.R
import com.carlyu.sidesenseapp.common.sensing.TouchViewUtil
import com.carlyu.sidesenseapp.common.util.FeatureConfig
import com.carlyu.sidesenseapp.common.util.LogUtil
import com.carlyu.sidesenseapp.common.util.ShortcutUtil
import com.carlyu.sidesenseapp.idd.types.ItemType
import com.carlyu.sidesenseapp.launcher.apps.suggestion.list.AutoAddShortcutDialogManager
import com.carlyu.sidesenseapp.permission.PermissionGroup
import org.json.JSONArray

/* loaded from: classes.dex */
class Preferences private constructor(context: Context) {
    private val mBooleanMap: MutableMap<String, Boolean> = mutableMapOf(
        Preferences.KEY_BOOLEAN_IS_SETTINGS_INITIALIZED to false,
        Preferences.KEY_BOOLEAN_LEFT_HAND_STATUS to true,
        Preferences.KEY_BOOLEAN_SCROLL_UP_LEFT_HAND_STATUS to true,
        Preferences.KEY_BOOLEAN_SCROLL_DOWN_LEFT_HAND_STATUS to true,
        Preferences.KEY_BOOLEAN_OOBE_DONE to false,
        Preferences.KEY_BOOLEAN_MULTI_OOBE_DONE to false,
        Preferences.KEY_BOOLEAN_SIDE_SENSE_LOCKSCREEN_SETTINGS to FeatureConfig.isSsOnLockscreenEnabled(),
        Preferences.KEY_BOOLEAN_ASSIST_SIDE_SENSE_GUIDANCE_TAPPED to false,
        Preferences.KEY_BOOLEAN_ASSIST_IDI_TAPPED to false,
        Preferences.KEY_BOOLEAN_ASSIST_XPERIA_ASSIST_TAPPED to false,
        Preferences.KEY_BOOLEAN_ASSIST_ALL_DONE to false,
        Preferences.KEY_BOOLEAN_GUIDANCE_NOTIFICATION_TAPPED to false,
        Preferences.KEY_BOOLEAN_GUIDANCE_NOTIFICATION_REPLACED to false,
        Preferences.KEY_BOOLEAN_GUIDANCE_NOTIFICATION_SHOWN to false,
        Preferences.KEY_BOOLEAN_PROMPT_DISPLAY_TOUCH_VIEW_LEFT_HAND_STATUS to true,
        Preferences.KEY_BOOLEAN_SIDEBAR_MOVING_STATUS to false,
        Preferences.KEY_BOOLEAN_IS_NTP_BASED_INITIAL_USE_TIME to false,
        Preferences.KEY_BOOLEAN_IS_LAUNCH_FROM_SHORTCUT to false,
        Preferences.KEY_BOOLEAN_DYNAMIC_AREA_FILTER to true,
        Preferences.KEY_BOOLEAN_FIXED_AREA to false,
        Preferences.KEY_BOOLEAN_TUTORIAL_PRACTICE_AREA_TAP to false,
        Preferences.KEY_BOOLEAN_FLOATING_MODE_ENABLED to false,
        Preferences.KEY_BOOLEAN_DOUBLE_TAP_ENABLED to false,
        Preferences.KEY_BOOLEAN_FIX_CUSTOM_APP_PRESET_DONE to false,
        Preferences.KEY_BOOLEAN_HELP_GUIDE_DIALOG_SHOWN to false,
        Preferences.KEY_BOOLEAN_BLACK_LIST_DIALOG_SHOWN to false,
        Preferences.KEY_BOOLEAN_AUTO_SHORTCUT_ADD_DIALOG_SHOWN_ALIPAY to false,
        Preferences.KEY_BOOLEAN_AUTO_SHORTCUT_ADD_DIALOG_SHOWN_WECHATPAY to false,
        Preferences.KEY_BOOLEAN_USE_SS_BAR_STATUS_WHEN_CD_IS_TURNED_OFF to true,
        Preferences.KEY_BOOLEAN_3TAB_MOVE_AMOUNT to false,
        "tab_animation_end" to false,
        Preferences.KEY_BOOLEAN_SHOULD_SHOW_DISPLAYTOUCH_DIALOG to true,
        Preferences.KEY_BOOLEAN_POINTER_LOCATION to false,
        Preferences.KEY_BOOLEAN_MULTI_TOUCH_GESTURE_DETECTOR to false,
        Preferences.KEY_BOOLEAN_LOOPS to true,
        Preferences.KEY_BOOLEAN_LANDSCAPE_BOTTOM_TOUCH to false,
        Preferences.KEY_BOOLEAN_FORCE_OOBE to false,
        Preferences.KEY_BOOLEAN_FORCE_MULTI_OOBE to false,
        Preferences.KEY_BOOLEAN_SIDE_SENSE_WIDGET to false,
        Preferences.KEY_BOOLEAN_SIDE_SENSE_LAUNCHER to false,
        Preferences.KEY_BOOLEAN_SHOW_DYNAMIC_AREA_FILTER to false,
        Preferences.KEY_BOOLEAN_DYNAMIC_AREA_FILTER_MEDIA_VERSION to false,
        Preferences.KEY_BOOLEAN_RESET_ASSIST to false,
        Preferences.KEY_BOOLEAN_MORE_APPS to true,
        Preferences.KEY_BOOLEAN_RESET_LOOPS_PROMPT to false,
        Preferences.KEY_BOOLEAN_ENABLE_ACTIVE_AREA to false,
        Preferences.KEY_BOOLEAN_DISPLAY_TOUCH_VIEW_AUTO_HIDE to FeatureConfig.isAutoHideSideBarEnabled(),
        Preferences.KEY_BOOLEAN_DISPLAY_TOUCH_VIEW_ANIMATION to true,
        Preferences.KEY_BOOLEAN_DISPLAY_TOUCH_VIEW_WIDTH_SETTING to false,
        Preferences.KEY_BOOLEAN_DISPLAY_TOUCH_VIEW_SHOW_TOUCH_AREA to false,
        Preferences.KEY_BOOLEAN_SHOW_GESTURE_NAVIGATION_AREA to false,
        Preferences.KEY_BOOLEAN_MEASURE_MENU_PERFORMANCE to false,
        Preferences.KEY_BOOLEAN_HPC_WIDGET_SETTING_OFF_BY_USER to false,
        Preferences.KEY_BOOLEAN_SHOW_IN_SCREENSHOT to false
    )

    private val mIntegerMap: MutableMap<String, Int> = mutableMapOf<String, Int>().apply {
        put(Preferences.KEY_INT_TRANSLATION_X, 0)
        put(Preferences.KEY_INT_THUMB_POSITION_X, 0)
        put(Preferences.KEY_INT_TRANSLATION_Y, 0)
        put(Preferences.KEY_INT_THUMB_POSITION_Y, 0)
        put(Preferences.KEY_INT_SCROLL_UP_TRANSLATION_X, 0)
        put(Preferences.KEY_INT_SCROLL_DOWN_TRANSLATION_X, 0)
        put(Preferences.KEY_INT_SCROLL_UP_THUMB_POSITION_X, 0)
        put(Preferences.KEY_INT_SCROLL_DOWN_THUMB_POSITION_X, 0)
        put(Preferences.KEY_INT_SCROLL_UP_TRANSLATION_Y, 0)
        put(Preferences.KEY_INT_SCROLL_DOWN_TRANSLATION_Y, 0)
        put(Preferences.KEY_INT_SCROLL_UP_THUMB_POSITION_Y, 0)
        put(Preferences.KEY_INT_SCROLL_DOWN_THUMB_POSITION_Y, 0)
        put(Preferences.KEY_INT_ORIENTATION, 1)
        put(Preferences.KEY_INT_LOOPS_PROMPT_SHOW_COUNT, 0)
        put(Preferences.KEY_INT_LOOPS_PROMPT_SHOW_COUNT_TO_STOP, 8)
        put(Preferences.KEY_INT_ASSIST_INTERVAL_TIME, 86400)
        put(Preferences.KEY_INT_IDD_INTERVAL_TIME, Preferences.DEFAULT_VALUE_INT_IDD_INTERVAL_TIME)
        put(Preferences.KEY_INT_ASSIST_SIDE_SENSE_GUIDANCE_SHOW_COUNT, 0)
        put(Preferences.KEY_INT_ASSIST_IDI_SETTINGS_SHOW_COUNT, 0)
        put(Preferences.KEY_INT_ASSIST_XPERIA_ASSIST_SHOW_COUNT, 0)
        put(Preferences.KEY_INT_DOUBLE_TAP_DIALOG_INDEX, 0)
        put(Preferences.KEY_INT_SCROLL_DISTANCE_DIALOG_INDEX, 1)
        put(Preferences.KEY_INT_LONG_PRESS_DIALOG_INDEX, 2)
        put(Preferences.KEY_INT_SCROLL_DISTANCE, 200)
        put(Preferences.KEY_INT_BLUR_RADIUS, 25)
        put(Preferences.KEY_INT_TAB_SWIPE_THRESHOLD, 50)
        put(Preferences.KEY_INT_GUIDANCE_NOTIFICATION_INTERVAL_TIME, 86400)
        put(Preferences.KEY_INT_SCROLL_SIDE_COUNT, 0)
        put(Preferences.KEY_INT_SS_MENU_SHOW_COUNT, 0)
        put(Preferences.KEY_INT_MW_MENU_SHOW_COUNT, 0)
        put(Preferences.KEY_INT_SS_OOBE_SHOW_COUNT, 0)
        put(Preferences.KEY_INT_MW_OOBE_SHOW_COUNT, 0)
        put(Preferences.KEY_INT_DASHBOARD_SHOW_COUNT, 0)
        put(Preferences.KEY_INT_DASHBOARD_SHOW_COUNT_USE, 0)
        put(Preferences.KEY_INT_DASHBOARD_SHOW_COUNT_USE_NOT, 0)
        put(Preferences.KEY_INT_LAST_DASHBOARD_SHOW_COUNT, 0)
        put(Preferences.KEY_INT_LAST_DASHBOARD_SHOW_COUNT_USE, 0)
        put(Preferences.KEY_INT_LAST_DASHBOARD_SHOW_COUNT_USE_NOT, 0)
        put(Preferences.KEY_INT_DISPLAYTOUCH_LONG_PRESS_TIMEOUT, 200)
        put(Preferences.KEY_INT_FLOATING_CHANGED_COUNT, 0)
        put(Preferences.KEY_INT_LAST_MONTH_FLOATING_CHANGED_COUNT, 0)
        put(Preferences.KEY_INT_GUIDE_TOP_PAGE_COUNT, 0)
        put(Preferences.KEY_INT_GUIDE_SS_PAGE_COUNT, 0)
        put(Preferences.KEY_INT_GUIDE_MW_PAGE_COUNT, 0)
        put(Preferences.KEY_INT_GUIDE_TW_PAGE_COUNT, 0)
        put(Preferences.KEY_INT_SS_MENU_TAB_COUNT, 0)
        put(Preferences.KEY_INT_LAST_WEEK_SS_MENU_TAB_COUNT, 0)
        put(Preferences.KEY_INT_MW_MENU_TAB_COUNT, 0)
        put(Preferences.KEY_INT_LAST_WEEK_MW_MENU_TAB_COUNT, 0)
        put(Preferences.KEY_INT_SS_MENU_SWIPE_COUNT, 0)
        put(Preferences.KEY_INT_LAST_WEEK_SS_MENU_SWIPE_COUNT, 0)
        put(Preferences.KEY_INT_MW_MENU_SWIPE_COUNT, 0)
        put(Preferences.KEY_INT_LAST_WEEK_MW_MENU_SWIPE_COUNT, 0)
        put(Preferences.KEY_INT_MENU_SETTINGS_ICON_COUNT, 0)
        put(Preferences.KEY_INT_LAST_WEEK_MENU_SETTINGS_ICON_COUNT, 0)
        put(Preferences.KEY_INT_TOOL_NOTIFICATION_COUNT, 0)
        put(Preferences.KEY_INT_LAST_WEEK_TOOL_NOTIFICATION_COUNT, 0)
        put(Preferences.KEY_INT_TOOL_SCREENSHOT_COUNT, 0)
        put(Preferences.KEY_INT_LAST_WEEK_TOOL_SCREENSHOT_COUNT, 0)
        put(Preferences.KEY_INT_TOOL_MUTE_COUNT, 0)
        put(Preferences.KEY_INT_LAST_WEEK_TOOL_MUTE_COUNT, 0)
        put(Preferences.KEY_INT_MW_TOOL_CANCEL_COUNT, 0)
        put(Preferences.KEY_INT_LAST_WEEK_MW_TOOL_CANCEL_COUNT, 0)
        put(Preferences.KEY_INT_MW_TOOL_SWAP_COUNT, 0)
        put(Preferences.KEY_INT_LAST_WEEK_MW_TOOL_SWAP_COUNT, 0)
        put(Preferences.KEY_INT_MW_TOOL_TW_COUNT, 0)
        put(Preferences.KEY_INT_LAST_WEEK_MW_TOOL_TW_COUNT, 0)
        put(Preferences.KEY_INT_EP_SS_HOME_COUNT, 0)
        put(Preferences.KEY_INT_LAST_WEEK_EP_SS_HOME_COUNT, 0)
        put(Preferences.KEY_INT_EP_MW_HOME_COUNT, 0)
        put(Preferences.KEY_INT_LAST_WEEK_EP_MW_HOME_COUNT, 0)
        put(Preferences.KEY_INT_EP_DASHBOARD_HOME_COUNT, 0)
        put(Preferences.KEY_INT_LAST_WEEK_EP_DASHBOARD_HOME_COUNT, 0)
        put(Preferences.KEY_INT_EP_SS_GESTURE_BAR_COUNT, 0)
        put(Preferences.KEY_INT_LAST_WEEK_EP_SS_GESTURE_BAR_COUNT, 0)
        put(Preferences.KEY_INT_EP_MW_GESTURE_BAR_COUNT, 0)
        put(Preferences.KEY_INT_LAST_WEEK_EP_MW_GESTURE_BAR_COUNT, 0)
        put(Preferences.KEY_INT_EP_DASHBOARD_GESTURE_BAR_COUNT, 0)
        put(Preferences.KEY_INT_LAST_WEEK_EP_DASHBOARD_GESTURE_BAR_COUNT, 0)
        put(Preferences.KEY_INT_EP_SS_GESTURE_FLOATING_COUNT, 0)
        put(Preferences.KEY_INT_LAST_WEEK_EP_SS_GESTURE_FLOATING_COUNT, 0)
        put(Preferences.KEY_INT_EP_MW_GESTURE_FLOATING_COUNT, 0)
        put(Preferences.KEY_INT_LAST_WEEK_EP_MW_GESTURE_FLOATING_COUNT, 0)
        put(Preferences.KEY_INT_IDD_GESTURE_ACTION_DOUBLE_TAP_COUNT, 0)
        put(Preferences.KEY_INT_IDD_LAST_GESTURE_ACTION_DOUBLE_TAP_COUNT, 0)
        put(Preferences.KEY_INT_IDD_GESTURE_ACTION_SCROLL_SIDE_COUNT, 0)
        put(Preferences.KEY_INT_IDD_LAST_GESTURE_ACTION_SCROLL_SIDE_COUNT, 0)
        put(Preferences.KEY_INT_IDD_GESTURE_ACTION_SCROLL_UP_COUNT, 0)
        put(Preferences.KEY_INT_IDD_LAST_GESTURE_ACTION_SCROLL_UP_COUNT, 0)
        put(Preferences.KEY_INT_IDD_GESTURE_ACTION_SCROLL_DOWN_COUNT, 0)
        put(Preferences.KEY_INT_IDD_LAST_GESTURE_ACTION_SCROLL_DOWN_COUNT, 0)
        put(Preferences.KEY_INT_IDD_SWIPE_SIDE_ANGLE_AVERAGE, 0)
        put(Preferences.KEY_INT_IDD_SWIPE_UP_ANGLE_AVERAGE, 0)
        put(Preferences.KEY_INT_IDD_SWIPE_DOWN_ANGLE_AVERAGE, 0)
        put(Preferences.KEY_INT_IDD_LONGTAP_COUNT, 0)
        put(Preferences.KEY_INT_IDD_LAST_LONGTAP_COUNT, 0)
        put(Preferences.KEY_INT_SIDE_TOUCH_VIEW_TRANSPARENCY, 20)
        put(Preferences.KEY_INT_DISPLAY_TOUCH_VIEW_TRANSPARENCY, 30)
        put(Preferences.KEY_INT_DISPLAY_TOUCH_VIEW_ACTIVE_TRANSPARENCY, 20)
        put(Preferences.KEY_INT_DISPLAY_TOUCH_VIEW_IDLE_TRANSPARENCY, 60)
        put(Preferences.KEY_INT_PROMPT_DISPLAY_TOUCH_VIEW_WIDTH, 0)
        put(Preferences.KEY_INT_PROMPT_DISPLAY_TOUCH_VIEW_CENTER_Y, 0)
        put(Preferences.KEY_INT_DISPLAY_TOUCH_VIEW_POSITION_CHANGING_METHOD, 0)
        put(Preferences.KEY_INT_POPUP_PROMPT_SHOW_COUNT, 0)
        put(Preferences.KEY_INT_GESTURE_DOUBLE_TAP, 16)
        put(Preferences.KEY_INT_GESTURE_SCROLL_UP, 1)
        put(Preferences.KEY_INT_GESTURE_SCROLL_SIDE, 16)
        put(Preferences.KEY_INT_GESTURE_SCROLL_DOWN, 2)
        put(Preferences.KEY_INT_LAUNCHER_STATUS, 2)
        put(Preferences.KEY_INT_MULTI_LAUNCHER_STATUS, 2)
        put(Preferences.KEY_INT_PRACTICE_DOUBLE_TAP_STATUS, 0)
        put(Preferences.KEY_INT_PRACTICE_SCROLL_DOWN_STATUS, 0)
        put(Preferences.KEY_INT_PRACTICE_SCROLL_UP_STATUS, 0)
        put(Preferences.KEY_INT_PRACTICE_HEIGHT_TOP, 0)
        put(Preferences.KEY_INT_PRACTICE_HEIGHT_BOTTOM, 0)
        put(Preferences.KEY_INT_PRACTICE_TOUCH_AREA, 0)
        put(Preferences.KEY_INT_LAST_WHATSNEW_VERSION, 0)
        put(Preferences.KEY_INT_TURN_OFF_METHOD, 0)
        put(Preferences.KEY_INT_SCREENSHOT_STATUS, 0)
        put(Preferences.KEY_INT_MENU_SHOW_COUNT_USE, 0)
        put(Preferences.KEY_INT_MENU_SHOW_COUNT_USE_ON_GE, 0)
        put(Preferences.KEY_INT_MENU_SHOW_COUNT_USE_NOT, 0)
        put(Preferences.KEY_INT_LAST_MONTH_MENU_SHOW_COUNT, 0)
        put(Preferences.KEY_INT_LAST_MONTH_MENU_SHOW_COUNT_USE, 0)
        put(Preferences.KEY_INT_LAST_MONTH_MENU_SHOW_COUNT_USE_ON_GE, 0)
        put(Preferences.KEY_INT_LAST_MONTH_MENU_SHOW_COUNT_USE_NOT, 0)
        put(Preferences.KEY_INT_MW_MENU_SHOW_COUNT_USE, 0)
        put(Preferences.KEY_INT_MW_MENU_SHOW_COUNT_USE_NOT, 0)
        put(Preferences.KEY_INT_LAST_MONTH_MW_MENU_SHOW_COUNT, 0)
        put(Preferences.KEY_INT_LAST_MONTH_MW_MENU_SHOW_COUNT_USE, 0)
        put(Preferences.KEY_INT_LAST_MONTH_MW_MENU_SHOW_COUNT_USE_NOT, 0)
        put(Preferences.KEY_INT_TW_MENU_SHOW_COUNT_USE, 0)
        put(Preferences.KEY_INT_TW_MENU_SHOW_COUNT_USE_ON_GE, 0)
        put(Preferences.KEY_INT_LAST_MONTH_TW_MENU_SHOW_COUNT_USE, 0)
        put(Preferences.KEY_INT_LAST_MONTH_TW_MENU_SHOW_COUNT_USE_ON_GE, 0)
        put(Preferences.KEY_INT_FLOATING_VIEW_ORIENTATION, 1)
        put(Preferences.KEY_INT_FLOATING_VIEW_X, 0)
        put(Preferences.KEY_INT_FLOATING_VIEW_Y, 0)
        put(Preferences.KEY_INT_FLOATING_VIEW_X_LAND, 0)
        put(Preferences.KEY_INT_FLOATING_VIEW_Y_LAND, 0)
        put(Preferences.FLOATING_END_THRESHOLD, 18)
        put(Preferences.KEY_INT_FLOATING_TAP_POSITION_X, 0)
        put(Preferences.KEY_INT_FLOATING_TAP_POSITION_Y, 0)
        put(Preferences.KEY_INT_DISPLAY_TOUCH_VIEW_IDLE_ANIMATION_TIME, 1500)
        put(Preferences.KEY_INT_FLOATING_ANIMATION_VIEW_START_MARGIN, 15)
        put(Preferences.KEY_INT_SIDEBAR_START_AND_CLOSE_ANIMATION_TIME, 200)
        put(Preferences.KEY_INT_HEADPHONE_NOTIFICATION_STATUS, 0)
        put(Preferences.KEY_INT_HPC_WIDGET_GUIDANCE_DONE, 0)
        put(Preferences.KEY_INT_DASHBOARD_TIPS_STATUS, 0)
        put(Preferences.KEY_INT_VALID_AREA_Y, 70)
        put(Preferences.KEY_INT_WIDGET_SIZE, 100)
        put(Preferences.KEY_INT_FLING_VELOCITY, Preferences.DEFAULT_VALUE_INT_FLING_VELOCITY)
        put(Preferences.KEY_INT_GESTURE_WAIT_TIME, 500)
        put(
            Preferences.KEY_INT_LOOPS_PROMPT_INTERVAL_TIME,
            Preferences.DEFAULT_VALUE_INT_LOOPS_PROMPT_INTERVAL_TIME
        )
        put(Preferences.KEY_INT_DISPLAY_TOUCH_VIEW_HIDE_TIME, 800)
        put(Preferences.KEY_INT_DISPLAY_TOUCH_VIEW_ANIMATION_TIME, 200)
        put(Preferences.KEY_INT_LOOPS_POSITION, 50)
        put(Preferences.KEY_INT_SWIPE_CHOICE_VIEW_SELECTED_ICON_SIZE, 48)
        put(Preferences.KEY_INT_SWIPE_CHOICE_SCROLL_DISTANCE_R0, 40)
        put(Preferences.KEY_INT_THREE_WAY_SWIPE_DIAGONALLY_UP_ANGLE, 30)
        put(
            Preferences.KEY_INT_THREE_WAY_SWIPE_DIAGONALLY_DOWN_ANGLE,
            Preferences.DEFAULT_VALUE_INT_THREE_WAY_SWIPE_DIAGONALLY_DOWN_ANGLE
        )
        put(Preferences.KEY_INT_IDD_SWIPE_SIDE_DISTANCE_AVERAGE, 0)
        put(Preferences.KEY_INT_IDD_SWIPE_UP_DISTANCE_AVERAGE, 0)
        put(Preferences.KEY_INT_IDD_SWIPE_DOWN_DISTANCE_AVERAGE, 0)
        put(Preferences.KEY_INT_SWIPE_CHOICE_VIEW_BASE_POSITION_X_WHEN_SWIPE_SIDE, 90)
        put(Preferences.KEY_INT_SWIPE_CHOICE_VIEW_BASE_POSITION_Y_WHEN_SWIPE_SIDE, 90)
        put(Preferences.KEY_INT_SWIPE_CHOICE_VIEW_BASE_POSITION_X_WHEN_SWIPE_UP, 90)
        put(Preferences.KEY_INT_SWIPE_CHOICE_VIEW_BASE_POSITION_Y_WHEN_SWIPE_UP, 90)
        put(Preferences.KEY_INT_SWIPE_CHOICE_VIEW_BASE_POSITION_X_WHEN_SWIPE_DOWN, 90)
        put(Preferences.KEY_INT_SWIPE_CHOICE_VIEW_BASE_POSITION_Y_WHEN_SWIPE_DOWN, 90)
        put(Preferences.KEY_INT_SWIPE_CHOICE_VIEW_MOVE_CONSTRAINTS_X, 30)
        put(Preferences.KEY_INT_SWIPE_CHOICE_VIEW_MOVE_CONSTRAINTS_Y, 30)
        put(Preferences.KEY_INT_SWIPE_CHOICE_VIEW_TIME_CONSTANT, 400)
        put(Preferences.KEY_INT_SWIPE_CHOICE_VIEW_FADE_OUT_ANIMATION_TIME, 400)
    }

    private val mLongMap: MutableMap<String, Long> = mutableMapOf<String, Long>().apply {
        put(Preferences.KEY_LONG_INITIAL_USE_TIME, 0L)
        put(Preferences.KEY_LONG_LAST_SHOW_LOOPS_PROMPT_TIME, 0L)
        put(Preferences.KEY_LONG_IDD_DASHBOARD_SHOW_TIME_AVERAGE, 0L)
        put(Preferences.KEY_LONG_IDD_SWIPE_SIDE_TIME_AVERAGE, 0L)
        put(Preferences.KEY_LONG_IDD_SWIPE_UP_TIME_AVERAGE, 0L)
        put(Preferences.KEY_LONG_IDD_SWIPE_DOWN_TIME_AVERAGE, 0L)
    }

    private val mSharedPrefs: SharedPreferences =
        PreferenceManager.getDefaultSharedPreferences(context)

    init {
        putDefaultValueFromResources()
        initScreenshotStatus()
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    private fun getIddGestureAngleAveragePreference(str: String?): String? {
        return when (str) {
            KEY_INT_GESTURE_LONG_TAP -> KEY_INT_IDD_LONGTAP_COUNT
            KEY_INT_GESTURE_SCROLL_UP -> KEY_INT_IDD_SWIPE_UP_ANGLE_AVERAGE
            KEY_INT_GESTURE_DOUBLE_TAP -> KEY_INT_IDD_GESTURE_ACTION_DOUBLE_TAP_COUNT
            KEY_INT_GESTURE_SCROLL_DOWN -> KEY_INT_IDD_SWIPE_DOWN_ANGLE_AVERAGE
            KEY_INT_GESTURE_SCROLL_SIDE -> KEY_INT_IDD_SWIPE_SIDE_ANGLE_AVERAGE
            else -> null
        }
    }

    private fun getIddGestureCountPreference(str: String?): String? {
        requireNotNull(str)

        return when (str) {
            KEY_INT_GESTURE_LONG_TAP -> KEY_INT_IDD_LONGTAP_COUNT
            KEY_INT_GESTURE_SCROLL_UP -> KEY_INT_IDD_GESTURE_ACTION_SCROLL_UP_COUNT
            KEY_INT_GESTURE_DOUBLE_TAP -> KEY_INT_IDD_GESTURE_ACTION_DOUBLE_TAP_COUNT
            KEY_INT_GESTURE_SCROLL_DOWN -> KEY_INT_IDD_GESTURE_ACTION_SCROLL_DOWN_COUNT
            KEY_INT_GESTURE_SCROLL_SIDE -> KEY_INT_IDD_GESTURE_ACTION_SCROLL_SIDE_COUNT
            else -> null
        }
    }


    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    private fun getIddGestureDistanceAveragePreference(str: String?): String? {
        return when (str) {
            KEY_INT_GESTURE_LONG_TAP -> KEY_INT_IDD_LONGTAP_COUNT
            KEY_INT_GESTURE_SCROLL_UP -> KEY_INT_IDD_SWIPE_UP_DISTANCE_AVERAGE
            KEY_INT_GESTURE_DOUBLE_TAP -> KEY_INT_IDD_GESTURE_ACTION_DOUBLE_TAP_COUNT
            KEY_INT_GESTURE_SCROLL_DOWN -> KEY_INT_IDD_SWIPE_DOWN_DISTANCE_AVERAGE
            KEY_INT_GESTURE_SCROLL_SIDE -> KEY_INT_IDD_SWIPE_SIDE_DISTANCE_AVERAGE
            else -> null
        }
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    private fun getIddGestureTimeAveragePreference(str: String?): String? {
        return when (str) {
            KEY_INT_GESTURE_LONG_TAP -> null
            KEY_INT_GESTURE_SCROLL_UP -> KEY_LONG_IDD_SWIPE_UP_TIME_AVERAGE
            KEY_INT_GESTURE_DOUBLE_TAP -> null
            KEY_INT_GESTURE_SCROLL_DOWN -> KEY_LONG_IDD_SWIPE_DOWN_TIME_AVERAGE
            KEY_INT_GESTURE_SCROLL_SIDE -> KEY_LONG_IDD_SWIPE_SIDE_TIME_AVERAGE
            else -> null
        }
    }

    private fun getIddLastGestureCountPreference(str: String?): String? {
        requireNotNull(str)

        return when (str) {
            KEY_INT_GESTURE_LONG_TAP -> KEY_INT_IDD_LAST_LONGTAP_COUNT
            KEY_INT_GESTURE_SCROLL_UP -> KEY_INT_IDD_LAST_GESTURE_ACTION_SCROLL_UP_COUNT
            KEY_INT_GESTURE_DOUBLE_TAP -> KEY_INT_IDD_LAST_GESTURE_ACTION_DOUBLE_TAP_COUNT
            KEY_INT_GESTURE_SCROLL_DOWN -> KEY_INT_IDD_LAST_GESTURE_ACTION_SCROLL_DOWN_COUNT
            KEY_INT_GESTURE_SCROLL_SIDE -> KEY_INT_IDD_LAST_GESTURE_ACTION_SCROLL_SIDE_COUNT
            else -> null
        }
    }

    private fun initScreenshotStatus() {
        putScreenshotStatus(0)
    }

    private fun putDefaultValueFromResources() {
        val sideTouchViewParams: TouchViewUtil.TouchViewParams =
            TouchViewUtil.getSideTouchViewParams()!!
        mIntegerMap[KEY_INT_SIDE_TOUCH_VIEW_HEIGHT] =
            Integer.valueOf(sideTouchViewParams.defHeight - sideTouchViewParams.minHeight)
        mIntegerMap[KEY_INT_SIDE_TOUCH_VIEW_POSITION] =
            Integer.valueOf(sideTouchViewParams.defPosition - sideTouchViewParams.minPosition)
        val displayTouchViewParamsPort: TouchViewUtil.TouchViewParams =
            TouchViewUtil.getDisplayTouchViewParamsPort()!!
        mIntegerMap[KEY_INT_DISPLAY_TOUCH_VIEW_HEIGHT] =
            Integer.valueOf(displayTouchViewParamsPort.defHeight - displayTouchViewParamsPort.minHeight)
        mIntegerMap[KEY_INT_DISPLAY_TOUCH_VIEW_WIDTH] =
            Integer.valueOf(displayTouchViewParamsPort.defWidth - displayTouchViewParamsPort.minWidth)
        mIntegerMap[KEY_INT_DISPLAY_TOUCH_VIEW_POSITION] =
            Integer.valueOf(displayTouchViewParamsPort.defPosition - displayTouchViewParamsPort.minPosition)
        val displayTouchViewParamsLand: TouchViewUtil.TouchViewParams =
            TouchViewUtil.getDisplayTouchViewParamsLand()!!
        mIntegerMap[KEY_INT_DISPLAY_TOUCH_VIEW_HEIGHT_LAND] =
            Integer.valueOf(displayTouchViewParamsLand.defHeight - displayTouchViewParamsLand.minHeight)
        mIntegerMap[KEY_INT_DISPLAY_TOUCH_VIEW_WIDTH_LAND] =
            Integer.valueOf(displayTouchViewParamsLand.defWidth - displayTouchViewParamsLand.minWidth)
        mIntegerMap[KEY_INT_DISPLAY_TOUCH_VIEW_POSITION_LAND] =
            Integer.valueOf(displayTouchViewParamsLand.defPosition - displayTouchViewParamsLand.minPosition)
        mIntegerMap[KEY_INT_DISPLAYTOUCH_LONG_PRESS_TIMEOUT] =
            ViewConfiguration.getLongPressTimeout()
    }

    fun getAutoAddDialogStatusForShortcut(context: Context, str: String): Int {
        val i: Int
        val str2: String
        val stringArray = context.resources.getStringArray(R.array.auto_add_ali_pay_list)
        val stringArray2 = context.resources.getStringArray(R.array.auto_add_we_chat_pay_list)
        if (str == stringArray[0]) {
            i = DEFAULT_VALUE_DIALOG_STATUS
            str2 = KEY_INT_DIALOG_STATUS_ALI_PAY_PAY
        } else if (str == stringArray[1]) {
            i = DEFAULT_VALUE_DIALOG_STATUS
            str2 = KEY_INT_DIALOG_STATUS_ALI_PAY_SCAN
        } else if (str == stringArray2[0]) {
            i = DEFAULT_VALUE_DIALOG_STATUS
            str2 = KEY_INT_DIALOG_STATUS_WE_CHAT_PAY_PAY
        } else if (str != stringArray2[1]) {
            return -1
        } else {
            i = DEFAULT_VALUE_DIALOG_STATUS
            str2 = KEY_INT_DIALOG_STATUS_WE_CHAT_PAY_SCAN
        }
        return getInt(str2, i)
    }

    fun getBoolean(str: String): Boolean {
        val z = mSharedPrefs.getBoolean(
            str,
            mBooleanMap[str]!!
        )
        val sb =
            StringBuilder().append(TAG).append(".getBoolean: key=").append(str).append(" value=")
                .append(z)
        LogUtil.d(LogUtil.LOG_TAG, sb.toString())
        return z
    }

    val dashboardShowCount: Int
        get() = getInt(KEY_INT_DASHBOARD_SHOW_COUNT)

    val dashboardShowCountUse: Int
        get() = getInt(KEY_INT_DASHBOARD_SHOW_COUNT_USE)

    val dashboardShowCountUseNot: Int
        get() = getInt(KEY_INT_DASHBOARD_SHOW_COUNT_USE_NOT)


    var dashboardShowTimeAverage: Long = 0L
        get() = getLong(KEY_LONG_IDD_DASHBOARD_SHOW_TIME_AVERAGE)
        set(j) {
            val dashboardShowCount = dashboardShowCount
            if (dashboardShowCount <= 0) {
                return
            }
            val dashboardShowTimeAverage = field
            val currentTimeMillis = System.currentTimeMillis() - j
            val j2 =
                (((dashboardShowCount - 1) * dashboardShowTimeAverage) + currentTimeMillis) / dashboardShowCount
            val sb = StringBuilder().apply {
                append(TAG).append(".setDashboardShowTimeAverage currentShowCount = ")
                    .append(dashboardShowCount)
                append(" lastAverage = ").append(dashboardShowTimeAverage)
                append(" currentShowTime = ").append(currentTimeMillis)
                append(" average =").append(j2)
            }
            LogUtil.d(LogUtil.LOG_TAG, sb.toString())
            putLong(KEY_LONG_IDD_DASHBOARD_SHOW_TIME_AVERAGE, j2)
            field = j
        }


    val epDashboardGestureBarCount: Int
        get() = getInt(KEY_INT_EP_DASHBOARD_GESTURE_BAR_COUNT)

    val epDashboardHomeCount: Int
        get() = getInt(KEY_INT_EP_DASHBOARD_HOME_COUNT)

    val epMwGestureBarCount: Int
        get() = getInt(KEY_INT_EP_MW_GESTURE_BAR_COUNT)

    val epMwGestureFloatingCount: Int
        get() = getInt(KEY_INT_EP_MW_GESTURE_FLOATING_COUNT)

    val epMwHomeCount: Int
        get() = getInt(KEY_INT_EP_MW_HOME_COUNT)

    val epSsGestureBarCount: Int
        get() = getInt(KEY_INT_EP_SS_GESTURE_BAR_COUNT)

    val epSsGestureFloatingCount: Int
        get() = getInt(KEY_INT_EP_SS_GESTURE_FLOATING_COUNT)

    val epSsHomeCount: Int
        get() = getInt(KEY_INT_EP_SS_HOME_COUNT)

    val floatingChangedCount: Int
        get() = getInt(KEY_INT_FLOATING_CHANGED_COUNT)

    fun getGestureAngleAverage(str: String): Int {
        val iddGestureAngleAveragePreference = getIddGestureAngleAveragePreference(str) ?: return -1
        return getInt(iddGestureAngleAveragePreference)
    }

    fun getGestureCount(i: Int): Int {
        return GESTURE_ACTIONS_COUNTER[i]!!
    }

    fun getGestureCount(str: String): Int {
        val iddGestureCountPreference = getIddGestureCountPreference(str) ?: return -1
        return getInt(iddGestureCountPreference)
    }

    fun getGestureDistanceAverage(str: String): Int {
        val iddGestureDistanceAveragePreference =
            getIddGestureDistanceAveragePreference(str) ?: return -1
        return getInt(iddGestureDistanceAveragePreference)
    }

    fun getGestureTimeAverage(str: String): Long {
        val iddGestureTimeAveragePreference = getIddGestureTimeAveragePreference(str) ?: return -1L
        return getLong(iddGestureTimeAveragePreference)
    }

    val guideMwPageCount: Int
        get() = getInt(KEY_INT_GUIDE_MW_PAGE_COUNT)

    val guideSsPageCount: Int
        get() = getInt(KEY_INT_GUIDE_SS_PAGE_COUNT)

    val guideTopPageCount: Int
        get() = getInt(KEY_INT_GUIDE_TOP_PAGE_COUNT)

    val guideTwPageCount: Int
        get() = getInt(KEY_INT_GUIDE_TW_PAGE_COUNT)

    val initialUseTime: Long
        get() = getLong(KEY_LONG_INITIAL_USE_TIME)

    fun getInt(str: String): Int {
        val defaultValue = mIntegerMap[str] ?: 0
        val value = mSharedPrefs.getInt(str, defaultValue)
        val sb = StringBuilder().apply {
            append(TAG).append(".getInt: key=").append(str).append(" value=").append(value)
        }
        LogUtil.d(LogUtil.LOG_TAG, sb.toString())
        return value
    }

    val isNtpBasedInitialUseTime: Boolean
        get() = getBoolean(KEY_BOOLEAN_IS_NTP_BASED_INITIAL_USE_TIME)

    fun getLastGestureCount(i: Int): Int {
        return LAST_MONTH_GESTURE_ACTIONS_COUNTER[i]!!
    }

    var lastMonthFloatingChangedCount: Int
        get() = getInt(KEY_INT_LAST_MONTH_FLOATING_CHANGED_COUNT)
        set(i) {
            putInt(KEY_INT_LAST_MONTH_FLOATING_CHANGED_COUNT, i)
        }

    var lastMonthMenuShowCount: Int
        get() = getInt(KEY_INT_LAST_MONTH_MENU_SHOW_COUNT)
        set(i) {
            putInt(KEY_INT_LAST_MONTH_MENU_SHOW_COUNT, i)
        }

    var lastMonthMenuShowCountUse: Int
        get() = getInt(KEY_INT_LAST_MONTH_MENU_SHOW_COUNT_USE)
        set(i) {
            putInt(KEY_INT_LAST_MONTH_MENU_SHOW_COUNT_USE, i)
        }

    var lastMonthMenuShowCountUseNot: Int
        get() = getInt(KEY_INT_LAST_MONTH_MENU_SHOW_COUNT_USE_NOT)
        set(i) {
            putInt(KEY_INT_LAST_MONTH_MENU_SHOW_COUNT_USE_NOT, i)
        }

    var lastMonthMenuShowCountUseOnGe: Int
        get() = getInt(KEY_INT_LAST_MONTH_MENU_SHOW_COUNT_USE_ON_GE)
        set(i) {
            putInt(KEY_INT_LAST_MONTH_MENU_SHOW_COUNT_USE_ON_GE, i)
        }

    var lastMonthMwMenuShowCount: Int
        get() = getInt(KEY_INT_LAST_MONTH_MW_MENU_SHOW_COUNT)
        set(i) {
            putInt(KEY_INT_LAST_MONTH_MW_MENU_SHOW_COUNT, i)
        }

    var lastMonthMwMenuShowCountUse: Int
        get() = getInt(KEY_INT_LAST_MONTH_MW_MENU_SHOW_COUNT_USE)
        set(i) {
            putInt(KEY_INT_LAST_MONTH_MW_MENU_SHOW_COUNT_USE, i)
        }

    var lastMonthMwMenuShowCountUseNot: Int
        get() = getInt(KEY_INT_LAST_MONTH_MW_MENU_SHOW_COUNT_USE_NOT)
        set(i) {
            putInt(KEY_INT_LAST_MONTH_MW_MENU_SHOW_COUNT_USE_NOT, i)
        }

    var lastMonthTwMenuShowCountUse: Int
        get() = getInt(KEY_INT_LAST_MONTH_TW_MENU_SHOW_COUNT_USE)
        set(i) {
            putInt(KEY_INT_LAST_MONTH_TW_MENU_SHOW_COUNT_USE, i)
        }

    var lastMonthTwMenuShowCountUseOnGe: Int
        get() = getInt(KEY_INT_LAST_MONTH_TW_MENU_SHOW_COUNT_USE_ON_GE)
        set(i) {
            putInt(KEY_INT_LAST_MONTH_TW_MENU_SHOW_COUNT_USE_ON_GE, i)
        }

    fun getLastPermissionDialogShowCount(permissionGroup: PermissionGroup): Int {
        return LAST_PERMISSION_DIALOG_SHOW_COUNT[permissionGroup]!!
    }

    fun getLastShortcutsLongTapCount(str: String): Int {
        return LAST_SHORTCUTS_LONG_TAP_COUNTER[str]!!
    }

    fun getLastShortcutsTapCount(str: String): Int {
        return LAST_SHORTCUTS_TAP_COUNTER[str]!!
    }

    val lastShowLoopsPromptTime: Long
        get() = getLong(KEY_LONG_LAST_SHOW_LOOPS_PROMPT_TIME)

    var lastWeekDashboardShowCount: Int
        get() = getInt(KEY_INT_LAST_DASHBOARD_SHOW_COUNT)
        set(i) {
            putInt(KEY_INT_LAST_DASHBOARD_SHOW_COUNT, i)
        }

    var lastWeekDashboardShowCountUse: Int
        get() = getInt(KEY_INT_LAST_DASHBOARD_SHOW_COUNT_USE)
        set(i) {
            putInt(KEY_INT_LAST_DASHBOARD_SHOW_COUNT_USE, i)
        }

    var lastWeekDashboardShowCountUseNot: Int
        get() = getInt(KEY_INT_LAST_DASHBOARD_SHOW_COUNT_USE_NOT)
        set(i) {
            putInt(KEY_INT_LAST_DASHBOARD_SHOW_COUNT_USE_NOT, i)
        }

    var lastWeekEpDashboardGestureBarCount: Int
        get() = getInt(KEY_INT_LAST_WEEK_EP_DASHBOARD_GESTURE_BAR_COUNT)
        set(i) {
            putInt(KEY_INT_LAST_WEEK_EP_DASHBOARD_GESTURE_BAR_COUNT, i)
        }

    var lastWeekEpDashboardHomeCount: Int
        get() = getInt(KEY_INT_LAST_WEEK_EP_DASHBOARD_HOME_COUNT)
        set(i) {
            putInt(KEY_INT_LAST_WEEK_EP_DASHBOARD_HOME_COUNT, i)
        }

    var lastWeekEpMwGestureBarCount: Int
        get() = getInt(KEY_INT_LAST_WEEK_EP_MW_GESTURE_BAR_COUNT)
        set(i) {
            putInt(KEY_INT_LAST_WEEK_EP_MW_GESTURE_BAR_COUNT, i)
        }

    var lastWeekEpMwGestureFloatingCount: Int
        get() = getInt(KEY_INT_LAST_WEEK_EP_MW_GESTURE_FLOATING_COUNT)
        set(i) {
            putInt(KEY_INT_LAST_WEEK_EP_MW_GESTURE_FLOATING_COUNT, i)
        }

    var lastWeekEpMwHomeCount: Int
        get() = getInt(KEY_INT_LAST_WEEK_EP_MW_HOME_COUNT)
        set(i) {
            putInt(KEY_INT_LAST_WEEK_EP_MW_HOME_COUNT, i)
        }

    var lastWeekEpSsGestureBarCount: Int
        get() = getInt(KEY_INT_LAST_WEEK_EP_SS_GESTURE_BAR_COUNT)
        set(i) {
            putInt(KEY_INT_LAST_WEEK_EP_SS_GESTURE_BAR_COUNT, i)
        }

    var lastWeekEpSsGestureFloatingCount: Int
        get() = getInt(KEY_INT_LAST_WEEK_EP_SS_GESTURE_FLOATING_COUNT)
        set(i) {
            putInt(KEY_INT_LAST_WEEK_EP_SS_GESTURE_FLOATING_COUNT, i)
        }

    var lastWeekEpSsHomeCount: Int
        get() = getInt(KEY_INT_LAST_WEEK_EP_SS_HOME_COUNT)
        set(i) {
            putInt(KEY_INT_LAST_WEEK_EP_SS_HOME_COUNT, i)
        }

    fun getLastWeekGestureCount(str: String): Int {
        val iddLastGestureCountPreference = getIddLastGestureCountPreference(str) ?: return -1
        return getInt(iddLastGestureCountPreference)
    }

    var lastWeekMenuSettingsIconCount: Int
        get() = getInt(KEY_INT_LAST_WEEK_MENU_SETTINGS_ICON_COUNT)
        set(i) {
            putInt(KEY_INT_LAST_WEEK_MENU_SETTINGS_ICON_COUNT, i)
        }

    var lastWeekMwMenuSwipeCount: Int
        get() = getInt(KEY_INT_LAST_WEEK_MW_MENU_SWIPE_COUNT)
        set(i) {
            putInt(KEY_INT_LAST_WEEK_MW_MENU_SWIPE_COUNT, i)
        }

    var lastWeekMwMenuTabCount: Int
        get() = getInt(KEY_INT_LAST_WEEK_MW_MENU_TAB_COUNT)
        set(i) {
            putInt(KEY_INT_LAST_WEEK_MW_MENU_TAB_COUNT, i)
        }

    var lastWeekMwToolCancelCount: Int
        get() = getInt(KEY_INT_LAST_WEEK_MW_TOOL_CANCEL_COUNT)
        set(i) {
            putInt(KEY_INT_LAST_WEEK_MW_TOOL_CANCEL_COUNT, i)
        }

    var lastWeekMwToolSwapCount: Int
        get() = getInt(KEY_INT_LAST_WEEK_MW_TOOL_SWAP_COUNT)
        set(i) {
            putInt(KEY_INT_LAST_WEEK_MW_TOOL_SWAP_COUNT, i)
        }

    var lastWeekMwToolTwCount: Int
        get() = getInt(KEY_INT_LAST_WEEK_MW_TOOL_TW_COUNT)
        set(i) {
            putInt(KEY_INT_LAST_WEEK_MW_TOOL_TW_COUNT, i)
        }

    var lastWeekSsMenuSwipeCount: Int
        get() = getInt(KEY_INT_LAST_WEEK_SS_MENU_SWIPE_COUNT)
        set(i) {
            putInt(KEY_INT_LAST_WEEK_SS_MENU_SWIPE_COUNT, i)
        }

    var lastWeekSsMenuTabCount: Int
        get() = getInt(KEY_INT_LAST_WEEK_SS_MENU_TAB_COUNT)
        set(i) {
            putInt(KEY_INT_LAST_WEEK_SS_MENU_TAB_COUNT, i)
        }

    var lastWeekToolMuteCount: Int
        get() = getInt(KEY_INT_LAST_WEEK_TOOL_MUTE_COUNT)
        set(i) {
            putInt(KEY_INT_LAST_WEEK_TOOL_MUTE_COUNT, i)
        }

    var lastWeekToolNotificationCount: Int
        get() = getInt(KEY_INT_LAST_WEEK_TOOL_NOTIFICATION_COUNT)
        set(i) {
            putInt(KEY_INT_LAST_WEEK_TOOL_NOTIFICATION_COUNT, i)
        }

    var lastWeekToolScreenshotCount: Int
        get() = getInt(KEY_INT_LAST_WEEK_TOOL_SCREENSHOT_COUNT)
        set(i) {
            putInt(KEY_INT_LAST_WEEK_TOOL_SCREENSHOT_COUNT, i)
        }

    val lastWhatsnewVersion: Int
        get() = getInt(KEY_INT_LAST_WHATSNEW_VERSION)

    fun getLeftHandStatus(i: Int): Boolean {
        return getBoolean(
            if (i == getInt(KEY_INT_GESTURE_SCROLL_UP)) KEY_BOOLEAN_SCROLL_UP_LEFT_HAND_STATUS else if (i == getInt(
                    KEY_INT_GESTURE_SCROLL_DOWN
                )
            ) KEY_BOOLEAN_SCROLL_DOWN_LEFT_HAND_STATUS else KEY_BOOLEAN_LEFT_HAND_STATUS
        )
    }

    fun getLong(str: String): Long {
        val j = mSharedPrefs.getLong(
            str,
            mLongMap[str]!!
        )
        val sb = StringBuilder()
            .append(TAG)
            .append(".getLong: key=")
            .append(str)
            .append(" value=")
            .append(j)
        LogUtil.d(LogUtil.LOG_TAG, sb.toString())
        return j
    }

    val loopsPosition: Int
        get() = getInt(KEY_INT_LOOPS_POSITION)

    val loopsPromptShowCount: Int
        get() = getInt(KEY_INT_LOOPS_PROMPT_SHOW_COUNT)

    val loopsPromptShowCountToStop: Int
        get() = getInt(KEY_INT_LOOPS_PROMPT_SHOW_COUNT_TO_STOP)

    val menuSettingsIconCount: Int
        get() = getInt(KEY_INT_MENU_SETTINGS_ICON_COUNT)

    val menuShowCount: Int
        get() = getInt(KEY_INT_SS_MENU_SHOW_COUNT)

    val menuShowCountUse: Int
        get() = getInt(KEY_INT_MENU_SHOW_COUNT_USE)

    val menuShowCountUseNot: Int
        get() = getInt(KEY_INT_MENU_SHOW_COUNT_USE_NOT)

    val menuShowCountUseOnGe: Int
        get() = getInt(KEY_INT_MENU_SHOW_COUNT_USE_ON_GE)

    var movePosition: Int
        get() = getInt(KEY_INT_MOVE_POSITION, 0)
        set(i) {
            putInt(KEY_INT_MOVE_POSITION, i)
        }

    val mwMenuShowCount: Int
        get() = getInt(KEY_INT_MW_MENU_SHOW_COUNT)

    val mwMenuShowCountUse: Int
        get() = getInt(KEY_INT_MW_MENU_SHOW_COUNT_USE)

    val mwMenuShowCountUseNot: Int
        get() = getInt(KEY_INT_MW_MENU_SHOW_COUNT_USE_NOT)

    val mwMenuSwipeCount: Int
        get() = getInt(KEY_INT_MW_MENU_SWIPE_COUNT)

    val mwMenuTabCount: Int
        get() = getInt(KEY_INT_MW_MENU_TAB_COUNT)

    val mwOobeShowCount: Int
        get() = getInt(KEY_INT_MW_OOBE_SHOW_COUNT)

    val mwToolCancelCount: Int
        get() = getInt(KEY_INT_MW_TOOL_CANCEL_COUNT)

    val mwToolSwapCount: Int
        get() = getInt(KEY_INT_MW_TOOL_SWAP_COUNT)

    val mwToolTwCount: Int
        get() = getInt(KEY_INT_MW_TOOL_TW_COUNT)

    val oobeShowCount: Int
        get() = getInt(KEY_INT_SS_OOBE_SHOW_COUNT)

    fun getPermissionDialogShowCount(permissionGroup: PermissionGroup): Int {
        return PERMISSION_DIALOG_SHOW_COUNT[permissionGroup]!!
    }

    val popupPromptShowCount: Int
        get() = getInt(KEY_INT_POPUP_PROMPT_SHOW_COUNT)

    val screenshotStatus: Int
        get() = getInt(KEY_INT_SCREENSHOT_STATUS)

    val scrollSideCount: Int
        get() = getInt(KEY_INT_SCROLL_SIDE_COUNT)

    fun getShortcutsLongTapCount(str: String): Int {
        return SHORTCUTS_LONG_TAP_COUNTER[str]!!
    }

    fun getShortcutsTapCount(str: String): Int {
        return SHORTCUTS_TAP_COUNTER[str]!!
    }

    val showLoopsIntervalTime: Int
        get() = getInt(KEY_INT_LOOPS_PROMPT_INTERVAL_TIME)

    val ssMenuSwipeCount: Int
        get() = getInt(KEY_INT_SS_MENU_SWIPE_COUNT)

    val ssMenuTabCount: Int
        get() = getInt(KEY_INT_SS_MENU_TAB_COUNT)

    fun getThumPositionX(i: Int): Int {
        return getInt(
            if (i == getInt(KEY_INT_GESTURE_SCROLL_UP)) KEY_INT_SCROLL_UP_THUMB_POSITION_X else if (i == getInt(
                    KEY_INT_GESTURE_SCROLL_DOWN
                )
            ) KEY_INT_SCROLL_DOWN_THUMB_POSITION_X else KEY_INT_THUMB_POSITION_X
        )
    }

    fun getThumPositionY(i: Int): Int {
        return getInt(
            if (i == getInt(KEY_INT_GESTURE_SCROLL_UP)) KEY_INT_SCROLL_UP_THUMB_POSITION_Y else if (i == getInt(
                    KEY_INT_GESTURE_SCROLL_DOWN
                )
            ) KEY_INT_SCROLL_DOWN_THUMB_POSITION_Y else KEY_INT_THUMB_POSITION_Y
        )
    }

    val toolMuteCount: Int
        get() = getInt(KEY_INT_TOOL_MUTE_COUNT)

    val toolNotificationCount: Int
        get() = getInt(KEY_INT_TOOL_NOTIFICATION_COUNT)

    val toolScreenshotCount: Int
        get() = getInt(KEY_INT_TOOL_SCREENSHOT_COUNT)

    fun getTranslationX(i: Int): Int {
        return getInt(
            if (i == getInt(KEY_INT_GESTURE_SCROLL_UP)) KEY_INT_SCROLL_UP_TRANSLATION_X else if (i == getInt(
                    KEY_INT_GESTURE_SCROLL_DOWN
                )
            ) KEY_INT_SCROLL_DOWN_TRANSLATION_X else KEY_INT_TRANSLATION_X
        )
    }

    fun getTranslationY(i: Int): Int {
        return getInt(
            if (i == getInt(KEY_INT_GESTURE_SCROLL_UP)) KEY_INT_SCROLL_UP_TRANSLATION_Y else if (i == getInt(
                    KEY_INT_GESTURE_SCROLL_DOWN
                )
            ) KEY_INT_SCROLL_DOWN_TRANSLATION_Y else KEY_INT_TRANSLATION_Y
        )
    }

    val twMenuShowCountUse: Int
        get() = getInt(KEY_INT_TW_MENU_SHOW_COUNT_USE)

    val twMenuShowCountUseOnGe: Int
        get() = getInt(KEY_INT_TW_MENU_SHOW_COUNT_USE_ON_GE)

    val versionHistory: List<Int>
        get() {
            val arrayList: ArrayList<Int> = ArrayList()
            val string =
                mSharedPrefs.getString(KEY_VERSION_HISTORY, ShortcutUtil.SHORTCUT_NAME_NULL)
            if (string != ShortcutUtil.SHORTCUT_NAME_NULL) {
                try {
                    val jSONArray = JSONArray(string)
                    for (i in 0 until jSONArray.length()) {
                        arrayList.add(jSONArray.getString(i).toInt())
                    }
                } catch (unused: Exception) {
                    // Handle exception if needed
                }
            }
            return arrayList
        }

    fun incrementDashboardShowCount() {
        m3287i(this, KEY_INT_DASHBOARD_SHOW_COUNT, 1, KEY_INT_DASHBOARD_SHOW_COUNT)
    }

    fun incrementDashboardShowCountUse() {
        putInt(KEY_INT_DASHBOARD_SHOW_COUNT_USE, dashboardShowCountUse + 1)
    }

    fun incrementDashboardShowCountUseNot() {
        putInt(KEY_INT_DASHBOARD_SHOW_COUNT_USE_NOT, dashboardShowCountUseNot + 1)
    }

    fun incrementEpDashboardGestureBarCount() {
        m3287i(
            this,
            KEY_INT_EP_DASHBOARD_GESTURE_BAR_COUNT,
            1,
            KEY_INT_EP_DASHBOARD_GESTURE_BAR_COUNT
        )
    }

    fun incrementEpDashboardHomeCount() {
        m3287i(this, KEY_INT_EP_DASHBOARD_HOME_COUNT, 1, KEY_INT_EP_DASHBOARD_HOME_COUNT)
    }

    fun incrementEpMwGestureBarCount() {
        m3287i(this, KEY_INT_EP_MW_GESTURE_BAR_COUNT, 1, KEY_INT_EP_MW_GESTURE_BAR_COUNT)
    }

    fun incrementEpMwGestureFloatingCount() {
        m3287i(
            this,
            KEY_INT_EP_MW_GESTURE_FLOATING_COUNT,
            1,
            KEY_INT_EP_MW_GESTURE_FLOATING_COUNT
        )
    }

    fun incrementEpMwHomeCount() {
        m3287i(this, KEY_INT_EP_MW_HOME_COUNT, 1, KEY_INT_EP_MW_HOME_COUNT)
    }

    fun incrementEpSsGestureBarCount() {
        m3287i(this, KEY_INT_EP_SS_GESTURE_BAR_COUNT, 1, KEY_INT_EP_SS_GESTURE_BAR_COUNT)
    }

    fun incrementEpSsGestureFloatingCount() {
        m3287i(
            this,
            KEY_INT_EP_SS_GESTURE_FLOATING_COUNT,
            1,
            KEY_INT_EP_SS_GESTURE_FLOATING_COUNT
        )
    }

    fun incrementEpSsHomeCount() {
        m3287i(this, KEY_INT_EP_SS_HOME_COUNT, 1, KEY_INT_EP_SS_HOME_COUNT)
    }

    fun incrementFloatingChangedCount() {
        m3287i(this, KEY_INT_FLOATING_CHANGED_COUNT, 1, KEY_INT_FLOATING_CHANGED_COUNT)
    }

    fun incrementGestureCount(i: Int) {
        GESTURE_ACTIONS_COUNTER[i] =
            getGestureCount(i) + 1
    }

    fun incrementGestureCount(str: String) {
        val iddGestureCountPreference = getIddGestureCountPreference(str) ?: return
        m3287i(this, iddGestureCountPreference, 1, iddGestureCountPreference)
    }

    fun incrementGuideMwPageCount() {
        putInt(KEY_INT_GUIDE_MW_PAGE_COUNT, guideMwPageCount + 1)
    }

    fun incrementGuideSsPageCount() {
        putInt(KEY_INT_GUIDE_SS_PAGE_COUNT, guideSsPageCount + 1)
    }

    fun incrementGuideTopPageCount() {
        putInt(KEY_INT_GUIDE_TOP_PAGE_COUNT, guideTopPageCount + 1)
    }

    fun incrementGuideTwPageCount() {
        putInt(KEY_INT_GUIDE_TW_PAGE_COUNT, guideTwPageCount + 1)
    }

    fun incrementLoopsPromptShowCount() {
        putLoopsPromptShowCount(getInt(KEY_INT_LOOPS_PROMPT_SHOW_COUNT) + 1)
    }

    fun incrementMenuSettingsIconCount() {
        m3287i(this, KEY_INT_MENU_SETTINGS_ICON_COUNT, 1, KEY_INT_MENU_SETTINGS_ICON_COUNT)
    }

    fun incrementMenuShowCount() {
        m3287i(this, KEY_INT_SS_MENU_SHOW_COUNT, 1, KEY_INT_SS_MENU_SHOW_COUNT)
    }

    fun incrementMenuShowCountUse() {
        putInt(KEY_INT_MENU_SHOW_COUNT_USE, menuShowCountUse + 1)
    }

    fun incrementMenuShowCountUseNot() {
        putInt(KEY_INT_MENU_SHOW_COUNT_USE_NOT, menuShowCountUseNot + 1)
    }

    fun incrementMenuShowCountUseOnGe() {
        putInt(KEY_INT_MENU_SHOW_COUNT_USE_ON_GE, menuShowCountUseOnGe + 1)
    }

    fun incrementMwMenuShowCount() {
        m3287i(this, KEY_INT_MW_MENU_SHOW_COUNT, 1, KEY_INT_MW_MENU_SHOW_COUNT)
    }

    fun incrementMwMenuShowCountUse() {
        putInt(KEY_INT_MW_MENU_SHOW_COUNT_USE, mwMenuShowCountUse + 1)
    }

    fun incrementMwMenuShowCountUseNot() {
        putInt(KEY_INT_MW_MENU_SHOW_COUNT_USE_NOT, mwMenuShowCountUseNot + 1)
    }

    fun incrementMwMenuSwipeCount() {
        m3287i(this, KEY_INT_MW_MENU_SWIPE_COUNT, 1, KEY_INT_MW_MENU_SWIPE_COUNT)
    }

    fun incrementMwMenuTabCount() {
        m3287i(this, KEY_INT_MW_MENU_TAB_COUNT, 1, KEY_INT_MW_MENU_TAB_COUNT)
    }

    fun incrementMwOobeShowCount() {
        m3287i(this, KEY_INT_MW_OOBE_SHOW_COUNT, 1, KEY_INT_MW_OOBE_SHOW_COUNT)
    }

    fun incrementMwToolCancelCount() {
        m3287i(this, KEY_INT_MW_TOOL_CANCEL_COUNT, 1, KEY_INT_MW_TOOL_CANCEL_COUNT)
    }

    fun incrementMwToolSwapCount() {
        m3287i(this, KEY_INT_MW_TOOL_SWAP_COUNT, 1, KEY_INT_MW_TOOL_SWAP_COUNT)
    }

    fun incrementMwToolTwCount() {
        m3287i(this, KEY_INT_MW_TOOL_TW_COUNT, 1, KEY_INT_MW_TOOL_TW_COUNT)
    }

    fun incrementOobeShowCount() {
        m3287i(this, KEY_INT_SS_OOBE_SHOW_COUNT, 1, KEY_INT_SS_OOBE_SHOW_COUNT)
    }

    fun incrementPermissionDialogShowCount(permissionGroup: PermissionGroup) {
        val permissionDialogShowCount = getPermissionDialogShowCount(permissionGroup)
        if (permissionDialogShowCount < 3) {
            PERMISSION_DIALOG_SHOW_COUNT[permissionGroup] = permissionDialogShowCount + 1
        }
    }


    fun incrementPopupPromptShowCount() {
        putPopupPromptShowCount(getInt(KEY_INT_POPUP_PROMPT_SHOW_COUNT) + 1)
    }

    fun incrementScrollSideCount() {
        m3287i(this, KEY_INT_SCROLL_SIDE_COUNT, 1, KEY_INT_SCROLL_SIDE_COUNT)
    }

    fun incrementShortcutsLongTapCount(str: String) {
        SHORTCUTS_LONG_TAP_COUNTER[str] =
            getShortcutsTapCount(str) + 1
    }

    fun incrementShortcutsTapCount(str: String) {
        SHORTCUTS_TAP_COUNTER[str] =
            getShortcutsTapCount(str) + 1
    }

    fun incrementSsMenuSwipeCount() {
        m3287i(this, KEY_INT_SS_MENU_SWIPE_COUNT, 1, KEY_INT_SS_MENU_SWIPE_COUNT)
    }

    fun incrementSsMenuTabCount() {
        m3287i(this, KEY_INT_SS_MENU_TAB_COUNT, 1, KEY_INT_SS_MENU_TAB_COUNT)
    }

    fun incrementToolMuteCount() {
        m3287i(this, KEY_INT_TOOL_MUTE_COUNT, 1, KEY_INT_TOOL_MUTE_COUNT)
    }

    fun incrementToolNotificationCount() {
        m3287i(this, KEY_INT_TOOL_NOTIFICATION_COUNT, 1, KEY_INT_TOOL_NOTIFICATION_COUNT)
    }

    fun incrementToolScreenshotCount() {
        m3287i(this, KEY_INT_TOOL_SCREENSHOT_COUNT, 1, KEY_INT_TOOL_SCREENSHOT_COUNT)
    }

    fun incrementTwMenuShowCountUse() {
        putInt(KEY_INT_TW_MENU_SHOW_COUNT_USE, twMenuShowCountUse + 1)
    }

    fun incrementTwMenuShowCountUseOnGe() {
        putInt(KEY_INT_TW_MENU_SHOW_COUNT_USE_ON_GE, twMenuShowCountUseOnGe + 1)
    }

    val isBlackListDialogShown: Boolean
        get() = getBoolean(KEY_BOOLEAN_BLACK_LIST_DIALOG_SHOWN)

    val isDashboardBeingShow: Boolean
        get() = getInt(KEY_INT_DASHBOARD_TIPS_STATUS) == 0

    val isDashboardRemoved: Boolean
        get() = getInt(KEY_INT_DASHBOARD_TIPS_STATUS) == 1

    val isFixCustomAppPresetDone: Boolean
        get() = getBoolean(KEY_BOOLEAN_FIX_CUSTOM_APP_PRESET_DONE)

    val isGuidanceNotificationRemoved: Boolean
        get() = getBoolean(KEY_BOOLEAN_GUIDANCE_NOTIFICATION_TAPPED) || getBoolean(
            KEY_BOOLEAN_ASSIST_SIDE_SENSE_GUIDANCE_TAPPED
        ) || getBoolean(KEY_BOOLEAN_GUIDANCE_NOTIFICATION_REPLACED)

    val isHeadphoneNotificationBeingShown: Boolean
        get() = getInt(KEY_INT_HEADPHONE_NOTIFICATION_STATUS) == 1

    val isHeadphoneNotificationRemoved: Boolean
        get() = getInt(KEY_INT_HEADPHONE_NOTIFICATION_STATUS) == 2

    val isHelpGuideDialogShown: Boolean
        get() = getBoolean(KEY_BOOLEAN_HELP_GUIDE_DIALOG_SHOWN)

    var isHpcWidgetSettingOffByUser: Boolean
        get() = getBoolean(KEY_BOOLEAN_HPC_WIDGET_SETTING_OFF_BY_USER)
        set(z) {
            putBoolean(KEY_BOOLEAN_HPC_WIDGET_SETTING_OFF_BY_USER, z)
        }

    fun isLeftHandStatus(i: Int): Boolean {
        return getBoolean(
            if (i == getInt(KEY_INT_GESTURE_SCROLL_UP)) KEY_BOOLEAN_SCROLL_UP_LEFT_HAND_STATUS else if (i == getInt(
                    KEY_INT_GESTURE_SCROLL_DOWN
                )
            ) KEY_BOOLEAN_SCROLL_DOWN_LEFT_HAND_STATUS else KEY_BOOLEAN_LEFT_HAND_STATUS
        )
    }

    val isLockScreenSettingsEnabled: Boolean
        get() = getBoolean(KEY_BOOLEAN_SIDE_SENSE_LOCKSCREEN_SETTINGS)

    val isLoopsEnabled: Boolean
        get() = getBoolean(KEY_BOOLEAN_LOOPS)

    val isMultiOobeDone: Boolean
        get() {
            if (getBoolean(KEY_BOOLEAN_FORCE_MULTI_OOBE)) {
                return false
            }
            return getBoolean(KEY_BOOLEAN_MULTI_OOBE_DONE)
        }

    val isOobeDone: Boolean
        get() {
            if (getBoolean(KEY_BOOLEAN_FORCE_OOBE)) {
                return false
            }
            return getBoolean(KEY_BOOLEAN_OOBE_DONE)
        }

    val isSideSenseWidgetEnabled: Boolean
        get() = getBoolean(KEY_BOOLEAN_SIDE_SENSE_WIDGET)

    val isThumbPositionChanged: Boolean
        get() = getInt(KEY_INT_THUMB_POSITION_X) > 0 || getInt(
            KEY_INT_THUMB_POSITION_Y
        ) > 0 || getInt(KEY_INT_SCROLL_DOWN_THUMB_POSITION_X) > 0 || getInt(
            KEY_INT_SCROLL_DOWN_THUMB_POSITION_Y
        ) > 0 || getInt(KEY_INT_SCROLL_UP_THUMB_POSITION_X) > 0 || getInt(
            KEY_INT_SCROLL_UP_THUMB_POSITION_Y
        ) > 0

    fun putBoolean(str: String?, z: Boolean) {
        val sb = StringBuilder()
            .append(TAG)
            .append(".putBoolean: key=")
            .append(str)
            .append(" value=")
            .append(z)
        LogUtil.d(LogUtil.LOG_TAG, sb.toString())
        mSharedPrefs.edit().putBoolean(str, z).apply()
    }

    fun putInitialUseTime(j: Long) {
        putLong(KEY_LONG_INITIAL_USE_TIME, j)
    }

    fun putInt(str: String?, i: Int) {
        val sb = StringBuilder()
            .append(TAG)
            .append(".putInt: key=")
            .append(str)
            .append(" value=")
            .append(i)
        LogUtil.d(LogUtil.LOG_TAG, sb.toString())
        mSharedPrefs.edit().putInt(str, i).apply()
    }

    fun putIsNtpBasedInitialUseTime(z: Boolean) {
        putBoolean(KEY_BOOLEAN_IS_NTP_BASED_INITIAL_USE_TIME, z)
    }

    fun putLastShowLoopsPromptTime(j: Long) {
        putLong(KEY_LONG_LAST_SHOW_LOOPS_PROMPT_TIME, j)
    }

    fun putLastWhatsnewVersion(i: Int) {
        putInt(KEY_INT_LAST_WHATSNEW_VERSION, i)
    }

    fun putLeftHandStatus(i: Int, z: Boolean) {
        putBoolean(
            if (i == getInt(KEY_INT_GESTURE_SCROLL_UP)) KEY_BOOLEAN_SCROLL_UP_LEFT_HAND_STATUS else if (i == getInt(
                    KEY_INT_GESTURE_SCROLL_DOWN
                )
            ) KEY_BOOLEAN_SCROLL_DOWN_LEFT_HAND_STATUS else KEY_BOOLEAN_LEFT_HAND_STATUS, z
        )
    }

    fun putLong(str: String?, j: Long) {
        val sb = StringBuilder().append(TAG).append(".putLong: key=").append(str).append(" value=")
            .append(j)
        LogUtil.d(LogUtil.LOG_TAG, sb.toString())
        mSharedPrefs.edit().putLong(str, j).apply()
    }

    fun putLoopsPromptShowCount(i: Int) {
        putInt(KEY_INT_LOOPS_PROMPT_SHOW_COUNT, i)
    }

    fun putPopupPromptShowCount(i: Int) {
        putInt(KEY_INT_POPUP_PROMPT_SHOW_COUNT, i)
    }

    fun putScreenshotStatus(i: Int) {
        putInt(KEY_INT_SCREENSHOT_STATUS, i)
    }

    fun putThumPositionX(i: Int, i2: Int) {
        putInt(
            if (i == getInt(KEY_INT_GESTURE_SCROLL_UP)) KEY_INT_SCROLL_UP_THUMB_POSITION_X else if (i == getInt(
                    KEY_INT_GESTURE_SCROLL_DOWN
                )
            ) KEY_INT_SCROLL_DOWN_THUMB_POSITION_X else KEY_INT_THUMB_POSITION_X, i2
        )
    }

    fun putThumPositionY(i: Int, i2: Int) {
        putInt(
            if (i == getInt(KEY_INT_GESTURE_SCROLL_UP)) KEY_INT_SCROLL_UP_THUMB_POSITION_Y else if (i == getInt(
                    KEY_INT_GESTURE_SCROLL_DOWN
                )
            ) KEY_INT_SCROLL_DOWN_THUMB_POSITION_Y else KEY_INT_THUMB_POSITION_Y, i2
        )
    }

    fun putTranslationX(i: Int, i2: Int) {
        putInt(
            if (i == getInt(KEY_INT_GESTURE_SCROLL_UP)) KEY_INT_SCROLL_UP_TRANSLATION_X else if (i == getInt(
                    KEY_INT_GESTURE_SCROLL_DOWN
                )
            ) KEY_INT_SCROLL_DOWN_TRANSLATION_X else KEY_INT_TRANSLATION_X, i2
        )
    }

    fun putTranslationY(i: Int, i2: Int) {
        putInt(
            if (i == getInt(KEY_INT_GESTURE_SCROLL_UP)) KEY_INT_SCROLL_UP_TRANSLATION_Y else if (i == getInt(
                    KEY_INT_GESTURE_SCROLL_DOWN
                )
            ) KEY_INT_SCROLL_DOWN_TRANSLATION_Y else KEY_INT_TRANSLATION_Y, i2
        )
    }

    fun putVersionHistory(list: List<Int?>) {
        val jSONArray = JSONArray()
        for (i in list.indices) {
            jSONArray.put(list[i])
        }
        mSharedPrefs.edit().putString(KEY_VERSION_HISTORY, jSONArray.toString()).apply()
    }

    fun resetGuidanceNotification(): Boolean {
        setGuidanceNotificationTapped(false)
        setGuidanceAssistViewTapped(false)
        return false
    }

    fun setAutoAddDialogStatusForShortcut(context: Context, str: String, i: Int) {
        val str2: String
        val stringArray = context.resources.getStringArray(R.array.auto_add_ali_pay_list)
        val stringArray2 = context.resources.getStringArray(R.array.auto_add_we_chat_pay_list)
        str2 = if (str == stringArray[0]) {
            KEY_INT_DIALOG_STATUS_ALI_PAY_PAY
        } else if (str == stringArray[1]) {
            KEY_INT_DIALOG_STATUS_ALI_PAY_SCAN
        } else if (str == stringArray2[0]) {
            KEY_INT_DIALOG_STATUS_WE_CHAT_PAY_PAY
        } else if (str != stringArray2[1]) {
            return
        } else {
            KEY_INT_DIALOG_STATUS_WE_CHAT_PAY_SCAN
        }
        putInt(str2, i)
    }

    fun setBlackListDialogShown() {
        putBoolean(KEY_BOOLEAN_BLACK_LIST_DIALOG_SHOWN, true)
    }

    fun setDashboardBeingShow() {
        putInt(KEY_INT_DASHBOARD_TIPS_STATUS, 0)
    }

    fun setDashboardRemoved() {
        putInt(KEY_INT_DASHBOARD_TIPS_STATUS, 1)
    }

    fun setFixCustomAppPresetDone() {
        putBoolean(KEY_BOOLEAN_FIX_CUSTOM_APP_PRESET_DONE, true)
    }

    fun setGestureAngleAverage(str: String, d: Double) {
        val gestureCount = getGestureCount(str)
        if (gestureCount < 0) {
            return
        }
        val degrees = Math.toDegrees(d).toInt()
        val gestureAngleAverage = getGestureAngleAverage(str)
        val i = (((gestureCount - 1) * gestureAngleAverage) + degrees) / gestureCount
        val sb = StringBuilder().apply {
            append(TAG).append(".setGestureAngleAverage currentShowCount = ").append(gestureCount)
            append(" currentAngle = ").append(degrees)
            append(" lastAverage = ").append(gestureAngleAverage)
            append(" average = ").append(i)
        }
        LogUtil.d(LogUtil.LOG_TAG, sb.toString())
        putInt(getIddGestureAngleAveragePreference(str), i)
    }


    fun setGestureCount(i: Int, i2: Int) {
        GESTURE_ACTIONS_COUNTER[i] =
            i2
    }

    fun setGestureDistanceAverage(str: String, i: Int) {
        val gestureCount = getGestureCount(str)
        if (gestureCount >= 0) {
            val gestureDistanceAverage = getGestureDistanceAverage(str)
            if (gestureDistanceAverage >= 0) {
                val i2 = (((gestureCount - 1) * gestureDistanceAverage) + i) / gestureCount
                val sb = StringBuilder().apply {
                    append(TAG).append(".setGestureDistanceAverage currentShowCount = ")
                        .append(gestureCount)
                    append(" current = ").append(i)
                    append(" lastAverage = ").append(gestureDistanceAverage)
                    append(" average =").append(i2)
                }
                LogUtil.d(LogUtil.LOG_TAG, sb.toString())
                putInt(getIddGestureDistanceAveragePreference(str), i2)
            }
        }
    }


    fun setGestureTimeAverage(str: String, j: Long) {
        val gestureCount = getGestureCount(str)
        if (gestureCount < 0) {
            return
        }
        val gestureTimeAverage = getGestureTimeAverage(str)
        if (gestureTimeAverage < 0) {
            return
        }
        val currentTimeMillis = System.currentTimeMillis() - j
        val j2 = (((gestureCount - 1) * gestureTimeAverage) + currentTimeMillis) / gestureCount
        val sb = StringBuilder().apply {
            append(TAG).append(".setGestureTimeAverage currentShowCount = ").append(gestureCount)
            append(" currentShowTime = ").append(currentTimeMillis)
            append(" lastAverage = ").append(gestureTimeAverage)
            append(" average = ").append(j2)
        }
        LogUtil.d(LogUtil.LOG_TAG, sb.toString())
        putLong(getIddGestureTimeAveragePreference(str), j2)
    }


    fun setGuidanceAssistViewTapped(z: Boolean) {
        putBoolean(KEY_BOOLEAN_ASSIST_SIDE_SENSE_GUIDANCE_TAPPED, z)
    }

    fun setGuidanceNotificationReplaced(z: Boolean) {
        putBoolean(KEY_BOOLEAN_GUIDANCE_NOTIFICATION_REPLACED, z)
    }

    fun setGuidanceNotificationTapped(z: Boolean) {
        putBoolean(KEY_BOOLEAN_GUIDANCE_NOTIFICATION_TAPPED, z)
    }

    fun setHeadphoneNotificationBeingShown() {
        putInt(KEY_INT_HEADPHONE_NOTIFICATION_STATUS, 1)
    }

    fun setHeadphoneNotificationNotShown() {
        putInt(KEY_INT_HEADPHONE_NOTIFICATION_STATUS, 0)
    }

    fun setHeadphoneNotificationRemoved() {
        putInt(KEY_INT_HEADPHONE_NOTIFICATION_STATUS, 2)
    }

    fun setHeadphoneNotificationReset() {
        putInt(KEY_INT_HEADPHONE_NOTIFICATION_STATUS, 0)
    }

    fun setHelpGuideDialogShown() {
        putBoolean(KEY_BOOLEAN_HELP_GUIDE_DIALOG_SHOWN, true)
    }

    fun setLastGestureCount(i: Int, i2: Int) {
        (LAST_MONTH_GESTURE_ACTIONS_COUNTER as MutableMap)[i] = i2
    }

    fun setLastPermissionDialogShowCount(permissionGroup: PermissionGroup, i: Int) {
        (LAST_PERMISSION_DIALOG_SHOW_COUNT as MutableMap)[permissionGroup] = i
    }


    fun setLastShortcutsLongTapCount(str: String, i: Int) {
        LAST_SHORTCUTS_LONG_TAP_COUNTER[str] =
            i
    }

    fun setLastShortcutsTapCount(str: String, i: Int) {
        LAST_SHORTCUTS_TAP_COUNTER[str] =
            i
    }

    fun setLastWeekGestureCount(str: String, i: Int) {
        val iddLastGestureCountPreference = getIddLastGestureCountPreference(str) ?: return
        putInt(iddLastGestureCountPreference, i)
    }

    fun setShortcutsLongTapCount(str: String, i: Int) {
        SHORTCUTS_LONG_TAP_COUNTER[str] =
            i
    }

    fun setShortcutsTapCount(str: String, i: Int) {
        SHORTCUTS_TAP_COUNTER[str] =
            i
    }

    fun getInt(str: String, defaultValue: Int): Int {
        val value = mSharedPrefs.getInt(str, defaultValue)
        val sb = StringBuilder().apply {
            append(TAG).append(".getInt: key=").append(str).append(" value=").append(value)
        }
        LogUtil.d(LogUtil.LOG_TAG, sb.toString())
        return value
    }


    companion object {
        const val ACTION_BACK: Int = 2
        const val ACTION_DASHBOARD: Int = 16
        const val ACTION_DEFAULT: Int = 16
        const val ACTION_DUALDRIVE: Int = 13
        const val ACTION_HOME: Int = 3
        const val ACTION_LAUNCH_APP_AND_SHORTCUT: Int = 14
        const val ACTION_LAUNCH_TW_APP: Int = 15
        const val ACTION_MULTI_WINDOW_MENU: Int = 10
        const val ACTION_MULTI_WINDOW_SETTINGS: Int = 11
        const val ACTION_NONE: Int = 0
        const val ACTION_NOTIFICATION: Int = 4
        const val ACTION_ONE_HAND: Int = 5
        const val ACTION_QUICK_SCRUB: Int = 8
        const val ACTION_RECENT_APPS: Int = 7
        const val ACTION_SCREENSHOT: Int = 12
        const val ACTION_SETTINGS: Int = 9
        const val ACTION_SIDE_SENSE_MENU: Int = 1
        const val ACTION_SWITCH_APPS: Int = 6
        const val DASHBOARD_TIPS_STATUS_BEING_SHOW: Int = 0
        const val DASHBOARD_TIPS_STATUS_REMOVED: Int = 1
        const val DEFAULT_ACTION_COUNT: Int = 0
        private const val DEFAULT_DASHBOARD_TIPS_STATUS_STATUS = 0
        private const val DEFAULT_HEADPHONE_NOTIFICATION_STATUS = 0
        const val DEFAULT_SCROLL_DISTANCE: Int = 200
        const val DEFAULT_SHORTCUTS_COUNT: Int = 0
        const val DEFAULT_VALUE_BOOLEAN_3TAB_MOVE_AMOUNT: Boolean = false
        private const val DEFAULT_VALUE_BOOLEAN_ASSIST_ALL_DONE = false
        private const val DEFAULT_VALUE_BOOLEAN_ASSIST_IDI_TAPPED = false
        private const val DEFAULT_VALUE_BOOLEAN_ASSIST_SIDE_SENSE_GUIDANCE_TAPPED = false
        private const val DEFAULT_VALUE_BOOLEAN_ASSIST_XPERIA_ASSIST_TAPPED = false
        private const val DEFAULT_VALUE_BOOLEAN_AUTO_SHORTCUT_ADD_DIALOG_SHOWN_ALIPAY = false
        private const val DEFAULT_VALUE_BOOLEAN_AUTO_SHORTCUT_ADD_DIALOG_SHOWN_WECHATPAY = false
        private const val DEFAULT_VALUE_BOOLEAN_BLACK_LIST_DIALOG_SHOWN = false
        private const val DEFAULT_VALUE_BOOLEAN_DISPLAY_TOUCH_VIEW_ANIMATION = true
        const val DEFAULT_VALUE_BOOLEAN_DISPLAY_TOUCH_VIEW_AUTO_HIDE: Boolean = true
        private const val DEFAULT_VALUE_BOOLEAN_DISPLAY_TOUCH_VIEW_SHOW_TOUCH_AREA = false
        private const val DEFAULT_VALUE_BOOLEAN_DISPLAY_TOUCH_VIEW_WIDTH_SETTING = false
        private const val DEFAULT_VALUE_BOOLEAN_DOUBLE_TAP_ENABLED = false
        private const val DEFAULT_VALUE_BOOLEAN_DYNAMIC_AREA_FILTER_MEDIA_VERSION = false
        private const val DEFAULT_VALUE_BOOLEAN_ENABLE_ACTIVE_AREA = false
        private const val DEFAULT_VALUE_BOOLEAN_FIX_CUSTOM_APP_PRESET_DONE = false
        private const val DEFAULT_VALUE_BOOLEAN_FLOATING_MODE_ENABLED = false
        private const val DEFAULT_VALUE_BOOLEAN_FORCE_MULTI_OOBE = false
        private const val DEFAULT_VALUE_BOOLEAN_FORCE_OOBE = false
        private const val DEFAULT_VALUE_BOOLEAN_GUIDANCE_NOTIFICATION_REPLACED = false
        private const val DEFAULT_VALUE_BOOLEAN_GUIDANCE_NOTIFICATION_SHOWN = false
        private const val DEFAULT_VALUE_BOOLEAN_GUIDANCE_NOTIFICATION_TAPPED = false
        private const val DEFAULT_VALUE_BOOLEAN_HELP_GUIDE_DIALOG_SHOWN = false
        private const val DEFAULT_VALUE_BOOLEAN_HPC_WIDGET_OFF_BY_SETTING = false
        private const val DEFAULT_VALUE_BOOLEAN_IS_LAUNCH_FROM_SHORTCUT = false
        private const val DEFAULT_VALUE_BOOLEAN_IS_NTP_BASED_INITIAL_USE_TIME = false
        private const val DEFAULT_VALUE_BOOLEAN_IS_SETTINGS_INITIALIZED = false
        private const val DEFAULT_VALUE_BOOLEAN_LANDSCAPE_BOTTOM_TOUCH = false
        private const val DEFAULT_VALUE_BOOLEAN_LEFT_HAND_STATUS = true
        private const val DEFAULT_VALUE_BOOLEAN_LOOPS = true
        private const val DEFAULT_VALUE_BOOLEAN_MEASURE_MENU_PERFORMANCE = false
        private const val DEFAULT_VALUE_BOOLEAN_MORE_APPS = true
        private const val DEFAULT_VALUE_BOOLEAN_MULTI_OOBE_DONE = false
        private const val DEFAULT_VALUE_BOOLEAN_MULTI_TOUCH_GESTURE_DETECTOR = false
        private const val DEFAULT_VALUE_BOOLEAN_OOBE_DONE = false
        private const val DEFAULT_VALUE_BOOLEAN_POINTER_LOCATION = false
        private const val DEFAULT_VALUE_BOOLEAN_PROMPT_DISPLAY_TOUCH_VIEW_LEFT_HAND_STATUS = true
        private const val DEFAULT_VALUE_BOOLEAN_RESET_ASSIST = false
        private const val DEFAULT_VALUE_BOOLEAN_RESET_LOOPS_PROMPT = false
        private const val DEFAULT_VALUE_BOOLEAN_SCROLL_DOWN_LEFT_HAND_STATUS = true
        private const val DEFAULT_VALUE_BOOLEAN_SCROLL_UP_LEFT_HAND_STATUS = true
        private const val DEFAULT_VALUE_BOOLEAN_SHOW_GESTURE_NAVIGATION_AREA = false
        private const val DEFAULT_VALUE_BOOLEAN_SHOW_IN_SCREENSHOT = false
        private const val DEFAULT_VALUE_BOOLEAN_SIDEBAR_MOVING_STATUS = false
        private const val DEFAULT_VALUE_BOOLEAN_SIDE_SENSE_LAUNCHER = false
        private const val DEFAULT_VALUE_BOOLEAN_SIDE_SENSE_LOCKSCREEN_SETTINGS = false
        private const val DEFAULT_VALUE_BOOLEAN_SIDE_SENSE_WIDGET = false
        private const val DEFAULT_VALUE_BOOLEAN_USE_SS_BAR_STATUS_WHEN_CD_IS_TURNED_OFF = true
        private const val DEFAULT_VALUE_DISPLAY_TOUCH_VIEW_IDLE_ANIMATION_TIME = 1500
        private const val DEFAULT_VALUE_DYNAMIC_AREA_FILTER = true
        private const val DEFAULT_VALUE_FIXED_AREA = false
        private const val DEFAULT_VALUE_FLOATING_END_THRESHOLD = 18
        private const val DEFAULT_VALUE_FLOATING_TAP_POSITION_X = 0
        private const val DEFAULT_VALUE_FLOATING_TAP_POSITION_Y = 0
        private const val DEFAULT_VALUE_INT_ASSIST_INTERVAL_TIME = 86400
        const val DEFAULT_VALUE_INT_ASSIST_SHOW_COUNT: Int = 0
        const val DEFAULT_VALUE_INT_BLUR_RADIUS: Int = 25
        private const val DEFAULT_VALUE_INT_DASHBOARD_SHOW_COUNT = 0
        private const val DEFAULT_VALUE_INT_DASHBOARD_SHOW_COUNT_USE = 0
        private const val DEFAULT_VALUE_INT_DASHBOARD_SHOW_COUNT_USE_NOT = 0
        const val DEFAULT_VALUE_INT_DISPLAY_TOUCH_VIEW_ACTIVE_TRANSPARENCY: Int = 20
        private const val DEFAULT_VALUE_INT_DISPLAY_TOUCH_VIEW_ANIMATION_TIME = 200
        private const val DEFAULT_VALUE_INT_DISPLAY_TOUCH_VIEW_HIDE_TIME = 800
        const val DEFAULT_VALUE_INT_DISPLAY_TOUCH_VIEW_IDLE_TRANSPARENCY: Int = 60
        const val DEFAULT_VALUE_INT_DISPLAY_TOUCH_VIEW_POSITION_CHANGING_METHOD: Int = 0
        const val DEFAULT_VALUE_INT_DISPLAY_TOUCH_VIEW_TRANSPARENCY: Int = 30
        const val DEFAULT_VALUE_INT_DOUBLE_TAP_DIALOG_INDEX: Int = 0
        private const val DEFAULT_VALUE_INT_EP_DASHBOARD_GESTURE_BAR_COUNT = 0
        private const val DEFAULT_VALUE_INT_EP_DASHBOARD_HOME_COUNT = 0
        private const val DEFAULT_VALUE_INT_EP_MW_GESTURE_BAR_COUNT = 0
        private const val DEFAULT_VALUE_INT_EP_MW_GESTURE_FLOATING_COUNT = 0
        private const val DEFAULT_VALUE_INT_EP_MW_HOME_COUNT = 0
        private const val DEFAULT_VALUE_INT_EP_SS_GESTURE_BAR_COUNT = 0
        private const val DEFAULT_VALUE_INT_EP_SS_GESTURE_FLOATING_COUNT = 0
        private const val DEFAULT_VALUE_INT_EP_SS_HOME_COUNT = 0
        private const val DEFAULT_VALUE_INT_FLING_VELOCITY = 300
        private const val DEFAULT_VALUE_INT_FLOATING_ANIMATION_VIEW_MARGIN = 15
        private const val DEFAULT_VALUE_INT_FLOATING_CHANGED_COUNT = 0
        private const val DEFAULT_VALUE_INT_FLOATING_VIEW_ORIENTATION = 1
        private const val DEFAULT_VALUE_INT_FLOATING_VIEW_X = 0
        private const val DEFAULT_VALUE_INT_FLOATING_VIEW_X_LAND = 0
        private const val DEFAULT_VALUE_INT_FLOATING_VIEW_Y = 0
        private const val DEFAULT_VALUE_INT_FLOATING_VIEW_Y_LAND = 0
        private const val DEFAULT_VALUE_INT_GESTURE_WAIT_TIME = 500
        private const val DEFAULT_VALUE_INT_GUIDANCE_NOTIFICATION_INTERVAL_TIME = 86400
        private const val DEFAULT_VALUE_INT_GUIDE_COUNT = 0
        private const val DEFAULT_VALUE_INT_IDD_GESTURE_ACTION_DOUBLE_TAP_COUNT = 0
        private const val DEFAULT_VALUE_INT_IDD_GESTURE_ACTION_SCROLL_DOWN_COUNT = 0
        private const val DEFAULT_VALUE_INT_IDD_GESTURE_ACTION_SCROLL_SIDE_COUNT = 0
        private const val DEFAULT_VALUE_INT_IDD_GESTURE_ACTION_SCROLL_UP_COUNT = 0
        private const val DEFAULT_VALUE_INT_IDD_INTERVAL_TIME = 604800
        private const val DEFAULT_VALUE_INT_IDD_LAST_GESTURE_ACTION_DOUBLE_TAP_COUNT = 0
        private const val DEFAULT_VALUE_INT_IDD_LAST_GESTURE_ACTION_SCROLL_DOWN_COUNT = 0
        private const val DEFAULT_VALUE_INT_IDD_LAST_GESTURE_ACTION_SCROLL_SIDE_COUNT = 0
        private const val DEFAULT_VALUE_INT_IDD_LAST_GESTURE_ACTION_SCROLL_UP_COUNT = 0
        private const val DEFAULT_VALUE_INT_IDD_LAST_LONGTAP_COUNT = 0
        private const val DEFAULT_VALUE_INT_IDD_LONGTAP_COUNT = 0
        private const val DEFAULT_VALUE_INT_IDD_SWIPE_DOWN_ANGLE_AVERAGE = 0
        private const val DEFAULT_VALUE_INT_IDD_SWIPE_DOWN_DISTANCE_AVERAGE = 0
        private const val DEFAULT_VALUE_INT_IDD_SWIPE_SIDE_ANGLE_AVERAGE = 0
        private const val DEFAULT_VALUE_INT_IDD_SWIPE_SIDE_DISTANCE_AVERAGE = 0
        private const val DEFAULT_VALUE_INT_IDD_SWIPE_UP_ANGLE_AVERAGE = 0
        private const val DEFAULT_VALUE_INT_IDD_SWIPE_UP_DISTANCE_AVERAGE = 0
        private const val DEFAULT_VALUE_INT_LAST_DASHBOARD_SHOW_COUNT = 0
        private const val DEFAULT_VALUE_INT_LAST_DASHBOARD_SHOW_COUNT_USE = 0
        private const val DEFAULT_VALUE_INT_LAST_DASHBOARD_SHOW_COUNT_USE_NOT = 0
        private const val DEFAULT_VALUE_INT_LAST_MONTH_FLOATING_CHANGED_COUNT = 0
        private const val DEFAULT_VALUE_INT_LAST_MONTH_MENU_SHOW_COUNT = 0
        private const val DEFAULT_VALUE_INT_LAST_MONTH_MENU_SHOW_COUNT_NOT_USE = 0
        private const val DEFAULT_VALUE_INT_LAST_MONTH_MENU_SHOW_COUNT_USE = 0
        private const val DEFAULT_VALUE_INT_LAST_MONTH_MENU_SHOW_COUNT_USE_ON_GE = 0
        private const val DEFAULT_VALUE_INT_LAST_MONTH_MW_MENU_SHOW_COUNT = 0
        private const val DEFAULT_VALUE_INT_LAST_MONTH_MW_MENU_SHOW_COUNT_NOT_USE = 0
        private const val DEFAULT_VALUE_INT_LAST_MONTH_MW_MENU_SHOW_COUNT_USE = 0
        private const val DEFAULT_VALUE_INT_LAST_MONTH_TW_MENU_SHOW_COUNT_USE = 0
        private const val DEFAULT_VALUE_INT_LAST_MONTH_TW_MENU_SHOW_COUNT_USE_ON_GE = 0
        private const val DEFAULT_VALUE_INT_LAST_WEEK_EP_DASHBOARD_HOME_COUNT = 0
        private const val DEFAULT_VALUE_INT_LAST_WEEK_EP_MW_GESTURE_FLOATING_COUNT = 0
        private const val DEFAULT_VALUE_INT_LAST_WEEK_EP_MW_HOME_COUNT = 0
        private const val DEFAULT_VALUE_INT_LAST_WEEK_EP_SS_GESTURE_BAR_COUNT = 0
        private const val DEFAULT_VALUE_INT_LAST_WEEK_EP_SS_GESTURE_FLOATING_COUNT = 0
        private const val DEFAULT_VALUE_INT_LAST_WEEK_EP_SS_HOME_COUNT = 0
        private const val DEFAULT_VALUE_INT_LAST_WEEK_MENU_SETTINGS_ICON_COUNT = 0
        private const val DEFAULT_VALUE_INT_LAST_WEEK_MW_MENU_SWIPE_COUNT = 0
        private const val DEFAULT_VALUE_INT_LAST_WEEK_MW_MENU_TAB_COUNT = 0
        private const val DEFAULT_VALUE_INT_LAST_WEEK_MW_TOOL_CANCEL_COUNT = 0
        private const val DEFAULT_VALUE_INT_LAST_WEEK_MW_TOOL_SWAP_COUNT = 0
        private const val DEFAULT_VALUE_INT_LAST_WEEK_MW_TOOL_TW_COUNT = 0
        private const val DEFAULT_VALUE_INT_LAST_WEEK_SS_MENU_SWIPE_COUNT = 0
        private const val DEFAULT_VALUE_INT_LAST_WEEK_SS_MENU_TAB_COUNT = 0
        private const val DEFAULT_VALUE_INT_LAST_WEEK_TOOL_MUTE_COUNT = 0
        private const val DEFAULT_VALUE_INT_LAST_WEEK_TOOL_NOTIFICATION_COUNT = 0
        private const val DEFAULT_VALUE_INT_LAST_WEEK_TOOL_SCREENSHOT_COUNT = 0
        const val DEFAULT_VALUE_INT_LAST_WHATSNEW_VERSION: Int = 0
        private const val DEFAULT_VALUE_INT_LAUNCHER_STATUS = 2
        const val DEFAULT_VALUE_INT_LONG_PRESS_DIALOG_INDEX: Int = 2
        const val DEFAULT_VALUE_INT_LOOPS_POSITION: Int = 50
        const val DEFAULT_VALUE_INT_LOOPS_PROMPT_INTERVAL_TIME: Int = 43200
        private const val DEFAULT_VALUE_INT_LOOPS_PROMPT_SHOW_COUNT = 0
        private const val DEFAULT_VALUE_INT_LOOPS_PROMPT_SHOW_COUNT_TO_STOP = 8
        private const val DEFAULT_VALUE_INT_MENU_SETTINGS_ICON_COUNT = 0
        private const val DEFAULT_VALUE_INT_MENU_SHOW_COUNT_NOT_USE = 0
        private const val DEFAULT_VALUE_INT_MENU_SHOW_COUNT_USE = 0
        private const val DEFAULT_VALUE_INT_MENU_SHOW_COUNT_USE_ON_GE = 0
        private const val DEFAULT_VALUE_INT_MULTI_LAUNCHER_STATUS = 2
        private const val DEFAULT_VALUE_INT_MW_MENU_SHOW_COUNT = 0
        private const val DEFAULT_VALUE_INT_MW_MENU_SHOW_COUNT_NOT_USE = 0
        private const val DEFAULT_VALUE_INT_MW_MENU_SHOW_COUNT_USE = 0
        private const val DEFAULT_VALUE_INT_MW_MENU_SWIPE_COUNT = 0
        private const val DEFAULT_VALUE_INT_MW_MENU_TAB_COUNT = 0
        private const val DEFAULT_VALUE_INT_MW_OOBE_SHOW_COUNT = 0
        private const val DEFAULT_VALUE_INT_MW_TOOL_CANCEL_COUNT = 0
        private const val DEFAULT_VALUE_INT_MW_TOOL_SWAP_COUNT = 0
        private const val DEFAULT_VALUE_INT_MW_TOOL_TW_COUNT = 0
        private const val DEFAULT_VALUE_INT_ORIENTATION = 1
        private const val DEFAULT_VALUE_INT_POPUP_PROMPT_SHOW_COUNT = 0
        private const val DEFAULT_VALUE_INT_PRACTICE_DOUBLE_TAP_STATUS = 0
        private const val DEFAULT_VALUE_INT_PRACTICE_HEIGHT_BOTTOM = 0
        private const val DEFAULT_VALUE_INT_PRACTICE_HEIGHT_TOP = 0
        private const val DEFAULT_VALUE_INT_PRACTICE_SCROLL_DOWN_STATUS = 0
        private const val DEFAULT_VALUE_INT_PRACTICE_SCROLL_UP_STATUS = 0
        private const val DEFAULT_VALUE_INT_PROMPT_DISPLAY_TOUCH_VIEW_CENTER_Y = 0
        private const val DEFAULT_VALUE_INT_PROMPT_DISPLAY_TOUCH_VIEW_WIDTH = 0
        private const val DEFAULT_VALUE_INT_RACTICE_TOUCH_AREA = 0
        private const val DEFAULT_VALUE_INT_SCREENSHOT_STATUS = 0
        const val DEFAULT_VALUE_INT_SCROLL_DISTANCE_DIALOG_INDEX: Int = 1
        private const val DEFAULT_VALUE_INT_SCROLL_SIDE_COUNT = 0
        private const val DEFAULT_VALUE_INT_SCROLL_THUMB_POSITION_X = 0
        private const val DEFAULT_VALUE_INT_SCROLL_THUMB_POSITION_Y = 0
        private const val DEFAULT_VALUE_INT_SCROLL_TRANSLATION_X = 0
        private const val DEFAULT_VALUE_INT_SCROLL_TRANSLATION_Y = 0
        private const val DEFAULT_VALUE_INT_SIDEBAR_START_AND_CLOSE_ANIMATION_TIME = 200
        const val DEFAULT_VALUE_INT_SIDE_TOUCH_VIEW_TRANSPARENCY: Int = 40
        private const val DEFAULT_VALUE_INT_SS_MENU_SHOW_COUNT = 0
        private const val DEFAULT_VALUE_INT_SS_MENU_SWIPE_COUNT = 0
        private const val DEFAULT_VALUE_INT_SS_MENU_TAB_COUNT = 0
        private const val DEFAULT_VALUE_INT_SS_OOBE_SHOW_COUNT = 0
        private const val DEFAULT_VALUE_INT_SWIPE_CHOICE_SCROLL_DISTANCE_R0 = 40

        /* renamed from: DEFAULT_VALUE_INT_SWIPE_CHOICE_VIEW_BASE_POSITION_X_WHEN_SWIPE_DOWN */
        const val f2976xdd0fa220: Int = 90

        /* renamed from: DEFAULT_VALUE_INT_SWIPE_CHOICE_VIEW_BASE_POSITION_X_WHEN_SWIPE_SIDE */
        const val f2977xdd165ad5: Int = 90

        /* renamed from: DEFAULT_VALUE_INT_SWIPE_CHOICE_VIEW_BASE_POSITION_X_WHEN_SWIPE_UP */
        const val f2978x7d6ed799: Int = 90

        /* renamed from: DEFAULT_VALUE_INT_SWIPE_CHOICE_VIEW_BASE_POSITION_Y_WHEN_SWIPE_DOWN */
        const val f2979x2db98021: Int = 90

        /* renamed from: DEFAULT_VALUE_INT_SWIPE_CHOICE_VIEW_BASE_POSITION_Y_WHEN_SWIPE_SIDE */
        const val f2980x2dc038d6: Int = 90

        /* renamed from: DEFAULT_VALUE_INT_SWIPE_CHOICE_VIEW_BASE_POSITION_Y_WHEN_SWIPE_UP */
        const val f2981xd74a41da: Int = 90
        const val DEFAULT_VALUE_INT_SWIPE_CHOICE_VIEW_FADE_OUT_ANIMATION_TIME: Int = 400
        const val DEFAULT_VALUE_INT_SWIPE_CHOICE_VIEW_MOVE_CONSTRAINTS_X: Int = 30
        const val DEFAULT_VALUE_INT_SWIPE_CHOICE_VIEW_MOVE_CONSTRAINTS_Y: Int = 30
        private const val DEFAULT_VALUE_INT_SWIPE_CHOICE_VIEW_SELECTED_ICON_SIZE = 48
        const val DEFAULT_VALUE_INT_SWIPE_CHOICE_VIEW_TIME_CONSTANT: Int = 400
        const val DEFAULT_VALUE_INT_TAB_SWIPE_THRESHOLD: Int = 50
        private const val DEFAULT_VALUE_INT_THREE_WAY_SWIPE_DIAGONALLY_DOWN_ANGLE = -30
        private const val DEFAULT_VALUE_INT_THREE_WAY_SWIPE_DIAGONALLY_UP_ANGLE = 30
        private const val DEFAULT_VALUE_INT_THUMB_POSITION_X = 0
        private const val DEFAULT_VALUE_INT_THUMB_POSITION_Y = 0
        private const val DEFAULT_VALUE_INT_TOOL_MUTE_COUNT = 0
        private const val DEFAULT_VALUE_INT_TOOL_NOTIFICATION_COUNT = 0
        private const val DEFAULT_VALUE_INT_TOOL_SCREENSHOT_COUNT = 0
        private const val DEFAULT_VALUE_INT_TRANSLATION_X = 0
        private const val DEFAULT_VALUE_INT_TRANSLATION_Y = 0
        private const val DEFAULT_VALUE_INT_TURN_OFF_METHOD = 0
        private const val DEFAULT_VALUE_INT_TW_MENU_SHOW_COUNT_USE = 0
        private const val DEFAULT_VALUE_INT_TW_MENU_SHOW_COUNT_USE_ON_GE = 0
        private const val DEFAULT_VALUE_INT_VALID_AREA_Y = 70
        private const val DEFAULT_VALUE_INT_WIDGET_GUIDANCE_DONE = 0
        private const val DEFAULT_VALUE_INT_WIDGET_SIZE = 100
        private const val DEFAULT_VALUE_LAST_WEEK_EP_DASHBOARD_GESTURE_BAR_COUNT = 0
        private const val DEFAULT_VALUE_LAST_WEEK_EP_MW_GESTURE_BAR_COUNT = 0
        private const val DEFAULT_VALUE_LONG_IDD_DASHBOARD_SHOW_TIME_AVERAGE: Long = 0
        private const val DEFAULT_VALUE_LONG_IDD_SWIPE_DOWN_TIME_AVERAGE: Long = 0
        private const val DEFAULT_VALUE_LONG_IDD_SWIPE_SIDE_TIME_AVERAGE: Long = 0
        private const val DEFAULT_VALUE_LONG_IDD_SWIPE_UP_TIME_AVERAGE: Long = 0
        const val DEFAULT_VALUE_LONG_INITIAL_USE_TIME: Long = 0
        const val DEFAULT_VALUE_LONG_LAST_SHOW_LOOPS_PROMPT_TIME: Long = 0
        private const val DEFAULT_VALUE_MOVE_POSITION = 0
        private const val DEFAULT_VALUE_SHOULD_SHOW_DISPLAYTOUCH_DIALOG = true
        private const val DEFAULT_VALUE_SHOW_DYNAMIC_AREA_FILTER = false
        private const val DEFAULT_VALUE_TAB_ANIMATION_END = false
        private const val DEFAULT_VALUE_TUTORIAL_PRACTICE_AREA_TAP = false
        const val DISPLAYTOUCH_LONG_PRESS_TIMEOUT_FAST: Int = 150
        const val DISPLAYTOUCH_LONG_PRESS_TIMEOUT_NORMAL: Int = 200
        const val DISPLAYTOUCH_LONG_PRESS_TIMEOUT_SLOW: Int = 400
        const val DISPLAYTOUCH_LONG_PRESS_TIMEOUT_SLOWER: Int = 700
        const val DISPLAY_TOUCH_VIEW_POSITION_CHANGING_METHOD_DRAG: Int = 2
        const val DISPLAY_TOUCH_VIEW_POSITION_CHANGING_METHOD_NONE: Int = 0
        const val DISPLAY_TOUCH_VIEW_POSITION_CHANGING_METHOD_SETTINGS: Int = 1
        const val DISPLAY_TOUCH_VIEW_TRANSPARENCY_MAX: Int = 100
        const val DISPLAY_TOUCH_VIEW_TRANSPARENCY_MIN: Int = 0
        const val FLOATING_END_THRESHOLD: String = "floating_end_threshold"
        const val GESTURE_DOUBLE_TAP_DEFAULT_ACTION: Int = 16
        const val GESTURE_SCROLL_DOWN_DEFAULT_ACTION: Int = 2
        const val GESTURE_SCROLL_SIDE_DEFAULT_ACTION: Int = 16
        const val GESTURE_SCROLL_UP_DEFAULT_ACTION: Int = 1
        private const val HEADPHONE_NOTIFICATION_STATUS_BEING_SHOWN = 1
        private const val HEADPHONE_NOTIFICATION_STATUS_NOT_SHOWN = 0
        private const val HEADPHONE_NOTIFICATION_STATUS_REMOVED = 2
        const val HPC_WIDGET_GUIDANCE_DONE_BY_NO: Int = 2
        const val HPC_WIDGET_GUIDANCE_DONE_BY_OTHER: Int = 3
        const val HPC_WIDGET_GUIDANCE_DONE_BY_YES: Int = 1
        const val HPC_WIDGET_GUIDANCE_NOT_SHOWN: Int = 0
        const val INT_PRACTICE_TOUCH_AREA_BOTH: Int = 0
        const val INT_PRACTICE_TOUCH_AREA_LEFT: Int = 2
        const val INT_PRACTICE_TOUCH_AREA_RIGHT: Int = 1
        const val KEY_BOOLEAN_3TAB_MOVE_AMOUNT: String = "3tab_move_amount"
        const val KEY_BOOLEAN_ASSIST_ALL_DONE: String = "assist_all_done"
        const val KEY_BOOLEAN_ASSIST_IDI_TAPPED: String = "assist_idi"
        const val KEY_BOOLEAN_ASSIST_SIDE_SENSE_GUIDANCE_TAPPED: String =
            "assist_side_sense_guidance"
        const val KEY_BOOLEAN_ASSIST_XPERIA_ASSIST_TAPPED: String = "assist_xperia_assist"
        const val KEY_BOOLEAN_AUTO_SHORTCUT_ADD_DIALOG_SHOWN_ALIPAY: String =
            "auto_shortcut_add_dialog_shown_alipay"
        const val KEY_BOOLEAN_AUTO_SHORTCUT_ADD_DIALOG_SHOWN_WECHATPAY: String =
            "auto_shortcut_add_dialog_shown_wechatpay"
        private const val KEY_BOOLEAN_BLACK_LIST_DIALOG_SHOWN = "black_list_dialog_shown"
        const val KEY_BOOLEAN_DISPLAY_TOUCH_VIEW_ANIMATION: String = "display_touch_view_animation"
        const val KEY_BOOLEAN_DISPLAY_TOUCH_VIEW_AUTO_HIDE: String = "display_touch_auto_hide"
        const val KEY_BOOLEAN_DISPLAY_TOUCH_VIEW_SHOW_TOUCH_AREA: String =
            "display_touch_view_show_touch_area"
        const val KEY_BOOLEAN_DISPLAY_TOUCH_VIEW_WIDTH_SETTING: String =
            "display_touch_view_width_setting"
        const val KEY_BOOLEAN_DOUBLE_TAP_ENABLED: String = "double_tap_enabled"
        const val KEY_BOOLEAN_DYNAMIC_AREA_FILTER: String = "dynamic_area_filter"
        const val KEY_BOOLEAN_DYNAMIC_AREA_FILTER_MEDIA_VERSION: String =
            "dynamic_area_filter_media_version"
        const val KEY_BOOLEAN_ENABLE_ACTIVE_AREA: String = "enable_active_area"
        const val KEY_BOOLEAN_FIXED_AREA: String = "fixed_area"
        private const val KEY_BOOLEAN_FIX_CUSTOM_APP_PRESET_DONE = "fix_custom_app_preset_done"
        const val KEY_BOOLEAN_FLOATING_MODE_ENABLED: String = "floating_mode_enabled"
        const val KEY_BOOLEAN_FORCE_MULTI_OOBE: String = "force_multi_oobe"
        const val KEY_BOOLEAN_FORCE_OOBE: String = "force_oobe"
        const val KEY_BOOLEAN_GUIDANCE_NOTIFICATION_REPLACED: String =
            "guidance_notification_replaced"
        const val KEY_BOOLEAN_GUIDANCE_NOTIFICATION_SHOWN: String = "guidance_notification_shown"
        const val KEY_BOOLEAN_GUIDANCE_NOTIFICATION_TAPPED: String = "guidance_notification_tapped"
        private const val KEY_BOOLEAN_HELP_GUIDE_DIALOG_SHOWN = "help_guide_dialog_shown"
        const val KEY_BOOLEAN_HPC_WIDGET_SETTING_OFF_BY_USER: String = "hpc_widget_off_by_setting"
        const val KEY_BOOLEAN_IS_LAUNCH_FROM_SHORTCUT: String = "is_launch_from_home"
        const val KEY_BOOLEAN_IS_NTP_BASED_INITIAL_USE_TIME: String = "ntp_based_initial_use_time"
        const val KEY_BOOLEAN_IS_SETTINGS_INITIALIZED: String = "is_settings_initialized"
        const val KEY_BOOLEAN_LANDSCAPE_BOTTOM_TOUCH: String = "landscape_bottom_touch"
        private const val KEY_BOOLEAN_LEFT_HAND_STATUS = "left_hand_status"
        const val KEY_BOOLEAN_LOOPS: String = "loops"
        const val KEY_BOOLEAN_MEASURE_MENU_PERFORMANCE: String = "measure_menu_performance"
        const val KEY_BOOLEAN_MORE_APPS: String = "more_apps"
        const val KEY_BOOLEAN_MULTI_OOBE_DONE: String = "multi_oobe_done"
        const val KEY_BOOLEAN_MULTI_TOUCH_GESTURE_DETECTOR: String = "multi_touch_gesture_detector"
        const val KEY_BOOLEAN_OOBE_DONE: String = "oobe_done"
        const val KEY_BOOLEAN_POINTER_LOCATION: String = "pointer_location"
        const val KEY_BOOLEAN_PROMPT_DISPLAY_TOUCH_VIEW_LEFT_HAND_STATUS: String =
            "prompt_display_view_left_hand_status"
        const val KEY_BOOLEAN_RESET_ASSIST: String = "reset_assist"
        const val KEY_BOOLEAN_RESET_LOOPS_PROMPT: String = "reset_loops_prompt"
        private const val KEY_BOOLEAN_SCROLL_DOWN_LEFT_HAND_STATUS = "scroll_down_left_hand_status"
        private const val KEY_BOOLEAN_SCROLL_UP_LEFT_HAND_STATUS = "scroll_up_left_hand_status"
        const val KEY_BOOLEAN_SHOULD_SHOW_DISPLAYTOUCH_DIALOG: String =
            "should_show_displaytouch_dialog"
        const val KEY_BOOLEAN_SHOW_DYNAMIC_AREA_FILTER: String = "show_dynamic_area_filter"
        const val KEY_BOOLEAN_SHOW_GESTURE_NAVIGATION_AREA: String = "show_gesture_navigation_area"
        const val KEY_BOOLEAN_SHOW_IN_SCREENSHOT: String = "show_in_screenshot"
        const val KEY_BOOLEAN_SIDEBAR_MOVING_STATUS: String = "sidebar_moving_status"
        const val KEY_BOOLEAN_SIDE_SENSE_LAUNCHER: String = "side_sense_launcher"
        const val KEY_BOOLEAN_SIDE_SENSE_LOCKSCREEN_SETTINGS: String = "side_sense_ls_settings"
        const val KEY_BOOLEAN_SIDE_SENSE_WIDGET: String = "side_sense_widget"
        const val KEY_BOOLEAN_TAB_ANIMATION_END: String = "tab_animation_end"
        const val KEY_BOOLEAN_TUTORIAL_PRACTICE_AREA_TAP: String =
            "tutorial_practice_location_area_tap"
        const val KEY_BOOLEAN_USE_SS_BAR_STATUS_WHEN_CD_IS_TURNED_OFF: String =
            "use_ss_bar_status_when_cd_is_turned_off"
        const val KEY_INT_ASSIST_IDI_SETTINGS_SHOW_COUNT: String = "assist_idi_settings_show_count"
        const val KEY_INT_ASSIST_INTERVAL_TIME: String = "assist_interval_time"
        const val KEY_INT_ASSIST_SIDE_SENSE_GUIDANCE_SHOW_COUNT: String =
            "assist_side_sense_guidance_show_count"
        const val KEY_INT_ASSIST_XPERIA_ASSIST_SHOW_COUNT: String =
            "assist_xperia_assist_show_count"
        const val KEY_INT_BLUR_RADIUS: String = "blur_radius"
        const val KEY_INT_DASHBOARD_SHOW_COUNT: String = "dashboard_show_count"
        const val KEY_INT_DASHBOARD_SHOW_COUNT_USE: String = "dashboard_show_count_use"
        const val KEY_INT_DASHBOARD_SHOW_COUNT_USE_NOT: String = "dashboard_show_count_use_not"
        const val KEY_INT_DASHBOARD_TIPS_STATUS: String = "dashboard_tips_status"
        const val KEY_INT_DIALOG_STATUS_ALI_PAY_PAY: String = "dialog_status_ali_pay_pay"
        const val KEY_INT_DIALOG_STATUS_ALI_PAY_SCAN: String = "dialog_status_ali_pay_scan"
        const val KEY_INT_DIALOG_STATUS_WE_CHAT_PAY_PAY: String = "dialog_status_we_chat_pay_pay"
        const val KEY_INT_DIALOG_STATUS_WE_CHAT_PAY_SCAN: String = "dialog_status_we_chat_pay_scan"
        const val KEY_INT_DISPLAYTOUCH_LONG_PRESS_TIMEOUT: String =
            "displaytouch_long_press_timeout"
        const val KEY_INT_DISPLAY_TOUCH_VIEW_ACTIVE_TRANSPARENCY: String =
            "display_touch_view_active_transparency"
        const val KEY_INT_DISPLAY_TOUCH_VIEW_ANIMATION_TIME: String =
            "display_touch_view_animation_time"
        const val KEY_INT_DISPLAY_TOUCH_VIEW_HEIGHT: String = "display_touch_view_height"
        const val KEY_INT_DISPLAY_TOUCH_VIEW_HEIGHT_LAND: String = "display_touch_view_height_land"
        const val KEY_INT_DISPLAY_TOUCH_VIEW_HIDE_TIME: String = "display_touch_view_hide_time"
        const val KEY_INT_DISPLAY_TOUCH_VIEW_IDLE_ANIMATION_TIME: String =
            "display_touch_view_idle_animation_time"
        const val KEY_INT_DISPLAY_TOUCH_VIEW_IDLE_TRANSPARENCY: String =
            "display_touch_view_idle_transparency"
        const val KEY_INT_DISPLAY_TOUCH_VIEW_POSITION: String = "display_touch_view_position"
        const val KEY_INT_DISPLAY_TOUCH_VIEW_POSITION_CHANGING_METHOD: String =
            "display_touch_view_position_changing_method"
        const val KEY_INT_DISPLAY_TOUCH_VIEW_POSITION_LAND: String =
            "display_touch_view_position_land"

        @Deprecated("")
        val KEY_INT_DISPLAY_TOUCH_VIEW_TRANSPARENCY: String = "display_touch_view_transparency"
        const val KEY_INT_DISPLAY_TOUCH_VIEW_WIDTH: String = "display_touch_view_width"
        const val KEY_INT_DISPLAY_TOUCH_VIEW_WIDTH_LAND: String = "display_touch_view_width_land"
        const val KEY_INT_DOUBLE_TAP_DIALOG_INDEX: String = "double_tap_index"
        const val KEY_INT_EP_DASHBOARD_GESTURE_BAR_COUNT: String =
            "entry_point_dashboard_gesture_bar_count"
        const val KEY_INT_EP_DASHBOARD_HOME_COUNT: String = "entry_point_dashboard_home_count"
        const val KEY_INT_EP_MW_GESTURE_BAR_COUNT: String = "entry_point_mw_gesture_bar_count"
        const val KEY_INT_EP_MW_GESTURE_FLOATING_COUNT: String =
            "entry_point_mw_gesture_floating_count"
        const val KEY_INT_EP_MW_HOME_COUNT: String = "entry_point_mw_home_count"
        const val KEY_INT_EP_SS_GESTURE_BAR_COUNT: String = "entry_point_ss_gesture_bar_count"
        const val KEY_INT_EP_SS_GESTURE_FLOATING_COUNT: String =
            "entry_point_ss_gesture_floating_count"
        const val KEY_INT_EP_SS_HOME_COUNT: String = "entry_point_ss_home_count"
        const val KEY_INT_FLING_VELOCITY: String = "fling_velocity"
        const val KEY_INT_FLOATING_ANIMATION_VIEW_START_MARGIN: String =
            "floating_animation_view_start_margin"
        const val KEY_INT_FLOATING_CHANGED_COUNT: String = "floating_changed_count"
        const val KEY_INT_FLOATING_TAP_POSITION_X: String = "floating_tap_position_x"
        const val KEY_INT_FLOATING_TAP_POSITION_Y: String = "floating_tap_position_y"
        const val KEY_INT_FLOATING_VIEW_ORIENTATION: String = "floating_view_orientation"
        const val KEY_INT_FLOATING_VIEW_X: String = "floating_view_x"
        const val KEY_INT_FLOATING_VIEW_X_LAND: String = "floating_view_x_land"
        const val KEY_INT_FLOATING_VIEW_Y: String = "floating_view_y"
        const val KEY_INT_FLOATING_VIEW_Y_LAND: String = "floating_view_y_land"
        const val KEY_INT_GESTURE_DOUBLE_TAP: String = "double_tap"
        const val KEY_INT_GESTURE_LONG_TAP: String = "long_tap"
        const val KEY_INT_GESTURE_SCROLL_DOWN: String = "scroll_down"
        const val KEY_INT_GESTURE_SCROLL_SIDE: String = "scroll_side"
        const val KEY_INT_GESTURE_SCROLL_UP: String = "scroll_up"
        const val KEY_INT_GESTURE_WAIT_TIME: String = "gesture_wait_time"
        const val KEY_INT_GUIDANCE_NOTIFICATION_INTERVAL_TIME: String =
            "guidance_notification_interval_time"
        const val KEY_INT_GUIDE_MW_PAGE_COUNT: String = "guide_mw_page_count"
        const val KEY_INT_GUIDE_SS_PAGE_COUNT: String = "guide_ss_page_count"
        const val KEY_INT_GUIDE_TOP_PAGE_COUNT: String = "guide_top_page_count"
        const val KEY_INT_GUIDE_TW_PAGE_COUNT: String = "guide_tw_page_count"
        const val KEY_INT_HEADPHONE_NOTIFICATION_STATUS: String = "headphone_notification_status"
        const val KEY_INT_HPC_WIDGET_GUIDANCE_DONE: String = "hpc_widget_guidance_done"
        const val KEY_INT_IDD_GESTURE_ACTION_DOUBLE_TAP_COUNT: String =
            "idd_gesture_action_double_tap_count"
        const val KEY_INT_IDD_GESTURE_ACTION_SCROLL_DOWN_COUNT: String =
            "idd_gesture_action_scroll_down_count"
        const val KEY_INT_IDD_GESTURE_ACTION_SCROLL_SIDE_COUNT: String =
            "idd_gesture_action_scroll_side_count"
        const val KEY_INT_IDD_GESTURE_ACTION_SCROLL_UP_COUNT: String =
            "idd_gesture_action_scroll_up_count"
        const val KEY_INT_IDD_INTERVAL_TIME: String = "idd_interval_time"
        const val KEY_INT_IDD_LAST_GESTURE_ACTION_DOUBLE_TAP_COUNT: String =
            "idd_last_gesture_action_double_tap_count"
        const val KEY_INT_IDD_LAST_GESTURE_ACTION_SCROLL_DOWN_COUNT: String =
            "idd_last_gesture_action_scroll_down_count"
        const val KEY_INT_IDD_LAST_GESTURE_ACTION_SCROLL_SIDE_COUNT: String =
            "idd_last_gesture_action_scroll_side_count"
        const val KEY_INT_IDD_LAST_GESTURE_ACTION_SCROLL_UP_COUNT: String =
            "idd_last_gesture_action_scroll_up_count"
        const val KEY_INT_IDD_LAST_LONGTAP_COUNT: String = "idd_last_longtap_count"
        const val KEY_INT_IDD_LONGTAP_COUNT: String = "idd_longtap_count"
        const val KEY_INT_IDD_SWIPE_DOWN_ANGLE_AVERAGE: String = "idd_swipe_down_angle_average"
        const val KEY_INT_IDD_SWIPE_DOWN_DISTANCE_AVERAGE: String =
            "idd_swipe_down_distance_average"
        const val KEY_INT_IDD_SWIPE_SIDE_ANGLE_AVERAGE: String = "idd_swipe_side_angle_average"
        const val KEY_INT_IDD_SWIPE_SIDE_DISTANCE_AVERAGE: String =
            "idd_swipe_side_distance_average"
        const val KEY_INT_IDD_SWIPE_UP_ANGLE_AVERAGE: String = "idd_swipe_up_angle_average"
        const val KEY_INT_IDD_SWIPE_UP_DISTANCE_AVERAGE: String = "idd_swipe_up_distance_average"
        const val KEY_INT_LAST_DASHBOARD_SHOW_COUNT: String = "last_dashboard_show_count"
        const val KEY_INT_LAST_DASHBOARD_SHOW_COUNT_USE: String = "last_dashboard_show_count_use"
        const val KEY_INT_LAST_DASHBOARD_SHOW_COUNT_USE_NOT: String =
            "last_dashboard_show_count_use_not"
        const val KEY_INT_LAST_MONTH_FLOATING_CHANGED_COUNT: String =
            "last_month_floating_changed_count"
        const val KEY_INT_LAST_MONTH_MENU_SHOW_COUNT: String = "last_month_menu_show_count"
        const val KEY_INT_LAST_MONTH_MENU_SHOW_COUNT_USE: String = "last_month_menu_show_count_use"
        const val KEY_INT_LAST_MONTH_MENU_SHOW_COUNT_USE_NOT: String =
            "last_month_menu_show_count_use_not"
        const val KEY_INT_LAST_MONTH_MENU_SHOW_COUNT_USE_ON_GE: String =
            "last_month_menu_show_count_use_on_ge"
        const val KEY_INT_LAST_MONTH_MW_MENU_SHOW_COUNT: String = "last_month_mw_menu_show_count"
        const val KEY_INT_LAST_MONTH_MW_MENU_SHOW_COUNT_USE: String =
            "last_month_mw_menu_show_count_use"
        const val KEY_INT_LAST_MONTH_MW_MENU_SHOW_COUNT_USE_NOT: String =
            "last_month_mw_menu_show_count_use_not"
        const val KEY_INT_LAST_MONTH_TW_MENU_SHOW_COUNT_USE: String =
            "last_month_tw_menu_show_count_use"
        const val KEY_INT_LAST_MONTH_TW_MENU_SHOW_COUNT_USE_ON_GE: String =
            "last_month_tw_menu_show_count_use_on_ge"
        const val KEY_INT_LAST_WEEK_EP_DASHBOARD_GESTURE_BAR_COUNT: String =
            "last_week_entry_point_dashboard_gesture_bar_count"
        const val KEY_INT_LAST_WEEK_EP_DASHBOARD_HOME_COUNT: String =
            "last_week_entry_point_dashboard_home_count"
        const val KEY_INT_LAST_WEEK_EP_MW_GESTURE_BAR_COUNT: String =
            "last_week_entry_point_mw_gesture_bar_count"
        const val KEY_INT_LAST_WEEK_EP_MW_GESTURE_FLOATING_COUNT: String =
            "last_week_entry_point_mw_gesture_floating_count"
        const val KEY_INT_LAST_WEEK_EP_MW_HOME_COUNT: String = "last_week_entry_point_mw_home_count"
        const val KEY_INT_LAST_WEEK_EP_SS_GESTURE_BAR_COUNT: String =
            "last_week_entry_point_ss_gesture_bar_count"
        const val KEY_INT_LAST_WEEK_EP_SS_GESTURE_FLOATING_COUNT: String =
            "last_week_entry_point_ss_gesture_floating_count"
        const val KEY_INT_LAST_WEEK_EP_SS_HOME_COUNT: String = "last_week_entry_point_ss_home_count"
        const val KEY_INT_LAST_WEEK_MENU_SETTINGS_ICON_COUNT: String =
            "last_week_menu_settings_icon_count"
        const val KEY_INT_LAST_WEEK_MW_MENU_SWIPE_COUNT: String = "last_week_mw_menu_swip_count"
        const val KEY_INT_LAST_WEEK_MW_MENU_TAB_COUNT: String = "last_week_mw_menu_tab_count"
        const val KEY_INT_LAST_WEEK_MW_TOOL_CANCEL_COUNT: String = "last_week_mw_tool_cancel_count"
        const val KEY_INT_LAST_WEEK_MW_TOOL_SWAP_COUNT: String = "last_week_mw_tool_swap_count"
        const val KEY_INT_LAST_WEEK_MW_TOOL_TW_COUNT: String = "last_week_mw_tool_tw_count"
        const val KEY_INT_LAST_WEEK_SS_MENU_SWIPE_COUNT: String = "last_week_ss_menu_swip_count"
        const val KEY_INT_LAST_WEEK_SS_MENU_TAB_COUNT: String = "last_week_ss_menu_tab_count"
        const val KEY_INT_LAST_WEEK_TOOL_MUTE_COUNT: String = "last_week_tool_mute_count"
        const val KEY_INT_LAST_WEEK_TOOL_NOTIFICATION_COUNT: String =
            "last_week_tool_notification_count"
        const val KEY_INT_LAST_WEEK_TOOL_SCREENSHOT_COUNT: String =
            "last_week_tool_screenshot_count"
        const val KEY_INT_LAST_WHATSNEW_VERSION: String = "last_whatsnew_version"
        const val KEY_INT_LAUNCHER_STATUS: String = "launcher_status"
        const val KEY_INT_LONG_PRESS_DIALOG_INDEX: String = "long_press_dialog_index"
        const val KEY_INT_LOOPS_POSITION: String = "loops_position"
        const val KEY_INT_LOOPS_PROMPT_INTERVAL_TIME: String = "loops_prompt_interval_time"
        const val KEY_INT_LOOPS_PROMPT_SHOW_COUNT: String = "loops_show_count"
        const val KEY_INT_LOOPS_PROMPT_SHOW_COUNT_TO_STOP: String = "loops_show_count_to_stop"
        const val KEY_INT_MENU_SETTINGS_ICON_COUNT: String = "menu_settings_icon_count"
        const val KEY_INT_MENU_SHOW_COUNT_USE: String = "menu_show_count_use"
        const val KEY_INT_MENU_SHOW_COUNT_USE_NOT: String = "menu_show_count_use_not"
        const val KEY_INT_MENU_SHOW_COUNT_USE_ON_GE: String = "menu_show_count_use_on_ge"
        const val KEY_INT_MOVE_POSITION: String = "move_position"
        const val KEY_INT_MULTI_LAUNCHER_STATUS: String = "multi_launcher_status"
        const val KEY_INT_MW_MENU_SHOW_COUNT: String = "mw_menu_show_count"
        const val KEY_INT_MW_MENU_SHOW_COUNT_USE: String = "mw_menu_show_count_use"
        const val KEY_INT_MW_MENU_SHOW_COUNT_USE_NOT: String = "mw_menu_show_count_use_not"
        const val KEY_INT_MW_MENU_SWIPE_COUNT: String = "mw_menu_swip_count"
        const val KEY_INT_MW_MENU_TAB_COUNT: String = "mw_menu_tab_count"
        const val KEY_INT_MW_OOBE_SHOW_COUNT: String = "mw_oobe_show_count"
        const val KEY_INT_MW_TOOL_CANCEL_COUNT: String = "mw_tool_cancel_count"
        const val KEY_INT_MW_TOOL_SWAP_COUNT: String = "mw_tool_swap_count"
        const val KEY_INT_MW_TOOL_TW_COUNT: String = "mw_tool_tw_count"
        const val KEY_INT_ORIENTATION: String = "orientation"
        const val KEY_INT_POPUP_PROMPT_SHOW_COUNT: String = "popup_show_count"
        const val KEY_INT_PRACTICE_DOUBLE_TAP_STATUS: String = "practice_double_tap_status"
        const val KEY_INT_PRACTICE_HEIGHT_BOTTOM: String = "practice_height_bottom"
        const val KEY_INT_PRACTICE_HEIGHT_TOP: String = "practice_height_top"
        const val KEY_INT_PRACTICE_SCROLL_DOWN_STATUS: String = "practice_scroll_down_status"
        const val KEY_INT_PRACTICE_SCROLL_UP_STATUS: String = "practice_scroll_up_status"
        const val KEY_INT_PRACTICE_TOUCH_AREA: String = "practice_touch_area"
        const val KEY_INT_PROMPT_DISPLAY_TOUCH_VIEW_CENTER_Y: String =
            "prompt_display_touch_view_center_y"
        const val KEY_INT_PROMPT_DISPLAY_TOUCH_VIEW_WIDTH: String =
            "prompt_display_touch_view_width"
        const val KEY_INT_SCREENSHOT_STATUS: String = "screenshot_status"
        const val KEY_INT_SCROLL_DISTANCE: String = "scroll_distance"
        const val KEY_INT_SCROLL_DISTANCE_DIALOG_INDEX: String = "scroll_distance_index"
        private const val KEY_INT_SCROLL_DOWN_THUMB_POSITION_X = "scroll_down_thumb_position_x"
        private const val KEY_INT_SCROLL_DOWN_THUMB_POSITION_Y = "scroll_down_thumb_position_y"
        private const val KEY_INT_SCROLL_DOWN_TRANSLATION_X = "scroll_down_translation_x"
        private const val KEY_INT_SCROLL_DOWN_TRANSLATION_Y = "scroll_down_translation_y"
        const val KEY_INT_SCROLL_SIDE_COUNT: String = "scroll_side_count"
        private const val KEY_INT_SCROLL_UP_THUMB_POSITION_X = "scroll_up_thumb_position_x"
        private const val KEY_INT_SCROLL_UP_THUMB_POSITION_Y = "scroll_up_thumb_position_y"
        private const val KEY_INT_SCROLL_UP_TRANSLATION_X = "scroll_up_translation_x"
        private const val KEY_INT_SCROLL_UP_TRANSLATION_Y = "scroll_up_translation_y"
        const val KEY_INT_SIDEBAR_START_AND_CLOSE_ANIMATION_TIME: String =
            "sidebar_start_and_close_animation_time"
        const val KEY_INT_SIDE_TOUCH_VIEW_HEIGHT: String = "side_touch_view_height"
        const val KEY_INT_SIDE_TOUCH_VIEW_POSITION: String = "side_touch_view_position"
        const val KEY_INT_SIDE_TOUCH_VIEW_TRANSPARENCY: String = "side_touch_view_transparency"
        const val KEY_INT_SIDE_TOUCH_VIEW_WIDTH: String = "side_touch_view_width"
        const val KEY_INT_SS_MENU_SHOW_COUNT: String = "ss_menu_show_count"
        const val KEY_INT_SS_MENU_SWIPE_COUNT: String = "ss_menu_swip_count"
        const val KEY_INT_SS_MENU_TAB_COUNT: String = "ss_menu_tab_count"
        const val KEY_INT_SS_OOBE_SHOW_COUNT: String = "ss_oobe_show_count"
        const val KEY_INT_SWIPE_CHOICE_SCROLL_DISTANCE_R0: String =
            "swipe_choice_scroll_distance_r0"
        const val KEY_INT_SWIPE_CHOICE_VIEW_BASE_POSITION_X_WHEN_SWIPE_DOWN: String =
            "swipe_choice_view_base_position_x_when_swipe_down"
        const val KEY_INT_SWIPE_CHOICE_VIEW_BASE_POSITION_X_WHEN_SWIPE_SIDE: String =
            "swipe_choice_view_base_position_x_when_swipe_side"
        const val KEY_INT_SWIPE_CHOICE_VIEW_BASE_POSITION_X_WHEN_SWIPE_UP: String =
            "swipe_choice_view_base_position_x_when_swipe_up"
        const val KEY_INT_SWIPE_CHOICE_VIEW_BASE_POSITION_Y_WHEN_SWIPE_DOWN: String =
            "swipe_choice_view_base_position_y_when_swipe_down"
        const val KEY_INT_SWIPE_CHOICE_VIEW_BASE_POSITION_Y_WHEN_SWIPE_SIDE: String =
            "swipe_choice_view_base_position_y_when_swipe_side"
        const val KEY_INT_SWIPE_CHOICE_VIEW_BASE_POSITION_Y_WHEN_SWIPE_UP: String =
            "swipe_choice_view_base_position_y_when_swipe_up"
        const val KEY_INT_SWIPE_CHOICE_VIEW_FADE_OUT_ANIMATION_TIME: String =
            "swipe_choice_view_fade_out_animation_time"
        const val KEY_INT_SWIPE_CHOICE_VIEW_MOVE_CONSTRAINTS_X: String =
            "swipe_choice_view_move_constraints_x"
        const val KEY_INT_SWIPE_CHOICE_VIEW_MOVE_CONSTRAINTS_Y: String =
            "swipe_choice_view_move_constraints_y"
        const val KEY_INT_SWIPE_CHOICE_VIEW_SELECTED_ICON_SIZE: String =
            "swipe_choice_view_selected_icon_size"
        const val KEY_INT_SWIPE_CHOICE_VIEW_TIME_CONSTANT: String =
            "swipe_choice_view_time_constant"
        const val KEY_INT_TAB_SWIPE_THRESHOLD: String = "tab_swipe_threshold"
        const val KEY_INT_THREE_WAY_SWIPE_DIAGONALLY_DOWN_ANGLE: String =
            "3way_swipe_diagonally_down_angle"
        const val KEY_INT_THREE_WAY_SWIPE_DIAGONALLY_UP_ANGLE: String =
            "3way_swipe_diagonally_up_angle"
        private const val KEY_INT_THUMB_POSITION_X = "thumb_position_x"
        private const val KEY_INT_THUMB_POSITION_Y = "thumb_position_y"
        const val KEY_INT_TOOL_MUTE_COUNT: String = "tool_mute_count"
        const val KEY_INT_TOOL_NOTIFICATION_COUNT: String = "tool_notification_count"
        const val KEY_INT_TOOL_SCREENSHOT_COUNT: String = "tool_screenshot_count"
        private const val KEY_INT_TRANSLATION_X = "translation_x"
        private const val KEY_INT_TRANSLATION_Y = "translation_y"
        const val KEY_INT_TURN_OFF_METHOD: String = "turn_off_method"
        const val KEY_INT_TW_MENU_SHOW_COUNT_USE: String = "tw_menu_show_count_use"
        const val KEY_INT_TW_MENU_SHOW_COUNT_USE_ON_GE: String = "tw_menu_show_count_use_on_ge"
        const val KEY_INT_VALID_AREA_Y: String = "valid_area_y"
        const val KEY_INT_WIDGET_SIZE: String = "widget_size"
        const val KEY_LONG_IDD_DASHBOARD_SHOW_TIME_AVERAGE: String =
            "idd_dashboard_show_time_average"
        const val KEY_LONG_IDD_SWIPE_DOWN_TIME_AVERAGE: String = "idd_swipe_down_time_average"
        const val KEY_LONG_IDD_SWIPE_SIDE_TIME_AVERAGE: String = "idd_swipe_side_time_average"
        const val KEY_LONG_IDD_SWIPE_UP_TIME_AVERAGE: String = "idd_swipe_up_time_average"
        const val KEY_LONG_INITIAL_USE_TIME: String = "initial_use_time"
        const val KEY_LONG_LAST_SHOW_LOOPS_PROMPT_TIME: String = "last_loops_prompt_time"
        const val KEY_VERSION_HISTORY: String = "version_history"
        const val LONG_SCROLL_DISTANCE: Int = 240
        const val SCREENSHOT_STATUS_EXECUTABLE: Int = 0
        const val SCREENSHOT_STATUS_TAKING: Int = 1
        const val SCREENSHOT_STATUS_WAIT_ANIMATION: Int = 2
        const val SHORT_SCROLL_DISTANCE: Int = 150
        const val SIDE_SENSE_VERSION: Int = 14
        const val SIDE_SENSE_VERSION_1_0: Int = 1
        const val SIDE_SENSE_VERSION_1_1: Int = 2
        const val SIDE_SENSE_VERSION_2_0: Int = 3
        const val SIDE_SENSE_VERSION_2_1: Int = 4
        const val SIDE_SENSE_VERSION_3_0: Int = 5
        const val SIDE_SENSE_VERSION_3_2: Int = 6
        const val SIDE_SENSE_VERSION_4_0: Int = 7
        const val SIDE_SENSE_VERSION_4_1: Int = 8
        const val SIDE_SENSE_VERSION_4_2: Int = 9
        const val SIDE_SENSE_VERSION_5_0: Int = 10
        const val SIDE_SENSE_VERSION_5_1: Int = 11
        const val SIDE_SENSE_VERSION_6_0: Int = 12
        const val SIDE_SENSE_VERSION_7_0: Int = 13
        const val SIDE_SENSE_VERSION_7_1: Int = 14
        const val SIDE_TOUCH_VIEW_TRANSPARENCY_MAX: Int = 100
        const val SIDE_TOUCH_VIEW_TRANSPARENCY_MIN: Int = 20
        private const val TAG = "Preferences"
        const val TURN_OFF_METHOD_DIALOG: Int = 3
        const val TURN_OFF_METHOD_PRO: Int = 1
        const val TURN_OFF_METHOD_SETTINGS: Int = 2
        const val TURN_OFF_METHOD_UNKNOWN: Int = 0
        const val VALUE_INT_LAUNCHER_STATUS_DONE: Int = 2
        const val VALUE_INT_MULTI_LAUNCHER_STATUS_DONE: Int = 2
        const val VALUE_INT_MULTI_SELECT_PRIMARY_APP: Int = 0
        const val VALUE_INT_MULTI_SELECT_SECONDARY_APP: Int = 1
        const val VALUE_INT_PRACTICE_DOUBLE_TAP_STATUS_FAR_POSITION: Int = 4
        const val VALUE_INT_PRACTICE_DOUBLE_TAP_STATUS_FAST: Int = 3
        const val VALUE_INT_PRACTICE_DOUBLE_TAP_STATUS_SLOW: Int = 2
        const val VALUE_INT_PRACTICE_ERROR_STATUS_TIMEOUT: Int = 6
        const val VALUE_INT_PRACTICE_ERROR_TOUCH_EDGE_VIEW: Int = 7
        const val VALUE_INT_PRACTICE_SCROLL_STATUS_SHORT: Int = 5
        const val VALUE_INT_PRACTICE_STATUS_SUCCESS: Int = 1
        const val VALUE_INT_PRACTICE_STATUS_UNKNOWN: Int = 0
        const val VALUE_INT_SELECT_FOREGROUND_APP: Int = 0
        const val VALUE_INT_SELECT_TEMPORARY_WINDOW_APP: Int = 1
        const val XDPI_OF_PF42: Float = 537.882f
        const val YDPI_OF_PF42: Float = 537.882f
        val DEFAULT_VALUE_DIALOG_STATUS: Int =
            AutoAddShortcutDialogManager.AutoAddDialogStatus.NOT_YET_DETERMINED.status
        val GESTURE_ACTIONS_TEXT_RES: MutableMap<Int, Int> = mutableMapOf(
            0 to R.string.ss_strings_gesture_none_txt,
            1 to R.string.ss_strings_gesture_app_launcher_menu_txt,
            2 to R.string.ss_strings_gesture_navigate_back_txt,
            3 to R.string.ss_strings_gesture_home_txt,
            4 to R.string.ss_strings_gesture_notification_panel_txt,
            6 to R.string.ss_strings_gesture_last_used_app_txt,
            7 to R.string.ss_strings_gesture_recent_apps_txt,
            9 to R.string.ss_strings_action_settings_title_txt,
            10 to R.string.ss_strings_gesture_multi_window_menu_txt,
            11 to R.string.ss_strings_action_settings_title_txt,
            12 to R.string.ss_strings_gesture_screenshot_txt,
            13 to R.string.ss_strings_gesture_dualdrive_txt,
            14 to R.string.ss_strings_gesture_app_and_shortcut_txt,
            16 to R.string.ss_strings_gesture_dashboard_txt
        )
        private val GESTURE_ACTIONS_COUNTER: MutableMap<Int, Int> = mutableMapOf(
            1 to 0,
            2 to 0,
            3 to 0,
            4 to 0,
            5 to 0,
            6 to 0,
            7 to 0,
            9 to 0,
            10 to 0,
            11 to 0,
            12 to 0,
            13 to 0,
            14 to 0,
            16 to 0
        )
        private val LAST_MONTH_GESTURE_ACTIONS_COUNTER: MutableMap<Int, Int> = mutableMapOf(
            1 to 0,
            2 to 0,
            3 to 0,
            4 to 0,
            5 to 0,
            6 to 0,
            7 to 0,
            9 to 0,
            10 to 0,
            11 to 0,
            12 to 0,
            13 to 0,
            14 to 0,
            16 to 0
        )

        private val SHORTCUTS_TAP_COUNTER: MutableMap<String, Int> = mutableMapOf(
            ItemType.WIFI to 0,
            ItemType.MOBILE_DATA to 0,
            ItemType.BLUETOOTH to 0,
            ItemType.AUTO_ROTATE to 0,
            ItemType.EXTRA_DIM to 0,
            ItemType.SCREENSHOT to 0,
            "NOTIFICATION" to 0,
            ItemType.FLASHLIGHT to 0,
            ItemType.BRIGHTNESS to 0
        )

        private val LAST_SHORTCUTS_TAP_COUNTER: MutableMap<String, Int> = mutableMapOf(
            ItemType.WIFI to 0,
            ItemType.MOBILE_DATA to 0,
            ItemType.BLUETOOTH to 0,
            ItemType.AUTO_ROTATE to 0,
            ItemType.EXTRA_DIM to 0,
            ItemType.SCREENSHOT to 0,
            "NOTIFICATION" to 0,
            ItemType.FLASHLIGHT to 0,
            ItemType.BRIGHTNESS to 0
        )

        private val SHORTCUTS_LONG_TAP_COUNTER: MutableMap<String, Int> = mutableMapOf(
            ItemType.WIFI to 0,
            ItemType.MOBILE_DATA to 0,
            ItemType.BLUETOOTH to 0,
            ItemType.AUTO_ROTATE to 0,
            ItemType.EXTRA_DIM to 0,
            ItemType.SCREENSHOT to 0,
            "NOTIFICATION" to 0,
            ItemType.FLASHLIGHT to 0,
            ItemType.BRIGHTNESS to 0
        )

        private val LAST_SHORTCUTS_LONG_TAP_COUNTER: MutableMap<String, Int> = mutableMapOf(
            ItemType.WIFI to 0,
            ItemType.MOBILE_DATA to 0,
            ItemType.BLUETOOTH to 0,
            ItemType.AUTO_ROTATE to 0,
            ItemType.EXTRA_DIM to 0,
            ItemType.SCREENSHOT to 0,
            "NOTIFICATION" to 0,
            ItemType.FLASHLIGHT to 0,
            ItemType.BRIGHTNESS to 0
        )

        private val PERMISSION_DIALOG_SHOW_COUNT: MutableMap<PermissionGroup, Int> = mutableMapOf(
            PermissionGroup.BLUETOOTH to 0
        )

        private val LAST_PERMISSION_DIALOG_SHOW_COUNT: MutableMap<PermissionGroup, Int> =
            mutableMapOf(
                PermissionGroup.BLUETOOTH to 0
            )

        val SIDEBAR_WIDTH_PRESET_RES_LIST: List<Int> = listOf(
            R.dimen.display_touch_view_width_preset1,
            R.dimen.display_touch_view_width_preset2,
            R.dimen.display_touch_view_width_preset3
        )

        private var sInstance: Preferences? = null

        @Synchronized
        fun getInstance(context: Context): Preferences? {
            var preferences: Preferences?
            synchronized(Preferences::class.java) {
                if (sInstance == null) {
                    sInstance =
                        Preferences(context.createDeviceProtectedStorageContext())
                }
                preferences =
                    sInstance
            }
            return preferences
        }

        fun m3287i(preferences: Preferences, str: String?, i: Int, str2: String?) {
            preferences.putInt(str2, preferences.getInt(str!!) + i)
        }
    }
}