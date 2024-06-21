package com.carlyu.sidesenseapp.loops


import android.content.Context
import android.graphics.drawable.AnimatedVectorDrawable
import android.os.Handler
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatImageView
import com.carlyu.sidesenseapp.R

open class LoopsImageView @JvmOverloads constructor(
    context: Context,
    attributeSet: AttributeSet? = null,
    defStyleAttr: Int = 0
) : AppCompatImageView(context, attributeSet, defStyleAttr) {
    companion object {
        const val DEFAULT_ANIM_DURATION = 1000
    }

    private val mHandler = Handler()
    private var mLoopsAnimation: LoopsAnimation? = null
    private var mLoopsPositionX = 0
    private var mLoopsPositionY = 0
    private var mStartOffset = 0

    init {
        attributeSet?.let {
            init(context, it)
        }
    }

    private fun init(context: Context, attributeSet: AttributeSet) {
        val obtainStyledAttributes =
            context.obtainStyledAttributes(attributeSet, R.styleable.LoopsImageView)
        try {
            mLoopsAnimation = LoopsAnimation(
                getDrawable() as AnimatedVectorDrawable,
                obtainStyledAttributes.getInt(0, DEFAULT_ANIM_DURATION)
            )
            mStartOffset =
                obtainStyledAttributes.getInteger(R.styleable.LoopsImageView_startOffset, 0)
            mLoopsPositionX = obtainStyledAttributes.getDimensionPixelSize(
                R.styleable.LoopsImageView_loopsPositionX,
                0
            )
            mLoopsPositionY = obtainStyledAttributes.getDimensionPixelSize(
                R.styleable.LoopsImageView_loopsPositionY,
                0
            )
        } finally {
            obtainStyledAttributes.recycle()
        }
    }

    fun getLoopsPositionX(): Int {
        return mLoopsPositionX
    }

    fun getLoopsPositionY(): Int {
        return mLoopsPositionY
    }

    open fun onInflated() {
        // Do nothing
    }

    fun setListener(animationListener: LoopsAnimation.AnimationListener) {
        mLoopsAnimation?.setListener(animationListener)
    }

    open fun startAnimation() {
        if (mStartOffset > 0) {
            mHandler.postDelayed({
                setAnimation(null)
                mLoopsAnimation?.start()
            }, mStartOffset.toLong())
        } else {
            setAnimation(null)
            mLoopsAnimation?.start()
        }
    }

    open fun stopAnimation() {
        clearAnimation()
        mLoopsAnimation?.stop()
    }
}
