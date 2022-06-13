package com.example.digikalasample.ui

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.viewpager.widget.ViewPager.OnPageChangeListener
import com.example.digikalasample.R
import com.example.digikalasample.data.model.Category
import com.example.digikalasample.data.model.Product
import com.example.digikalasample.databinding.FragmentMainBinding
import com.example.digikalasample.ui.adapter.CategoryAdapter
import com.example.digikalasample.ui.adapter.DetailViewPagerAdapter
import com.example.digikalasample.ui.adapter.ProductAdapter
import com.example.digikalasample.viewmodel.ProductViewModel
import dagger.hilt.android.AndroidEntryPoint


var flagAnimationOnceShowed = false

@AndroidEntryPoint
class MainFragment : Fragment() {
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
        productViewModel.popularProductList.observe(viewLifecycleOwner) {
            if (it != null)
                adapterPopular.submitList(it)
        }

        productViewModel.newestProductList.observe(viewLifecycleOwner) {
            if (it != null)
                adapterNewest.submitList(it)
        }
        productViewModel.ratingProductList.observe(viewLifecycleOwner) {
            if (it != null)
                adapterRating.submitList(it)
        }
        productViewModel.categoriesList.observe(viewLifecycleOwner) {
            if (it != null)
                adapterCategories.submitList(it)
        }

        productViewModel.relatedProductById.observe(viewLifecycleOwner){
            val images = it?.images
            val mViewPagerAdapter: DetailViewPagerAdapter? =
                images?.let { DetailViewPagerAdapter(requireContext(), it) }
            binding.mainViewPager.adapter = mViewPagerAdapter
            binding.indicator.setViewPager(binding.mainViewPager)

        }

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
            R.id.item_menu_search-> {

                return false
            }
            R.id.item_menu_shopping_cart-> {

                return false
            }
        }
        return false
    }
}
