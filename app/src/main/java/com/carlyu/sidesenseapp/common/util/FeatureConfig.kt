package com.carlyu.sidesenseapp.common.util

import android.os.Build
import com.carlyu.sidesenseapp.common.reflection.SystemPropertiesR

object FeatureConfig {
    private const val TAG = "FeatureConfig"

    private fun getDebugProperty(str: String): Boolean? {
        if (Build.TYPE != "user") {
            val str2 = SystemPropertiesR.get(str, ShortcutUtil.SHORTCUT_NAME_NULL)
            val bool = when {
                str2 == "true" -> true
                str2 == "false" -> false
                else -> null
            }
            LogUtil.v(LogUtil.LOG_TAG, "getDebugProperty: $str=$bool for debug purpose")
            return bool
        }
        return null
    }

    fun isAuPayLinkEnabled(): Boolean {
        val debugProperty = getDebugProperty(SystemPropertiesR.PROPERTY_SS_AP_ENABLED)
        return debugProperty
            ?: (!ProductUtil.isSwVariantKddi() || ProductUtil.is182HRTL() || ProductUtil.is191HRTL() || ProductUtil.is192HRTL() || ProductUtil.is201HRTL() || ProductUtil.is202HRTL() || ProductUtil.isPdx212()).not()
    }

    fun isAutoAddDialogAndChangeRecommendItemToTopEnabled(): Boolean {
        val debugProperty = getDebugProperty(SystemPropertiesR.PROPERTY_SS_AC_ENABLED)
        return debugProperty
            ?: (ProductUtil.isSwVariantChina() && (ProductUtil.is212HRTL() || !(OSVersion.isP() || OSVersion.isQ() || OSVersion.isR() || ProductUtil.isPdx212()))).not()
    }

    fun isAutoHideSideBarEnabled(): Boolean {
        val debugProperty = getDebugProperty(SystemPropertiesR.PROPERTY_SS_AH_ENABLED)
        if (debugProperty != null) {
            return debugProperty
        }
        return if (OSVersion.isR() || OSVersion.isQ() || OSVersion.isP()) {
            ProductUtil.is182HRTL() || ProductUtil.is191HRTL() || ProductUtil.is192HRTL() || ProductUtil.is201HRTL() || ProductUtil.is202HRTL()
        } else {
            false
        }
    }

    fun isGuidanceNotificationEnabled(): Boolean {
        val debugProperty = getDebugProperty(SystemPropertiesR.PROPERTY_SS_GN_ENABLED)
        if (debugProperty != null) {
            return debugProperty
        }
        return if (OSVersion.isR() || OSVersion.isQ() || OSVersion.isP()) {
            ProductUtil.is182HRTL() || ProductUtil.is191HRTL() || ProductUtil.is192HRTL() || ProductUtil.is201HRTL() || ProductUtil.is202HRTL()
        } else {
            false
        }
    }

    fun isHelpGuideEnabled(): Boolean {
        val debugProperty = getDebugProperty(SystemPropertiesR.PROPERTY_SS_HG_ENABLED)
        return debugProperty ?: !ProductUtil.isPdx212()
    }

    fun isLandscapeModeEnabled(): Boolean {
        val debugProperty = getDebugProperty(SystemPropertiesR.PROPERTY_SS_LM_ENABLED)
        if (debugProperty != null) {
            return debugProperty
        }
        if (ProductUtil.isPdx201() || ProductUtil.isPdx212()) {
            return false
        }
        return (OSVersion.isR() || OSVersion.isQ() || OSVersion.isP()).not() || ProductUtil.is182HRTL() || ProductUtil.is191HRTL() || ProductUtil.is192HRTL() || ProductUtil.is201HRTL() || ProductUtil.is202HRTL()
    }

    fun isQsEnabled(): Boolean {
        val debugProperty = getDebugProperty(SystemPropertiesR.PROPERTY_SS_QS_ENABLED)
        if (debugProperty != null) {
            return debugProperty
        }
        return if (OSVersion.isR() || OSVersion.isQ() || OSVersion.isP()) {
            ProductUtil.is182HRTL() || ProductUtil.is191HRTL() || ProductUtil.is192HRTL() || ProductUtil.is201HRTL() || ProductUtil.is202HRTL()
        } else {
            false
        }
    }

    fun isSideSenseDefaultOff(): Boolean {
        return (ProductUtil.isGriffin() && ProductUtil.isSwVariantPro()) || ProductUtil.isPdx204() || ProductUtil.isPdx212()
    }

    fun isSsOnLockscreenEnabled(): Boolean {
        val debugProperty = getDebugProperty(SystemPropertiesR.PROPERTY_SS_LS_ENABLED)
        if (debugProperty != null) {
            return debugProperty
        }
        return if (OSVersion.isR() || OSVersion.isQ() || OSVersion.isP()) {
            ProductUtil.is182HRTL() || ProductUtil.is191HRTL() || ProductUtil.is192HRTL() || ProductUtil.is201HRTL() || ProductUtil.is202HRTL()
        } else {
            false
        }
    }

    fun isTwModeEnabled(): Boolean {
        val debugProperty = getDebugProperty(SystemPropertiesR.PROPERTY_SS_TW_ENABLED)
        if (debugProperty != null) {
            return debugProperty
        }
        if (ProductUtil.isPdx201() || ProductUtil.isPdx212()) {
            return false
        }
        return (OSVersion.isR() || OSVersion.isQ() || OSVersion.isP()).not() || ProductUtil.is182HRTL() || ProductUtil.is191HRTL() || ProductUtil.is192HRTL() || ProductUtil.is201HRTL() || ProductUtil.is202HRTL()
    }
}
