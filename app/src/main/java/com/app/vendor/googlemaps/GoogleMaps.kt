package com.app.vendor.googlemaps

import android.Manifest
import android.content.pm.PackageManager
import android.location.Location
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.app.ActivityCompat
import com.app.vendor.R
import com.app.vendor.base.App
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.*
import com.google.android.gms.tasks.OnSuccessListener
import com.google.android.gms.tasks.Task
import com.karumi.dexter.Dexter
import com.karumi.dexter.MultiplePermissionsReport
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.PermissionDeniedResponse
import com.karumi.dexter.listener.PermissionGrantedResponse
import com.karumi.dexter.listener.PermissionRequest
import com.karumi.dexter.listener.multi.MultiplePermissionsListener
import com.karumi.dexter.listener.single.PermissionListener
import io.nlopez.smartlocation.OnLocationUpdatedListener
import io.nlopez.smartlocation.SmartLocation
import com.google.android.gms.maps.model.RoundCap
import android.location.LocationManager


class GoogleMaps : AppCompatActivity(), OnMapReadyCallback, GoogleMap.OnMarkerClickListener {

    private lateinit var map: GoogleMap
    private lateinit var client: FusedLocationProviderClient
private lateinit var locationManager:LocationManager
private lateinit var location:Location
 var   LOCATION_REQUEST_CODE=1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_google_maps)

        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)

    client = LocationServices.getFusedLocationProviderClient(this)
        locationPermission()

    }

    private fun locationPermission() {
        Dexter.withActivity(this)
            .withPermissions(android.Manifest.permission.ACCESS_FINE_LOCATION,
                android.Manifest.permission.ACCESS_COARSE_LOCATION)
            .withListener(object : MultiplePermissionsListener {
                override fun onPermissionsChecked(p0: MultiplePermissionsReport?) {
                 //getMyLocation()
                }

                override fun onPermissionRationaleShouldBeShown(
                    p0: MutableList<PermissionRequest>?,
                    p1: PermissionToken?
                ) {
                    p1?.continuePermissionRequest()
                }

            })
            }



    private fun getMyLocation() {
     /*  var c1:Boolean = SmartLocation.with(App.INSTANCE).location().state().locationServicesEnabled();
        var c2:Boolean  = SmartLocation.with(App.INSTANCE).location().state().isAnyProviderAvailable();
        var c3:Boolean  = SmartLocation.with(App.INSTANCE).location().state().isGpsAvailable();
        var d:Boolean  = c1 && c2 && c3
        if (c1 && c2 && c3) {
            SmartLocation.with(App.INSTANCE).location().start {

            }
        }*/


        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                this, arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                LOCATION_REQUEST_CODE
            )
        }
        map.isMyLocationEnabled=true
       client.lastLocation.addOnSuccessListener { location ->
            if (location != null) {
              //  lastlocation = location
                val currentlocation = LatLng(location.latitude, location.longitude)
                placeMarkerOnCurrentLocation(currentlocation)
                map.animateCamera(CameraUpdateFactory.newLatLngZoom(currentlocation, 10f))
            }
        }

        }
    private fun placeMarkerOnCurrentLocation(currentlocation: LatLng) {
        val markerOptions = MarkerOptions().position(currentlocation)
        markerOptions.title("$currentlocation")
        map.addMarker(markerOptions)
    }

    override fun onMapReady(googleMap: GoogleMap) {
        map = googleMap
        map.setOnMarkerClickListener(this)

        // Add a marker in India and move the camera
        val india = LatLng(20.6578, 78.9629)
     //  map.addMarker(MarkerOptions().position(india).title("Marker in India"))
       // map.moveCamera(CameraUpdateFactory.newLatLng(india))
        //map.animateCamera(CameraUpdateFactory.zoomTo(2f))

        getMyLocation()
       // polygon()
        //polyLine()
    }

    override fun onMarkerClick(p0: Marker?):Boolean=false

/*
    private fun polyLine() {
        val polyline: Polyline = map.addPolyline(
            PolylineOptions()
                .clickable(true)
                .add(
                    LatLng(28.6578, 77.9629),
                    LatLng(28.747, 77.592),
                    LatLng(28.364, 77.891),
                    LatLng(28.501, 77.217),
                    LatLng(28.306, 77.248),
                    LatLng(28.491, 77.309),

                )
        )

        map.moveCamera(CameraUpdateFactory.newLatLngZoom(LatLng(20.6578, 78.9629), 10f))
        polyline.endCap = RoundCap()
    }

    private fun polygon() {

        val polygon: Polygon = map.addPolygon(
         PolygonOptions()
                .clickable(true)
                .add(
                    LatLng(20.6578, 78.9629),
                    LatLng(24.747, 80.592),
                    LatLng(24.364, 81.891),
                    LatLng(23.501, 84.217),
                    LatLng(22.306, 86.248),
                    LatLng(22.491, 87.309),
                    LatLng(20.6578, 78.9629)
                )
        )

      map.moveCamera(CameraUpdateFactory.newLatLngZoom(LatLng(20.6578, 78.9629), 6f))

    }
*/


}



