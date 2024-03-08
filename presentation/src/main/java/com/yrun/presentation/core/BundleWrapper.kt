package com.yrun.presentation.core

import android.os.Bundle

interface BundleWrapper {

    interface Save {
        fun save(choice: String, key: String)
    }

    interface Restore {
        fun restore(key: String): String

        fun restoreTo(): String

        fun restoreFrom(): String

    }

    interface Mutable : Save, Restore {
        fun isEmpty(): Boolean
    }

    class Base(private val bundle: Bundle?) : Mutable {
        override fun isEmpty() = bundle == null

        override fun save(choice: String, key: String) {
            bundle?.putString(key, choice)
        }

        override fun restore(key: String) = bundle?.getString(key) ?: ""

        override fun restoreTo(): String = restore(TO)

        override fun restoreFrom(): String = restore(FROM)

    }

    companion object {
        const val FROM = "fromSelectedId"
        const val TO = "toSelectedId"
    }
}