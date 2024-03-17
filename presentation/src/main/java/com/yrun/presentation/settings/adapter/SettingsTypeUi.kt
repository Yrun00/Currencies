package com.yrun.presentation.settings.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import com.yrun.data.databinding.ChoiceBinding
import com.yrun.data.databinding.EmptyDestinationBinding

interface SettingsTypeUi {

    fun viewHolder(
        parent: ViewGroup, clickActions: (String) -> Unit
    ): SettingsAdapter.SettingsViewHolder

    object Base : SettingsTypeUi {

        override fun viewHolder(
            parent: ViewGroup, clickActions: (String) -> Unit
        ): SettingsAdapter.SettingsViewHolder =
            SettingsAdapter.SettingsViewHolder.Base(
                ChoiceBinding.inflate(
                    LayoutInflater.from(parent.context), parent, false
                ), clickActions
            )
    }

    object Empty : SettingsTypeUi {

        override fun viewHolder(
            parent: ViewGroup, clickActions: (String) -> Unit
        ): SettingsAdapter.SettingsViewHolder =
            SettingsAdapter.SettingsViewHolder.Empty(
                EmptyDestinationBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            )
    }
}