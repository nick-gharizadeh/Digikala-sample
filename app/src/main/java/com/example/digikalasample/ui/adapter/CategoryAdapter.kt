package com.example.digikalasample.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.digikalasample.R
import com.example.digikalasample.data.model.Category
import com.example.digikalasample.databinding.CategoryItemBinding

typealias ClickHandlerCategory = (Category) -> Unit

class CategoryAdapter(private var clickHandler: ClickHandlerCategory) :
    ListAdapter<Category, CategoryAdapter.ItemHolder>(CategoryDiffCallback) {
    class ItemHolder(val binding: CategoryItemBinding) : RecyclerView.ViewHolder(binding.root)


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemHolder {
        val binding: CategoryItemBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.category_item,
            parent, false
        )
        return ItemHolder(binding)

    }

    override fun onBindViewHolder(holder: ItemHolder, position: Int) {
        val category = getItem(position)
        holder.binding.category = category
        holder.binding.itemCategoryTitle.setOnClickListener {
            clickHandler.invoke(category)
        }
    }
}


object CategoryDiffCallback : DiffUtil.ItemCallback<Category>() {

    override fun areItemsTheSame(oldItem: Category, newItem: Category): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(oldItem: Category, newItem: Category): Boolean {
        return oldItem.id == newItem.id
    }
}