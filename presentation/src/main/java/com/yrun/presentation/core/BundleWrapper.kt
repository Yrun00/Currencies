package com.yrun.presentation.core

import android.os.Bundle
import android.os.Parcelable

interface BundleWrapper {

    interface Save {
        fun save(choice: String, key: String)

        fun save(parcelable: Parcelable?, key: String)
    }

    interface Restore {
        fun restore(key: String): String

        fun restoreTo(): String

        fun restoreFrom(): String

        fun restoreRecycler(): Parcelable?

    }

    interface Mutable : Save, Restore {
        fun isEmpty(): Boolean
    }

    class Base(private val bundle: Bundle?) : Mutable {
        override fun isEmpty() = bundle == null

        override fun save(choice: String, key: String) {
            bundle?.putString(key, choice)
        }

        override fun save(parcelable: Parcelable?, key: String) {
            bundle?.putParcelable(key, parcelable)
        }

        override fun restore(key: String) = bundle?.getString(key) ?: ""

        override fun restoreTo(): String = restore(TO)

        override fun restoreFrom(): String = restore(FROM)

        override fun restoreRecycler(): Parcelable? {
            return bundle?.getParcelable(RECYCLER)
        }
    }

    companion object {
        const val FROM = "fromSelectedId"
        const val TO = "toSelectedId"
        const val RECYCLER = "recycler_state"
    }
}