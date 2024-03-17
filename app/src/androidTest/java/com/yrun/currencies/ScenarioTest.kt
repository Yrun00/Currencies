package com.yrun.currencies

import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.yrun.presentation.main.MainActivity
import com.yrun.presentation.page_objects.DashboardPage
import com.yrun.presentation.page_objects.LoadPage
import com.yrun.presentation.page_objects.PremiumPage
import com.yrun.presentation.page_objects.SettingsPage
import com.yrun.presentation.page_objects.SnackbarPage
import org.junit.Assert.*
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class ScenarioTest {

    @get:Rule
    var activityScenarioRule = ActivityScenarioRule(MainActivity::class.java)

    @Test
    fun scenarioTest() {
        val premiumPage = PremiumPage()
        val dashboardPage = DashboardPage()
        val settingsPage = SettingsPage()
        val snackbarPage = SnackbarPage()
        val loadPage = LoadPage()

        loadPage.checkError("No internet connection")
        activityScenarioRule.scenario.recreate()
        loadPage.checkError("No internet connection")
        loadPage.clickRetry()
        dashboardPage.checkErrorVisible("No internet connection")
        dashboardPage.clickRetry()
        dashboardPage.checkEmpty()
        activityScenarioRule.scenario.recreate()
        dashboardPage.checkEmpty()

        dashboardPage.clickSettings()
        settingsPage.checkFrom(listOf<String>("EUR", "RUB", "USD"))
        activityScenarioRule.scenario.recreate()
        settingsPage.checkFrom(listOf<String>("EUR", "RUB", "USD"))

        settingsPage.clickFrom("USD", position = 2)
        settingsPage.checkFromChosen("USD", position = 2)
        settingsPage.checkTo(listOf<String>("EUR", "RUB"))
        activityScenarioRule.scenario.recreate()
        settingsPage.checkFromChosen("USD", position = 2)
        settingsPage.checkTo(listOf<String>("EUR", "RUB"))

        settingsPage.clickTo("EUR", position = 0)
        settingsPage.checkFromChosen("USD", position = 2)
        settingsPage.checkToChosen("EUR", position = 0)
        activityScenarioRule.scenario.recreate()
        settingsPage.checkFromChosen("USD", position = 2)
        settingsPage.checkToChosen("EUR", position = 0)

        settingsPage.clickSave()
        dashboardPage.checkPairs(
            pairs = listOf(
                SettingsPage.CurrencyPair(
                    pair = "USD/EUR",
                    rate = "1.11"
                )
            )
        )
        activityScenarioRule.scenario.recreate()
        dashboardPage.checkPairs(
            pairs = listOf(
                SettingsPage.CurrencyPair(
                    pair = "USD/EUR",
                    rate = "1.11"
                )
            )
        )

        dashboardPage.clickSettings()
        settingsPage.checkFrom(listOf<String>("EUR", "RUB", "USD"))
        settingsPage.clickFrom("USD", position = 2)
        settingsPage.checkFromChosen("USD", position = 2)
        settingsPage.checkTo(listOf<String>("RUB"))
        settingsPage.clickTo("RUB", position = 0)
        settingsPage.checkFromChosen("USD", position = 2)
        settingsPage.checkToChosen("RUB", position = 0)

        settingsPage.clickSave()
        dashboardPage.checkPairs(
            pairs = listOf(
                SettingsPage.CurrencyPair(
                    pair = "USD/EUR",
                    rate = "1.11"
                ),
                SettingsPage.CurrencyPair(
                    pair = "USD/RUB",
                    rate = "2.11"
                )
            )
        )
        dashboardPage.clickSettings()
        settingsPage.checkFrom(listOf<String>("EUR", "RUB", "USD"))
        settingsPage.clickFrom("USD", position = 2)
        settingsPage.checkFromChosen("USD", position = 2)
        settingsPage.checkToEmpty()
        settingsPage.checkSaveNotVisible()
        settingsPage.clickFrom("EUR", position = 0)
        settingsPage.checkFromChosen("EUR", position = 0)
        settingsPage.checkTo(listOf<String>("RUB", "USD"))
        settingsPage.clickTo("USD", position = 1)
        settingsPage.checkToChosen("USD", position = 1)

        settingsPage.clickSave()
        premiumPage.checkVisible()
        activityScenarioRule.scenario.recreate()
        premiumPage.checkVisible()
        premiumPage.clickBack()
        settingsPage.checkFrom(listOf<String>("EUR", "RUB", "USD"))
        settingsPage.checkFromChosen("EUR", position = 0)
        settingsPage.checkTo(listOf<String>("RUB", "USD"))
        settingsPage.checkToChosen("USD", position = 1)
        settingsPage.clickGoBack()
        dashboardPage.checkPairs(
            pairs = listOf(
                SettingsPage.CurrencyPair(
                    pair = "USD/EUR",
                    rate = "1.11"
                ),
                SettingsPage.CurrencyPair(
                    pair = "USD/RUB",
                    rate = "2.11"
                )
            )
        )
        dashboardPage.clickSettings()
        settingsPage.checkFrom(listOf<String>("EUR", "RUB", "USD"))
        settingsPage.clickFrom("USD", position = 2)
        settingsPage.checkFromChosen("USD", position = 2)
        settingsPage.checkToEmpty()
        settingsPage.checkSaveNotVisible()
        settingsPage.clickFrom("EUR", position = 0)
        settingsPage.checkFromChosen("EUR", position = 0)
        settingsPage.checkTo(listOf<String>("RUB", "USD"))
        settingsPage.clickTo("USD", position = 1)
        settingsPage.checkToChosen("USD", position = 1)
        settingsPage.clickSave()
        premiumPage.checkVisible()
        premiumPage.clickBuyPremium()

        dashboardPage.checkPairs(
            pairs = listOf(
                SettingsPage.CurrencyPair(
                    pair = "USD/EUR",
                    rate = "1.11"
                ),
                SettingsPage.CurrencyPair(
                    pair = "USD/RUB",
                    rate = "2.11"
                ),
                SettingsPage.CurrencyPair(
                    pair = "EUR/USD",
                    rate = "3.11"
                )
            )
        )
        dashboardPage.clickDeletePair(position = 0)
        snackbarPage.checkVisible(pair = "USD/EUR")
        snackbarPage.clickDelete()

        dashboardPage.checkPairs(
            pairs = listOf(
                SettingsPage.CurrencyPair(
                    pair = "USD/RUB",
                    rate = "2.11"
                ),
                SettingsPage.CurrencyPair(
                    pair = "EUR/USD",
                    rate = "3.11"
                )
            )
        )

        dashboardPage.clickSettings()
        settingsPage.checkFrom(listOf<String>("EUR", "RUB", "USD"))
        settingsPage.clickFrom("USD", position = 2)
        settingsPage.checkFromChosen("USD", position = 2)
        settingsPage.checkTo(listOf<String>("EUR"))
        settingsPage.clickTo("EUR", position = 0)
        settingsPage.checkToChosen("EUR", position = 0)
        settingsPage.clickFrom("EUR", position = 0)
        settingsPage.checkTo(listOf<String>("RUB"))
        settingsPage.clickTo("RUB", position = 0)
        settingsPage.checkToChosen("RUB", position = 0)
        settingsPage.checkSaveVisible()
        activityScenarioRule.scenario.recreate()
        settingsPage.checkSaveVisible()
        settingsPage.clickGoBack()
        dashboardPage.checkPairs(
            pairs = listOf(
                SettingsPage.CurrencyPair(
                    pair = "USD/RUB",
                    rate = "2.11"
                ),
                SettingsPage.CurrencyPair(
                    pair = "EUR/USD",
                    rate = "3.11"
                )
            )
        )
        dashboardPage.clickDeletePair(position = 1)

        snackbarPage.checkVisible(pair = "EUR/USD")
        snackbarPage.clickDelete()
        dashboardPage.checkPairs(
            pairs = listOf(
                SettingsPage.CurrencyPair(
                    pair = "USD/RUB",
                    rate = "2.11"
                )
            )
        )
        dashboardPage.clickDeletePair(position = 0)
        snackbarPage.checkVisible(pair = "USD/RUB")
        snackbarPage.clickDelete()
        dashboardPage.checkEmpty()
    }
}