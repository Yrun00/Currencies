package com.yrun.presentation.page_objects

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withParent
import androidx.test.espresso.matcher.ViewMatchers.withText
import com.yrun.data.R
import org.hamcrest.CoreMatchers.allOf

class PremiumPage {
    fun checkVisible() {
        onView(
            allOf(
                withParent(withId(R.id.premiumLayout)),
                withId(R.id.backToSettingsButton),
                ImageViewDrawableMatcher(R.drawable.baseline_arrow_back_48),
            )
        ).check(matches(isDisplayed()))
        onView(
            allOf(
                withParent(withId(R.id.premiumLayout)),
                withId(R.id.buyPremiumButton),
                withText("Buy Premium"),
            )
        ).check(matches(isDisplayed()))

    }

    fun clickBack() {
        onView(
            allOf(
                withParent(withId(R.id.premiumLayout)),
                withId(R.id.backToSettingsButton),
                ImageViewDrawableMatcher(R.drawable.baseline_arrow_back_48),
            )
        ).perform(click())
    }

    fun clickBuyPremium() {
        onView(
            allOf(
                withParent(withId(R.id.premiumLayout)),
                withId(R.id.buyPremiumButton),
                withText("Buy Premium"),
            )
        ).perform(click())
    }
}