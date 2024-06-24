package com.carlyu.sidesenseapp.common.sensing

import android.content.Context
import android.content.res.Resources
import com.carlyu.sidesenseapp.R
import com.carlyu.sidesenseapp.common.reflection.SystemPropertiesR
import com.carlyu.sidesenseapp.common.util.DisplayUtil
import com.carlyu.sidesenseapp.common.util.DpUtil
import com.carlyu.sidesenseapp.common.util.LogUtil

object TouchViewUtil {
    private const val TAG = "TouchViewUtil"

    private lateinit var mR: Resources
    private var mMyDisplayWidthPort: Int = 0
    private var mMyDisplayHeightPort: Int = 0
    private var mMyDisplayHeightNoNavBarPort: Int = 0
    private var mMyDisplayAspectPort: Float = 0f
    private var mMyDisplayWidthLand: Int = 0
    private var mMyDisplayHeightLand: Int = 0
    private var mMyDisplayHeightNoNavBarLand: Int = 0
    private var mMyDisplayAspectLand: Float = 0f
    private var mScaleFactor: Float = 0f
    private var mDisplayTouchViewParamsPort: TouchViewParams? = null
    private var mDisplayTouchViewParamsLand: TouchViewParams? = null
    private var mSideTouchViewParams: TouchViewParams? = null

    @Synchronized
    fun init(context: Context) {
        mR = context.resources
        val displayRealSize = DisplayUtil.getDisplayRealSize(context)
        val min = displayRealSize.x.coerceAtMost(displayRealSize.y)
        mMyDisplayWidthPort = min
        val max = displayRealSize.x.coerceAtLeast(displayRealSize.y)
        mMyDisplayHeightPort = max
        mMyDisplayHeightNoNavBarPort = max - DisplayUtil.getNavigationBarHeight(context, true)
        mMyDisplayAspectPort = max.toFloat() / min.toFloat()
        val max2 = displayRealSize.x.coerceAtLeast(displayRealSize.y)
        mMyDisplayWidthLand = max2
        val min2 = displayRealSize.x.coerceAtMost(displayRealSize.y)
        mMyDisplayHeightLand = min2
        mMyDisplayHeightNoNavBarLand = min2 - DisplayUtil.getGestureNavigationBarHeight(context)
        mMyDisplayAspectLand = min2.toFloat() / max2.toFloat()
        mScaleFactor = SystemPropertiesR.getInt(
            SystemPropertiesR.PROPERTY_DENSITY_DEVICE_STABLE,
            160
        ) / context.resources.displayMetrics.densityDpi.toFloat()
        initDisplayTouchViewParams(context)
        initSideTouchViewParams(context)
    }

    private fun getAdjustedPixel(i: Int): Int {
        return (mR.getDimensionPixelSize(i) * mScaleFactor).toInt()
    }

    private fun getDefPosition(context: Context, i: Int): Int {
        val i2 = i / 2
        LogUtil.v(
            LogUtil.LOG_TAG,
            "$TAG.getDefPosition()\n pos =\n mMyDisplayHeightPort * 2/3:${(mMyDisplayHeightPort * 2) / 3}\n touchViewHeight/2:$i2"
        )
        return ((mMyDisplayHeightPort * 2) / 3) + i2
    }

    private fun getOptimalPosition(context: Context, i: Int): Int {
        val adjustedPixel = getAdjustedPixel(R.dimen.sw_apps_view_height)
        val adjustedPixel2 = getAdjustedPixel(R.dimen.apps_view_item_margin_top)
        val adjustedPixel3 = getAdjustedPixel(R.dimen.apps_view_item_icon_height)
        val dpToPx = DpUtil.dpToPx(context, 1.0f)
        val adjustedPixel4 = getAdjustedPixel(R.dimen.divider_view_margin_bottom)
        val adjustedPixel5 = getAdjustedPixel(R.dimen.quick_settings_view_height)
        val adjustedPixel6 = getAdjustedPixel(R.dimen.tools_view_height)
        val adjustedPixel7 = getAdjustedPixel(R.dimen.launcher_settings_view_height)
        val f =
            (((mMyDisplayHeightPort - adjustedPixel2) - adjustedPixel3) + adjustedPixel + dpToPx + adjustedPixel4 + adjustedPixel5 + dpToPx + adjustedPixel4 + adjustedPixel6 + dpToPx + adjustedPixel7).toFloat()
        val sb = StringBuilder()
        sb.append("$TAG.getOptimalPosition()\n pos =\n touchViewHeight/2:").append(i / 2)
            .append("\n + appsViewItemMarginTop:").append(adjustedPixel2)
            .append("\n + appsViewItemIconHeight/2:").append(adjustedPixel3 / 2)
            .append("\n + swAppsViewHeight:").append(adjustedPixel)
            .append("\n + divider:").append(dpToPx)
            .append("\n + dividerMarginBottom:").append(adjustedPixel4)
            .append("\n + quickSettingsViewHeight:").append(adjustedPixel5)
            .append("\n + divider:").append(dpToPx)
            .append("\n + dividerMarginBottom:").append(adjustedPixel4)
            .append("\n + toolsViewHeight:").append(adjustedPixel6)
            .append("\n + divider:").append(dpToPx)
            .append("\n + launcherSettingsViewHeight:").append(adjustedPixel7)
            .append("\n = ").append(f)
            .append("[px]\n = ").append(DpUtil.pxToDp(context, f))
            .append("[dp]")
        LogUtil.v(LogUtil.LOG_TAG, sb.toString())
        return f.toInt()
    }

    fun getDisplayTouchViewParams(): TouchViewParams? {
        return if (DisplayUtil.isPortrait()) this.mDisplayTouchViewParamsPort else this.mDisplayTouchViewParamsLand
    }

    fun getDisplayTouchViewParamsLand(): TouchViewParams? {
        return this.mDisplayTouchViewParamsLand
    }

    fun getDisplayTouchViewParamsPort(): TouchViewParams? {
        return this.mDisplayTouchViewParamsPort
    }

    fun getSideTouchViewParams(): TouchViewParams? {
        return this.mSideTouchViewParams
    }

    private fun initDisplayTouchViewParams(context: Context) {
        mDisplayTouchViewParamsPort = TouchViewParams(object : ITouchViewParamsProvider {
            private val idealHeight =
                getAdjustedPixel(R.dimen.display_touch_view_height_port_default)
            private val idealDisplayWidth =
                getAdjustedPixel(R.dimen.display_touch_view_screen_width_port_for_default)
            private val idealDisplayHeight =
                getAdjustedPixel(R.dimen.display_touch_view_screen_height_port_for_default)
            private val idealDisplayAspect =
                idealDisplayHeight.toFloat() / idealDisplayWidth.toFloat()

            override fun animLength(): Int {
                return getAdjustedPixel(R.dimen.display_touch_view_animation_length)
            }

            override fun defHeight(): Int {
                val i = ((mMyDisplayAspectPort * idealHeight) / idealDisplayAspect).toInt()
                val minHeight = minHeight()
                val maxHeight = maxHeight()
                return when {
                    i < minHeight -> minHeight
                    i > maxHeight -> maxHeight
                    else -> i
                }
            }

            override fun defPosition(): Int {
                return getDefPosition(context, defHeight())
            }

            override fun defTransparency(): Int {
                return 60
            }

            override fun defWidth(): Int {
                return getAdjustedPixel(R.dimen.display_touch_view_width_default)
            }

            override fun maxHeight(): Int {
                val adjustedPixel = getAdjustedPixel(R.dimen.display_touch_view_height_max)
                return if (adjustedPixel == 0) mMyDisplayHeightNoNavBarPort else adjustedPixel
            }

            override fun maxPosition(): Int {
                val adjustedPixel = getAdjustedPixel(R.dimen.display_touch_view_position_max)
                return if (adjustedPixel == 0) mMyDisplayHeightNoNavBarPort else adjustedPixel
            }

            override fun maxTransparency(): Int {
                return 100
            }

            override fun maxWidth(): Int {
                val adjustedPixel = getAdjustedPixel(R.dimen.display_touch_view_width_max)
                return if (adjustedPixel == 0) mMyDisplayWidthPort else adjustedPixel
            }

            override fun minHeight(): Int {
                return getAdjustedPixel(R.dimen.display_touch_view_height_min)
            }

            override fun minPosition(): Int {
                return getAdjustedPixel(R.dimen.display_touch_view_position_min)
            }

            override fun minTransparency(): Int {
                return 0
            }

            override fun minWidth(): Int {
                return getAdjustedPixel(R.dimen.display_touch_view_width_min)
            }
        })
        mDisplayTouchViewParamsLand = TouchViewParams(object : ITouchViewParamsProvider {
            private val idealHeight =
                getAdjustedPixel(R.dimen.display_touch_view_height_land_default)
            private val idealDisplayWidth =
                getAdjustedPixel(R.dimen.display_touch_view_screen_width_land_for_default)
            private val idealDisplayHeight =
                getAdjustedPixel(R.dimen.display_touch_view_screen_height_land_for_default)
            private val idealDisplayAspect =
                idealDisplayHeight.toFloat() / idealDisplayWidth.toFloat()

            override fun animLength(): Int {
                return getAdjustedPixel(R.dimen.display_touch_view_animation_length)
            }

            override fun defHeight(): Int {
                val i = ((mMyDisplayAspectLand * idealHeight) / idealDisplayAspect).toInt()
                val minHeight = minHeight()
                val maxHeight = maxHeight()
                return when {
                    i < minHeight -> minHeight
                    i > maxHeight -> maxHeight
                    else -> i
                }
            }

            override fun defPosition(): Int {
                return mMyDisplayHeightNoNavBarLand - getAdjustedPixel(R.dimen.side_touch_view_land_default_position)
            }

            override fun defTransparency(): Int {
                return 60
            }

            override fun defWidth(): Int {
                return getAdjustedPixel(R.dimen.display_touch_view_width_default)
            }

            override fun maxHeight(): Int {
                val adjustedPixel = getAdjustedPixel(R.dimen.display_touch_view_height_max)
                return if (adjustedPixel == 0) mMyDisplayHeightNoNavBarLand else adjustedPixel
            }

            override fun maxPosition(): Int {
                val adjustedPixel = getAdjustedPixel(R.dimen.display_touch_view_position_max)
                return if (adjustedPixel == 0) mMyDisplayHeightNoNavBarLand else adjustedPixel
            }

            override fun maxTransparency(): Int {
                return 100
            }

            override fun maxWidth(): Int {
                val adjustedPixel = getAdjustedPixel(R.dimen.display_touch_view_width_max)
                return if (adjustedPixel == 0) mMyDisplayWidthLand else adjustedPixel
            }

            override fun minHeight(): Int {
                return getAdjustedPixel(R.dimen.display_touch_view_height_min)
            }

            override fun minPosition(): Int {
                return getAdjustedPixel(R.dimen.display_touch_view_position_min)
            }

            override fun minTransparency(): Int {
                return 0
            }

            override fun minWidth(): Int {
                return getAdjustedPixel(R.dimen.display_touch_view_width_min)
            }
        })
    }

    private fun initSideTouchViewParams(context: Context) {
        val adjustedPixel = getAdjustedPixel(R.dimen.side_touch_view_height_default)
        val adjustedPixel2 =
            getAdjustedPixel(R.dimen.side_touch_view_screen_height_for_default).toFloat() / getAdjustedPixel(
                R.dimen.side_touch_view_screen_width_for_default
            ).toFloat()
        mSideTouchViewParams = TouchViewParams(object : ITouchViewParamsProvider {
            override fun animLength(): Int {
                return 0
            }

            override fun defHeight(): Int {
                val i = ((mMyDisplayAspectPort * adjustedPixel) / adjustedPixel2).toInt()
                val minHeight = minHeight()
                val maxHeight = maxHeight()
                return when {
                    i < minHeight -> minHeight
                    i > maxHeight -> maxHeight
                    else -> i
                }
            }

            override fun defPosition(): Int {
                return getOptimalPosition(context, defHeight())
            }

            override fun defTransparency(): Int {
                return 40
            }

            override fun defWidth(): Int {
                return 0
            }

            override fun maxHeight(): Int {
                val adjustedPixel3 = getAdjustedPixel(R.dimen.side_touch_view_height_max)
                return if (adjustedPixel3 == 0) mMyDisplayHeightNoNavBarPort else adjustedPixel3
            }

            override fun maxPosition(): Int {
                val adjustedPixel3 = getAdjustedPixel(R.dimen.side_touch_view_position_max)
                return if (adjustedPixel3 == 0) mMyDisplayHeightNoNavBarPort else adjustedPixel3
            }

            override fun maxTransparency(): Int {
                return 100
            }

            override fun maxWidth(): Int {
                return 0
            }

            override fun minHeight(): Int {
                return getAdjustedPixel(R.dimen.side_touch_view_height_min)
            }

            override fun minPosition(): Int {
                return getAdjustedPixel(R.dimen.side_touch_view_position_min)
            }

            override fun minTransparency(): Int {
                return 20
            }

            override fun minWidth(): Int {
                return 0
            }
        })
    }

    class TouchViewParams(iTouchViewParamsProvider: ITouchViewParamsProvider) {
        val animLength: Int = iTouchViewParamsProvider.animLength()
        val defHeight: Int = iTouchViewParamsProvider.defHeight()
        val defPosition: Int = iTouchViewParamsProvider.defPosition()
        val defTransparency: Int = iTouchViewParamsProvider.defTransparency()
        val defWidth: Int = iTouchViewParamsProvider.defWidth()
        val maxHeight: Int = iTouchViewParamsProvider.maxHeight()
        val maxPosition: Int = iTouchViewParamsProvider.maxPosition()
        val maxTransparency: Int = iTouchViewParamsProvider.maxTransparency()
        val maxWidth: Int = iTouchViewParamsProvider.maxWidth()
        val minHeight: Int = iTouchViewParamsProvider.minHeight()
        val minPosition: Int = iTouchViewParamsProvider.minPosition()
        val minTransparency: Int = iTouchViewParamsProvider.minTransparency()
        val minWidth: Int = iTouchViewParamsProvider.minWidth()
    }
}
