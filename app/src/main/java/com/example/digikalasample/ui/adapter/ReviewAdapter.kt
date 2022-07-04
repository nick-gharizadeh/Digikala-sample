package com.example.digikalasample.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.digikalasample.R
import com.example.digikalasample.data.model.review.Review
import com.example.digikalasample.databinding.ReviewItemBinding

typealias ClickHandlerDeleteReview = (Review) -> Unit

class ReviewAdapter(private var clickHandlerDelete: ClickHandlerDeleteReview) :
    ListAdapter<Review, ReviewAdapter.ItemHolder>(ReviewDiffCallback) {

    object ReviewDiffCallback : DiffUtil.ItemCallback<Review>() {

        override fun areItemsTheSame(oldItem: Review, newItem: Review): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: Review, newItem: Review): Boolean {
            return oldItem.id == newItem.id
        }
    }

    class ItemHolder(val binding: ReviewItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        private val imageViewAvatar: ImageView = itemView.findViewById(R.id.imageViewAvatar)
        fun bind(review: Review) {
            Glide.with(itemView)
                .load(review.reviewer_avatar_urls.`48`)
                .placeholder(R.drawable.place_holder)
                .circleCrop()
                .into(imageViewAvatar)

        }
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemHolder {
        val binding: ReviewItemBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.review_item,
            parent, false
        )
        return ItemHolder(binding)

    }

    override fun onBindViewHolder(holder: ItemHolder, position: Int) {
        val review = getItem(position)
        holder.binding.review = review
        holder.bind(review)
        holder.binding.imageViewDelete.setOnClickListener {
            clickHandlerDelete.invoke(review)
        }
    }
}

