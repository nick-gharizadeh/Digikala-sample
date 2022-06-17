package com.example.digikalasample.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.digikalasample.R
import com.example.digikalasample.data.model.Product
import com.example.digikalasample.databinding.FragmentProductsWithCategoryBinding
import com.example.digikalasample.ui.adapter.ProductWithCategoryAdaptor
import com.example.digikalasample.viewmodel.ProductViewModel


class ProductsWithCategoryFragment : BaseFragment() {
    private lateinit var binding: FragmentProductsWithCategoryBinding
    val productViewModel: ProductViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentProductsWithCategoryBinding.inflate(layoutInflater)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.animationView.alpha = 0f
        binding.animationView.animate().setDuration(900).alpha(1f).withEndAction {
            binding.animationView.visibility = View.GONE
            binding.recyclerviewProductWithCategory.visibility = View.VISIBLE
        }
        val adapter = ProductWithCategoryAdaptor { goToDetailFragment(it) }
        binding.recyclerviewProductWithCategory.adapter = adapter
        productViewModel.productByCategoriesList.observe(viewLifecycleOwner)
        {
            adapter.submitList(it)
        }


    }

    private fun goToDetailFragment(product: Product) {
        product.description = RemoveHTMLTags.removeHTMLTagsFromString(product.description)
        productViewModel.product = product
        findNavController().navigate(R.id.action_productsWithCategoryFragment_to_productDetailFragment)
    }
}