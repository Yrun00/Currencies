package com.yrun.presentation.dashboard

import androidx.recyclerview.widget.DiffUtil
import com.yrun.presentation.dashboard.adapter.DashboardUi

class PairsDiffUtilCallback(
    private val oldList: List<DashboardUi>,
    private val newList: List<DashboardUi>
) :
    DiffUtil.Callback() {
    override fun getOldListSize() = oldList.size

    override fun getNewListSize() = newList.size

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean =
        oldList[oldItemPosition].id() == newList[newItemPosition].id()

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean =
        oldList[oldItemPosition] == newList[newItemPosition]

}
