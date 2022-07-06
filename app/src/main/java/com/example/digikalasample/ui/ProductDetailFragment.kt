package com.example.digikalasample.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.FragmentTransaction
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.digikalasample.R
import com.example.digikalasample.data.model.product.Product
import com.example.digikalasample.databinding.FragmentProductDetailBinding
import com.example.digikalasample.ui.adapter.ProductAdapter
import com.example.digikalasample.ui.adapter.ViewPagerAdapter
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
        val adapterRelatedProducts = ProductAdapter {
            goToDetailFragment(it)
        }
        binding.relatedProductRecyclerView.adapter = adapterRelatedProducts
        productViewModel.reviewsList.value = null
        productViewModel.relatedProductList.value = null
        productViewModel.getRelatedProducts(productViewModel.mProduct!!.related_ids)
        productViewModel.relatedProductList.observe(viewLifecycleOwner) {
            if (it != null)
                adapterRelatedProducts.submitList(it)
        }
        val colors = resources.getStringArray(R.array.colors)
        val images = productViewModel.mProduct?.images
        val mViewPagerAdapter: ViewPagerAdapter? =
            images?.let { ViewPagerAdapter(requireContext(), it) }
        binding.productDetailViewPager.adapter = mViewPagerAdapter
        binding.indicator.setViewPager(binding.productDetailViewPager)
        val adapter = ArrayAdapter(
            requireContext(),
            android.R.layout.simple_spinner_item, colors
        )
        binding.productColorSpinner.adapter = adapter
        binding.buttonAddToShoppingCart.setOnClickListener {
            productViewModel.addToShoppingCard()
        }



        binding.reviewsButton.setOnClickListener {
            productViewModel.getReviews(productViewModel.mProduct?.id.toString())
            findNavController().navigate(R.id.action_productDetailFragment_to_reviewFragment)
        }
    }

    private fun goToDetailFragment(product: Product) {
        product.description = RemoveHTMLTags.removeHTMLTagsFromString(product.description)
        productViewModel.mProduct = product
        val fragmentTransaction: FragmentTransaction? = activity?.supportFragmentManager?.beginTransaction()
        fragmentTransaction?.add(R.id.fragmentContainerView, ProductDetailFragment())
        fragmentTransaction?.addToBackStack("detail")
        fragmentTransaction?.commit()
    }


}