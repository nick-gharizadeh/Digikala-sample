package com.example.digikalasample.ui

import android.os.Bundle
import android.view.*
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.digikalasample.R
import com.example.digikalasample.data.model.FilterItem
import com.example.digikalasample.data.model.product.Product
import com.example.digikalasample.databinding.FragmentSearchBinding
import com.example.digikalasample.ui.adapter.HorizontalProductAdaptor
import com.example.digikalasample.viewmodel.ProductViewModel


enum class FilterType {
    Size,
    Color
}

class SearchFragment : BaseFragment() {
    private lateinit var binding: FragmentSearchBinding
    val productViewModel: ProductViewModel by activityViewModels()
    var alert: android.app.AlertDialog? = null
    val filterColorList: List<FilterItem> =
        listOf(
            FilterItem(57, "آبی"),
            FilterItem(50, "سفید"),
            FilterItem(51, "صورتی"),
            FilterItem(59, "مرجانی"),
            FilterItem(49, "مشکی"),
        )
    val filterSizeList: List<FilterItem> =
        listOf(
            FilterItem(31, "M"),
            FilterItem(30, "L"),
            FilterItem(68, "XL"),
            FilterItem(69, "XXL"),
        )


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
            showSortAlertDialog()
        }

        binding.buttonFilter.setOnClickListener {
            showFilterTypeAlertDialog()
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

    private fun showSortAlertDialog() {
        val alertDialog: android.app.AlertDialog.Builder =
            android.app.AlertDialog.Builder(requireContext())
        alertDialog.setTitle("مرتب سازی بر اساس: ")
        val items =
            arrayOf("پیشنهاد خریداران", "محبوب ترین", "کمترین قیمت", "بیشترین قیمت", "جدیدترین")
        alertDialog.setSingleChoiceItems(
            items, -1
        ) { dialog, which ->
            when (which) {
                0 -> searchBySort("rating")
                1 -> searchBySort("popularity")
                2 -> searchBySort("price")
                3 -> searchBySort("price", "desc")
                4 -> searchBySort("date")
            }
        }
        alert = alertDialog.create()
        alert?.setCanceledOnTouchOutside(true)
        alert?.show()


    }

    private fun showFilterTypeAlertDialog() {
        val alertDialog: android.app.AlertDialog.Builder =
            android.app.AlertDialog.Builder(requireContext())
        alertDialog.setTitle("فیلتر بر اساس: ")
        val items = arrayOf(
            "سایز", "رنگ"
        )
        alertDialog.setSingleChoiceItems(
            items, -1
        ) { dialog, which ->
            when (which) {
                0 -> {
                    showFilterSizeAlertDialog()
                    dialog.dismiss()
                }
                1 -> {
                    showFilterColorAlertDialog()
                    dialog.dismiss()
                }
            }
        }
        alert = alertDialog.create()
        alert?.setCanceledOnTouchOutside(true)
        alert?.show()
    }

    private fun showFilterColorAlertDialog() {
        val alertDialog: android.app.AlertDialog.Builder =
            android.app.AlertDialog.Builder(requireContext())
        alertDialog.setTitle("فیلتر بر اساس: ")
        val items = arrayOf(
            filterColorList[0].name,
            filterColorList[1].name,
            filterColorList[2].name,
            filterColorList[3].name,
            filterColorList[4].name
        )
        alertDialog.setSingleChoiceItems(
            items, -1
        ) { dialog, which ->
            when (which) {
                0 -> searchByFilter(FilterType.Color, filterColorList[0])
                1 -> searchByFilter(FilterType.Color, filterColorList[1])
                2 -> searchByFilter(FilterType.Color, filterColorList[2])
                3 -> searchByFilter(FilterType.Color, filterColorList[3])
                4 -> searchByFilter(FilterType.Color, filterColorList[4])
            }
        }
        alert = alertDialog.create()
        alert?.setCanceledOnTouchOutside(true)
        alert?.show()
    }

    private fun showFilterSizeAlertDialog() {
        val alertDialog: android.app.AlertDialog.Builder =
            android.app.AlertDialog.Builder(requireContext())
        alertDialog.setTitle("فیلتر بر اساس: ")
        val items = arrayOf(
            filterSizeList[0].name,
            filterSizeList[1].name,
            filterSizeList[2].name,
            filterSizeList[3].name,
        )
        alertDialog.setSingleChoiceItems(
            items, -1
        ) { dialog, which ->
            when (which) {
                0 -> searchByFilter(FilterType.Size, filterSizeList[0])
                1 -> searchByFilter(FilterType.Size, filterSizeList[1])
                2 -> searchByFilter(FilterType.Size, filterSizeList[2])
                3 -> searchByFilter(FilterType.Size, filterSizeList[3])
            }
        }
        alert = alertDialog.create()
        alert?.setCanceledOnTouchOutside(true)
        alert?.show()
    }

    fun searchBySort(orderCriterion: String, order: String = "asc") {
        productViewModel.orderCriterion = orderCriterion
        productViewModel.orderSortType = order
        productViewModel.getProductsBySearch(
            productViewModel.lastSearch,
            productViewModel.orderCriterion!!,
            order
        )
        alert?.dismiss()
    }

    fun searchByFilter(filterType: FilterType, filterItem: FilterItem) {
        if (filterType == FilterType.Color) {
            productViewModel.doFilterByColor(filterItem)
        } else if (filterType == FilterType.Size) {
            productViewModel.doFilterBySize(filterItem)
        }
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