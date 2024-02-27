package com.yrun.presentation

import com.yrun.presentation.main.Clear
import com.yrun.presentation.main.CustomViewModel
import org.junit.Assert

class FakeClear : Clear {

    private var actual: Class<out CustomViewModel> = FakeViewModel::class.java

    override fun clear(clazz: Class<out CustomViewModel>) {
        actual = clazz
    }

    fun checkCalled(expected: Class<out CustomViewModel>) {
        Assert.assertEquals(expected, actual)
    }
}

object FakeViewModel : CustomViewModel
