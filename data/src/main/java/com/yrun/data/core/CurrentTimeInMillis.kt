package com.yrun.data.core

interface CurrentTimeInMillis {

    fun currentTime(): Long

    class Base : CurrentTimeInMillis {
        override fun currentTime(): Long {
            return System.currentTimeMillis()
        }
    }
}