package com.example.digikalasample.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.example.digikalasample.R
import com.example.digikalasample.data.model.Product
import com.example.digikalasample.databinding.ProductItemCategoryBinding


typealias ClickHandlerProduct = (Product) -> Unit

class ProductWithCategoryAdaptor(private var clickHandler: ClickHandlerProduct) :
    ListAdapter<Product, ProductWithCategoryAdaptor.ItemHolder>(ProductAdapter.ProductDiffCallback) {
    class ItemHolder(val binding: ProductItemCategoryBinding) :
        RecyclerView.ViewHolder(binding.root) {
        val imageViewCover: ImageView = itemView.findViewById(R.id.product_image_category)
        fun bind(product:Product){
          if (product.images.isNotEmpty()) {
              Glide.with(itemView)
                  .load(product.images[0].src)
                  .placeholder(R.drawable.place_holder)
                  .into(imageViewCover)
          }
      }
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemHolder {
        val binding: ProductItemCategoryBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.product_item_category,
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


