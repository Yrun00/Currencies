package com.yrun.presentation.page_objects

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import com.yrun.data.R
import org.hamcrest.CoreMatchers.allOf

class LoadPage {
    fun checkError(error: String) {
        onView(
            allOf(
                withId(R.id.fragmentErrorTextView),
                withText(error)
            )
        ).check(matches(isDisplayed()))

        onView(
            allOf(
                withId(R.id.fragmentRetryButton),
                withText("Retry")
            )
        ).check(matches(isDisplayed()))
    }

    fun clickRetry() {
        onView(
            allOf(
                withId(R.id.fragmentRetryButton),
                withText("Retry")
            )
        ).perform(click())
    }
}