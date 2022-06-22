package com.example.digikalasample.ui

import android.os.Bundle
import android.view.*
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.digikalasample.R
import com.example.digikalasample.data.model.product.Category
import com.example.digikalasample.data.model.product.Product
import com.example.digikalasample.databinding.FragmentMainBinding
import com.example.digikalasample.ui.adapter.CategoryAdapter
import com.example.digikalasample.ui.adapter.DetailViewPagerAdapter
import com.example.digikalasample.ui.adapter.ProductAdapter
import com.example.digikalasample.viewmodel.ProductViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch


var flagAnimationOnceShowed = false
var flagIsDataSetFromShared = false

@AndroidEntryPoint
class MainFragment : BaseFragment() {
    private lateinit var binding: FragmentMainBinding
    val productViewModel: ProductViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMainBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setHasOptionsMenu(true)
        if (!flagAnimationOnceShowed) {
            binding.animationView.alpha = 0f
            binding.animationView.animate().setDuration(1500).alpha(1f).withEndAction {
                flagAnimationOnceShowed = true
                binding.animationView.visibility = View.GONE
                binding.mainLayout.visibility = View.VISIBLE
            }
        } else {
            binding.animationView.visibility = View.GONE
            binding.mainLayout.visibility = View.VISIBLE
        }


        val adapterPopular = ProductAdapter {
            goToDetailFragment(it)
        }
        val adapterRating = ProductAdapter {
            goToDetailFragment(it)
        }
        val adapterNewest = ProductAdapter {
            goToDetailFragment(it)
        }
        val adapterCategories = CategoryAdapter { goToProductsWithCategoryFragment(it) }
        binding.mostViewRecyclerView.adapter = adapterPopular
        binding.newestRecyclerView.adapter = adapterNewest
        binding.bestSellRecyclerView.adapter = adapterRating
        binding.categoryRecyclerView.adapter = adapterCategories

        viewLifecycleOwner.lifecycleScope.launch {
            productViewModel.popularProductList.collect {
                adapterPopular.submitList(it)
            }
        }

        viewLifecycleOwner.lifecycleScope.launch {
            productViewModel.newestProductList.collect {
                adapterNewest.submitList(removeWrongElement(it))
            }
        }

        viewLifecycleOwner.lifecycleScope.launch {
            productViewModel.ratingProductList.collect {
                adapterRating.submitList(it)
            }
        }


        viewLifecycleOwner.lifecycleScope.launch {
            productViewModel.categoriesList.collect {
                adapterCategories.submitList(it)
            }
        }

        productViewModel.specialProduct.observe(viewLifecycleOwner) {
            val images = it?.images
            val mViewPagerAdapter: DetailViewPagerAdapter? =
                images?.let { DetailViewPagerAdapter(requireContext(), it) }
            binding.mainViewPager.adapter = mViewPagerAdapter
            binding.indicator.setViewPager(binding.mainViewPager)

        }

    }

    private fun removeWrongElement(list: List<Product?>): List<Product?> {
        list.forEach { product ->
            if (product?.id == 608)
                return list.minus(product)
        }
        return listOf()
    }

    private fun goToDetailFragment(product: Product) {
        product.description = RemoveHTMLTags.removeHTMLTagsFromString(product.description)
        productViewModel.product = product
        findNavController().navigate(R.id.action_mainFragment_to_productDetailFragment)
    }

    private fun goToProductsWithCategoryFragment(category: Category) {
        productViewModel.productByCategoriesList.value = null
        productViewModel.getProductsByCategory(category.id)
        findNavController().navigate(R.id.action_mainFragment_to_productsWithCategoryFragment)
    }


    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.main_menu, menu);
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.item_menu_search -> {
                findNavController().navigate(R.id.action_mainFragment_to_searchFragment)
                return false
            }
            R.id.item_menu_shopping_cart -> {
                findNavController().navigate(R.id.action_mainFragment_to_shoppingCartFragment)
                return false
            }
            R.id.item_menu_register -> {
                findNavController().navigate(R.id.action_mainFragment_to_registerFragment)
                return false
            }
        }
        return false
    }


}
