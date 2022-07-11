package com.example.digikalasample.ui

import android.content.res.Configuration
import android.os.Bundle
import android.view.*
import androidx.appcompat.app.AppCompatDelegate
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.example.digikalasample.R
import com.example.digikalasample.data.model.product.Category
import com.example.digikalasample.data.model.product.Product
import com.example.digikalasample.databinding.FragmentMainBinding
import com.example.digikalasample.ui.adapter.CategoryAdapter
import com.example.digikalasample.ui.adapter.ProductAdapter
import com.example.digikalasample.ui.adapter.ViewPagerAdapter
import com.example.digikalasample.viewmodel.ProductViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch


var flagAnimationOnceShowed = false
var flagOnceDataSet = false

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
        flagIsNavigateFromShoppingCart = false
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


        productViewModel.mCustomer.observe(viewLifecycleOwner) {
            val editor = sharedPreferences.edit()
            it?.id?.let { it1 -> editor.putInt("CustomerId", it1) }
            editor.apply()
            productViewModel.mCustomerId = it?.id
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
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                productViewModel.popularProductList.collect {
                    adapterPopular.submitList(it)
                }
            }
        }

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                productViewModel.newestProductList.collect {
                    adapterNewest.submitList(removeWrongElement(it))
                }
            }
        }

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                productViewModel.ratingProductList.collect {
                    adapterRating.submitList(it)
                }
            }
        }


        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                productViewModel.categoriesList.collect {
                    adapterCategories.submitList(it)
                }
            }
        }

        productViewModel.specialOffers.observe(viewLifecycleOwner) { it ->
            val images = it?.images
            val mViewPagerAdapter: ViewPagerAdapter? =
                images?.let { ViewPagerAdapter(requireContext(), it) }
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
        productViewModel.mProduct = product
        findNavController().navigate(R.id.action_mainFragment_to_productDetailFragment)
    }

    private fun goToProductsWithCategoryFragment(category: Category) {
        productViewModel.productByCategoriesList.value = null
        productViewModel.getProductsByCategory(category.id)
        findNavController().navigate(R.id.action_mainFragment_to_productsWithCategoryFragment)
    }


    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.main_menu, menu)
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
            R.id.item_menu_addresses -> {
                findNavController().navigate(R.id.action_mainFragment_to_addressesFragment)
                return false
            }
            R.id.item_menu_settings -> {
                findNavController().navigate(R.id.action_mainFragment_to_changeThemeFragment)
                return false
            }
            R.id.item_menu_account -> {
                findNavController().navigate(R.id.action_mainFragment_to_accountFragment)
                return false
            }
        }
        return false
    }

    override fun onStop() {
        if (productViewModel.shoppingCardList.isNotEmpty()) {
            val editor = sharedPreferences.edit()
            var shoppingCartString: String? = ""
            var shoppingCartCountString: String? = ""
            for (product in productViewModel.shoppingCardList) {
                shoppingCartString += "${product?.id.toString()};"
                shoppingCartCountString += "${product?.count.toString()};"

            }
            editor.putString("shoppingCartString", shoppingCartString)
            editor.putString("shoppingCartCountString", shoppingCartCountString)
            editor.apply()
        } else {
            sharedPreferences.edit().remove("shoppingCartString").apply()
            sharedPreferences.edit().remove("shoppingCartCountString").apply()
        }
        super.onStop()
    }
}
