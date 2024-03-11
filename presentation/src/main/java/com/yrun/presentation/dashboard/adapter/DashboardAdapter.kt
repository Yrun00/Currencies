package com.yrun.presentation.dashboard.adapter

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.yrun.data.databinding.EmptyFavoitesBinding
import com.yrun.data.databinding.ErrorBinding
import com.yrun.data.databinding.PairBinding
import com.yrun.data.databinding.ProgressBinding
import com.yrun.presentation.core.ShowList
import com.yrun.presentation.dashboard.ClickActions
import com.yrun.presentation.dashboard.PairsDiffUtilCallback

class DashboardAdapter(
    private val clickListener: ClickActions,
    private val types: List<DashboardTypeUi> = listOf(
        DashboardTypeUi.Success,
        DashboardTypeUi.Progress,
        DashboardTypeUi.Error,
        DashboardTypeUi.Empty
    )
) : RecyclerView.Adapter<DashboardAdapter.DashboardViewHolder>(), ShowList<DashboardUi> {

    private val pairs = mutableListOf<DashboardUi>()

    override fun show(list: List<DashboardUi>) {
        val diffResult =
            DiffUtil.calculateDiff(PairsDiffUtilCallback(oldList = pairs, newList = list))
        pairs.clear()
        pairs.addAll(list)
        diffResult.dispatchUpdatesTo(this)
    }

    override fun getItemViewType(position: Int): Int {
        val type = pairs[position].type()
        val index = types.indexOf(type)
        if (index == -1)
            throw IllegalStateException("Type $type is not included in the typeList $types")
        return index
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        types[viewType].viewHolder(parent, clickListener)

    override fun getItemCount(): Int = pairs.size

    override fun onBindViewHolder(holder: DashboardViewHolder, position: Int) =
        holder.bind(pairs[position])

    abstract class DashboardViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        open fun bind(item: DashboardUi) = Unit

        class Empty(binding: EmptyFavoitesBinding) : DashboardViewHolder(binding.root)

        class Progress(binding: ProgressBinding) : DashboardViewHolder(binding.root)

        class Error(
            private val binding: ErrorBinding,
            private val clickListener: ClickActions
        ) :
            DashboardViewHolder(binding.root) {

            override fun bind(item: DashboardUi) {
                item.show(binding)
                binding.retryButton.setOnClickListener { clickListener.retry() }
            }
        }

        class Base(
            private val binding: PairBinding, private val clickListener: ClickActions
        ) : DashboardViewHolder(binding.root) {

            override fun bind(item: DashboardUi) {
                item.show(binding)

                binding.DeleteButton.setOnClickListener {
                    clickListener.deletePair(item.id())
                }
            }
        }
    }
}