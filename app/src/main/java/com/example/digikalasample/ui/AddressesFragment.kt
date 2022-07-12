package com.example.digikalasample.ui

import android.content.Context
import android.content.Intent
import android.location.LocationManager
import android.os.Bundle
import android.provider.Settings
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.location.LocationManagerCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.digikalasample.R
import com.example.digikalasample.data.model.address.Address
import com.example.digikalasample.data.model.order.Billing
import com.example.digikalasample.data.model.order.LineItem
import com.example.digikalasample.data.model.order.Order
import com.example.digikalasample.databinding.FragmentAddressesBinding
import com.example.digikalasample.ui.adapter.AddressAdapter
import com.example.digikalasample.viewmodel.AddressViewModel
import com.example.digikalasample.viewmodel.ProductViewModel


class AddressesFragment : Fragment() {
    private lateinit var binding: FragmentAddressesBinding
    private val addressViewModel: AddressViewModel by activityViewModels()
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
        val adapter = AddressAdapter({ onAddressesSelected(it) },
            { goToEditAddressFragment(it) },
            { goToMapFragment(it) })

        binding.recyclerViewAddress.adapter = adapter
        addressViewModel.allAddresses?.observe(viewLifecycleOwner) {
            adapter.submitList(it)
        }

        binding.extendedFabInsertAddress.setOnClickListener {
            if (!isLocationEnabled()) {
                Toast.makeText(
                    requireContext(),
                    "برای افزودن نشانی، لطفا GPS خود را روشن کنید",
                    Toast.LENGTH_SHORT
                ).show()
                loadGPSPageSetting()
            } else
                findNavController().navigate(R.id.action_addressesFragment_to_insertAddressFragment)
        }

    }

    private fun loadGPSPageSetting() {
        val intent = Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS)
        startActivity(intent)
    }

    private fun onAddressesSelected(address: Address) {
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
            productViewModel.calculatePrice()
            val order = Order(
                customer_id = productViewModel.mCustomerId!!,
                line_items = itemsList,
                billing = Billing(
                    address_1 = address.addressField!!,
                    first_name = productViewModel.mCustomer.value!!.first_name,
                    last_name = productViewModel.mCustomer.value!!.last_name
                ), coupon_lines = productViewModel.usedCouponList
            )
            productViewModel.createOrder(
                order
            )
            productViewModel.usedCouponList = emptyList()
            productViewModel.couponAmount = 0
            productViewModel.shoppingCardList = emptyList()
            findNavController().navigate(R.id.action_addressesFragment_to_shoppingCartFragment)

        }
    }

    private fun isLocationEnabled(): Boolean {
        val locationManager =
            activity?.getSystemService(Context.LOCATION_SERVICE) as LocationManager
        return LocationManagerCompat.isLocationEnabled(locationManager)
    }

    private fun goToEditAddressFragment(address: Address) {
        if (!isLocationEnabled()) {
            Toast.makeText(
                requireContext(),
                "برای افزودن نشانی، لطفا GPS خود را روشن کنید",
                Toast.LENGTH_SHORT
            ).show()
            loadGPSPageSetting()
        } else {
            val action =
                AddressesFragmentDirections.actionAddressesFragmentToEditAddressFragment(address)
            findNavController().navigate(action)
        }
    }

    private fun goToMapFragment(address: Address) {
        val action =
            AddressesFragmentDirections.actionAddressesFragmentToMapFragment(address)
        findNavController().navigate(action)
    }
}