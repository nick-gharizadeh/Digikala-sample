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
import com.example.digikalasample.databinding.FragmentAddressesBinding
import com.example.digikalasample.ui.adapter.AddressAdapter
import com.example.digikalasample.ui.adapter.ProductAdapter
import com.example.digikalasample.ui.adapter.ShoppingCartAdapter
import com.example.digikalasample.viewmodel.AddressViewModel

class AddressesFragment : Fragment() {
    private lateinit var binding: FragmentAddressesBinding
    val addressViewModel: AddressViewModel by activityViewModels()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentAddressesBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val adapter =AddressAdapter()
        binding.recyclerViewAddress.adapter = adapter

        addressViewModel.insertAddress(
            Address(0,
            "name",
            "add",
            "30.65747",
            "33.0233")
        )
        addressViewModel.allAddresses?.observe(viewLifecycleOwner){
            adapter.submitList(it)
        }

        binding.extendedFabInsertAddress.setOnClickListener {
            findNavController().navigate(R.id.action_addressesFragment_to_insertAddressFragment)
        }

    }
}