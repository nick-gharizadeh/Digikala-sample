package com.example.digikalasample.ui

import android.content.ClipData
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import com.example.digikalasample.R
import com.example.digikalasample.data.model.FilterItem
import com.example.digikalasample.databinding.FragmentFilterBinding
import com.example.digikalasample.databinding.FragmentMainBinding
import com.example.digikalasample.ui.adapter.FilterItemAdapter
import com.example.digikalasample.ui.adapter.ShoppingCartAdapter
import com.example.digikalasample.viewmodel.ProductViewModel

class FilterFragment : Fragment() {
    private lateinit var binding: FragmentFilterBinding
    val productViewModel: ProductViewModel by activityViewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentFilterBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val filterColorList : List<FilterItem> =
            listOf(
                FilterItem(57,"آبی" ),
                FilterItem(50,"سفید" ),
                FilterItem(51,"صورتی" ),
                FilterItem(59,"مرجانی" ),
                FilterItem(49,"مشکی" ),
            )
       val adapter = FilterItemAdapter{productViewModel.addToFilterItemList(it)}
        binding.recyclerViewColor.adapter = adapter
        adapter.submitList(filterColorList)

    }



}