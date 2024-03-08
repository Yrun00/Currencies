package com.yrun.presentation.core

import android.os.Bundle

interface BundleWrapper {

    interface Save {
        fun save(choice: String)
    }

    interface Restore {
        fun restore(): String
    }

    interface Mutable : Save, Restore {
        fun isEmpty(): Boolean
    }

    class Base(private val bundle: Bundle?, private val key: String) : Mutable {
        override fun isEmpty() = bundle == null

        override fun save(choice: String) {
            bundle?.putString(key, choice)
        }

        override fun restore() = bundle?.getString(key) ?: ""
    }
}