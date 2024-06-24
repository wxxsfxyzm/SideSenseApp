package com.carlyu.sidesenseapp.common.util

import android.annotation.SuppressLint
import android.content.ComponentName
import android.content.Context
import android.content.pm.LauncherApps
import android.content.pm.ShortcutInfo
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.os.Binder
import android.os.UserHandle
import android.text.TextUtils
import android.widget.Toast
import com.carlyu.sidesenseapp.R
import com.carlyu.sidesenseapp.launcher.apps.cache.AppItemCache
import com.carlyu.sidesenseapp.launcher.apps.suggestion.list.AllApps
import com.carlyu.sidesenseapp.launcher.apps.suggestion.list.Item
import java.util.*

class ShortcutUtil private constructor(private val mContext: Context) {
    private var mApplicationAndShortcutMap: LinkedHashMap<Item.AppItem, ArrayList<Item.AppItem>>? =
        null
    private var mShortcutInfoList: List<ShortcutInfo> = createShortcutInfoList()

    private fun canLaunchYoutubeSearchShortcut(): Boolean {
        val fullscreenComponent = MultiWindowUtil.getFullscreenComponent(mContext)
        if (fullscreenComponent == null || !isYoutubeApp(fullscreenComponent)) {
            val primaryComponent = MultiWindowUtil.getPrimaryComponent(mContext)
            if (primaryComponent == null || !isYoutubeApp(primaryComponent)) {
                val secondaryComponent = MultiWindowUtil.getSecondaryComponent(mContext)
                if (secondaryComponent == null || !isYoutubeApp(secondaryComponent)) {
                    val temporaryWindowComponent =
                        TemporaryWindowUtil.getTemporaryWindowComponent(mContext)
                    return temporaryWindowComponent == null || !isYoutubeApp(
                        temporaryWindowComponent
                    )
                }
                return false
            }
            return false
        }
        return false
    }

    private fun createApplicationAndShortcutMap(): LinkedHashMap<Item.AppItem, ArrayList<Item.AppItem>> {
        val linkedHashMap = LinkedHashMap<Item.AppItem, ArrayList<Item.AppItem>>()
        val launcherAppList = AllApps.getInstance(mContext).launcherAppList
        val makeShortcutsListForExpandable =
            makeShortcutsListForExpandable(launcherAppList, shortcutsList)
        for (i in launcherAppList.indices) {
            linkedHashMap[launcherAppList[i]] = makeShortcutsListForExpandable[i]
        }
        return linkedHashMap
    }

    private fun isYoutubeApp(componentName: ComponentName): Boolean {
        return componentName.packageName == YOUTUBE_PACKAGE_NAME
    }

    private fun isYoutubeSearchShortcut(componentName: ComponentName): Boolean {
        return componentName.flattenToString() == YOUTUBE_SEARCH_SHORTCUT_COMPONENT_NAME
    }

    private fun makeShortcutsListForExpandable(
        arrayList: ArrayList<Item.AppItem>,
        list: List<Item.AppItem>
    ): ArrayList<ArrayList<Item.AppItem>> {
        val arrayList2 = ArrayList<ArrayList<Item.AppItem>>()
        for (i in arrayList.indices) {
            val arrayList3 = ArrayList<Item.AppItem>()
            for (i2 in list.indices) {
                if (arrayList[i].componentName.packageName == list[i2].componentName.packageName) {
                    arrayList3.add(list[i2])
                }
            }
            arrayList2.add(arrayList3)
        }
        return arrayList2
    }

    private fun showAppAlreadyOpenToast() {
        Toast.makeText(
            mContext,
            mContext.getText(R.string.ss_strings_already_launched_toast),
            Toast.LENGTH_SHORT
        ).show()
    }

    @SuppressLint("WrongConstant")
    fun createShortcutInfoList(): List<ShortcutInfo> {
        val shortcutQuery = LauncherApps.ShortcutQuery()
        shortcutQuery.setQueryFlags(1035)
        val list = try {
            (mContext.getSystemService(Context.LAUNCHER_APPS_SERVICE) as LauncherApps)
                .getShortcuts(shortcutQuery, UserHandle.getUserHandleForUid(Binder.getCallingUid()))
        } catch (e: IllegalStateException) {
            LogUtil.d(LogUtil.LOG_TAG, "$TAG.createShortcutInfoList e=$e")
            null
        }
        return list ?: Collections.emptyList()
    }

    fun getApplicationAndShortcutMap(): LinkedHashMap<Item.AppItem, ArrayList<Item.AppItem>> {
        if (mApplicationAndShortcutMap == null) {
            mApplicationAndShortcutMap = createApplicationAndShortcutMap()
        }
        return mApplicationAndShortcutMap!!
    }

    fun getShortcutComponentName(shortcutInfo: ShortcutInfo): ComponentName {
        return ComponentName.unflattenFromString(shortcutInfo.activity.flattenToShortString() + APP_AND_SHORTCUT_COMPONENT_NAME_SEPARATOR + shortcutInfo.id)
    }

    fun getShortcutIcon(shortcutInfo: ShortcutInfo): Drawable? {
        val shortcutIconDrawable =
            (mContext.getSystemService(Context.LAUNCHER_APPS_SERVICE) as LauncherApps)
                .getShortcutIconDrawable(shortcutInfo, mContext.resources.displayMetrics.densityDpi)
        val drawable =
            AppItemCache.getInstance(mContext).getDrawable(shortcutInfo.activity.flattenToString())
        return if (shortcutIconDrawable == null || drawable == null) {
            null
        } else {
            BitmapDrawable(
                mContext.resources,
                BitmapUtil.makeIconWithBadge(
                    BitmapUtil.convertToBitmap(
                        mContext,
                        shortcutIconDrawable
                    ), BitmapUtil.convertToBitmap(mContext, drawable)
                )
            )
        }
    }

    fun getShortcutInfo(str: String, componentName: ComponentName): ShortcutInfo? {
        return mShortcutInfoList.find { it.id == str && componentName.packageName == it.activity.packageName }
    }

    val shortcutsList: List<Item.AppItem>
        get() {
            val arrayList = ArrayList<Item.AppItem>()
            for (shortcutInfo in mShortcutInfoList) {
                if (shortcutInfo.activity != null) {
                    try {
                        arrayList.add(Item.AppItem(mContext, 0.0f, shortcutInfo))
                    } catch (unused: IllegalArgumentException) {
                        LogUtil.w(
                            LogUtil.LOG_TAG,
                            "$TAG.getShortcutsList : package is not contained!!"
                        )
                    }
                }
            }
            return arrayList
        }

    fun isValidShortcutName(str: String?): Boolean {
        return !TextUtils.isEmpty(str)
    }

    fun makeShortcutLabel(charSequence: CharSequence, charSequence2: CharSequence): String {
        return "$charSequence$APP_AND_SHORTCUT_NAME_SEPARATOR$charSequence2"
    }

    fun startShortcut(appItem: Item.AppItem) {
        val shortcutInfo = appItem.shortcutInfo
        LaunchAppUtil.getInstance(mContext).notifyToPE(shortcutInfo.activity, true)
        if (!isYoutubeSearchShortcut(appItem.componentName) || canLaunchYoutubeSearchShortcut()) {
            (mContext.getSystemService(Context.LAUNCHER_APPS_SERVICE) as LauncherApps).startShortcut(
                shortcutInfo,
                null,
                null
            )
        } else {
            showAppAlreadyOpenToast()
        }
    }

    fun updateShortcutInfoList() {
        mShortcutInfoList = createShortcutInfoList()
        mApplicationAndShortcutMap = createApplicationAndShortcutMap()
    }

    companion object {
        const val APP_AND_SHORTCUT_COMPONENT_NAME_SEPARATOR = "$"
        private const val APP_AND_SHORTCUT_NAME_SEPARATOR = "/"
        const val SHORTCUT_NAME_NULL = ""
        private const val TAG = "ShortcutUtil"
        private const val YOUTUBE_PACKAGE_NAME = "com.google.android.youtube"
        private const val YOUTUBE_SEARCH_SHORTCUT_COMPONENT_NAME =
            "com.google.android.youtube/com.google.android.youtube.app.honeycomb.Shell\$HomeActivity\$search-shortcut"

        @SuppressLint("StaticFieldLeak")
        @Volatile
        private var sInstance: ShortcutUtil? = null

        @Synchronized
        fun getInstance(context: Context): ShortcutUtil {
            return sInstance ?: synchronized(this) {
                sInstance ?: ShortcutUtil(context).also { sInstance = it }
            }
        }
    }
}
