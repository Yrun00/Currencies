package com.yrun.presentation.page_objects

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.withId
import com.yrun.data.R


class DashboardPage {

    fun checkErrorVisible(message: String) {
        onView(
            RecyclerViewMatcher(
                0,
                targetViewId = R.id.errorTextView,
                recyclerViewId = R.id.dashboardRecyclerView
            )
        )
            .check(ViewAssertions.matches(ViewMatchers.withText(message)))
    }

    fun clickRetry() {
        onView(
            withId(R.id.retryButton)
        ).perform(click())
    }

    fun checkEmpty() {
        onView(
            RecyclerViewMatcher(
                0,
                targetViewId = R.id.emptyFavoriteTextView,
                recyclerViewId = R.id.dashboardRecyclerView
            )
        )
            .check(ViewAssertions.matches(ViewMatchers.withText("Go to settings and add new pair")))
    }

    fun clickSettings() {
        onView(withId(R.id.settingButton)).perform(click())
    }

    fun checkPairs(pairs: List<SettingsPage.CurrencyPair>) {
        pairs.forEachIndexed { index, currencyPair ->
            currencyPair.check(index)
        }
    }

    fun clickDeletePair(position: Int) {
        onView(
            RecyclerViewMatcher(
                position,
                targetViewId = R.id.deleteButton,
                recyclerViewId = R.id.dashboardRecyclerView
            )
        )
            .perform(click())
    }
}