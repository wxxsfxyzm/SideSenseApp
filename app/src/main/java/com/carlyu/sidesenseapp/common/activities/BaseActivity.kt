package com.carlyu.sidesenseapp.common.activities

import android.annotation.SuppressLint
import android.app.Activity
import android.view.View
import com.carlyu.sidesenseapp.R
import com.carlyu.sidesenseapp.common.listener.SideSenseActivityStateListener
import com.google.android.material.appbar.CollapsingToolbarLayout

/* loaded from: classes.dex */
class BaseActivity : Activity() {
    var mCollapsingToolbarLayout: CollapsingToolbarLayout? = null

    // android.app.Activity
    public override fun onPause() {
        super.onPause()
        SideSenseActivityStateListener.getInstance(this)?.notifyActivityStateChanged(
            this,
            componentName, SideSenseActivityStateListener.ACTIVITY_STATE_PAUSED
        )
    }

    // android.app.Activity
    public override fun onResume() {
        super.onResume()
        SideSenseActivityStateListener.getInstance(this)?.notifyActivityStateChanged(
            this,
            componentName, SideSenseActivityStateListener.ACTIVITY_STATE_RESUMED
        )
    }

    // android.app.Activity
    @SuppressLint("RestrictedApi")
    override fun setContentView(i: Int) {
        super.setContentView(i)
        val collapsingToolbarLayout =
            findViewById<View>(R.id.collapsing_toolbar) as CollapsingToolbarLayout
        this.mCollapsingToolbarLayout = collapsingToolbarLayout
        collapsingToolbarLayout.lineSpacingMultiplier = TOOLBAR_LINE_SPACING_MULTIPLIER
    }

    companion object {
        private const val TOOLBAR_LINE_SPACING_MULTIPLIER = 1.1f
    }
}