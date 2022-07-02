package com.example.digikalasample.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.digikalasample.R
import com.example.digikalasample.databinding.FragmentAddressesBinding

class AddressesFragment : Fragment() {
    private lateinit var binding: FragmentAddressesBinding


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentAddressesBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.extendedFabInsertAddress.setOnClickListener {
            findNavController().navigate(R.id.action_addressesFragment_to_insertAddressFragment)
        }
    }
}