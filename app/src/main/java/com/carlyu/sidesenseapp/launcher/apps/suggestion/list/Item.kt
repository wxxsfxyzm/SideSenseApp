package com.carlyu.sidesenseapp.launcher.apps.suggestion.list

import android.app.ActivityOptions
import android.content.ActivityNotFoundException
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.pm.LauncherActivityInfo
import android.content.pm.ShortcutInfo
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.util.DisplayMetrics
import android.view.View
import com.carlyu.sidesenseapp.common.listener.AllAppsClickListener
import com.carlyu.sidesenseapp.common.util.*
import com.carlyu.sidesenseapp.idd.Idd
import com.carlyu.sidesenseapp.idd.types.InteractionType
import com.carlyu.sidesenseapp.idd.types.ItemType
import com.carlyu.sidesenseapp.idd.types.SuggestType
import com.carlyu.sidesenseapp.launcher.apps.allapps.service.AllAppsViewService
import com.carlyu.sidesenseapp.launcher.apps.cache.AppItemCache
import com.carlyu.sidesenseapp.launcher.base.service.LauncherViewService
import com.carlyu.sidesenseapp.mwindow.controller.MultiWindowController
import com.carlyu.sidesenseapp.mwindow.suggestion.list.MultiItem
import java.util.*

class Item {
    companion object {
        private const val TAG = "Item"
    }

    enum class ItemClassification {
        NotDefined,
        Default,
        Prediction,
        Custom
    }

    enum class LaunchWindowType {
        SingleWindow,
        MultiWindow,
        TemporaryWindow
    }

    abstract class BaseAppItem(
        val context: Context,
        private val textId: Int,
        private val drawableId: Int,
        val itemClassification: ItemClassification = ItemClassification.NotDefined
    ) {
        var id: Int = 0

        open fun getApplicationIcon(): Drawable? {
            return context.applicationInfo.loadIcon(context.packageManager)
        }

        open fun getApplicationName(): CharSequence? {
            return context.applicationInfo.loadLabel(context.packageManager)
        }

        open fun getDrawable(): Drawable? {
            return context.getDrawable(drawableId)
        }

        open fun getText(): CharSequence? {
            return context.getText(textId)
        }

        open fun isEnabled(): Boolean = true

        abstract fun onClick(view: View)

        abstract fun onClick(view: View, launchWindowType: LaunchWindowType)

        fun startActivity(componentName: ComponentName) {
            val intent = Intent().apply {
                setComponent(componentName)
                flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_RESET_TASK_IF_NEEDED
            }
            context.startActivity(intent)
        }
    }

    class AllAppsItem(context: Context, textId: Int, drawableId: Int) :
        BaseAppItem(context, textId, drawableId, ItemClassification.NotDefined) {

        private fun sendOnClickEvent(view: View) {
            val location = IntArray(2)
            view.getLocationOnScreen(location)
            LogUtil.d(
                LogUtil.LOG_TAG,
                "$TAG.sendOnClickEvent rawX=${location[0]} rawY=${location[1]}"
            )
            AllAppsClickListener.getInstance(context).notifyAllAppsClicked(location)
        }

        override fun onClick(view: View) {
            onClick(view, LaunchWindowType.SingleWindow)
        }

        override fun onClick(view: View, launchWindowType: LaunchWindowType) {
            sendOnClickEvent(view)
            val itemType =
                if (launchWindowType == LaunchWindowType.TemporaryWindow) ItemType.TW_MOREAPPS else "MOREAPPS"
            Idd.SsMenu.getInstance()
                .addAction("APP", itemType, InteractionType.SINGLE_CLICK, null, itemType, id)
            Idd.MwMenu.getInstance()
                .addAction("APP", "MOREAPPS", InteractionType.SINGLE_CLICK, null, "MOREAPPS", id)
        }
    }

    class AppItem : BaseAppItem {
        private val appItemCache = AppItemCache.getInstance(context)
        private val shortcutUtil = ShortcutUtil.getInstance(context)
        val appLabel: CharSequence?
        val componentName: ComponentName
        val score: Float
        val shortcutInfo: ShortcutInfo?

        constructor(context: Context, score: Float, launcherActivityInfo: LauncherActivityInfo) :
                this(context, score, launcherActivityInfo, ItemClassification.NotDefined)

        constructor(
            context: Context,
            score: Float,
            launcherActivityInfo: LauncherActivityInfo,
            itemClassification: ItemClassification
        ) :
                super(context, 0, 0, itemClassification) {
            this.componentName = launcherActivityInfo.componentName
            this.appLabel = appItemCache.getLabel(componentName.flattenToString())
            this.score = score
            this.shortcutInfo = null
        }

        constructor(context: Context, score: Float, shortcutInfo: ShortcutInfo) :
                super(context, 0, 0) {
            this.shortcutInfo = shortcutInfo
            this.componentName = shortcutUtil.getShortcutComponentName(shortcutInfo)
            this.appLabel = appItemCache.getLabel(shortcutInfo.activity.flattenToString())
            this.score = score
        }

        override fun getApplicationIcon(): Drawable? {
            return launcherActivityInfo?.applicationInfo?.loadIcon(context.packageManager)
        }

        override fun getApplicationName(): CharSequence? {
            return launcherActivityInfo?.applicationInfo?.loadLabel(context.packageManager)
                ?: shortcutInfo?.shortLabel?.let { shortcutUtil.makeShortcutLabel(appLabel, it) }
        }

        override fun getDrawable(): Drawable? {
            var drawable = appItemCache.getDrawable(componentName.flattenToString())
            if (shortcutInfo != null) {
                drawable = shortcutUtil.getShortcutIcon(shortcutInfo)
                if (drawable != null) {
                    val flattenToString =
                        shortcutUtil.getShortcutComponentName(shortcutInfo).flattenToString()
                    appItemCache.addDrawable(flattenToString, drawable)
                    return appItemCache.getDrawable(flattenToString)
                }
            } else if (drawable == null && launcherActivityInfo != null) {
                val icon = launcherActivityInfo.getIcon(DisplayMetrics.DENSITY_DEVICE_STABLE)
                if (icon != null) {
                    appItemCache.addDrawable(componentName.flattenToString(), icon)
                    return appItemCache.getDrawable(componentName.flattenToString())
                }
                return icon
            }
            return drawable
        }

        fun getShortcutName(): String {
            return shortcutInfo?.id ?: ShortcutUtil.SHORTCUT_NAME_NULL
        }

        override fun getText(): CharSequence? {
            val label = appItemCache.getLabel(componentName.flattenToString())
            if (label != null) return label

            launcherActivityInfo?.label?.let {
                appItemCache.addLabel(componentName.flattenToString(), it.toString())
                return it
            }

            shortcutInfo?.shortLabel?.let {
                appLabel?.let { appLabel ->
                    val shortcutLabel = shortcutUtil.makeShortcutLabel(appLabel, it)
                    appItemCache.addLabel(componentName.flattenToString(), shortcutLabel)
                    return shortcutLabel
                }
                return it
            }
            return label
        }

        fun getTextForExpandable(): CharSequence? {
            return shortcutInfo?.shortLabel?.also {
                appItemCache.addLabel(it.toString(), it.toString())
            }
        }

        fun isShortcut(): Boolean = shortcutInfo != null

        override fun onClick(view: View) {
            onClick(view, LaunchWindowType.SingleWindow)
        }

        override fun onClick(view: View, launchWindowType: LaunchWindowType) {
            val bundle =
                ActivityOptions.makeClipRevealAnimation(view, 0, 0, view.width, view.height)
                    .toBundle()
            val itemType = when (itemClassification) {
                ItemClassification.Default -> SuggestType.DEFAULT
                ItemClassification.Prediction -> SuggestType.PREDICTION
                ItemClassification.Custom -> SuggestType.CUSTOM
                else -> null
            }
            val actionType = if (AllAppsViewService.isUIOpened()) "MOREAPPS" else "APP"
            val isPairAppItem = this is MultiItem.PairAppItem

            if (isPairAppItem) {
                Idd.MwMenu.getInstance().addAction(
                    actionType, ItemType.PAIR_APP, InteractionType.SINGLE_CLICK, itemType,
                    (this as MultiItem.PairAppItem).getPairComponents(), id
                )
            } else {
                val isTemporaryWindow = launchWindowType == LaunchWindowType.TemporaryWindow
                Idd.SsMenu.getInstance().setIsTwMode(isTemporaryWindow)
                if (isTemporaryWindow) {
                    Idd.SsMenu.getInstance().setWindowOnCloseForTw(context, componentName)
                    Idd.SsMenu.getInstance().addAction(
                        actionType, ItemType.TW_APP, InteractionType.SINGLE_CLICK, itemType,
                        componentName.packageName, id
                    )
                } else if (shortcutInfo != null) {
                    Idd.SsMenu.getInstance().addAction(
                        actionType, ItemType.SHORTCUT, InteractionType.SINGLE_CLICK, itemType,
                        componentName, id
                    )
                    Idd.MwMenu.getInstance().addAction(
                        actionType, "APP", InteractionType.SINGLE_CLICK, itemType,
                        componentName.packageName, id
                    )
                } else {
                    Idd.SsMenu.getInstance().addAction(
                        actionType, "APP", InteractionType.SINGLE_CLICK, itemType,
                        componentName.packageName, id
                    )
                    Idd.MwMenu.getInstance().addAction(
                        actionType, "APP", InteractionType.SINGLE_CLICK, itemType,
                        componentName.packageName, id
                    )
                }
            }

            if (isPairAppItem) return

            when (launchWindowType) {
                LaunchWindowType.MultiWindow -> startMultiActivity(componentName)
                LaunchWindowType.TemporaryWindow -> startTemporaryWindow(componentName, bundle)
                else -> startActivity(this, bundle)
            }
        }

        private fun startMultiActivity(componentName: ComponentName) {
            MultiWindowController.getInstance(context).startMultiActivity(
                MultiWindowController.StartAction.SpecifiedApp, componentName, null
            )
        }

        private fun startTemporaryWindow(componentName: ComponentName, bundle: Bundle) {
            val intent = Intent(Intent.ACTION_MAIN).apply {
                addCategory(Intent.CATEGORY_LAUNCHER)
                setComponent(componentName)
                flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_RESET_TASK_IF_NEEDED
            }
            LaunchAppUtil.getInstance(context).startActivityOnTW(context, intent, bundle)
        }

        fun startActivity(appItem: AppItem, bundle: Bundle) {
            if (appItem.launcherActivityInfo == null) {
                if (appItem.shortcutInfo != null) {
                    try {
                        shortcutUtil.startShortcut(appItem)
                        return
                    } catch (e: ActivityNotFoundException) {
                        LauncherViewService.closeUI(context, true)
                        return
                    }
                }
                return
            }
            val intent = Intent(Intent.ACTION_MAIN).apply {
                addCategory(Intent.CATEGORY_LAUNCHER)
                setComponent(appItem.componentName)
                flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_RESET_TASK_IF_NEEDED
            }
            LaunchAppUtil.getInstance(context).startActivity(
                context,
                IntentModifierUtil.getModifiedIntent(intent, appItem.componentName),
                bundle
            )
        }
    }
}
