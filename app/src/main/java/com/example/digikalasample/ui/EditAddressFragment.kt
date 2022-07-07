package com.example.digikalasample.ui

import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.digikalasample.R
import com.example.digikalasample.data.model.address.Address
import com.example.digikalasample.databinding.FragmentEditAddressBinding
import com.example.digikalasample.viewmodel.AddressViewModel
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions


class EditAddressFragment : Fragment() {
    private lateinit var binding: FragmentEditAddressBinding
    private val addressViewModel: AddressViewModel by activityViewModels()
    private lateinit var map: GoogleMap
    var currentMarker: Marker? = null
    private val args: EditAddressFragmentArgs by navArgs()
    lateinit var address: Address


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentEditAddressBinding.inflate(layoutInflater)
        return binding.root
    }

    @RequiresApi(Build.VERSION_CODES.N)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        address = args.safeArgAddress
        binding.TextInputAddressName2.editText?.setText(address.name)
        binding.TextInputAddressField2.editText?.setText(address.addressField)
        binding.buttonEditAddress.setOnClickListener {
            val newAddress = Address(
                address.id,
                binding.TextInputAddressName2.editText?.text.toString(),
                binding.TextInputAddressField2.editText?.text.toString(),
                currentMarker?.position?.latitude.toString(),
                currentMarker?.position?.longitude.toString()
            )
            addressViewModel.updateAddress(newAddress)
            findNavController().navigate(R.id.action_editAddressFragment_to_addressesFragment)
        }
        binding.buttonDeleteAddress.setOnClickListener {
            addressViewModel.deleteAddress(address)
            findNavController().navigate(R.id.action_editAddressFragment_to_addressesFragment)

        }
        val mapFragment = childFragmentManager
            .findFragmentById(R.id.myMapEdit) as SupportMapFragment
        mapFragment.getMapAsync { readyMap ->
            map = readyMap
            showLocationOnMap(LatLng(address.theLat.toDouble(), address.theLong.toDouble()))
            map.setOnMarkerDragListener(object : GoogleMap.OnMarkerDragListener {
                override fun onMarkerDrag(p0: Marker) {

                }

                override fun onMarkerDragEnd(p0: Marker) {
                    if (currentMarker != null) {
                        currentMarker?.remove()
                    }
                    val newLatLong = LatLng(p0.position.latitude, p0.position.longitude)
                    showLocationOnMap(newLatLong)
                }

                override fun onMarkerDragStart(p0: Marker) {
                }

            })
        }

    }


    private fun showLocationOnMap(latLng: LatLng) {
        map.setMinZoomPreference(2.0f)
        map.setMaxZoomPreference(18.0f)
        map.cameraPosition.zoom
        val markerOption =
            MarkerOptions()
                .position(latLng)
                .draggable(true)
                .zIndex(2.0f)
                .draggable(true)

        map.moveCamera(CameraUpdateFactory.newLatLng(latLng))
        map.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 20.0f))
        currentMarker = map.addMarker(markerOption)
        currentMarker?.showInfoWindow()

    }
}