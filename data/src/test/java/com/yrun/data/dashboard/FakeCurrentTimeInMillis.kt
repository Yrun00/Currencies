package com.yrun.data.dashboard

import com.yrun.data.core.CurrentTimeInMillis

class FakeCurrentTimeInMillis : CurrentTimeInMillis {
    override fun currentTime(): Long {
        return (48 * 3600 * 1000)
    }
}