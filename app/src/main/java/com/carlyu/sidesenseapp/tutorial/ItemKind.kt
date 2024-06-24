package com.carlyu.sidesenseapp.tutorial

object ItemKind {
    private const val TAG = "ItemKind"
    private val sPagerList: MutableList<PageItem> = mutableListOf(
        TutorialPageItem.LEARN_GESTURES,
        TutorialPageItem.DOUBLE_TAP,
        PracticePageItem.PRACTICE_DOUBLE_TAP,
        TutorialPageItem.SCROLL_UP,
        PracticePageItem.PRACTICE_SCROLL_UP,
        TutorialPageItem.SCROLL_DOWN,
        PracticePageItem.PRACTICE_SCROLL_DOWN,
        TutorialPageItem.LESSONS_COMPLETE
    )

    interface PageItem {
        fun getDone(): Int
        fun getNext(): Int
        fun getPageName(): String
        fun getPrevious(): Int
    }

    fun getPageItem(index: Int): PageItem? = sPagerList.getOrNull(index)

    fun getPageItem(pageName: String): PageItem? =
        sPagerList.firstOrNull { it.getPageName() == pageName }

    fun size(): Int = sPagerList.size
}
