package com.example.weplay

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.weplay.databinding.ActivityPartyContentMakeBinding
import com.example.weplay.domain.ParticipantInfo
import com.example.weplay.domain.Party
import com.example.weplay.party.MainActivity
import com.google.android.gms.maps.model.LatLng
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class PartyContentMakeActivity : AppCompatActivity() {

    private lateinit var binding: ActivityPartyContentMakeBinding
    private lateinit var auth: FirebaseAuth
    private lateinit var user: FirebaseUser
    private lateinit var userName: String


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPartyContentMakeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initUser()
        initLayout()
    }

    private fun initUser() {
        auth = Firebase.auth
        user = auth.currentUser!!

        Firebase.database.getReference("Users/${user.uid}").get().addOnSuccessListener {
            userName = it.child("nickName").value.toString()
        }
    }

    private fun initLayout() {
        with(binding) {

            val participantsNum = intent.getIntExtra("person", 0)
            val pSports = intent.getStringExtra("field")
            val latitude = intent.getDoubleExtra("latitude", 0.0)
            val longitude = intent.getDoubleExtra("longitude", 0.0)
            val pdate = intent.getLongExtra("pdate", 0)
            val pcity = intent.getStringExtra("pcity")
            val pplace = intent.getStringExtra("pplace")

            partyCreateBtn.setOnClickListener {
                val result = Firebase.database.getReference("Parties/party")
                    .get()

                result.addOnSuccessListener {
                    val title = inputPartyTitle.text.toString()
                    val content = inputPartyContent.text.toString()

                    if (title.isNotEmpty() && content.isNotEmpty()) {
                        val newParty = Party(
                            result.result.childrenCount.toInt(),
                            pplace!!, title, pdate, participantsNum, HashMap(), content,
                            latitude, longitude, pSports!!, pcity!!
                        )
                        Firebase.database.getReference("Parties/party/${result.result.childrenCount.toInt()}")
                            .setValue(newParty)

                        Firebase.database.getReference("Parties/party/${result.result.childrenCount.toInt()}")
                            .child("pparticipants/${user.uid}")
                            .setValue(ParticipantInfo(user.uid, userName))

                        val intent = Intent(this@PartyContentMakeActivity, MainActivity::class.java)
                        startActivity(intent)
                    }
                }


            }

        }
    }
}