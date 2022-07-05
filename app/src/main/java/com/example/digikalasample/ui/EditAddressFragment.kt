package com.example.digikalasample.ui

import android.Manifest
import android.annotation.SuppressLint
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Location
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
import androidx.core.app.ActivityCompat
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
import com.google.android.gms.location.Priority
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions


class EditAddressFragment : Fragment() {
    private lateinit var binding: FragmentEditAddressBinding
    val addressViewModel: AddressViewModel by activityViewModels()
    private lateinit var map: GoogleMap
    private lateinit var fusedLocationClient: FusedLocationProviderClient
    var latitude = ""
    var longitude = ""
    val args: EditAddressFragmentArgs by navArgs()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

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
        val address = args.safeArgAddress
        binding.TextInputAddressName2.editText?.setText(address.name)
        binding.TextInputAddressField2.editText?.setText(address.addressField)
        binding.buttonEditAddress.setOnClickListener {
            val newAddress = Address(
                address.id,
                binding.TextInputAddressName2.editText?.text.toString(),
                binding.TextInputAddressField2.editText?.text.toString(),
                latitude, longitude
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
        }

    }


    @RequiresApi(Build.VERSION_CODES.N)
    private fun getLocationPermission() {
        val locationPermissionRequest =
            registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) { permissions ->
                when {
                    permissions.getOrDefault(Manifest.permission.ACCESS_FINE_LOCATION, false) -> {
                        showLocation()
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

    @SuppressLint("MissingPermission")
    private fun showLocation() {
        if (ActivityCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            return
        }
        fusedLocationClient.getCurrentLocation(Priority.PRIORITY_HIGH_ACCURACY, null)
            .addOnSuccessListener { location: Location? ->
                location?.let {
                    showLocationOnMap(LatLng(it.latitude, it.longitude))
                    latitude = it.latitude.toString()
                    longitude = it.longitude.toString()
                }
            }


    }


    private fun showLocationOnMap(latLng: LatLng) {
        map.setMinZoomPreference(2.0f)
        map.setMaxZoomPreference(18.0f)
        map.cameraPosition.zoom
        map.addMarker(
            MarkerOptions()
                .position(latLng)
                .title("Marker in location")
                .zIndex(2.0f)
        )
        map.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 20.0f))
    }
}