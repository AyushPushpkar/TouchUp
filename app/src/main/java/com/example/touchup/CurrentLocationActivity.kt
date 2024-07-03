package com.example.touchup

import android.content.Intent
import android.content.pm.PackageManager
import android.location.Address
import android.location.Geocoder
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.touchup.databinding.ActivityCurrentLocationBinding
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import java.util.Locale
import android.provider.Settings
import androidx.core.app.ActivityCompat
import android.Manifest
import android.content.Context
import android.location.LocationManager

class CurrentLocationActivity : AppCompatActivity() {

    private val binding : ActivityCurrentLocationBinding by lazy {
        ActivityCurrentLocationBinding.inflate(layoutInflater)
    }
    private lateinit var mFusedLocationClient: FusedLocationProviderClient
    val permissionId = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this)

        binding.button2.setOnClickListener {
            getLocation()
        }

    }

    private fun getLocation(){

        if(checkPermission()){
            if(isLocationEnabled()){
                mFusedLocationClient.lastLocation.addOnCompleteListener(this){ task ->
                    val location = task.result
                    if(location != null){
                        val geocoder = Geocoder(this, Locale.getDefault())
                        val list: List<Address>? = geocoder.getFromLocation(location.latitude, location.longitude, 1)
                        binding.apply {
                            latitude.text = "Latitude\n${list?.get(0)?.latitude}"
                            longitude.text = "Longitude\n${list?.get(0)?.longitude}"
                            country.text = "Country\n${list?.get(0)?.countryName}"
                            locality.text = "Locality\n${list?.get(0)?.locality}"
                            address.text = "Address\n${list?.get(0)?.getAddressLine(0)}"
                        }

                    }
                }
            } else{
                Toast.makeText(this, "Please turn on Location", Toast.LENGTH_SHORT).show()
                intent = Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS)
                startActivity(intent)
            }
        } else{
            requestPermission()
        }

    }
    private fun checkPermission(): Boolean {
        if(ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED &&
            ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED)
            return true

        return false
    }

    private fun isLocationEnabled(): Boolean {
        val locationManager : LocationManager = getSystemService(Context.LOCATION_SERVICE) as LocationManager

        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) || locationManager.isProviderEnabled(
            LocationManager.NETWORK_PROVIDER
        )
    }

    private fun requestPermission() {
        ActivityCompat.requestPermissions(this,
            arrayOf(Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION),permissionId)
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == permissionId){
            if(grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                getLocation()
            }
        }
    }


}