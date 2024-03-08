package com.yrun.presentation.settings.adapter

import com.yrun.data.databinding.EmptyDestinationBinding
import com.yrun.presentation.core.views.ChangeText
import com.yrun.presentation.core.views.ChangeVisibility

interface ChoiceUi {

    fun id(): String

    fun type(): SettingsTypeUi

    fun isSelected(): Boolean

    fun show(choiceTextView: ChangeText, selectedImageView: ChangeVisibility) = Unit

    fun show(emptyDestinationBinding: EmptyDestinationBinding) = Unit

    data class Base(private val isSelected: Boolean, private val currency: String) : ChoiceUi {

        override fun id(): String = currency

        override fun type() = SettingsTypeUi.Base

        override fun isSelected(): Boolean = isSelected

        override fun show(choiceTextView: ChangeText, selectedImageView: ChangeVisibility) {
            choiceTextView.changeText(currency)
            selectedImageView.apply {
                if (isSelected) show()
                else hide()
            }
        }
    }

    object Empty : ChoiceUi {

        override fun id(): String = "empty"

        override fun type(): SettingsTypeUi = SettingsTypeUi.Empty

        override fun isSelected(): Boolean = false
    }
}
