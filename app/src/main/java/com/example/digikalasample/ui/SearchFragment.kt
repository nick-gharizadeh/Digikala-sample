package com.example.digikalasample.ui

import android.os.Bundle
import android.view.*
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.digikalasample.R
import com.example.digikalasample.data.model.product.Product
import com.example.digikalasample.databinding.FragmentSearchBinding
import com.example.digikalasample.ui.adapter.HorizontalProductAdaptor
import com.example.digikalasample.viewmodel.ProductViewModel


class SearchFragment : BaseFragment() {
    private lateinit var binding: FragmentSearchBinding
    val productViewModel: ProductViewModel by activityViewModels()
    var alert: android.app.AlertDialog? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        setHasOptionsMenu(true)
        binding = FragmentSearchBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val adapter = HorizontalProductAdaptor { goToDetailFragment(it) }
        binding.recyclerViewSearch.adapter = adapter
        productViewModel.searchedProductsList.observe(viewLifecycleOwner)
        {
            adapter.submitList(it)
        }
        binding.buttonSort.setOnClickListener {
            showAlertDialog()
        }

        binding.buttonFilter.setOnClickListener {
            findNavController().navigate(R.id.action_searchFragment_to_filterFragment)
        }

    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.search_menu, menu)
        val searchItem = menu.findItem(R.id.item_menu_search_fragment)
        val searchView = searchItem.actionView as SearchView
        searchView.onQueryTextChanged {
            productViewModel.lastSearch = it
            productViewModel.getProductsBySearch(it)
        }
        super.onCreateOptionsMenu(menu, inflater)
    }

    private fun goToDetailFragment(product: Product) {
        product.description = RemoveHTMLTags.removeHTMLTagsFromString(product.description)
        productViewModel.mProduct = product
        findNavController().navigate(R.id.action_searchFragment_to_productDetailFragment)
    }

    private fun showAlertDialog() {
        val alertDialog: android.app.AlertDialog.Builder =
            android.app.AlertDialog.Builder(requireContext())
        alertDialog.setTitle("مرتب سازی بر اساس: ")
        val items =
            arrayOf("پیشنهاد خریداران", "محبوب ترین", "کمترین قیمت", "بیشترین قیمت", "جدیدترین")
        val checkedItem = 1
        alertDialog.setSingleChoiceItems(
            items, checkedItem
        ) { dialog, which ->
            when (which) {
                0 -> setVariableToSort("rating")
                1 -> setVariableToSort("popularity")
                2 -> setVariableToSort("price")
                3 -> setVariableToSort("price", "desc")
                4 -> setVariableToSort("date")
            }
        }
        alert = alertDialog.create()
        alert?.setCanceledOnTouchOutside(true)
        alert?.show()


    }

    fun setVariableToSort(orderCriterion: String, order: String = "asc") {
        productViewModel.orderCriterion = orderCriterion
        productViewModel.orderSortType = order
        productViewModel.getProductsBySearch(productViewModel.lastSearch, productViewModel.orderCriterion!!, order)
        alert?.dismiss()
    }

}

inline fun SearchView.onQueryTextChanged(crossinline listener: (String) -> Unit) {
    this.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
        override fun onQueryTextSubmit(query: String?): Boolean {
            return true
        }

        override fun onQueryTextChange(newText: String?): Boolean {
            listener(newText.orEmpty())
            return true
        }

    })
}