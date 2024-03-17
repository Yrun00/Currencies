package com.yrun.presentation.settings

import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResult
import androidx.fragment.app.setFragmentResultListener

interface FragmentInteraction {

    fun source(fragment: Fragment, from: String, to: String)

    fun target(fragment: Fragment, block: (Pair<String, String>) -> Unit)


    class Base() :
        FragmentInteraction {
        override fun source(fragment: Fragment, from: String, to: String) {
            fragment.setFragmentResult(
                KEY,
                bundleOf(
                    KEY_FROM to from,
                    KEY_TO to to
                )
            )
        }

        override fun target(fragment: Fragment, block: (Pair<String, String>) -> Unit) {
            fragment.setFragmentResultListener(requestKey = KEY) { _, bundle ->
                val from: String = bundle.getString(KEY_FROM)!!
                val to: String = bundle.getString(KEY_TO)!!
                block.invoke(Pair(from, to))
            }
        }

        companion object {
            private const val KEY = "currencyPairBundle"
            private const val KEY_FROM = "currencyPairFrom"
            private const val KEY_TO = "currencyPairTo"

        }
    }
}