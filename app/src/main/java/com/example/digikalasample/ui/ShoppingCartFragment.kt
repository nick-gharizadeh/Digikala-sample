package com.example.digikalasample.ui

import android.annotation.SuppressLint
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import com.example.digikalasample.data.model.product.Product
import com.example.digikalasample.databinding.FragmentShoppingCartBinding
import com.example.digikalasample.ui.adapter.ShoppingCartAdapter
import com.example.digikalasample.viewmodel.ProductViewModel


lateinit var sharedPreferences: SharedPreferences

class ShoppingCartFragment : BaseFragment() {
    private lateinit var binding: FragmentShoppingCartBinding
    val productViewModel: ProductViewModel by activityViewModels()
    var adapter: ShoppingCartAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentShoppingCartBinding.inflate(layoutInflater)
        return binding.root
    }

    @SuppressLint("NotifyDataSetChanged")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        adapter = ShoppingCartAdapter { deleteFromShoppingCart(it) }
        binding.recyclerviewShoppingcard.adapter = adapter
        adapterSubmitList()
    }

    fun deleteFromShoppingCart(product: Product) {
        productViewModel.removeFromShoppingCard(product)
        adapterSubmitList()
    }

    fun adapterSubmitList() {
        adapter?.submitList(productViewModel.shoppingCardList)

    }
}