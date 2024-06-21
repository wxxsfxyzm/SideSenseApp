package com.carlyu.sidesenseapp.loops

import android.graphics.drawable.Animatable2
import android.graphics.drawable.AnimatedVectorDrawable
import android.graphics.drawable.Drawable
import android.os.Handler

class LoopsAnimation(
    private val mDrawable: AnimatedVectorDrawable,
    private val mDuration: Int
) {
    private var mListener: AnimationListener? = null
    private val mAnimationEndRunnable = Runnable {
        mListener?.onAnimationEnd()
    }
    private val mHandler = Handler()

    interface AnimationListener {
        fun onAnimationEnd()
        fun onAnimationStart()
        fun onAnimationStop()
    }

    init {
        mDrawable.registerAnimationCallback(object : Animatable2.AnimationCallback() {
            override fun onAnimationEnd(drawable: Drawable) {
                super.onAnimationEnd(drawable)
            }

            override fun onAnimationStart(drawable: Drawable) {
                super.onAnimationStart(drawable)
            }
        })
    }

    fun setListener(animationListener: AnimationListener) {
        mListener = animationListener
    }

    fun start() {
        mDrawable.start()
        mListener?.onAnimationStart()
        mHandler.postDelayed(mAnimationEndRunnable, mDuration.toLong())
    }

    fun stop() {
        mDrawable.stop()
        mHandler.removeCallbacks(mAnimationEndRunnable)
        mListener?.onAnimationStop()
    }
}
