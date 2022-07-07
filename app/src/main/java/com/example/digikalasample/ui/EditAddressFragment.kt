package com.example.digikalasample.ui

import android.Manifest
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.digikalasample.R
import com.example.digikalasample.data.model.address.Address
import com.example.digikalasample.databinding.FragmentEditAddressBinding
import com.example.digikalasample.viewmodel.AddressViewModel
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
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
    private lateinit var fusedLocationClient: FusedLocationProviderClient
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
        getLocationPermission()
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
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(requireActivity())
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


    @RequiresApi(Build.VERSION_CODES.N)
    private fun getLocationPermission() {
        val locationPermissionRequest =
            registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) { permissions ->
                when {
                    permissions.getOrDefault(Manifest.permission.ACCESS_FINE_LOCATION, false) -> {
                    }
                    else -> {
                        Toast.makeText(
                            requireContext(),
                            "برای ویرایش نشانی، دسترسی به مکان را فعال کنید",
                            Toast.LENGTH_SHORT
                        ).show()
                        val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
                        val uri: Uri = Uri.fromParts(
                            "package",
                            activity?.applicationContext?.packageName,
                            null
                        )
                        intent.data = uri
                        startActivity(intent)
                        findNavController().navigate(R.id.action_editAddressFragment_to_addressesFragment)
                    }
                }
            }
        locationPermissionRequest.launch(
            arrayOf(
                Manifest.permission.ACCESS_FINE_LOCATION,
            )
        )
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