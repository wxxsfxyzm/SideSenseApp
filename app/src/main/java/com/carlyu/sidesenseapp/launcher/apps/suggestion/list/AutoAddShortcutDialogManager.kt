package com.carlyu.sidesenseapp.launcher.apps.suggestion.list

import android.annotation.SuppressLint
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.widget.CompoundButton
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.ScrollView
import android.widget.TextView
import androidx.appcompat.widget.SwitchCompat
import com.carlyu.sidesenseapp.R
import com.carlyu.sidesenseapp.common.activities.AutoAddShortcutDialogActivity
import com.carlyu.sidesenseapp.common.preferences.Preferences
import com.carlyu.sidesenseapp.common.preferences.SettingsSystem
import com.carlyu.sidesenseapp.common.util.FeatureConfig
import com.carlyu.sidesenseapp.common.util.ShortcutUtil

class AutoAddShortcutDialogManager private constructor(private val mContext: Context) {
    private val mShortcutUtil: ShortcutUtil = ShortcutUtil.getInstance(mContext)
    private var mShortcutsList: List<Item.AppItem> = ArrayList()
    private var mAutoAddShortcutDataList: MutableList<AutoAddShortcutData> = ArrayList()

    enum class AutoAddDialogStatus(val status: Int) {
        NOT_YET_DETERMINED(1),
        ADD(2),
        NOT_ADD(3),
        ALREADY_ADDED(4)
    }

    inner class AutoAddShortcutData(i: Int, str: String) {
        private val mPreferenceKey: String = str
        private var mTargetShortcutList: Array<String> = mContext.resources.getStringArray(i)
        var mShortCutItemList: ArrayList<Item.AppItem> = createShortCutItemList()
        var mIsSelected: BooleanArray = BooleanArray(mShortCutItemList.size).apply { fill(true) }

        private fun createShortCutItemList(): ArrayList<Item.AppItem> {
            val arrayList = ArrayList<Item.AppItem>()
            if (!isAutoAddDialogShown()) {
                if (isCustomAppListContained(mTargetShortcutList)) {
                    setAutoAddDialogShown()
                    for (str in mTargetShortcutList) {
                        Preferences.getInstance(mContext)?.setAutoAddDialogStatusForShortcut(
                            mContext,
                            str,
                            AutoAddDialogStatus.ALREADY_ADDED.status
                        )
                    }
                    return arrayList
                }
                for (str in mTargetShortcutList) {
                    for (appItem in mShortcutsList) {
                        if (appItem.componentName == ComponentName.unflattenFromString(str)) {
                            arrayList.add(appItem)
                        }
                    }
                }
            }
            return arrayList
        }

        private fun isCustomAppListContained(strArr: Array<String>): Boolean {
            for (appItem in CustomApps.getInstance(mContext).appsList) {
                for (str in strArr) {
                    if (appItem.componentName == ComponentName.unflattenFromString(str)) {
                        return true
                    }
                }
            }
            return false
        }

        fun updateShortCutItemList() {
            mShortCutItemList = createShortCutItemList()
            mIsSelected = BooleanArray(mShortCutItemList.size).apply { fill(true) }
        }

        fun isAutoAddDialogShown(): Boolean {
            return Preferences.getInstance(mContext).getBoolean(mPreferenceKey)
        }

        fun setAutoAddDialogShown() {
            Preferences.getInstance(mContext).putBoolean(mPreferenceKey, true)
        }
    }

    companion object {
        private const val PACKAGE_NAME_ALIPAY = "com.eg.android.AlipayGphone"
        private const val PACKAGE_NAME_WECHATPAY = "com.tencent.mm"
        private const val TAG = "AutoAddShortcutDialogManager"

        @SuppressLint("StaticFieldLeak")
        @Volatile
        private var sInstance: AutoAddShortcutDialogManager? = null

        @Synchronized
        fun getInstance(context: Context): AutoAddShortcutDialogManager {
            return sInstance ?: synchronized(this) {
                sInstance ?: AutoAddShortcutDialogManager(context).also { sInstance = it }
            }
        }
    }

    private fun getShortcutInstallStatus(str: String): Boolean {
        for (appItem in AllApps.getInstance(mContext).launcherAppList) {
            if (appItem.componentName.packageName == str) {
                return true
            }
        }
        return false
    }

    private fun initAutoAddShortCutDataList() {
        if (mShortcutsList.isEmpty()) return

        mAutoAddShortcutDataList.clear()
        mAutoAddShortcutDataList.add(
            AutoAddShortcutData(
                R.array.auto_add_we_chat_pay_list,
                Preferences.KEY_BOOLEAN_AUTO_SHORTCUT_ADD_DIALOG_SHOWN_WECHATPAY
            )
        )
        mAutoAddShortcutDataList.add(
            AutoAddShortcutData(
                R.array.auto_add_ali_pay_list,
                Preferences.KEY_BOOLEAN_AUTO_SHORTCUT_ADD_DIALOG_SHOWN_ALIPAY
            )
        )
    }

    private fun isAutoAddItemExisted(): Boolean {
        updateAutoAddShortcutList()
        for (autoAddShortcutData in mAutoAddShortcutDataList) {
            if (!autoAddShortcutData.isAutoAddDialogShown() && autoAddShortcutData.mShortCutItemList.isNotEmpty()) {
                return true
            }
        }
        return false
    }

    @SuppressLint("InflateParams")
    private fun makeDialogLayout(autoAddShortcutData: AutoAddShortcutData): View {
        val shortCutItemList = autoAddShortcutData.mShortCutItemList
        val isSelected = autoAddShortcutData.mIsSelected
        val linearLayout = LinearLayout(mContext).apply {
            orientation = LinearLayout.VERTICAL
        }
        for (i in shortCutItemList.indices) {
            val inflate =
                LayoutInflater.from(mContext).inflate(R.layout.auto_add_shortcut_dialog_item, null)
            val switch = inflate.findViewById<SwitchCompat>(R.id.toggle_switch)
            inflate.findViewById<ImageView>(R.id.app_or_shortcut_icon)
                .setImageDrawable(shortCutItemList[i].drawable)
            inflate.findViewById<TextView>(R.id.app_or_shortcut_name).text =
                shortCutItemList[i].text
            switch.isChecked = true
            switch.setOnCheckedChangeListener { _: CompoundButton, z: Boolean ->
                isSelected[i] = z
            }
            linearLayout.addView(inflate)
        }
        return linearLayout
    }

    private fun updateAutoAddShortcutList() {
        for (autoAddShortcutData in mAutoAddShortcutDataList) {
            if (!autoAddShortcutData.isAutoAddDialogShown()) {
                autoAddShortcutData.updateShortCutItemList()
            }
        }
    }

    fun addSelectedShortcutsToCustomAppList() {
        var index = 0
        for (autoAddShortcutData in mAutoAddShortcutDataList) {
            if (!autoAddShortcutData.isAutoAddDialogShown()) {
                val shortCutItemList = autoAddShortcutData.mShortCutItemList
                if (shortCutItemList.isNotEmpty()) {
                    val isSelected = autoAddShortcutData.mIsSelected
                    val customAppsListController = CustomAppsListController.getInstance(mContext)
                    for (i in shortCutItemList.indices) {
                        if (isSelected[i]) {
                            Preferences.getInstance(mContext).setAutoAddDialogStatusForShortcut(
                                mContext,
                                shortCutItemList[i].componentName.flattenToString(),
                                AutoAddDialogStatus.ADD.status
                            )
                            customAppsListController.insert(shortCutItemList[i], index, true)
                            index++
                        } else {
                            Preferences.getInstance(mContext).setAutoAddDialogStatusForShortcut(
                                mContext,
                                shortCutItemList[i].componentName.flattenToString(),
                                AutoAddDialogStatus.NOT_ADD.status
                            )
                        }
                    }
                    autoAddShortcutData.setAutoAddDialogShown()
                }
            }
        }
    }

    fun destroy() {}

    @SuppressLint("InflateParams")
    fun getAutoAddDialogLayout(): View {
        val linearLayout = LinearLayout(mContext).apply {
            orientation = LinearLayout.VERTICAL
        }
        linearLayout.addView(
            LayoutInflater.from(mContext).inflate(R.layout.auto_add_shortcut_dialog_layout, null)
        )
        val innerLayout = LinearLayout(mContext).apply {
            orientation = LinearLayout.VERTICAL
        }
        for (autoAddShortcutData in mAutoAddShortcutDataList) {
            if (!autoAddShortcutData.isAutoAddDialogShown()) {
                innerLayout.addView(makeDialogLayout(autoAddShortcutData))
            }
        }
        val scrollView = ScrollView(mContext)
        scrollView.addView(innerLayout)
        linearLayout.addView(scrollView)
        return linearLayout
    }

    fun getIddDialogCategory(context: Context, i: Int, i2: Int): Int {
        return Preferences.getInstance(context)
            .getAutoAddDialogStatusForShortcut(context, context.resources.getStringArray(i)[i2])
    }

    fun getShortcutInstallAlipay(): Boolean {
        return getShortcutInstallStatus(PACKAGE_NAME_ALIPAY)
    }

    fun getShortcutInstallWeChat(): Boolean {
        return getShortcutInstallStatus(PACKAGE_NAME_WECHATPAY)
    }

    fun init() {
        mShortcutsList = mShortcutUtil.shortcutsList
        initAutoAddShortCutDataList()
    }

    fun shouldShowAutoAddShortcutDialog(): Boolean {
        return FeatureConfig.isAutoAddDialogAndChangeRecommendItemToTopEnabled() &&
                SettingsSystem.getInstance(mContext).getBoolean("somc.side_sense") &&
                Preferences.getInstance(mContext)!!.isOobeDone &&
                isAutoAddItemExisted()
    }

    fun showAutoAddDialog() {
        val intent = Intent(Intent.ACTION_MAIN).apply {
            setClass(mContext, AutoAddShortcutDialogActivity::class.java)
            flags =
                Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS
        }
        mContext.startActivity(intent)
    }

    fun updateAutoAddShortcutList(str: String) {
        if (str == PACKAGE_NAME_ALIPAY || str == PACKAGE_NAME_WECHATPAY) {
            mShortcutsList = mShortcutUtil.shortcutsList
            updateAutoAddShortcutList()
        }
    }
}
