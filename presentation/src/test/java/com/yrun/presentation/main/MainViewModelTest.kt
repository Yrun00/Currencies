package com.yrun.presentation.main

import com.yrun.presentation.FakeNavigation
import com.yrun.presentation.load.LoadScreen
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class MainViewModelTest {

    private lateinit var viewModel: MainViewModel
    private lateinit var navigation: FakeNavigation


    @Before
    fun setup() {
        navigation = FakeNavigation()
        viewModel = MainViewModel(navigation = navigation)

    }

    @Test
    fun testFirstRun() {
        viewModel.init(isFirsRun = true)
        val expected = LoadScreen
        assertEquals(expected, navigation.actual)
    }

    @Test
    fun notFirstRun() {
        viewModel.init(false)
        assertEquals(Screen.Empty, navigation.actual)
    }
}

