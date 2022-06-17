package com.example.digikalasample.ui

import android.os.Bundle
import android.view.*
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.digikalasample.R
import com.example.digikalasample.data.model.Product
import com.example.digikalasample.databinding.FragmentSearchBinding
import com.example.digikalasample.ui.adapter.ProductWithCategoryAdaptor
import com.example.digikalasample.viewmodel.ProductViewModel


class SearchFragment : BaseFragment() {
    private lateinit var binding: FragmentSearchBinding
    val productViewModel: ProductViewModel by activityViewModels()
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
        val adapter = ProductWithCategoryAdaptor { goToDetailFragment(it) }
        binding.recyclerViewSearch.adapter = adapter
        productViewModel.searchedProductsList.observe(viewLifecycleOwner)
        {
            adapter.submitList(it)
        }


    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.search_menu, menu)
        val searchItem = menu.findItem(R.id.item_menu_search_fragment)
        val searchView = searchItem.actionView as SearchView
        searchView.onQueryTextChanged {
            productViewModel.getProductsBySearch(it)
        }
        super.onCreateOptionsMenu(menu, inflater)
    }

    private fun goToDetailFragment(product: Product) {
        product.description = RemoveHTMLTags.removeHTMLTagsFromString(product.description)
        productViewModel.product = product
        findNavController().navigate(R.id.action_searchFragment_to_productDetailFragment)
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