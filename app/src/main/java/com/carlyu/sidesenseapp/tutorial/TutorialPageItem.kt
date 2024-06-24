package com.carlyu.sidesenseapp.tutorial

import com.carlyu.sidesenseapp.R

enum class TutorialPageItem(
    val mediaId: Int,
    val previous: Int,
    val next: Int,
    val done: Int,
    val finish: Int,
    val trynow: Int,
    val titleId: Int,
    val descriptionId: Int
) : ItemKind.PageItem {
    LEARN_GESTURES(
        R.raw.tutorial_learn_gestures,
        0,
        0,
        4,
        4,
        0,
        R.string.ss_strings_tutorial_learn_title_txt,
        R.string.ss_strings_tutorial_learn_text_txt
    ),
    DOUBLE_TAP(
        R.raw.tutorial_double_tap,
        0,
        0,
        4,
        4,
        0,
        R.string.ss_strings_tutorial_double_tap_title_txt,
        R.string.ss_strings_tutorial_double_tap_text_txt
    ),
    SCROLL_UP(
        R.raw.tutorial_slide_up,
        0,
        0,
        4,
        4,
        0,
        R.string.ss_strings_tutorial_slide_up_title_txt,
        R.string.ss_strings_tutorial_slide_up_text_txt
    ),
    SCROLL_DOWN(
        R.raw.tutorial_slide_down,
        0,
        0,
        4,
        4,
        0,
        R.string.ss_strings_tutorial_slide_down_title_txt,
        R.string.ss_strings_tutorial_slide_down_text_txt
    ),
    LESSONS_COMPLETE(
        R.raw.tutorial_finish,
        0,
        4,
        4,
        0,
        4,
        R.string.ss_strings_tutorial_finish_title_txt,
        R.string.ss_strings_tutorial_finish_text_txt
    );

    override fun getDone(): Int = done
    override fun getNext(): Int = next
    override fun getPageName(): String = name
    override fun getPrevious(): Int = previous

    fun getDescriptionId(): Int = descriptionId
    fun getFinish(): Int = finish
    fun getMediaContentsId(): Int = mediaId
    fun getTitleId(): Int = titleId
    fun getTrynow(): Int = trynow
}
