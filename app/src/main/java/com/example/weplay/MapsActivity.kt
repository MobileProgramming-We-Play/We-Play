package com.example.weplay

import android.content.Intent
import android.location.Location
import android.location.LocationListener
import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.weplay.databinding.ActivityMapsBinding
import com.example.weplay.party.MainActivity
import com.google.android.gms.maps.*
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_maps.*


class MapsActivity : AppCompatActivity() {
    lateinit var binding: ActivityMapsBinding
    lateinit var user: FirebaseUser
    lateinit var userList: DatabaseReference    //파이어베이스 접근하기 위한 객체 생성
    var firebaseDatabase = FirebaseDatabase.getInstance()
    var databaseReference: DatabaseReference = firebaseDatabase.getReference().child("GPS정보")

    private lateinit var mMap: GoogleMap
    private lateinit var mapView: MapView
    private lateinit var location : LatLng

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMapsBinding.inflate(layoutInflater)
        setContentView(binding.root)

//        mapView = binding.clKakaoMapView
       /* mapView.onCreate(savedInstanceState)
        mapView.getMapAsync(this)*/
        initMap()
        initLayout()

        binding.mapbtn.setOnClickListener {

            if (::location.isInitialized) {
                val intent = Intent(this, PartyContentMakeActivity::class.java).apply {
                    putExtra("latitude", location.latitude)
                    putExtra("longitude", location.longitude)
                    putExtra("person", intent.getIntExtra("person", 0))
                    putExtra("field", intent.getStringExtra("field"))
                }
                startActivity(intent)
            }

        }
    }

    private fun initMap() {
        val loc = LatLng(37.5436, 127.0770)

        val mapFragment =
            supportFragmentManager.findFragmentById(R.id.clKakaoMapView) as SupportMapFragment
        mapFragment.getMapAsync {
            mMap = it
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(loc, 16.0f))

            mMap.uiSettings.isZoomControlsEnabled = true

            mMap.setOnMapClickListener { itLatLng ->
                val options = MarkerOptions()
                options.title("선택한 좌표")
                val lat = itLatLng.latitude
                val lng = itLatLng.longitude
                location = LatLng(lat, lng)
                options.position(location)
                mMap.addMarker(options)
            }

            /*val option = MarkerOptions()
            option.position(loc)
            option.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN))
            option.title("위치")
            option.snippet("모임장소")
            mMap.addMarker(option)?.showInfoWindow()*/

//            googleMap.uiSettings.setAllGesturesEnabled(false)

        }

    }

    private fun initLayout() {
        user = Firebase.auth.currentUser!!
        userList = Firebase.database.getReference("Parties/party")
        val id = user.uid

        userList.child(id).get().addOnSuccessListener {
            binding.apply {
            }
        }
    }

    val gpsLocationListener: LocationListener = object : LocationListener {
        var gpsResult: TextView? = null
        override fun onLocationChanged(location: Location) {
            val provider: String = location.getProvider()
            val longitude: Double = location.getLongitude()
            val latitude: Double = location.getLatitude()
            val altitude: Double = location.getAltitude()
            gpsResult?.setText(
                """
                        위치정보 : $provider
                        위도 : $longitude
                        경도 : $latitude
                        고도  : $altitude
                        """.trimIndent()
            )
            databaseReference.child(user.toString()).child("경도").setValue(longitude);
            databaseReference.child(user.toString()).child("위도").setValue(latitude);
        }

        override fun onStatusChanged(provider: String?, status: Int, extras: Bundle?) {}
    }

    /*override fun onMapReady(googleMap: GoogleMap) {
        mMap =googleMap
        mMap.uiSettings.isZoomControlsEnabled = true

        mMap.setOnMapClickListener {
            val options = MarkerOptions()
            options.title("선택한 좌표")
            val lat = it.latitude
            val lng = it.longitude
            location = LatLng(lat, lng)
            options.position(location)
            mMap.addMarker(options)
        }
    }*/
}