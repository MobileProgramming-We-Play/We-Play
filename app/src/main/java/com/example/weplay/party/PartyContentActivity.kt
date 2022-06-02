package com.example.weplay.party

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.view.setMargins
import com.example.weplay.R
import com.example.weplay.databinding.ActivityPartyContentBinding
import com.example.weplay.domain.ParticipantInfo
import com.example.weplay.domain.Party
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import kotlin.math.roundToInt

class PartyContentActivity : AppCompatActivity() {

    private lateinit var binding: ActivityPartyContentBinding
    private lateinit var googleMap: GoogleMap
    private lateinit var party: Party
    private lateinit var auth: FirebaseAuth
    private lateinit var user: FirebaseUser
    private lateinit var userName: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPartyContentBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initUser()
        initData()
        initMap()
    }

    private fun initUser() {
        auth = Firebase.auth
        user = auth.currentUser!!

        Firebase.database.getReference("Users/${user.uid}").get().addOnSuccessListener {
            userName = it.child("nickName").value.toString()
        }
    }

    @SuppressLint("SetTextI18n")
    private fun initData() {
        party = intent.getSerializableExtra("party") as Party

        with(binding) {
            val participants = party.pparticipants

            partyTitle.text = party.ptitle
            partyPlace.text = party.pplace
            partyContent.text = party.pcontent
            partyDate.text = party.pdate.toString()
            partyParticipantsNum.text = "확정인원: ${participants.size}/${party.pparticipantsNum}"


            Log.i("참가자수: ", participants.size.toString())

            for (participant in participants) {
                val textView = TextView(this@PartyContentActivity)
                textView.text = participant.uname
                /*textView.layoutParams = ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.WRAP_CONTENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT,
                )*/
                textView.layoutParams = ViewGroup.MarginLayoutParams(
                    ViewGroup.LayoutParams.WRAP_CONTENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT,
                )

                val marginLayoutParams = textView.layoutParams as ViewGroup.MarginLayoutParams
                marginLayoutParams.setMargins(dpToPx(20, this@PartyContentActivity))


                partyParticipants.addView(textView)
            }

            if (participants.size == party.pparticipantsNum ||
                    participants.stream()
                        .filter {
                            it?.uid == user.uid
                        }
                        .count()
                        .toInt() != 0
            ) {
                partyJoinBtn.isEnabled = false
                partyJoinBtn.isClickable = false
            } else {
                val index = intent.getStringExtra("firebaseIndex")
                partyJoinBtn.setOnClickListener {
                    Firebase.database.getReference("$index")
                        .child("pparticipants/${participants.size}")
                        .setValue(ParticipantInfo(user.uid, userName))

                    partyJoinBtn.isEnabled = false
                    partyJoinBtn.isClickable = false
                }
            }


        }
    }

    private fun dpToPx(dp: Int, context: Context): Int {
        val density = context.resources.displayMetrics.density
        return (dp.toFloat() * density).roundToInt()
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

//            googleMap.uiSettings.setAllGesturesEnabled(false)

        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
        //startActivity(Intent(this, PartiesActivity::class.java))
        finish()
    }
}