package com.example.digikalasample.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.digikalasample.R
import com.example.digikalasample.data.model.FilterItem
import com.example.digikalasample.databinding.FilterItemBinding



typealias ClickHandlerFilterItem = (FilterItem) -> Unit

class FilterItemAdapter(val clickHandler : ClickHandlerFilterItem) :
    ListAdapter<FilterItem, FilterItemAdapter.ItemHolder>(FilterItemDiffCallback) {
    class ItemHolder(val binding: FilterItemBinding) : RecyclerView.ViewHolder(binding.root)


    object FilterItemDiffCallback : DiffUtil.ItemCallback<FilterItem>() {

        override fun areItemsTheSame(oldItem: FilterItem, newItem: FilterItem): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: FilterItem, newItem: FilterItem): Boolean {
            return oldItem.id == newItem.id
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemHolder {
        val binding: FilterItemBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.filter_item,
            parent, false
        )
        return ItemHolder(binding)

    }

    override fun onBindViewHolder(holder: ItemHolder, position: Int) {
        val filterItem = getItem(position)
        holder.binding.filteritem = filterItem
        holder.binding.checkBoxFilterItem.setOnCheckedChangeListener { compoundButton, b ->
            if (holder.binding.checkBoxFilterItem.isChecked){
                clickHandler.invoke(filterItem)
            }
        }
    }
}