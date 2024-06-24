package com.carlyu.sidesenseapp.tutorial

import android.app.Activity
import android.os.Bundle
import android.util.SparseArray
import android.view.View
import android.view.ViewTreeObserver
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import androidx.viewpager.widget.ViewPager
import com.booking.rtlviewpager.RtlViewPager

import com.carlyu.sidesenseapp.R
import com.sonymobile.sidesenseapp.common.sensing.sidetouch.SideTouchViewController
import com.sonymobile.sidesenseapp.tutorial.TutorialFragment
import com.sonymobile.sidesenseapp.tutorial.practice.PracticeFragment
import com.sonymobile.sidesenseapp.tutorial.practice.service.PracticeLocationViewService

class TutorialController(private val mActivity: Activity) {
    private var mCurrentPageNum = 0
    private var mIndicatorType = 0
    private val mPagerIndicatorClickListener = View.OnClickListener { view ->
        when (view.id) {
            R.id.tutorial_activity_indicator_next -> nextViewPage()
            R.id.tutorial_activity_indicator_previous -> previousViewPage()
        }
    }
    private val mTutorialFragmentPagerAdapter: TutorialFragmentPagerAdapter
    private val mViewpager: RtlViewPager

    init {
        mViewpager = mActivity.findViewById(R.id.tutorial_activity_pager)
        mTutorialFragmentPagerAdapter =
            TutorialFragmentPagerAdapter((mActivity as androidx.fragment.app.FragmentActivity).supportFragmentManager)
        mViewpager.adapter = mTutorialFragmentPagerAdapter
        val pageChangeListener = object : ViewPager.OnPageChangeListener {
            override fun onPageScrollStateChanged(state: Int) {}
            override fun onPageScrolled(
                position: Int,
                positionOffset: Float,
                positionOffsetPixels: Int
            ) {
            }

            override fun onPageSelected(position: Int) {
                resetPracticeCounter(mCurrentPageNum)
                mCurrentPageNum = position
                updateTutorialView(position)
                resetVideo(position)
            }
        }
        mViewpager.addOnPageChangeListener(pageChangeListener)
        updateTutorialView(mCurrentPageNum)
    }

    private fun checkShowIndicator(pageNum: Int) {
        val indicator = mActivity.findViewById<View>(R.id.tutorial_activity_indicator)
        val prevIndicator = mActivity.findViewById<View>(R.id.tutorial_activity_indicator_previous)
        val navIndicator = mActivity.findViewById<View>(R.id.tutorial_activity_indicator_navigation)
        val nextIndicator = mActivity.findViewById<View>(R.id.tutorial_activity_indicator_next)

        indicator.viewTreeObserver.addOnGlobalLayoutListener(object :
            ViewTreeObserver.OnGlobalLayoutListener {
            override fun onGlobalLayout() {
                if (indicator.width >= nextIndicator.width + navIndicator.width + prevIndicator.width) {
                    mIndicatorType = 1
                } else {
                    setNumericPageIndicator(pageNum)
                    mIndicatorType = 0
                }
                indicator.viewTreeObserver.removeOnGlobalLayoutListener(this)
            }
        })
    }

    private fun resetPracticeCounter(pageNum: Int) {
        val fragment = mTutorialFragmentPagerAdapter.getFragment(pageNum)
        if (fragment is PracticeFragment) {
            fragment.refreshPracticeView()
        }
    }

    private fun resetVideo(pageNum: Int) {
        val fragment = mTutorialFragmentPagerAdapter.getFragment(pageNum)
        when (fragment) {
            is TutorialFragment -> fragment.resetVideo()
            is PracticeFragment -> fragment.resetVideo()
        }
    }

    private fun setNumericPageIndicator(pageNum: Int) {
        val numericIndicator =
            mActivity.findViewById<TextView>(R.id.tutorial_activity_indicator_navigation_numeric)
        numericIndicator.text = mActivity.resources.getString(
            R.string.tutorial_activity_numeric_indicator,
            pageNum + 1,
            ItemKind.size()
        )
        numericIndicator.visibility = View.VISIBLE
    }

    private fun setPageIndicator(pageNum: Int) {
        val indicatorLayout =
            mActivity.findViewById<LinearLayout>(R.id.tutorial_activity_indicator_navigation)
        indicatorLayout.removeAllViews()
        for (i in 0 until ItemKind.size()) {
            val imageView = ImageView(mActivity).apply {
                setImageResource(R.drawable.tutorial_practice_indicator_icn)
                layoutParams = LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    mActivity.resources.getDimensionPixelSize(R.dimen.tutorial_activity_indicator_icon_one_side)
                ).apply {
                    weight =
                        mActivity.resources.getDimension(R.dimen.tutorial_activity_indicator_icon_one_side)
                }
                isSelected = i == pageNum
            }
            indicatorLayout.addView(imageView)
        }
    }

    private fun updateNavigatorVisible(viewId: Int, pageItem: ItemKind.PageItem) {
        val next = when (viewId) {
            R.id.tutorial_activity_indicator_next -> pageItem.getNext()
            R.id.tutorial_activity_indicator_previous -> pageItem.getPrevious()
            else -> 0
        }
        val view = mActivity.findViewById<View>(viewId)
        view.visibility = next
        view.setOnClickListener(if (view.visibility == View.VISIBLE) mPagerIndicatorClickListener else null)
    }

    private fun updateTutorialView(pageNum: Int) {
        val pageItem = ItemKind.getPageItem(pageNum)!!
        updateNavigatorVisible(R.id.tutorial_activity_indicator_previous, pageItem)
        updateNavigatorVisible(R.id.tutorial_activity_indicator_next, pageItem)
        setNumericPageIndicator(pageNum)
        val fragment = mTutorialFragmentPagerAdapter.getFragment(pageNum)
        fragment?.let {
            when (it) {
                is TutorialFragment -> PracticeLocationViewService.closeUI(mActivity)
                is PracticeFragment -> {
                    PracticeLocationViewService.controlView(mActivity)
                    it.startTimer()
                }
            }
            SideTouchViewController.getInstance(it.context).openOrCloseView()
        }
    }

    fun nextViewPage() {
        mViewpager.currentItem = mViewpager.currentItem + 1
    }

    fun previousViewPage(): Boolean {
        return if (mViewpager.currentItem - 1 < 0) {
            false
        } else {
            mViewpager.currentItem = mViewpager.currentItem - 1
            true
        }
    }

    fun updateTutorialView() {
        updateTutorialView(mCurrentPageNum)
    }

    class TutorialFragmentPagerAdapter(fm: FragmentManager) : FragmentPagerAdapter(fm) {
        private val mTutorialFragment = SparseArray<Fragment>()

        override fun getCount(): Int = ItemKind.size()

        fun getFragment(position: Int): Fragment? {
            return mTutorialFragment.get(position, null) ?: run {
                val pageItem = ItemKind.getPageItem(position)
                val fragment: Fragment
                val bundle = Bundle().apply { putInt(PAGE_ITEM_NUM, position) }
                when (pageItem) {
                    is TutorialPageItem -> fragment = TutorialFragment()
                    is PracticePageItem -> fragment = PracticeFragment()
                    else -> return null
                }
                fragment.arguments = bundle
                mTutorialFragment.put(position, fragment)
                fragment
            }
        }

        override fun getItem(position: Int): Fragment = getFragment(position)!!
    }

    companion object {
        const val PAGE_ITEM_NUM = "page_item_num"
        private const val INDICATOR_TYPE_DEFAULT = 0
        private const val INDICATOR_TYPE_DOT = 1
        private const val INDICATOR_TYPE_NUMERIC = 0
        private const val TAG = "TutorialController"
    }
}
