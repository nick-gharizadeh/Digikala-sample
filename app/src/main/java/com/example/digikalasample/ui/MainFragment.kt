package com.example.digikalasample.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.example.digikalasample.databinding.FragmentMainBinding
import com.example.digikalasample.ui.adapter.ProductAdapter
import com.example.digikalasample.viewmodel.ProductViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainFragment : Fragment() {
    lateinit var binding: FragmentMainBinding
    val viewModel: ProductViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMainBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val adapterPopular = ProductAdapter()
        val adapterRating = ProductAdapter()
        val adapterNewest = ProductAdapter()
        binding.mostViewRecyclerView.adapter = adapterPopular
        binding.newestRecyclerView.adapter = adapterNewest
        binding.bestSellRecyclerView.adapter = adapterRating
        viewModel.popularProductList.observe(viewLifecycleOwner) {
            if (it != null)
                adapterPopular.submitList(it)
        }

        viewModel.newestProductList.observe(viewLifecycleOwner) {
            if (it != null)
                adapterNewest.submitList(it)
        }
        viewModel.ratingProductList.observe(viewLifecycleOwner) {
            if (it != null)
                adapterRating.submitList(it)
        }

    }
}