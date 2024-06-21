package com.carlyu.sidesenseapp.loops

import android.content.Context
import android.content.res.TypedArray
import android.util.AttributeSet
import android.view.ViewGroup
import com.carlyu.sidesenseapp.R
import com.carlyu.sidesenseapp.common.util.LogUtil

class LoopsPromptImageView @JvmOverloads constructor(
    context: Context,
    attributeSet: AttributeSet? = null,
    defStyleAttr: Int = 0
) : LoopsImageView(context, attributeSet, defStyleAttr) {

    private var mLabelId: String? = null
    private var mLabelRelativeX = 0
    private var mLabelRelativeY = 0
    private var mPromptTextView: LoopsPromptTextView? = null

    init {
        attributeSet?.let {
            init(context, it)
        }
    }

    private fun init(context: Context, attributeSet: AttributeSet) {
        val obtainStyledAttributes: TypedArray =
            context.obtainStyledAttributes(attributeSet, R.styleable.LoopsPromptImageView)
        try {
            mLabelId = obtainStyledAttributes.getString(R.styleable.LoopsPromptImageView_labelId)
            mLabelRelativeX = obtainStyledAttributes.getDimensionPixelSize(
                R.styleable.LoopsPromptImageView_labelRelativeX,
                0
            )
            mLabelRelativeY = obtainStyledAttributes.getDimensionPixelSize(
                R.styleable.LoopsPromptImageView_labelRelativeY,
                0
            )
            LogUtil.v(
                LogUtil.LOG_TAG,
                "prompt label id=$mLabelId x=$mLabelRelativeX y=$mLabelRelativeY"
            )
        } finally {
            obtainStyledAttributes.recycle()
        }
    }

    override fun layout(l: Int, t: Int, r: Int, b: Int) {
        super.layout(l, t, r, b)
        mPromptTextView?.let { promptTextView ->
            val marginLayoutParams = promptTextView.layoutParams as ViewGroup.MarginLayoutParams
            val measuredWidth = promptTextView.measuredWidth
            val measuredHeight = promptTextView.measuredHeight
            val leftMargin = l + mLabelRelativeX
            marginLayoutParams.leftMargin = leftMargin
            val topMargin = (t + mLabelRelativeY) - (measuredHeight / 2)
            marginLayoutParams.topMargin = topMargin
            promptTextView.layout(
                leftMargin,
                topMargin,
                leftMargin + measuredWidth,
                topMargin + measuredHeight
            )
        }
    }

    override fun onInflated() {
        super.onInflated()
        if (mLabelId == null) {
            LogUtil.e(LogUtil.LOG_TAG, "loops prompt label is null")
            return
        }
        val identifier = context.resources.getIdentifier(mLabelId, "id", context.packageName)
        if (identifier == 0) {
            LogUtil.e(LogUtil.LOG_TAG, "loops prompt label is not valid")
        } else {
            mPromptTextView = rootView.findViewById(identifier)
        }
    }

    override fun setVisibility(visibility: Int) {
        super.setVisibility(visibility)
        mPromptTextView?.visibility = visibility
    }

    override fun startAnimation() {
        super.startAnimation()
        mPromptTextView?.startAnimation()
    }

    override fun stopAnimation() {
        super.stopAnimation()
        mPromptTextView?.stopAnimation()
    }
}
