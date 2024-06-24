package com.carlyu.sidesenseapp.common.actions

enum class ActionType(val key: Int) {
    NONE(0),
    SIDE_SENSE_MENU(1),
    BACK(2),
    HOME(3),
    NOTIFICATION(4),
    ONE_HAND(5),
    SWITCH_APPS(6),
    RECENT_APPS(7),
    SETTINGS(9),
    MULTI_WINDOW_MENU(10),
    MULTI_WINDOW_SETTINGS(11),
    SCREENSHOT(12),
    DUALDRIVE(13),
    LAUNCH_APP_AND_SHORTCUT(14),
    LAUNCH_TW_APP(15),
    DASHBOARD(16);

    companion object {
        fun fromKey(key: Int): ActionType? {
            return entries.find { it.key == key }
        }

        fun getSupportedActions(): Set<ActionType> {
            return enumValues<ActionType>().toSet()
        }
    }
}
