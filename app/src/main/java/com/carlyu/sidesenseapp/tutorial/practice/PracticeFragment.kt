package com.carlyu.sidesenseapp.tutorial.practice

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RelativeLayout
import androidx.fragment.app.Fragment
import com.carlyu.sidesenseapp.R
import com.carlyu.sidesenseapp.common.listener.EventsListener
import com.carlyu.sidesenseapp.common.listener.PracticeModeGestureListener
import com.carlyu.sidesenseapp.common.preferences.Preferences
import com.carlyu.sidesenseapp.common.util.LogUtil
import com.carlyu.sidesenseapp.common.util.ThemeUtil
import com.carlyu.sidesenseapp.common.util.VibrationUtil
// import com.carlyu.sidesenseapp.idd.Idd
import com.carlyu.sidesenseapp.tutorial.ItemKind
import com.carlyu.sidesenseapp.tutorial.PracticePageItem
import com.carlyu.sidesenseapp.tutorial.TutorialController
import com.carlyu.sidesenseapp.tutorial.TutorialPageItem
import com.carlyu.sidesenseapp.tutorial.TutorialVideoView
import com.carlyu.sidesenseapp.tutorial.practice.ui.PracticeView

class PracticeFragment : Fragment() {
    companion object {
        private const val TAG = "PracticeFragment"
    }

    private var doubleTapCount: Int = 0
    private var slideUpCount: Int = 0
    private var slideDownCount: Int = 0
    private lateinit var eventsListener: PracticeFragmentEventsListener
    private lateinit var rootView: RelativeLayout
    private lateinit var pageVideo: TutorialVideoView
    private lateinit var practiceView: PracticeView
    private lateinit var tutorialPageItem: TutorialPageItem
    private lateinit var practicePageItem: PracticePageItem
    private lateinit var preferences: Preferences
    private lateinit var vibrationUtil: VibrationUtil

    private val eventsListenerCallback = object : EventsListener.Callback {
        override fun onEvent(event: String) {
            LogUtil.d(
                LogUtil.LOG_TAG,
                "$TAG.onEvent event=$event mTutorialPageItem.name()=${tutorialPageItem.name}"
            )
            when (event) {
                PracticeModeGestureListener.EVENT_TUTORIAL_DOUBLE_TAP_STATUS_CHANGED -> handleDoubleTapStatusChanged()
                PracticeModeGestureListener.EVENT_TUTORIAL_SCROLL_DOWN_STATUS_CHANGED -> handleScrollDownStatusChanged()
                PracticeModeGestureListener.EVENT_TUTORIAL_SCROLL_UP_STATUS_CHANGED -> handleScrollUpStatusChanged()
                PracticeModeGestureListener.EVENT_TUTORIAL_PRACTICE_AREA_TAP_STATUS_CHANGED -> if (preferences.getBoolean(
                        Preferences.KEY_BOOLEAN_TUTORIAL_PRACTICE_AREA_TAP
                    )
                ) {
                    onPracticeAreaTap()
                    preferences.putBoolean(
                        Preferences.KEY_BOOLEAN_TUTORIAL_PRACTICE_AREA_TAP,
                        false
                    )
                }
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        rootView = inflater.inflate(
            R.layout.tutorial_practice_view,
            container!!.findViewById(R.id.tutorial_activity)
        ) as RelativeLayout
        tutorialPageItem = ItemKind.getPageItem(
            arguments?.getInt(TutorialController.PAGE_ITEM_NUM)?.minus(1) ?: -1
        ) as TutorialPageItem
        practicePageItem = ItemKind.getPageItem(
            arguments?.getInt(TutorialController.PAGE_ITEM_NUM) ?: -1
        ) as PracticePageItem
        pageVideo = rootView.findViewById(R.id.tutorial_practice_view_lesson_image)
        pageVideo.setTutorialCategory(1)
        practiceView = PracticeView(
            context,
            rootView,
            arguments?.getInt(TutorialController.PAGE_ITEM_NUM) ?: -1
        )
        preferences = Preferences.getInstance(context)
        vibrationUtil = VibrationUtil.getInstance(context)
        eventsListener = PracticeFragmentEventsListener(context, eventsListenerCallback)
        ThemeUtil.setTheme(activity)
        activity?.window?.apply {
            clearFlags(67108864)
            addFlags(Integer.MIN_VALUE)
            statusBarColor = resources.getColor(R.color.tutorial_status_bar_color, null)
        }
        return rootView
    }

    override fun onPause() {
        super.onPause()
        LogUtil.d(LogUtil.LOG_TAG, "$TAG.onPause.mTutorialPageItem.name()=${tutorialPageItem.name}")
        pageVideo.pauseVideo()
        eventsListener.destroy()
        stopTimer()
    }

    override fun onResume() {
        super.onResume()
        LogUtil.d(
            LogUtil.LOG_TAG,
            "$TAG.onResume.mTutorialPageItem.name()=${tutorialPageItem.name}"
        )
        setMediaContent()
        eventsListener.create()
    }

    private fun handleDoubleTapStatusChanged() {
        val status = preferences.getInt(Preferences.KEY_INT_PRACTICE_DOUBLE_TAP_STATUS)
        if (tutorialPageItem == TutorialPageItem.DOUBLE_TAP) {
            when (status) {
                1 -> {
                    vibrationUtil.vibrate(Preferences.KEY_INT_GESTURE_DOUBLE_TAP)
                    doubleTapCount++
                    practiceView.gestureSuccessful(doubleTapCount)
                }

                in 2..7 -> practiceView.gestureError(status)
            }
            // Idd.Tutorial.getInstance().setDoubleTapStatus(status)
        } else if (status == 1) {
            practiceView.gestureCommonError()
        }
    }

    private fun handleScrollDownStatusChanged() {
        val status = preferences.getInt(Preferences.KEY_INT_PRACTICE_SCROLL_DOWN_STATUS)
        if (tutorialPageItem == TutorialPageItem.SCROLL_DOWN) {
            when (status) {
                1 -> {
                    vibrationUtil.vibrate(Preferences.KEY_INT_GESTURE_SCROLL_DOWN)
                    slideDownCount++
                    practiceView.gestureSuccessful(slideDownCount)
                }

                in 5..7 -> practiceView.gestureError(status)
            }
            // Idd.Tutorial.getInstance().setScrollDownStatus(status)
        } else if (status == 1) {
            practiceView.gestureCommonError()
        }
    }

    private fun handleScrollUpStatusChanged() {
        val status = preferences.getInt(Preferences.KEY_INT_PRACTICE_SCROLL_UP_STATUS)
        if (tutorialPageItem == TutorialPageItem.SCROLL_UP) {
            when (status) {
                1 -> {
                    vibrationUtil.vibrate(Preferences.KEY_INT_GESTURE_SCROLL_UP)
                    slideUpCount++
                    practiceView.gestureSuccessful(slideUpCount)
                }

                in 5..7 -> practiceView.gestureError(status)
            }
            // Idd.Tutorial.getInstance().setScrollUpStatus(status)
        } else if (status == 1) {
            practiceView.gestureCommonError()
        }
    }

    private fun onPracticeAreaTap() {
        practiceView.setInVisibleErrorDialog()
        startTimer()
    }

    private fun stopTimer() {
        practiceView.stopErrorAlertTimer()
    }

    private fun setMediaContent() {
        practicePageItem.let {
            pageVideo.visibility = View.VISIBLE
            pageVideo.updateVideo(it.getMediaContentsId())
        }
    }

    private fun startTimer() {
        practiceView.startErrorAlertTimer()
    }

    fun refreshPracticeView() {
        practiceView.apply {
            setPracticeCounter()
            doubleTapCount = 0
            slideUpCount = 0
            slideDownCount = 0
            setTitle()
            setDescription()
            setEndLessonGone()
            setErrorDialogLayoutVisible()
            setInVisibleErrorDialog()
            stopTimer()
        }
    }

    fun resetVideo() {
        pageVideo.resetVideo()
    }
}
