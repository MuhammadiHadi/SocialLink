package com.example.sociallink.ui.fragment



import android.location.Geocoder
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.sociallink.R
import com.example.sociallink.databinding.FragmentLocationBinding
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.MapView
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions

class LocationFragment : Fragment(), OnMapReadyCallback {
      private var _binding:FragmentLocationBinding?=null
       private val binding get() =_binding!!
   var isZoomedIn:Boolean = false

    private lateinit var mapView: MapView
    private lateinit var googleMap: GoogleMap
    private lateinit var fusedLocationClient: FusedLocationProviderClient

    override fun onCreateView(
        inflater : LayoutInflater, container : ViewGroup? ,
        savedInstanceState : Bundle?,
    ) : View {
        _binding=FragmentLocationBinding.inflate(inflater,container,false)
        mapView = binding.root.findViewById(R.id.mapView)
        binding.ivArrow.setOnClickListener{
            backView()
        }

        mapView.onCreate(savedInstanceState)
        mapView.getMapAsync(this)
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(requireActivity())

      binding.Button.setOnClickListener {
          val currentZoom = googleMap.cameraPosition.zoom
          googleMap.moveCamera(CameraUpdateFactory.zoomTo(20f))
          if (isZoomedIn) {
              // Zoom out if already zoomed in
              googleMap.animateCamera(CameraUpdateFactory.zoomOut())
              isZoomedIn = false
          } else {
              // Zoom in if not already zoomed in
              googleMap.animateCamera(CameraUpdateFactory.zoomIn())
              isZoomedIn = true
          }
      }

        return binding.root
    }

 private   fun backView():Boolean{
     findNavController().popBackStack()
     return true
 }

    override fun onResume() {
        super.onResume()
        mapView.onResume()
    }


    override fun onLowMemory() {
        super.onLowMemory()
        mapView.onLowMemory()
    }

    override fun onMapReady(map: GoogleMap) {
        googleMap = map

        val currentLocation = LatLng(31.4592, 74.2764)
      val marker=  googleMap.addMarker(
            MarkerOptions()
                .position(currentLocation)
                .title("MYTM")

        )
        marker!!.showInfoWindow()
        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(currentLocation, 15f))


    }



}


