package com.example.digikalasample.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.digikalasample.R
import com.example.digikalasample.databinding.FragmentInsertAddressBinding
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions

class InsertAddressFragment : Fragment() {
    private lateinit var binding: FragmentInsertAddressBinding
    private lateinit var map: GoogleMap
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentInsertAddressBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val mapFragment = childFragmentManager
            .findFragmentById(R.id.myMap) as SupportMapFragment
        mapFragment.getMapAsync(OnMapReadyCallback { readyMap ->
            map = readyMap
            showLocationOnMap(LatLng(37.4219983, 122.084))

        })


    }

    private fun showLocationOnMap(latLng: LatLng) {
        map.setMinZoomPreference(3.0f)
        map.setMaxZoomPreference(14.0f)
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