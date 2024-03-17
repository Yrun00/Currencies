package com.yrun.presentation.page_objects

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withText

class SnackbarPage {
    fun checkVisible(pair: String) {
        onView(
            withText("Are you sure delete $pair?")
        ).check(matches(isDisplayed()))
        onView(withText("Delete")).check(matches(isDisplayed()))
    }

    fun clickDelete() {
        onView(withText("Delete")).perform(click())
    }
}