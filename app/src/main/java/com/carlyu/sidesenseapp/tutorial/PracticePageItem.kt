package com.carlyu.sidesenseapp.tutorial

import com.carlyu.sidesenseapp.R

enum class PracticePageItem(
    val practiceName: String,
    val titleId: Int,
    val descriptionId: Int,
    val finishId: Int,
    val mediaId: Int,
    val previous: Int,
    val next: Int,
    val done: Int
) : ItemKind.PageItem {
    PRACTICE_DOUBLE_TAP(
        TutorialPageItem.DOUBLE_TAP.name,
        R.string.ss_strings_tutorial_double_tap_lesson_ongong_title_txt,
        R.string.ss_strings_tutorial_lesson_ongong_text_txt,
        R.string.ss_strings_tutorial_finish_title_txt,
        R.raw.tutorial_lesson_double_tap_ongoing,
        0,
        0,
        4
    ),
    PRACTICE_SCROLL_UP(
        TutorialPageItem.SCROLL_UP.name,
        R.string.ss_strings_tutorial_slide_up_lesson_ongong_title_txt,
        R.string.ss_strings_tutorial_lesson_ongong_text_txt,
        R.string.ss_strings_tutorial_finish_title_txt,
        R.raw.tutorial_lesson_slide_up_ongoing,
        0,
        0,
        4
    ),
    PRACTICE_SCROLL_DOWN(
        TutorialPageItem.SCROLL_DOWN.name,
        R.string.ss_strings_tutorial_slide_down_lesson_ongong_title_txt,
        R.string.ss_strings_tutorial_lesson_ongong_text_txt,
        R.string.ss_strings_tutorial_finish_title_txt,
        R.raw.tutorial_lesson_slide_down_ongoing,
        0,
        0,
        4
    );

    override fun getDone(): Int = done
    override fun getNext(): Int = next
    override fun getPageName(): String = practiceName
    override fun getPrevious(): Int = previous

    fun getDescriptionId(): Int = descriptionId
    fun getFinishId(): Int = finishId
    fun getMediaContentsId(): Int = mediaId
    fun getTitleId(): Int = titleId
}
