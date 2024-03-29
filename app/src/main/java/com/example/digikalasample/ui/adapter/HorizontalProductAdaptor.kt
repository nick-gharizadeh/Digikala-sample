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
import com.example.digikalasample.databinding.ProductItemHorizantalBinding


typealias ClickHandlerProduct = (Product) -> Unit

class HorizontalProductAdaptor(private var clickHandler: ClickHandlerProduct) :
    ListAdapter<Product, HorizontalProductAdaptor.ItemHolder>(ProductAdapter.ProductDiffCallback) {
    class ItemHolder(val binding: ProductItemHorizantalBinding) :
        RecyclerView.ViewHolder(binding.root) {
        private val imageViewCover: ImageView = itemView.findViewById(R.id.product_image_category)
        fun bind(product: Product) {
            if (product.images.isNotEmpty()) {
                Glide.with(itemView)
                    .load(product.images[0].src)
                    .placeholder(R.drawable.place_holder)
                    .into(imageViewCover)
            }
        }
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemHolder {
        val binding: ProductItemHorizantalBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.product_item_horizantal,
            parent, false
        )
        return ItemHolder(binding)

    }

    override fun onBindViewHolder(holder: ItemHolder, position: Int) {
        val product = getItem(position)
        holder.binding.product = product
        holder.bind(product)
        holder.binding.itemLayoutProductCategory.setOnClickListener {
            clickHandler.invoke(product)
        }


    }
}


