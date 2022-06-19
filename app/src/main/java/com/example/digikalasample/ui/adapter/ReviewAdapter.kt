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
import com.example.digikalasample.data.model.Review
import com.example.digikalasample.databinding.ReviewItemBinding


class ReviewAdapter :
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
                .load(review.reviewer_avatar_urls.`96`)
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
        val comment = getItem(position)
        holder.binding.comment = comment
        holder.bind(comment)

    }
}

