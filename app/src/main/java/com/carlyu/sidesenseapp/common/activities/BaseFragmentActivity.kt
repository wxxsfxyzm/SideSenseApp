package com.carlyu.sidesenseapp.common.activities

import androidx.fragment.app.FragmentActivity
import com.sonymobile.sidesenseapp.common.listener.SideSenseActivityStateListener

/* loaded from: classes.dex */
open class BaseFragmentActivity : FragmentActivity() {
    // androidx.fragment.app.ActivityC0230d, android.app.Activity
    override fun onPause() {
        super.onPause()
        SideSenseActivityStateListener.getInstance(this)?.notifyActivityStateChanged(
            this,
            componentName,
            SideSenseActivityStateListener.ACTIVITY_STATE_PAUSED
        )
    }

    // androidx.fragment.app.ActivityC0230d, android.app.Activity
    override fun onResume() {
        super.onResume()
        SideSenseActivityStateListener.getInstance(this)?.notifyActivityStateChanged(
            this,
            componentName,
            SideSenseActivityStateListener.ACTIVITY_STATE_RESUMED
        )
    }
}