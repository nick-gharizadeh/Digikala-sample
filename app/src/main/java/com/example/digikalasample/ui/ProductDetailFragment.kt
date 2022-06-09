package com.example.digikalasample.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.activityViewModels
import com.example.digikalasample.R
import com.example.digikalasample.databinding.FragmentProductDetailBinding
import com.example.digikalasample.ui.adapter.DetailViewPagerAdapter
import com.example.digikalasample.viewmodel.ProductViewModel

class ProductDetailFragment : Fragment() {
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
        val colors = resources.getStringArray(R.array.colors)
        val images = productViewModel.product?.images
        val mViewPagerAdapter: DetailViewPagerAdapter? =
            images?.let { DetailViewPagerAdapter(requireContext(), it) }
        binding.productDetailViewPager.adapter = mViewPagerAdapter
        binding.indicator.setViewPager(binding.productDetailViewPager)
        val adapter = ArrayAdapter(
            requireContext(),
            android.R.layout.simple_spinner_item, colors
        )
        binding.productColorSpinner.adapter = adapter

    }
}