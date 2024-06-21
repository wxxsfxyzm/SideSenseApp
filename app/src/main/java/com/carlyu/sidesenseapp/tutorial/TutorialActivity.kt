package com.carlyu.sidesenseapp.tutorial

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.view.KeyEvent
import android.view.Window
import com.carlyu.sidesenseapp.R
import com.carlyu.sidesenseapp.common.activities.BaseFragmentActivity
import com.carlyu.sidesenseapp.common.util.LogUtil
import com.sonymobile.sidesenseapp.common.util.ActivityUtil
import com.sonymobile.sidesenseapp.common.util.DeviceProperty
import com.sonymobile.sidesenseapp.common.util.ProductUtil
import com.sonymobile.sidesenseapp.common.util.ThemeUtil
import com.sonymobile.sidesenseapp.idd.Idd
import com.sonymobile.sidesenseapp.tutorial.practice.service.PracticeLocationViewService
import p001a0.C0008c

/* loaded from: classes.dex */
class TutorialActivity : BaseFragmentActivity() {
    private var mHandler: Handler? = null
    private var mTutorial: TutorialController? = null

    fun nextViewPage() {
        mTutorial.nextViewPage()
    }

    // androidx.fragment.app.ActivityC0230d, android.app.Activity
    @Deprecated("This method has been deprecated in favor of using the Activity Result API\n      which brings increased type safety via an {@link ActivityResultContract} and the prebuilt\n      contracts for common intents available in\n      {@link androidx.activity.result.contract.ActivityResultContracts}, provides hooks for\n      testing, and allow receiving results in separate, testable classes independent from your\n      activity. Use\n      {@link #registerForActivityResult(ActivityResultContract, ActivityResultCallback)}\n      with the appropriate {@link ActivityResultContract} and handling the result in the\n      {@link ActivityResultCallback#onActivityResult(Object) callback}.")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (resultCode != 1) {
            return
        }
        mTutorial.nextViewPage()
    }

    // androidx.fragment.app.ActivityC0230d, androidx.activity.ComponentActivity, p110x.ActivityC1828b, android.app.Activity
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.tutorial_activity)
        ThemeUtil.setThemeP(this)
        val window: Window = window
        window.clearFlags(67108864)
        window.addFlags(Int.MIN_VALUE)
        window.statusBarColor =
            resources.getColor(R.color.tutorial_status_bar_color, null)
        this.mTutorial = TutorialController(this)
        this.mHandler = Handler()
        val intent = Intent.getIntent()
        Idd.Tutorial.getInstance().start(
            this,
            intent?.getStringExtra(EXTRA_ENTRY_POINT)
        )
    }

    // androidx.fragment.app.ActivityC0230d, android.app.Activity
    override fun onDestroy() {
        super.onDestroy()
        Idd.Tutorial.getInstance().stop()
    }

    // android.app.Activity, android.view.KeyEvent.Callback
    override fun onKeyDown(i: Int, keyEvent: KeyEvent): Boolean {
        val sb = StringBuilder()
        C0008c.m3285k(sb, TAG, "keyCode = ", i, "event = ")
        sb.append(keyEvent.toString())
        LogUtil.d(LogUtil.LOG_TAG, sb.toString())
        if (i != 4 || previousViewPage()) {
            return false
        }
        return super.onKeyDown(i, keyEvent)
    }

    // com.sonymobile.sidesenseapp.common.activities.BaseFragmentActivity, androidx.fragment.app.ActivityC0230d, android.app.Activity
    override fun onPause() {
        super.onPause()
        PracticeLocationViewService.closeUI(this)
    }

    // com.sonymobile.sidesenseapp.common.activities.BaseFragmentActivity, androidx.fragment.app.ActivityC0230d, android.app.Activity
    override fun onResume() {
        super.onResume()
        if (DeviceProperty.isSideTouchMode()) {
            mTutorial.updateTutorialView()
        } else {
            finishAndRemoveTask()
        }
    }

    fun previousViewPage(): Boolean {
        return mTutorial.previousViewPage()
    }

    companion object {
        const val CURRENT_PAGE_NUM: String = "current_page_num"
        const val EXTRA_ENTRY_POINT: String = "tutorial_entry_point"
        private const val ONE_HANDED_DELAY_TIME = 500
        private const val TAG = "TutorialActivity"
        val isTutorialAvailable: Boolean
            get() = DeviceProperty.isSideTouchAvailable() && !ProductUtil.isAkatsuki() && DeviceProperty.isSideTouchMode()

        fun removeTutorialTask(context: Context) {
            ActivityUtil.removeTask(
                context, ComponentName(
                    context.packageName,
                    TutorialActivity::class.java.name
                )
            )
        }
    }
}