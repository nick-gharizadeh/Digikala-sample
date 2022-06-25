package com.example.digikalasample.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.activityViewModels
import com.example.digikalasample.R
import com.example.digikalasample.databinding.FragmentProductDetailBinding
import com.example.digikalasample.ui.adapter.DetailViewPagerAdapter
import com.example.digikalasample.ui.adapter.ReviewAdapter
import com.example.digikalasample.viewmodel.ProductViewModel

class ProductDetailFragment : BaseFragment() {
    private lateinit var binding: FragmentProductDetailBinding
    val productViewModel: ProductViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_product_detail, container, false
        )
        binding.productViewModel = productViewModel
        binding.lifecycleOwner = this.viewLifecycleOwner
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        productViewModel.reviewsList.value = null
        val colors = resources.getStringArray(R.array.colors)
        val images = productViewModel.mProduct?.images
        val mViewPagerAdapter: DetailViewPagerAdapter? =
            images?.let { DetailViewPagerAdapter(requireContext(), it) }
        binding.productDetailViewPager.adapter = mViewPagerAdapter
        binding.indicator.setViewPager(binding.productDetailViewPager)
        val adapter = ArrayAdapter(
            requireContext(),
            android.R.layout.simple_spinner_item, colors
        )
        val reviewAdapter = ReviewAdapter()
        binding.productColorSpinner.adapter = adapter
        binding.recyclerViewComments.adapter = reviewAdapter
        binding.buttonAddToShoppingCart.setOnClickListener {
            productViewModel.addToShoppingCard()
        }

        productViewModel.reviewsList.observe(viewLifecycleOwner) {
            if (it != null)
                for (review in it) {
                    review?.review = review?.review?.let { it1 ->
                        RemoveHTMLTags.removeHTMLTagsFromString(
                            it1
                        )
                    }.toString()
                }
            reviewAdapter.submitList(it)
        }


        binding.detailButton.setOnClickListener {
            changeVisibilities(showReviews = false)
        }

        binding.reviewsButton.setOnClickListener {
            productViewModel.getReviews(productViewModel.mProduct?.id.toString())
            changeVisibilities(showReviews = true)

        }
    }

    private fun changeVisibilities(showReviews: Boolean) {
        if (showReviews) {
            binding.linearLayoutRecyclerView.visibility = View.VISIBLE
            binding.CardView1.visibility = View.GONE
            binding.CardView2.visibility = View.GONE
        } else {
            binding.linearLayoutRecyclerView.visibility = View.GONE
            binding.CardView1.visibility = View.VISIBLE
            binding.CardView2.visibility = View.VISIBLE
        }
    }


}