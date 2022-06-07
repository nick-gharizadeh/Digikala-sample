package com.example.digikalasample.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.GridLayoutManager
import com.example.digikalasample.R
import com.example.digikalasample.databinding.FragmentMainBinding
import com.example.digikalasample.databinding.FragmentProductsWithCategoryBinding
import com.example.digikalasample.ui.adapter.ProductAdapter
import com.example.digikalasample.viewmodel.ProductViewModel


class ProductsWithCategoryFragment : Fragment() {
    lateinit var binding: FragmentProductsWithCategoryBinding
    val productViewModel: ProductViewModel by activityViewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentProductsWithCategoryBinding.inflate(layoutInflater)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val adapter = ProductAdapter { }
        val numberOfColumns = 3
        binding.recyclerviewProductGrid.layoutManager = GridLayoutManager(
            context,
            numberOfColumns
        )
        binding.recyclerviewProductGrid.adapter = adapter
        productViewModel.productByCategoriesList.observe(viewLifecycleOwner)
        {
            adapter.submitList(it)
        }


    }
}