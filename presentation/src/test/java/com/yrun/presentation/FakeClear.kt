package com.yrun.presentation

import com.yrun.presentation.main.Clear
import com.yrun.presentation.main.CustomViewModel
import org.junit.Assert

class FakeClear : Clear {

    private var actual: String = FakeViewModel::class.java.toString()
    val actualList = mutableListOf<String>()

    override fun clear(clazz: Class<out CustomViewModel>) {
        actual = clazz.toString()
        actualList.add(actual.toString())
    }

    fun clearNotCalled() {
        Assert.assertEquals(FakeViewModel::class.java.toString(), actual)
    }

    fun checkLast(expected: String) {
        Assert.assertEquals(expected, actual)
    }

    fun checkList(expected: List<String>) {
        Assert.assertEquals(expected, actualList)
    }
}

object FakeViewModel : CustomViewModel
