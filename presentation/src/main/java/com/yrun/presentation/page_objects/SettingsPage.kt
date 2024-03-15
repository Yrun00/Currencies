package com.yrun.presentation.page_objects

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import com.yrun.data.R
import org.hamcrest.CoreMatchers.allOf
import org.hamcrest.CoreMatchers.not

class SettingsPage {

    fun checkFrom(listOf: List<String>) {
        listOf.forEachIndexed { index, string ->
            onView(RecyclerViewMatcher(index, R.id.choiceTextView, R.id.fromRecyclerView))
                .check(matches(withText(string)))
        }
    }

    fun clickFrom(s: String, position: Int) {
        onView(
            allOf(
                RecyclerViewMatcher(position, R.id.choiceTextView, R.id.fromRecyclerView),
                withText(s)
            )
        )
            .perform(click())
    }

    fun checkFromChosen(s: String, position: Int) {
        onView(
            allOf(
                RecyclerViewMatcher(position, R.id.selectedIconView, R.id.fromRecyclerView),
            )
        ).check(matches(isDisplayed()))
        onView(
            allOf(
                RecyclerViewMatcher(position, R.id.choiceTextView, R.id.fromRecyclerView),
                withText(s)
            )
        ).check(matches(isDisplayed()))
    }

    fun checkTo(listOf: List<String>) {
        listOf.forEachIndexed { index, string ->
            onView(RecyclerViewMatcher(index, R.id.choiceTextView, R.id.toRecyclerView))
                .check(matches(withText(string)))
        }
    }

    fun clickTo(s: String, position: Int) {
        onView(
            allOf(
                RecyclerViewMatcher(position, R.id.choiceTextView, R.id.toRecyclerView),
                withText(s)
            )

        )
            .perform(click())
    }

    fun checkToChosen(s: String, position: Int) {
        onView(
            allOf(
                RecyclerViewMatcher(position, R.id.selectedIconView, R.id.toRecyclerView),
            )
        ).check(matches(isDisplayed()))
        onView(
            allOf(
                RecyclerViewMatcher(position, R.id.choiceTextView, R.id.toRecyclerView),
                withText(s)
            )
        )
    }

    fun clickSave() {
        onView(
            allOf(
                withText("Save settings"),
                withId(R.id.saveButton)
            )
        ).perform(click())
    }

    fun checkToEmpty() {
        onView(RecyclerViewMatcher(0, R.id.emptyDestinationTextView, R.id.toRecyclerView)).check(
            matches(withText("No more currencies available"))
        )
    }

    fun checkSaveNotVisible() {
        onView(
            allOf(
                withText("Save settings"),
                withId(R.id.saveButton)
            )
        ).check(matches(not(isDisplayed())))
    }

    fun clickGoBack() {
        onView(
            allOf(
                withId(R.id.backButton)
            )
        ).check(matches(ImageViewDrawableMatcher(R.drawable.baseline_arrow_back_48)))
            .perform(click())
    }

    fun checkSaveVisible() {
        onView(
            allOf(
                withText("Save settings"),
                withId(R.id.saveButton)
            )
        ).check(matches(isDisplayed()))
    }

    class CurrencyPair(
        private val pair: String,
        private val rate: String
    ) {
        fun check(index: Int) {
            onView(RecyclerViewMatcher(index, R.id.pairText, R.id.dashboardRecyclerView))
                .check(matches(withText(pair)))

            onView(RecyclerViewMatcher(index, R.id.rateText, R.id.dashboardRecyclerView))
                .check(matches(withText(rate)))

            onView(RecyclerViewMatcher(index, R.id.DeleteButton, R.id.dashboardRecyclerView))
                .check(
                    matches(
                        ImageViewDrawableMatcher(R.drawable.delete_24)
                    )
                )
        }
    }
}