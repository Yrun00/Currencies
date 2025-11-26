package com.yrun.presentation.dashboard.adapter

import android.view.LayoutInflater
import android.view.ViewGroup

import com.yrun.presentation.dashboard.ClickActions
import com.yrun.presentation.databinding.EmptyFavoitesBinding
import com.yrun.presentation.databinding.ErrorBinding
import com.yrun.presentation.databinding.PairBinding
import com.yrun.presentation.databinding.ProgressBinding

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
                ), clickActions
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