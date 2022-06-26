package com.example.digikalasample.ui

import android.annotation.SuppressLint
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.digikalasample.R
import com.example.digikalasample.data.model.product.Product
import com.example.digikalasample.databinding.FragmentShoppingCartBinding
import com.example.digikalasample.ui.adapter.ShoppingCartAdapter
import com.example.digikalasample.viewmodel.ProductViewModel
import com.google.android.material.dialog.MaterialAlertDialogBuilder


lateinit var sharedPreferences: SharedPreferences

class ShoppingCartFragment : BaseFragment() {
    private lateinit var binding: FragmentShoppingCartBinding
    val productViewModel: ProductViewModel by activityViewModels()
    var adapter: ShoppingCartAdapter? = null


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentShoppingCartBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.buttonPostOrder.setOnClickListener {
            if (productViewModel.shoppingCardList.isNotEmpty()) {
                if (productViewModel.mCustomerId != null) {
                    productViewModel.createOrder(
                        productViewModel.mCustomerId!!,
                        productViewModel.finalAmount.value.toString()
                    )
//                    if (productViewModel.mOrder.value?.id != null) {
//                        val message =
//                            "کاربر گرامی سفارش شما با کد ${productViewModel.mOrder.value?.id} به ثبت رسید  "
//                        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
//                    }
                    productViewModel.shoppingCardList = emptyList()
                    adapterSubmitList()
                } else {
                    MaterialAlertDialogBuilder(requireContext())
                        .setMessage(getString(R.string.dialog_register_message))
                        .setPositiveButton(getString(R.string.register)) { dialog, which ->
                            findNavController().navigate(R.id.action_shoppingCartFragment_to_registerFragment)
                        }
                        .show()
                }
            } else {
                Toast.makeText(requireContext(), "سبد خرید خالی است!", Toast.LENGTH_SHORT).show()
            }
        }

        adapter = ShoppingCartAdapter({ deleteFromShoppingCart(it) }, { increase(it) }, {
            decrease(it)
        })
        binding.recyclerviewShoppingcard.adapter = adapter
        adapterSubmitList()
    }

    private fun deleteFromShoppingCart(product: Product) {
        productViewModel.removeFromShoppingCard(product)
        adapterSubmitList()
    }

    private fun increase(product: Product) {
        for (item in productViewModel.shoppingCardList) {
            if (item?.id == product.id) {
                product.count = product.count?.plus(1)
            }
        }
        setPrice()
    }

    private fun decrease(product: Product) {
        for (item in productViewModel.shoppingCardList) {
            if (item?.id == product.id) {
                if (product.count != 1)
                    product.count = product.count?.minus(1)
                else {
                    deleteFromShoppingCart(product)
                }
            }
        }
        setPrice()
    }

    private fun adapterSubmitList() {
        setPrice()
        adapter?.submitList(productViewModel.shoppingCardList)
    }

    @SuppressLint("SetTextI18n")
    fun setPrice() {
        productViewModel.calculatePrice()
        binding.textViewFinalAmount.text = productViewModel.finalAmount.value.toString() + " تومان"

    }
}