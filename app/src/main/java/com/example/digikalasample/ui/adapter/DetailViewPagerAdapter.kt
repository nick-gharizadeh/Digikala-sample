package com.example.digikalasample.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.viewpager.widget.PagerAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.example.digikalasample.R
import com.example.digikalasample.data.model.Image
import java.util.*

class DetailViewPagerAdapter(context: Context, images: List<Image>) :
    PagerAdapter() {
    var context: Context
    var images: List<Image>
    var mLayoutInflater: LayoutInflater
    override fun getCount(): Int {
        return images.size
    }

    override fun isViewFromObject(view: View, `object`: Any): Boolean {
        return view === `object` as LinearLayout
    }

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        val itemView: View = mLayoutInflater.inflate(R.layout.view_pager_item, container, false)
        val imageView: ImageView = itemView.findViewById(R.id.imageViewViewPager) as ImageView
        Glide.with(itemView)
            .load(images[position].src)
            .placeholder(R.drawable.place_holder)
            .into(imageView)
        Objects.requireNonNull(container).addView(itemView)
        return itemView
    }

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        container.removeView(`object` as LinearLayout)
    }
    init {
        this.context = context
        this.images = images
        mLayoutInflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
    }
}