package com.example.digikalasample.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.digikalasample.R
import com.example.digikalasample.data.model.address.Address
import com.example.digikalasample.databinding.AddressItemBinding



class AddressAdapter :
    ListAdapter<Address, AddressAdapter.ItemHolder>(AddressDiffCallback) {
    class ItemHolder(val binding: AddressItemBinding) : RecyclerView.ViewHolder(binding.root)


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemHolder {
        val binding: AddressItemBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.address_item,
            parent, false
        )
        return ItemHolder(binding)

    }

    override fun onBindViewHolder(holder: ItemHolder, position: Int) {
        val address = getItem(position)
        holder.binding.address = address

    }
}


object AddressDiffCallback : DiffUtil.ItemCallback<Address>() {

    override fun areItemsTheSame(oldItem: Address, newItem: Address): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(oldItem: Address, newItem: Address): Boolean {
        return oldItem.id == newItem.id
    }
}