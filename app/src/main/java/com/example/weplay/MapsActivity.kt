package com.example.weplay

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.weplay.databinding.ActivityMapsBinding
import com.google.android.gms.maps.*
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import kotlinx.android.synthetic.main.activity_maps.*


class MapsActivity : AppCompatActivity() {
    lateinit var binding: ActivityMapsBinding

    private lateinit var mMap: GoogleMap
    private lateinit var location : LatLng
    private val baseCityLatLng = HashMap<String, LatLng>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMapsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initData()
        initMap()
        initLayout()
    }

    private fun initData() {
        baseCityLatLng["강남구"] = LatLng(37.518421, 127.047222)
        baseCityLatLng["강동구"] = LatLng(37.530492, 127.123837)
        baseCityLatLng["강북구"] = LatLng(37.639938, 127.025508)
        baseCityLatLng["강서구"] = LatLng(37.551166, 126.849506)
        baseCityLatLng["관악구"] = LatLng(37.478290, 126.951502)
        baseCityLatLng["광진구"] = LatLng(37.538712, 127.082366)
        baseCityLatLng["구로구"] = LatLng(37.495632, 126.887650)
        baseCityLatLng["금천구"] = LatLng(37.456852, 126.895229)
        baseCityLatLng["노원구"] = LatLng(37.654259, 127.056294)
        baseCityLatLng["도봉구"] = LatLng(37.668952, 127.047082)
        baseCityLatLng["동대문구"] = LatLng(37.574552, 127.039721)
        baseCityLatLng["마포구"] = LatLng(37.566283, 126.901644)
        baseCityLatLng["서대문구"] = LatLng(37.579428, 126.936771)
        baseCityLatLng["서초구"] = LatLng(37.483804, 127.032693)
        baseCityLatLng["성동구"] = LatLng(37.563277, 127.036647)
        baseCityLatLng["성북구"] = LatLng(37.589562, 127.016700)
        baseCityLatLng["송파구"] = LatLng(37.514620, 127.106141)
        baseCityLatLng["양천구"] = LatLng(37.517189, 126.866618)
        baseCityLatLng["영등포구"] = LatLng(37.526505, 126.896190)
        baseCityLatLng["종로구"] = LatLng(37.570028, 126.979621)
        baseCityLatLng["중구"] = LatLng(37.563442, 126.997386)
        baseCityLatLng["용산구"] = LatLng(37.532185, 126.990278)
        baseCityLatLng["중랑구"] = LatLng(37.606261, 127.093048)
        baseCityLatLng["은평구"] = LatLng(37.602175, 126.929337)
        baseCityLatLng["동작구"] = LatLng(37.500016, 126.942841)
    }

    private fun initMap() {
        var loc = LatLng(37.5436, 127.0770)
        val baseCity = intent.getStringExtra("pcity")

        if (baseCityLatLng[baseCity] != null) {
            loc = baseCityLatLng[baseCity]!!
        }

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

        }

    }

    private fun initLayout() {
        val detailedPlace = inputDetailedPlace.text

        binding.mapbtn.setOnClickListener {
            if (::location.isInitialized && detailedPlace.isNotEmpty()) {
                val intent = Intent(this, PartyContentMakeActivity::class.java).apply {
                    putExtra("latitude", location.latitude)
                    putExtra("longitude", location.longitude)
                    putExtra("pplace", detailedPlace)
                    putExtra("person", intent.getIntExtra("person", 0))
                    putExtra("field", intent.getStringExtra("field"))
                    putExtra("pdate", intent.getLongExtra("pdate", 0))
                    putExtra("pcity", intent.getStringExtra("pcity"))
                }
                startActivity(intent)
            }

        }
    }

}