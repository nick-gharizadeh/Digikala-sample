package com.example.digikalasample.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import com.example.digikalasample.databinding.FragmentFilterBinding
import com.example.digikalasample.ui.adapter.FilterItemAdapter
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

       val adapter = FilterItemAdapter{productViewModel.doFilterByColor(it)}
        binding.recyclerViewColor.adapter = adapter


    }



}