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

typealias ClickHandlerAddress = (Address) -> Unit
typealias ClickHandlerEditAddress = (Address) -> Unit

class AddressAdapter(
    private var clickHandler: ClickHandlerAddress,
    private var clickHandlerEdit: ClickHandlerEditAddress
) :
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
        holder.binding.cvAddress.setOnClickListener {
            clickHandler.invoke(address)
        }
        holder.binding.imageViewEditAddress.setOnClickListener {
            clickHandlerEdit.invoke(address)
        }

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