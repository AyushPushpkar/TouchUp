package com.example.touchup

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.example.touchup.databinding.ActivityGoogleMapsBinding

class GoogleMapsActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var mMap: GoogleMap
    private lateinit var binding: ActivityGoogleMapsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityGoogleMapsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val toolbar = binding.toolbar
        setSupportActionBar(toolbar)

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
    }

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap

        // Add a marker in Sydney and move the camera
        val Hazaribag = LatLng(23.994001824899378, 85.36323212998391)
        mMap.addMarker(MarkerOptions().position(Hazaribag).title("Marker in Hazaribag"))
        mMap.moveCamera(CameraUpdateFactory.newLatLng(Hazaribag))
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.menu,menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        when(item.itemId){

            R.id.home ->{
                intent = Intent(this@GoogleMapsActivity,MainActivity::class.java)
                startActivity(intent)
            }
            R.id.hybrid ->{
                mMap.mapType = GoogleMap.MAP_TYPE_HYBRID
            }
            R.id.normal ->{
                mMap.mapType = GoogleMap.MAP_TYPE_NORMAL
            }
            R.id.satellite ->{
                mMap.mapType = GoogleMap.MAP_TYPE_SATELLITE
            }
            R.id.terrain ->{
                mMap.mapType = GoogleMap.MAP_TYPE_TERRAIN
            }

        }
        return true
    }
}