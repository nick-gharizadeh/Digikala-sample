package com.example.digikalasample.ui.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.example.digikalasample.R
import com.example.digikalasample.data.model.Product

typealias ClickHandler = (Product)-> Unit
class ProductAdapter(private var clickHandler: ClickHandler) :
    ListAdapter<Product, ProductAdapter.ViewHolder>(ProductDiffCallback) {

    object ProductDiffCallback : DiffUtil.ItemCallback<Product>() {

        override fun areItemsTheSame(oldItem: Product, newItem: Product): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: Product, newItem: Product): Boolean {
            return oldItem.id == newItem.id
        }
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val textViewTitle: TextView = view.findViewById(R.id.product_title)
        val textViewPrice: TextView = view.findViewById(R.id.product_price)
        val imageViewCover: ImageView = itemView.findViewById(R.id.product_cover)
        val layout: LinearLayout = itemView.findViewById(R.id.product_item_layout)
        fun bind(product: Product) {
            if (product.images.isNotEmpty()) {
                Glide.with(itemView)
                    .load(product.images[0].src)
                    .transform(CenterCrop())
                    .placeholder(R.drawable.place_holder)
                    .into(imageViewCover)
            }
        }

    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.product_item_main, viewGroup, false)
        return ViewHolder(view)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val product = getItem(position)
        holder.layout.setOnClickListener {
            clickHandler.invoke(getItem(position))
        }
        holder.bind(product)
        holder.textViewTitle.text = product.name
        holder.textViewPrice.text =   product.price + " تومان"
    }
}