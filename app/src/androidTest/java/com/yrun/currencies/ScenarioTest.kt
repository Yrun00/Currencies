package com.yrun.currencies

import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.yrun.presentation.main.MainActivity
import com.yrun.presentation.page_objects.DashboardPage
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
        val dashboardPage = DashboardPage()
        val settingsPage = SettingsPage()
        val snackbarPage = SnackbarPage()

        dashboardPage.checkErrorVisible("No internet connection")
        dashboardPage.checkErrorVisible("No internet connection")
        dashboardPage.clickRetry()
        dashboardPage.checkEmpty()

        dashboardPage.clickSettings()
        settingsPage.checkFrom(listOf<String>("USD", "EUR", "RUB"))

        settingsPage.clickFrom("USD", position = 0)
        settingsPage.checkFromChosen("USD", position = 0)
        settingsPage.checkTo(listOf<String>("EUR", "RUB"))

        settingsPage.clickTo("EUR", position = 0)
        settingsPage.checkFromChosen("USD", position = 0)
        settingsPage.checkToChosen("EUR", position = 0)

        settingsPage.clickSave()
        dashboardPage.checkPairs(
            pairs = listOf(
                com.yrun.presentation.page_objects.SettingsPage.CurrencyPair(
                    pair = "USD/EUR",
                    rate = "1.11"
                )
            )
        )

        dashboardPage.clickSettings()
        settingsPage.checkFrom(listOf<String>("USD", "EUR", "RUB"))
        settingsPage.clickFrom("USD", position = 0)
        settingsPage.checkFromChosen("USD", position = 0)
        settingsPage.checkTo(listOf<String>("RUB"))
        settingsPage.clickTo("RUB", position = 0)
        settingsPage.checkFromChosen("USD", position = 0)
        settingsPage.checkToChosen("RUB", position = 0)

        settingsPage.clickSave()
        dashboardPage.checkPairs(
            pairs = listOf(
                com.yrun.presentation.page_objects.SettingsPage.CurrencyPair(
                    pair = "USD/EUR",
                    rate = "1.11"
                ),
                com.yrun.presentation.page_objects.SettingsPage.CurrencyPair(
                    pair = "USD/RUB",
                    rate = "2.11"
                )
            )
        )
        dashboardPage.clickSettings()
        settingsPage.checkFrom(listOf<String>("USD", "EUR", "RUB"))
        settingsPage.clickFrom("USD", position = 0)
        settingsPage.checkFromChosen("USD", position = 0)
        settingsPage.checkToEmpty()
        settingsPage.checkSaveNotVisible()
        settingsPage.clickFrom("EUR", position = 1)
        settingsPage.checkFromChosen("EUR", position = 1)
        settingsPage.checkTo(listOf<String>("USD", "RUB"))
        settingsPage.clickTo("USD", position = 0)
        settingsPage.checkToChosen("USD", position = 0)

        settingsPage.clickSave()
        dashboardPage.checkPairs(
            pairs = listOf(
                com.yrun.presentation.page_objects.SettingsPage.CurrencyPair(
                    pair = "USD/EUR",
                    rate = "1.11"
                ),
                com.yrun.presentation.page_objects.SettingsPage.CurrencyPair(
                    pair = "USD/RUB",
                    rate = "2.11"
                ),
                com.yrun.presentation.page_objects.SettingsPage.CurrencyPair(
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
                com.yrun.presentation.page_objects.SettingsPage.CurrencyPair(
                    pair = "USD/RUB",
                    rate = "2.11"
                ),
                com.yrun.presentation.page_objects.SettingsPage.CurrencyPair(
                    pair = "EUR/USD",
                    rate = "3.11"
                )
            )
        )

        dashboardPage.clickSettings()
        settingsPage.checkFrom(listOf<String>("USD", "EUR", "RUB"))
        settingsPage.clickFrom("USD", position = 0)
        settingsPage.checkFromChosen("USD", position = 0)
        settingsPage.checkTo(listOf<String>("EUR"))
        settingsPage.clickTo("EUR", position = 0)
        settingsPage.checkToChosen("EUR", position = 0)
        settingsPage.clickFrom("EUR", position = 1)
        settingsPage.checkTo(listOf<String>("RUB"))
        settingsPage.clickTo("RUB", position = 0)
        settingsPage.checkToChosen("RUB", position = 0)
        settingsPage.checkSaveVisible()
        settingsPage.clickGoBack()
        dashboardPage.checkPairs(
            pairs = listOf(
                com.yrun.presentation.page_objects.SettingsPage.CurrencyPair(
                    pair = "USD/RUB",
                    rate = "2.11"
                ),
                com.yrun.presentation.page_objects.SettingsPage.CurrencyPair(
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
                com.yrun.presentation.page_objects.SettingsPage.CurrencyPair(
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