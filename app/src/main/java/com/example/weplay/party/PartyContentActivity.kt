package com.example.weplay.party

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.ViewGroup
import android.widget.TextView
import com.example.weplay.R
import com.example.weplay.databinding.ActivityPartyContentBinding
import com.example.weplay.domain.Party
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions

class PartyContentActivity : AppCompatActivity() {

    private lateinit var binding: ActivityPartyContentBinding
    private lateinit var googleMap: GoogleMap
    private lateinit var party: Party

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPartyContentBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initData()
        initMap()
    }

    private fun initData() {
        party = intent.getSerializableExtra("party") as Party

        with(binding) {
            partyTitle.text = party.ptitle
            partyContent.text = party.pcontent
            partyDate.text = party.pdate.toString()
            partyParticipantsNum.text = party.pparticipantsNum.toString()
//            partyPlace.text = party.pp
            val participants = party.pparticipants

            for (participant in participants) {
                if (participant != null) {
                    val textView = TextView(this@PartyContentActivity)
                    textView.text = participant.nickName
                    textView.layoutParams = ViewGroup.LayoutParams(
                        ViewGroup.LayoutParams.WRAP_CONTENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT
                    )
                    partyParticipants.addView(textView)
                }

            }

            partyJoinBtn.setOnClickListener {

            }
        }
    }

    private fun initMap() {
        val loc = LatLng(party.platitude, party.plongitude)

        val mapFragment =
            supportFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync {
            googleMap = it
            googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(loc, 16.0f))

            val option = MarkerOptions()
            option.position(loc)
            option.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN))
            option.title("위치")
            option.snippet("모임장소")
            googleMap.addMarker(option)?.showInfoWindow()

            googleMap.uiSettings.setAllGesturesEnabled(false)

        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
        startActivity(Intent(this, PartiesActivity::class.java))
    }
}