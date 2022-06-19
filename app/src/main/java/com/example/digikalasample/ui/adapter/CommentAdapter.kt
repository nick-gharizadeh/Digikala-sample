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
import com.example.digikalasample.data.model.Comment
import com.example.digikalasample.databinding.CommentItemBinding


class CommentAdapter :
    ListAdapter<Comment, CommentAdapter.ItemHolder>(CommentDiffCallback) {

    object CommentDiffCallback : DiffUtil.ItemCallback<Comment>() {

        override fun areItemsTheSame(oldItem: Comment, newItem: Comment): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: Comment, newItem: Comment): Boolean {
            return oldItem.id == newItem.id
        }
    }

    class ItemHolder(val binding: CommentItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        private val imageViewAvatar: ImageView = itemView.findViewById(R.id.imageViewAvatar)
        fun bind(review: Comment) {
            Glide.with(itemView)
                .load(review.reviewer_avatar_urls.`96`)
                .placeholder(R.drawable.place_holder)
                .circleCrop()
                .into(imageViewAvatar)

        }
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemHolder {
        val binding: CommentItemBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.comment_item,
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

