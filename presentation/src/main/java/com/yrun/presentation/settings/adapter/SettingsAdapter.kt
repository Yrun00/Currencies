package com.yrun.presentation.settings.adapter

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.yrun.data.databinding.ChoiceBinding
import com.yrun.data.databinding.EmptyDestinationBinding
import com.yrun.presentation.core.ShowList

class SettingsAdapter(
    private val clickListener: (String) -> Unit,
    private val types: List<SettingsTypeUi> = listOf(SettingsTypeUi.Base, SettingsTypeUi.Empty)
) : RecyclerView.Adapter<SettingsAdapter.SettingsViewHolder>(), ShowList<ChoiceUi> {

    private val choiceList = mutableListOf<ChoiceUi>()

    override fun getItemViewType(position: Int): Int {
        val type = choiceList[position].type()
        val index = types.indexOf(type)
        if (index == -1)
            throw IllegalStateException("Type $type is not included in the typeList $types")
        return index
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SettingsViewHolder {
        return types[viewType].viewHolder(parent, clickListener)
    }

    override fun onBindViewHolder(holder: SettingsViewHolder, position: Int) {
        val choice = choiceList[position]
        holder.bind(choice)
    }

    override fun getItemCount(): Int = choiceList.size

    override fun show(list: List<ChoiceUi>) {
        val result = ChoicesDiffUtil(oldList = this.choiceList, newList = list)
        val calculation = DiffUtil.calculateDiff(result)
        this.choiceList.clear()
        this.choiceList.addAll(list)
        calculation.dispatchUpdatesTo(this)
    }

    fun selectedChoice(): String {
        val selectedChoice = choiceList.find { it.isSelected() }
        return selectedChoice?.id() ?: ""
    }


    abstract class SettingsViewHolder(view: View) :
        RecyclerView.ViewHolder(view) {

        open fun bind(choice: ChoiceUi) = Unit

        class Base(
            private val binding: ChoiceBinding,
            private val clickListener: (String) -> Unit
        ) :
            SettingsViewHolder(binding.root) {
            override fun bind(choice: ChoiceUi) {
                super.bind(choice)
                choice.show(
                    selectedImageView = binding.selectedIconView,
                    choiceTextView = binding.choiceTextView,
                )
                itemView.setOnClickListener {
                    clickListener.invoke(choice.id())
                }

            }
        }

        class Empty(binding: EmptyDestinationBinding) :
            SettingsViewHolder(binding.root)
    }
}

