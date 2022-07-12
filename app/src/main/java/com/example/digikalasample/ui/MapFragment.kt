package com.example.digikalasample.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.example.digikalasample.R
import com.example.digikalasample.data.model.address.Address
import com.example.digikalasample.databinding.FragmentMapBinding
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions


class MapFragment : Fragment() {
    private lateinit var binding: FragmentMapBinding
    private lateinit var map: GoogleMap
    private var currentMarker: Marker? = null
    private val args: MapFragmentArgs by navArgs()
    lateinit var address: Address


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMapBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        address = args.addressSafeArgMap
        val mapFragment = childFragmentManager
            .findFragmentById(R.id.myMap) as SupportMapFragment
        mapFragment.getMapAsync { readyMap ->
            map = readyMap
            showLocationOnMap(LatLng(address.theLat.toDouble(), address.theLong.toDouble()))
        }

    }

    private fun showLocationOnMap(latLng: LatLng) {
        map.setMinZoomPreference(2.0f)
        map.setMaxZoomPreference(18.0f)
        map.cameraPosition.zoom
        val markerOption =
            MarkerOptions()
                .position(latLng)
                .zIndex(2.0f)

        map.moveCamera(CameraUpdateFactory.newLatLng(latLng))
        map.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 20.0f))
        currentMarker = map.addMarker(markerOption)
        currentMarker?.showInfoWindow()

    }

}