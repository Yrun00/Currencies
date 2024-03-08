package com.yrun.presentation.settings

import com.yrun.presentation.core.ShowList
import com.yrun.presentation.core.views.ChangeVisibility
import com.yrun.presentation.settings.adapter.ChoiceUi

interface SettingsUiState {

    fun show(
        fromAdapter: ShowList<ChoiceUi>,
        toAdapter: ShowList<ChoiceUi>
    )

    fun show(saveButton: ChangeVisibility) = saveButton.hide()

    data class Initial(private val fromChoices: List<ChoiceUi>) : SettingsUiState {

        override fun show(fromAdapter: ShowList<ChoiceUi>, toAdapter: ShowList<ChoiceUi>) {
            fromAdapter.show(fromChoices)
        }
    }

    object Empty : SettingsUiState {

        override fun show(fromAdapter: ShowList<ChoiceUi>, toAdapter: ShowList<ChoiceUi>) = Unit
    }

    object EmptyDestination : SettingsUiState {

        override fun show(fromAdapter: ShowList<ChoiceUi>, toAdapter: ShowList<ChoiceUi>) {
            toAdapter.show(listOf(ChoiceUi.Empty))
        }
    }

    data class AvailableDestination(
        private val fromCurrency: List<ChoiceUi>,
        private val toCurrency: List<ChoiceUi>
    ) : SettingsUiState {

        override fun show(fromAdapter: ShowList<ChoiceUi>, toAdapter: ShowList<ChoiceUi>) {
            fromAdapter.show(fromCurrency)
            toAdapter.show(toCurrency)
        }
    }

    data class SaveAvailable(
        private val fromCurrency: List<ChoiceUi>,
        private val toCurrency: List<ChoiceUi>
    ) : SettingsUiState {

        override fun show(fromAdapter: ShowList<ChoiceUi>, toAdapter: ShowList<ChoiceUi>) {
            fromAdapter.show(fromCurrency)
            toAdapter.show(toCurrency)
        }

        override fun show(saveButton: ChangeVisibility) {
            saveButton.show()
        }
    }
}