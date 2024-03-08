package com.yrun.presentation.dashboard.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import com.yrun.data.databinding.EmptyFavoitesBinding
import com.yrun.data.databinding.ErrorBinding
import com.yrun.data.databinding.PairBinding
import com.yrun.data.databinding.ProgressBinding
import com.yrun.presentation.dashboard.ClickActions

interface DashboardTypeUi {

    fun viewHolder(
        parent: ViewGroup, clickActions: ClickActions
    ): DashboardAdapter.DashboardViewHolder

    object Success : DashboardTypeUi {

        override fun viewHolder(
            parent: ViewGroup, clickActions: ClickActions
        ): DashboardAdapter.DashboardViewHolder =
            DashboardAdapter.DashboardViewHolder.Base(
                PairBinding.inflate(
                    LayoutInflater.from(parent.context), parent, false
                )
            )
    }

    object Empty : DashboardTypeUi {

        override fun viewHolder(
            parent: ViewGroup, clickActions: ClickActions
        ): DashboardAdapter.DashboardViewHolder =
            DashboardAdapter.DashboardViewHolder.Empty(
                EmptyFavoitesBinding.inflate(
                    LayoutInflater.from(parent.context), parent, false
                )
            )
    }

    object Progress : DashboardTypeUi {

        override fun viewHolder(parent: ViewGroup, clickActions: ClickActions) =
            DashboardAdapter.DashboardViewHolder.Progress(
                ProgressBinding.inflate(
                    LayoutInflater.from(parent.context), parent, false
                )
            )
    }

    object Error : DashboardTypeUi {

        override fun viewHolder(parent: ViewGroup, clickActions: ClickActions) =
            DashboardAdapter.DashboardViewHolder.Error(
                ErrorBinding.inflate(LayoutInflater.from(parent.context), parent, false),
                clickActions
            )
    }
}