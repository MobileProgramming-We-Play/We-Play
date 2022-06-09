package com.example.weplay

import android.location.Location
import android.location.LocationListener
import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.weplay.databinding.ActivityMapsBinding
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.LatLng
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class MapsActivity : AppCompatActivity() {
    lateinit var binding: ActivityMapsBinding
    lateinit var user: FirebaseUser
    lateinit var userList: DatabaseReference    //파이어베이스 접근하기 위한 객체 생성
    var firebaseDatabase = FirebaseDatabase.getInstance()
    var databaseReference: DatabaseReference = firebaseDatabase.getReference().child("GPS정보")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_maps)
        initLayout()
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

    fun onMapReady(googleMap: GoogleMap?) {
        val seoul = LatLng(37.566, 126.978)
        googleMap?.moveCamera(CameraUpdateFactory.newLatLng(seoul))
        googleMap?.moveCamera(CameraUpdateFactory.zoomTo(10f))
    }
}