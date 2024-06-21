package com.sonymobile.sidesenseapp.tutorial

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.ViewTreeObserver.OnGlobalLayoutListener
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.ScrollView
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.carlyu.sidesenseapp.R
import com.carlyu.sidesenseapp.common.util.LogUtil
import com.carlyu.sidesenseapp.tutorial.TutorialActivity
import com.carlyu.sidesenseapp.tutorial.TutorialVideoView
import com.sonymobile.sidesenseapp.common.actions.ActionManager
import com.sonymobile.sidesenseapp.common.actions.ActionType
import com.sonymobile.sidesenseapp.common.sensing.displaytouch.floating.C0797a
import com.sonymobile.sidesenseapp.idd.types.EntryPointType

/* loaded from: classes.dex */
class TutorialFragment : Fragment() {
    private var mActivity: TutorialActivity? = null
    private var mContext: Context? = null
    private var mPageView: ViewGroup? = null
    private var mPagerLayout: ViewGroup? = null
    private var mTutorialPageItem: TutorialPageItem? = null
    private var mTutorialVideoView: TutorialVideoView? = null
    private val pagerClicklistner =
        View.OnClickListener { view ->

            // from class: com.sonymobile.sidesenseapp.tutorial.TutorialFragment.1
            // android.view.View.OnClickListener
            var stringExtra: String
            when (view.id) {
                R.id.tutorial_pager_finish_button, R.id.tutorial_pager_finish_button_no_scroll -> {
                    val intent: Intent = mActivity.getIntent()
                    mActivity.setResult(2, intent)
                    mActivity.finishAndRemoveTask()
                    if ((intent == null) || (intent.getStringExtra(TutorialActivity.EXTRA_ENTRY_POINT)
                            .also {
                                stringExtra =
                                    it!!
                            }) == null || stringExtra != EntryPointType.SS_OOBE
                    ) {
                        return@OnClickListener
                    }
                    ActionManager.getInstance(this@TutorialFragment.mContext).startAction(
                        ActionType.SIDE_SENSE_MENU,
                        Builder().isAnimationFeedbackEnabled(false)
                            .entryPoint(EntryPointType.SS_TUTORIAL).build()
                    )
                    return@OnClickListener
                }

                C0684R.C0686id.tutorial_pager_try_now, C0684R.C0686id.tutorial_pager_try_now_no_scroll -> {
                    mActivity.nextViewPage()
                    return@OnClickListener
                }

                else -> return@OnClickListener
            }
        }

    /* JADX INFO: Access modifiers changed from: private */
    fun setLayoutSettings() {
        setTitle(mTutorialPageItem.getTitleId())
        setDescription(mTutorialPageItem.getDescriptionId())
        setFinish(mTutorialPageItem.getFinish())
        setTryNow(mTutorialPageItem.getTrynow())
        if (this.mTutorialPageItem === TutorialPageItem.LESSONS_COMPLETE) {
            setTxtGravity(17)
        }
    }

    private fun setScrollView() {
        mPagerLayout!!.viewTreeObserver.addOnGlobalLayoutListener(object : OnGlobalLayoutListener {
            // from class: com.sonymobile.sidesenseapp.tutorial.TutorialFragment.3
            // android.view.ViewTreeObserver.OnGlobalLayoutListener
            override fun onGlobalLayout() {
                this@TutorialFragment.updateTutorialLayout()
                mPagerLayout!!.viewTreeObserver.removeOnGlobalLayoutListener(this)
            }
        })
    }

    fun checkCanScroll(): Boolean {
        val scrollView =
            mPageView!!.findViewById<View>(C0684R.C0686id.tutorial_pager_scrollview) as ScrollView
        val childAt = scrollView.getChildAt(0)
        val z =
            scrollView.height < scrollView.paddingBottom + (scrollView.paddingTop + childAt.height)
        val sb = StringBuilder()
        C0797a.m1697d(sb, TAG, ".checkCanScroll res= ", z, " scrollView.getHeight()=")
        sb.append(scrollView.height)
        sb.append(" childLayout.getHeight()=")
        sb.append(childAt.height)
        LogUtil.m1502d(LogUtil.LOG_TAG, sb.toString())
        return z
    }

    // androidx.fragment.app.Fragment
    override fun onCreateView(
        layoutInflater: LayoutInflater,
        viewGroup: ViewGroup?,
        bundle: Bundle?
    ): View? {
        this.mPagerLayout =
            viewGroup!!.rootView.findViewById<View>(C0684R.C0686id.tutorial_activity_frameLayout) as ViewGroup
        this.mPageView = layoutInflater.inflate(
            C0684R.layout.tutorial_pager_view,
            viewGroup.findViewById<View>(C0684R.C0686id.tutorial_activity) as ViewGroup
        ) as ViewGroup?
        this.mContext = context
        this.mActivity = activity as TutorialActivity?
        this.mTutorialPageItem =
            ItemKind.getPageItem(arguments!!.getInt(TutorialController.PAGE_ITEM_NUM)) as TutorialPageItem
        val tutorialVideoView =
            mPageView!!.findViewById<View>(C0684R.C0686id.tutorial_activity_introduction_video) as TutorialVideoView
        this.mTutorialVideoView = tutorialVideoView
        tutorialVideoView.setTutorialCategory(0)
        setTitle(mTutorialPageItem.getTitleId())
        setDescription(mTutorialPageItem.getDescriptionId())
        setFinish(mTutorialPageItem.getFinish())
        setTryNow(mTutorialPageItem.getTrynow())
        setLayoutSettings()
        setScrollView()
        return this.mPageView
    }

    // androidx.fragment.app.Fragment
    override fun onPause() {
        super.onPause()
        mTutorialVideoView!!.pauseVideo()
    }

    // androidx.fragment.app.Fragment
    override fun onResume() {
        super.onResume()
        setMediaContent()
    }

    fun resetVideo() {
        val tutorialVideoView = this.mTutorialVideoView
        tutorialVideoView?.resetVideo()
    }

    fun setDescription(i: Int) {
        val textView =
            mPageView!!.findViewById<View>(C0684R.C0686id.tutorial_pager_description) as TextView
        textView.setText(i)
        textView.contentDescription = textView.text
    }

    fun setFinish(i: Int) {
        val findViewById =
            mPageView!!.findViewById<View>(C0684R.C0686id.tutorial_pager_finish_button)
        findViewById.visibility = i
        findViewById.setOnClickListener(if (findViewById.visibility == 0) this.pagerClicklistner else null)
        val findViewById2 =
            mPageView!!.findViewById<View>(C0684R.C0686id.tutorial_pager_finish_button_no_scroll)
        findViewById2.visibility = i
        findViewById2.setOnClickListener(if (findViewById2.visibility == 0) this.pagerClicklistner else null)
    }

    fun setMediaContent() {
        val tutorialPageItem: TutorialPageItem? = this.mTutorialPageItem
        if (tutorialPageItem == null || this.mTutorialVideoView == null) {
            return
        }
        val mediaContentsId: Int = tutorialPageItem.getMediaContentsId()
        val imageView =
            mPageView!!.findViewById<View>(C0684R.C0686id.tutorial_activity_introduction_image) as ImageView
        val layoutParams = imageView.layoutParams
        if (!TutorialPageItem.LESSONS_COMPLETE.getPageName()
                .equals(mTutorialPageItem.getPageName())
        ) {
            mTutorialVideoView!!.visibility = 0
            imageView.visibility = 8
            mTutorialVideoView!!.updateVideo(mediaContentsId)
            return
        }
        mTutorialVideoView!!.visibility = 8
        imageView.visibility = 0
        imageView.setImageResource(mediaContentsId)
        imageView.viewTreeObserver.addOnGlobalLayoutListener(object : OnGlobalLayoutListener {
            // from class: com.sonymobile.sidesenseapp.tutorial.TutorialFragment.2
            // android.view.ViewTreeObserver.OnGlobalLayoutListener
            override fun onGlobalLayout() {
                layoutParams.height = (imageView.width * 4) / 3
                imageView.layoutParams = layoutParams
                imageView.viewTreeObserver.removeOnGlobalLayoutListener(this)
            }
        })
    }

    fun setTitle(i: Int) {
        val textView =
            mPageView!!.findViewById<View>(C0684R.C0686id.tutorial_pager_title) as TextView
        textView.setText(i)
        textView.contentDescription = textView.text
    }

    fun setTryNow(i: Int) {
        val findViewById = mPageView!!.findViewById<View>(C0684R.C0686id.tutorial_pager_try_now)
        findViewById.visibility = i
        findViewById.setOnClickListener(if (findViewById.visibility == 0) this.pagerClicklistner else null)
        val findViewById2 =
            mPageView!!.findViewById<View>(C0684R.C0686id.tutorial_pager_try_now_no_scroll)
        findViewById2.visibility = i
        findViewById2.setOnClickListener(if (findViewById2.visibility == 0) this.pagerClicklistner else null)
    }

    fun setTxtGravity(i: Int) {
        (mPageView!!.findViewById<View>(C0684R.C0686id.tutorial_pager_title) as TextView).gravity =
            i
        (mPageView!!.findViewById<View>(C0684R.C0686id.tutorial_pager_description) as TextView).gravity =
            i
    }

    fun updateTutorialLayout() {
        val scrollView =
            mPageView!!.findViewById<View>(C0684R.C0686id.tutorial_pager_scrollview) as ScrollView
        val relativeLayout =
            mPageView!!.findViewById<View>(C0684R.C0686id.tutorial_pager_scroll_layout) as RelativeLayout
        val relativeLayout2 =
            mPageView!!.findViewById<View>(C0684R.C0686id.tutorial_pager_button_layout) as RelativeLayout
        val relativeLayout3 =
            mPageView!!.findViewById<View>(C0684R.C0686id.tutorial_pager_button_layout_no_scroll) as RelativeLayout
        scrollView.viewTreeObserver.addOnGlobalLayoutListener(object : OnGlobalLayoutListener {
            // from class: com.sonymobile.sidesenseapp.tutorial.TutorialFragment.4
            // android.view.ViewTreeObserver.OnGlobalLayoutListener
            override fun onGlobalLayout() {
                if (this@TutorialFragment.checkCanScroll()) {
                    if (mTutorialPageItem.getTrynow() === 4 && mTutorialPageItem.getFinish() === 4) {
                        scrollView.setPadding(0, 0, 0, 0)
                        relativeLayout.setPadding(0, 0, 0, 0)
                    } else {
                        scrollView.setPadding(
                            0,
                            0,
                            0,
                            this@TutorialFragment.resources.getDimensionPixelSize(C0684R.dimen.tutorial_pager_btn_height)
                        )
                        relativeLayout.setPadding(
                            0,
                            0,
                            0,
                            this@TutorialFragment.resources.getDimensionPixelSize(C0684R.dimen.tutorial_pager_btn_margin_top)
                        )
                        relativeLayout2.visibility = 8
                        relativeLayout3.visibility = 0
                        this@TutorialFragment.setLayoutSettings()
                    }
                }
                scrollView.viewTreeObserver.removeOnGlobalLayoutListener(this)
            }
        })
    }

    companion object {
        private const val TAG = "TutorialFragment"
        private const val TUTORIAL_END = 2
    }
}