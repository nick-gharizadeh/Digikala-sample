package com.example.digikalasample.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.digikalasample.R
import com.example.digikalasample.data.model.product.Product
import com.example.digikalasample.databinding.ShoppingCartItemBinding

typealias ClickHandlerDelete = (Product) -> Unit


class ShoppingCartAdapter(private var clickHandlerDelete: ClickHandlerDelete) :
    ListAdapter<Product, ShoppingCartAdapter.ItemHolder>(ProductAdapter.ProductDiffCallback) {


    class ItemHolder(val binding: ShoppingCartItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        private val imageViewProduct: ImageView = itemView.findViewById(R.id.shopping_cart_product_image)
        fun bind(product: Product) {
            Glide.with(itemView)
                .load(product.images[0].src)
                .placeholder(R.drawable.place_holder)
                .into(imageViewProduct)

        }
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemHolder {
        val binding: ShoppingCartItemBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.shopping_cart_item,
            parent, false
        )
        return ItemHolder(binding)

    }

    override fun onBindViewHolder(holder: ItemHolder, position: Int) {
        val product = getItem(position)
        holder.binding.product = product
        holder.bind(product)
        holder.binding.buttonDelete.setOnClickListener {
            clickHandlerDelete.invoke(product)
        }

    }
}