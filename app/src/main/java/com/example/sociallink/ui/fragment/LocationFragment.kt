package com.example.sociallink.ui.fragment



import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.pm.ApplicationInfo
import android.content.pm.PackageManager
import android.location.LocationManager
import android.os.Bundle
import android.os.Looper
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.app.ActivityCompat
import com.example.sociallink.R
import com.example.sociallink.databinding.FragmentLocationBinding
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.*
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.libraries.places.api.Places
import android.content.Context.*
import android.content.Intent
import android.location.Location
import android.provider.Settings
import android.widget.ImageView
import com.google.android.gms.location.*
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.SupportMapFragment



class LocationFragment : Fragment(), OnMapReadyCallback {
      private var _binding:FragmentLocationBinding?=null
       private val binding get() =_binding!!

    private val pERMISSION_ID = 42
    lateinit var mFusedLocationClient: FusedLocationProviderClient
    lateinit var mMap: GoogleMap


    // Current location is set to India, this will be of no use
    var currentLocation: LatLng = LatLng(30.3753,69.3451)

    override fun onCreateView(
        inflater : LayoutInflater, container : ViewGroup? ,
        savedInstanceState : Bundle?,
    ) : View {

        _binding=FragmentLocationBinding.inflate(inflater,container,false)
        val ai: ApplicationInfo = requireContext().packageManager
            .getApplicationInfo(requireContext().packageName, PackageManager.GET_META_DATA)
        val value = ai.metaData["com.google.android.geo.API_KEY"]
        val apiKey = value.toString()

        // Initializing the Places API with the help of our API_KEY
        if (!Places.isInitialized()) {
            Places.initialize(requireContext(), apiKey)
        }

        // Initializing Map
        val mapFragment =childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)

        // Initializing fused location client
        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(requireActivity())

        // Adding functionality to the button
        val btn =binding.root.findViewById<ImageView>(R.id.Button)
        btn.setOnClickListener {
            getLastLocation()
        }


        return binding.root
    }

    // Services such as getLastLocation()
    // will only run once map is ready
    override fun onMapReady(p0: GoogleMap) {
        mMap = p0
        getLastLocation()
    }

    // Get current location
    @SuppressLint("MissingPermission")
    private fun getLastLocation() {
        if (checkPermissions()) {
            if (isLocationEnabled()) {

                mFusedLocationClient.lastLocation.addOnCompleteListener(requireActivity()) { task ->
                    val location: Location? = task.result
                    if (location == null) {
                        requestNewLocationData()
                    } else {
                        currentLocation = LatLng(location.latitude, location.longitude)
                        mMap.clear()
                     val marker=  mMap.addMarker(MarkerOptions().position(currentLocation).title("Current location"))
                        marker!!.showInfoWindow()
                        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(currentLocation, 16F))

                    }
                }
            } else {
                Toast.makeText(requireContext(), "Turn on location", Toast.LENGTH_LONG).show()
                val intent = Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS)
                startActivity(intent)
            }
        } else {
            requestPermissions()
        }
    }

    // Get current location, if shifted
    // from previous location
    @SuppressLint("MissingPermission")
    private fun requestNewLocationData() {
        val mLocationRequest = LocationRequest()
        mLocationRequest.priority = LocationRequest.PRIORITY_HIGH_ACCURACY
        mLocationRequest.interval = 0
        mLocationRequest.fastestInterval = 0
        mLocationRequest.numUpdates = 1

        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(requireActivity())
        mFusedLocationClient.requestLocationUpdates(
            mLocationRequest, mLocationCallback,
            Looper.myLooper()
        )
    }

    // If current location could not be located, use last location
    private val mLocationCallback = object : LocationCallback() {
        override fun onLocationResult(locationResult: LocationResult) {
            val mLastLocation: Location? = locationResult.lastLocation
            if (mLastLocation != null) {
                currentLocation = LatLng(mLastLocation.latitude, mLastLocation.longitude)
            }
        }
    }

    // function to check if GPS is on
    private fun isLocationEnabled(): Boolean {
        val locationManager: LocationManager = requireContext().getSystemService(Context.LOCATION_SERVICE) as LocationManager
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) || locationManager.isProviderEnabled(
            LocationManager.NETWORK_PROVIDER
        )
    }

    // Check if location permissions are
    // granted to the application
    private fun checkPermissions(): Boolean {
        if (ActivityCompat.checkSelfPermission(requireActivity(), Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED &&
            ActivityCompat.checkSelfPermission(requireActivity(), Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED
        ) {
            return true
        }
        return false
    }

    // Request permissions if not granted before
    private fun requestPermissions() {
        ActivityCompat.requestPermissions(
            requireActivity(),
            arrayOf(Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION),
            pERMISSION_ID
        )
    }

    // What must happen when permission is granted
    @Deprecated("Deprecated in Java")
    override fun onRequestPermissionsResult(requestCode: Int , permissions: Array<String> , grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode , permissions , grantResults)
        if (requestCode == pERMISSION_ID) {
            if ((grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED)) {
                getLastLocation()
            }
        }
    }
}


