package com.example.digikalasample.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.digikalasample.R
import com.example.digikalasample.data.model.address.Address
import com.example.digikalasample.data.model.order.LineItem
import com.example.digikalasample.data.model.order.Order
import com.example.digikalasample.data.model.order.Shipping
import com.example.digikalasample.databinding.FragmentAddressesBinding
import com.example.digikalasample.ui.adapter.AddressAdapter
import com.example.digikalasample.viewmodel.AddressViewModel
import com.example.digikalasample.viewmodel.ProductViewModel

class AddressesFragment : Fragment() {
    private lateinit var binding: FragmentAddressesBinding
    val addressViewModel: AddressViewModel by activityViewModels()
    val productViewModel: ProductViewModel by activityViewModels()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentAddressesBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val adapter = AddressAdapter {
            onAddressesSelected(it)
        }
        binding.recyclerViewAddress.adapter = adapter
        addressViewModel.allAddresses?.observe(viewLifecycleOwner) {
            adapter.submitList(it)
        }

        binding.extendedFabInsertAddress.setOnClickListener {
            findNavController().navigate(R.id.action_addressesFragment_to_insertAddressFragment)
        }

    }

    fun onAddressesSelected(address: Address) {
        if (flagIsNavigateFromShoppingCart) {
            var itemsList = emptyList<LineItem>()
            for (product in productViewModel.shoppingCardList) {
                val lineItem = product?.id?.let { productId ->
                    product.count?.let { count ->
                        LineItem(
                            product_id = productId,
                            quantity = count,
                            name = product.name,
                        )
                    }
                }
                if (lineItem != null)
                    itemsList = itemsList.plus(lineItem)
            }
            val order = Order(
                customer_id = productViewModel.mCustomerId!!,
                line_items = itemsList,
                total = productViewModel.finalAmount.value.toString(),
                shipping = Shipping(
                    address_1 = address.addressField!!,
                    first_name = productViewModel.mCustomer.value!!.first_name,
                    last_name = productViewModel.mCustomer.value!!.last_name
                )
            )
            productViewModel.createOrder(
                order
            )
            productViewModel.shoppingCardList = emptyList()
            findNavController().navigate(R.id.action_addressesFragment_to_shoppingCartFragment)

        }
    }
}