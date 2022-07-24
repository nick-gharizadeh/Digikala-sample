package com.example.digikalasample.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.example.digikalasample.R
import com.example.digikalasample.data.RemoveHTMLTags
import com.example.digikalasample.data.model.product.Product
import com.example.digikalasample.databinding.FragmentProductsBinding
import com.example.digikalasample.ui.adapter.HorizontalProductAdaptor
import com.example.digikalasample.viewmodel.ProductViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch


class ProductsFragment : Fragment() {
    private lateinit var binding: FragmentProductsBinding
    val productViewModel: ProductViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentProductsBinding.inflate(layoutInflater)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.animationView.animate().setDuration(3000).alpha(1f).withEndAction {
            changeVisibilityOfLoaderAnimation()
        }
        val adapter = HorizontalProductAdaptor {
            goToDetailFragment(it)
        }
        binding.recyclerviewProducts.adapter = adapter

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                productViewModel.productsList.collect {
                    adapter.submitList(it)
                }
            }
        }

    }

    private fun changeVisibilityOfLoaderAnimation() {
        binding.animationView.visibility = View.GONE
        binding.recyclerviewProducts.visibility = View.VISIBLE
    }

    private fun goToDetailFragment(product: Product) {
        product.description = RemoveHTMLTags.removeHTMLTagsFromString(product.description)
        productViewModel.mProduct = product
        findNavController().navigate(R.id.action_productsFragment_to_productDetailFragment)
    }
}