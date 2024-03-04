package com.yrun.presentation.dashboard

import android.view.LayoutInflater
import android.view.ViewGroup
import com.yrun.data.databinding.EmptyBinding
import com.yrun.data.databinding.ErrorBinding
import com.yrun.data.databinding.PairBinding
import com.yrun.data.databinding.ProgressBinding

interface TypeUi {

    fun viewHolder(
        parent: ViewGroup, clickActions: ClickActions
    ): DashboardAdapter.DashboardViewHolder

    object Success : TypeUi {

        override fun viewHolder(
            parent: ViewGroup, clickActions: ClickActions
        ): DashboardAdapter.DashboardViewHolder =
            DashboardAdapter.DashboardViewHolder.Base(
                PairBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            )
    }

    object Empty : TypeUi {

        override fun viewHolder(
            parent: ViewGroup, clickActions: ClickActions
        ): DashboardAdapter.DashboardViewHolder =
            DashboardAdapter.DashboardViewHolder.Empty(
                EmptyBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            )
    }

    object Progress : TypeUi {

        override fun viewHolder(parent: ViewGroup, clickActions: ClickActions) =
            DashboardAdapter.DashboardViewHolder.Progress(
                ProgressBinding.inflate(
                    LayoutInflater.from(parent.context), parent, false
                )
            )
    }

    object Error : TypeUi {

        override fun viewHolder(parent: ViewGroup, clickActions: ClickActions) =
            DashboardAdapter.DashboardViewHolder.Error(
                ErrorBinding.inflate(LayoutInflater.from(parent.context), parent, false),
                clickActions
            )
    }
}