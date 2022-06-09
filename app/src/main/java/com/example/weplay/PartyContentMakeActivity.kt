package com.example.weplay

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.weplay.databinding.ActivityPartyContentMakeBinding
import com.example.weplay.domain.Party
import com.google.android.gms.maps.model.LatLng
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class PartyContentMakeActivity : AppCompatActivity() {

    private lateinit var binding: ActivityPartyContentMakeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPartyContentMakeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initLayout()
    }

    private fun initLayout() {
        with(binding) {
            val title = inputPartyTitle.text.toString()
            val content = inputPartyContent.text.toString()

            val participantsNum = intent.getIntExtra("person", 0)
            val pSports = intent.getStringExtra("field")
            val latitude = intent.getDoubleExtra("latitude", 0.0)
            val longitude = intent.getDoubleExtra("longitude", 0.0)


            val result = Firebase.database.getReference("Parties/party")
                .get()

            result.addOnSuccessListener {
//                Log.i("크기", result.result.childrenCount.toString())

                partyCreateBtn.setOnClickListener {
                    val newParty = Party(
                        result.result.childrenCount.toInt(),
                        "없음", title, 0, participantsNum, HashMap(), content,
                        latitude, longitude, pSports!!, "없음"
                    )
                    Firebase.database.getReference("Parties/party/${result.result.childrenCount.toInt()}")
                        .setValue(newParty)

                    finish()
                }
            }


        }
    }
}