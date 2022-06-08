package com.example.digikalasample.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.activityViewModels
import androidx.viewpager.widget.ViewPager
import com.example.digikalasample.R
import com.example.digikalasample.databinding.FragmentMainBinding
import com.example.digikalasample.databinding.FragmentProductDetailBinding
import com.example.digikalasample.ui.adapter.DetailViewPagerAdapter
import com.example.digikalasample.viewmodel.ProductViewModel
import me.relex.circleindicator.CircleIndicator

class ProductDetailFragment : Fragment() {
    lateinit var binding: FragmentProductDetailBinding
    val productViewModel: ProductViewModel by activityViewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

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

        val images = productViewModel.product?.images
        var mViewPagerAdapter: DetailViewPagerAdapter? = null

        mViewPagerAdapter = images?.let { DetailViewPagerAdapter(requireContext(), it) }
        binding.productDetailViewPager.adapter = mViewPagerAdapter
        binding.indicator.setViewPager(binding.productDetailViewPager)
    }
}