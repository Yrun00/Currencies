package com.yrun.presentation.dashboard

import com.yrun.data.databinding.ErrorBinding
import com.yrun.data.databinding.PairBinding

interface DashboardUi {

    fun id(): String
    fun type(): TypeUi
    fun show(binding: PairBinding) = Unit
    fun show(errorBinding: ErrorBinding) = Unit

    data class Success(
        private val pair: String, private val rate: String
    ) : DashboardUi {

        override fun id(): String = pair

        override fun type(): TypeUi = TypeUi.Success

        override fun show(binding: PairBinding) = with(binding) {
            pairText.text = pair
            rateText.text = rate
        }
    }

    object Empty : DashboardUi {

        override fun id(): String = "empty"

        override fun type(): TypeUi = TypeUi.Empty

    }

    object Progress : DashboardUi {

        override fun id(): String = "progress"

        override fun type(): TypeUi = TypeUi.Progress

    }

    data class Error(private val message: String) : DashboardUi {

        override fun id(): String = "error"

        override fun type(): TypeUi = TypeUi.Error

        override fun show(errorBinding: ErrorBinding) {
            errorBinding.errorTextView.text = message
        }
    }
}