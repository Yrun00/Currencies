package com.yrun.presentation.settings.adapter

import androidx.recyclerview.widget.DiffUtil

class ChoicesDiffUtil(
    private val oldList: List<ChoiceUi>,
    private val newList: List<ChoiceUi>
) : DiffUtil.Callback() {

    override fun getOldListSize() = oldList.size

    override fun getNewListSize() = newList.size

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean =
        oldList[oldItemPosition].id() == newList[newItemPosition].id()

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean =
        oldList[oldItemPosition] == newList[newItemPosition]
}